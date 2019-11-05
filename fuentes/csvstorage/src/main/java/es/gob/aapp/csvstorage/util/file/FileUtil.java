/*
 * Copyright (C) 2012-13 MINHAP, Gobierno de España This program is licensed and may be used,
 * modified and redistributed under the terms of the European Public License (EUPL), either version
 * 1.1 or (at your option) any later version as soon as they are approved by the European
 * Commission. Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * more details. You should have received a copy of the EUPL1.1 license along with this program; if
 * not, you may find it at http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 */

package es.gob.aapp.csvstorage.util.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import es.gob.aapp.csvstorage.services.exception.ValidationException;



/**
 * Clase de utilidades para ficheros.
 * 
 * @author carlos.munoz1
 * 
 */
public class FileUtil {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(FileUtil.class);
  private static final String SEPARATOR = System.getProperty("file.separator");
  private static final String FILE = "File: ";
  private static final String SE_HA_PRODUCIDO_UN_ERROR_AL_GENERAR_EL_HASH_DEL_DOCUMENTO =
      "Se ha producido un error al generar el hash del documento";

  private FileUtil() {}

  /**
   * Método para descomprimir un fichero en formato ZIP. Si se indica outputFolder, se crea y se
   * descomprime en ese directorio.
   * 
   * @param zipFile Cadena con Ruta + Nombre de fichero a descomprimir
   * @param outputFolder Directorio donde descomprimir.
   */
  public static void unZipFile(String zipFile, String outputFolder) throws IOException {

    byte[] buffer = new byte[1024];
    ZipInputStream zis = null;
    FileInputStream fInputStream = null;
    FileOutputStream fos = null;

    try {

      // create output directory if not exists
      File folder = new File(outputFolder);
      folder.mkdir();

      // get the zip file content
      fInputStream = new FileInputStream(zipFile);
      zis = new ZipInputStream(fInputStream);
      // get the zipped file list entry
      ZipEntry ze = zis.getNextEntry();

      while (ze != null) {

        String fileName = ze.getName();

        File newFile = new File(outputFolder + File.separator + fileName);

        LOG.debug("file unzip : " + newFile.getAbsoluteFile());

        // create all non exists folders else you will hit FileNotFoundException for compressed
        // folder
        new File(newFile.getParent()).mkdirs();

        fos = new FileOutputStream(newFile);

        int len;
        while ((len = zis.read(buffer)) > 0) {
          fos.write(buffer, 0, len);
        }

        fos.close();

        ze = zis.getNextEntry();
      }

      zis.closeEntry();
      zis.close();

      LOG.info("Fichero descomprimido correctamente. ");

    } finally {
      IOUtils.closeQuietly(zis);
      IOUtils.closeQuietly(fInputStream);
      IOUtils.closeQuietly(fos);
    }

  }

  /**
   * Método para borrar el fichero definido en la cadena pasada como //NOSONAR parámetro, si existe.
   * 
   * @param pathFile
   */
  public static void deleteFile(String pathFile) throws IOException {
    LOG.info("deleteFile ini");
    boolean isDeleted = false;
    File file = new File(pathFile);
    if (file.exists()) {
      isDeleted = file.delete();
      if (!isDeleted) {
        LOG.error("El documento no se ha podido borrar:" + pathFile);
        throw new IOException("Se ha producido un error al borrar el fichero:" + file.getName());
      }
    }

    LOG.info("deleteFile end");
  }

  /**
   * Crea un fichero en el recurso NAS cuyo nombre recibe por parametro.
   * 
   * @param path Ruta del recurso NAS
   * @param name nombre del documento
   * @param content Contenido del fichero
   */
  public static void saveFile(String path, String name, byte[] content) throws IOException {

    LOG.info(String.format("Escribiendo fichero en la NAS path=%s; name=%s", path, name));

    File directory = new File(path);
    FileOutputStream fOutputStream = null;
    if (!directory.exists() && !directory.mkdirs()) {
      throw new IOException("No se ha podido crear la carpeta=" + directory);
    }

    String filePath;
    if (path.endsWith("/") || path.endsWith("\\")) {
      filePath = path + name;
    } else {
      filePath = path + SEPARATOR + name;
    }
    try { // NOSONAR
      File file = new File(filePath);
      LOG.info(FILE + filePath);
      if (!file.exists() && !file.createNewFile()) {
        throw new IOException("No se ha podido crear el fichero " + file);
      }
      fOutputStream = new FileOutputStream(file, false);
      fOutputStream.write(content);
      fOutputStream.flush();
      fOutputStream.close();

    } finally {
      IOUtils.closeQuietly(fOutputStream);
    }

    LOG.info("Fichero escrito en la NAS: " + filePath);

  }


  /**
   * Crea un fichero en el recurso NAS cuyo nombre recibe por parametro.
   * 
   * @param path Ruta del recurso NAS
   * @param name nombre del documento
   */
  public static File createFolder(File path, String name) throws IOException {

    LOG.info(String.format("Creando carpeta path=%s; name=%s", path, name));

    File directory = new File(path, name);
    if (!directory.exists() && !directory.mkdirs()) {
      throw new IOException("No se ha podido crear la carpeta=" + directory);
    }

    LOG.info(String.format("Carpeta creada:%s", directory));
    return directory;
  }

  /**
   * Crea un fichero en el recurso NAS cuyo nombre recibe por parametro.
   * 
   * @param path Ruta del recurso NAS
   */
  public static byte[] getContentFile(String path) throws IOException {
    LOG.info(String.format("Recuperando contenido de  %s", path));
    try (FileInputStream fileInputStream = new FileInputStream(new File(path))) {
      return IOUtils.toByteArray(fileInputStream);
    }
  }

  /**
   * Eliminar un fichero en el recurso NAS cuyo nombre recibe por parametro.
   * 
   * @param path Ruta del recurso NAS
   * @param name nombre del documento
   */
  public static boolean deleteFile(String path, String name) {

    LOG.info("deleteFile ini");
    boolean correct = false;
    File directory = new File(path);
    directory.mkdirs();
    LOG.info("Directory: " + directory);
    String fileName = path + SEPARATOR + name;

    File file = new File(fileName);
    LOG.info(FILE + fileName);
    if (file.exists()) {
      correct = file.delete();
      if (!correct) {
        LOG.error("El documento no se ha podido borrar :" + name);
      }
    }

    LOG.info("deleteFile end");
    return correct;

  }

  /**
   * Realiza una copia del fichero.
   * 
   * @param path
   * @param oldName
   * @param newName
   * @return
   */
  public static boolean backupFile(String path, String oldName, String newName) {

    LOG.info("backupFile ini");
    boolean correct = false;

    Path pathOldName = Paths.get(path + SEPARATOR + oldName);
    Path pathNewName = Paths.get(path + SEPARATOR + newName);
    try {

      // Eliminamos el fichero de backup si existe
      deleteFile(path, newName);

      // Copiamos el fichero de backup
      Files.copy(pathOldName, pathNewName);

      correct = true;
    } catch (IOException e1) {
      LOG.info("No se ha podido copiar el documento de backup");
    }


    LOG.info("backupFile end");
    return correct;
  }

  /**
   * Renombra el fichero.
   * 
   * @param path oldfile
   * @param newName old Name
   * @return
   */
  public static boolean renameFile(String path, String newName) {
    boolean correct = false;
    LOG.info("renameFile ini");

    File oldfile = new File(path);
    File newfile = new File(oldfile.getParent() + SEPARATOR + newName);
    try {
      FileUtils.moveFile(oldfile, newfile);
      correct = true;
    } catch (IOException e) {
      LOG.warn("No se ha podido renombrar el documento " + path, e);
    }

    LOG.info("renameFile end");
    return correct;

  }

  /**
   * Renombra el fichero.
   * 
   * @param pathOld oldfile
   * @param pathNew old Name
   * @return
   */
  public static boolean renameFileToPath(String pathOld, String pathNew) {
    boolean correct;
    LOG.info("renameFileToPath ini");

    File oldfile = new File(pathOld);
    File newfile = new File(pathNew);
    correct = newfile.renameTo(oldfile);

    LOG.info("renameFileToPath end");
    return correct;

  }

  /**
   * Renombra el fichero.
   * 
   * @param path
   * @param oldName
   * @param newName
   * @return
   */
  public static boolean moveFile(String oldPath, String newPath, String newName)
      throws IOException {
    boolean correct = false;
    LOG.info("renameFile ini");
    try {

      File directory = new File(newPath);
      directory.mkdirs();

      Path pathOldName = Paths.get(oldPath);
      Path pathNewName = Paths.get(newPath + SEPARATOR + newName);

      Files.move(pathOldName, pathNewName, StandardCopyOption.REPLACE_EXISTING);
      correct = true;
    } catch (IOException e) {
      LOG.error("No se ha podido mover el documento");
      throw e;
    }

    LOG.info("renameFile end");
    return correct;

  }


  /**
   * Eliminar un fichero en el recurso NAS cuyo nombre recibe por parametro.
   * 
   * @param path Ruta del recurso NAS
   * @param name nombre del documento
   */
  public static Long getFileSize(String path, String name) {

    LOG.debug("getFileSize ini");
    Long size;
    String fileName = path + SEPARATOR + name;
    File file = new File(fileName);
    LOG.debug(FILE + fileName);
    if (file.exists() && file.isFile()) {
      size = Long.valueOf(file.length());
    } else {
      size = Long.valueOf(-1);
      LOG.warn("El documento " + fileName + " no existe");
    }

    LOG.debug("getFileSize end");
    return size;

  }

  public static long getFdOpen() {
    long retorno = 0;
    OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
    /*
     * if (os instanceof UnixOperatingSystemMXBean) { retorno = ((UnixOperatingSystemMXBean) os)
     * .getOpenFileDescriptorCount(); }
     */
    return retorno;
  }

  public static String hashCodeStream(String file, String algorithm) throws ValidationException {
    BufferedInputStream bs = null;
    FileInputStream fs = null;
    StringBuilder sb = new StringBuilder();
    try {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      fs = new FileInputStream(file);
      bs = new BufferedInputStream(fs);
      byte[] buffer = new byte[1024];
      int bytesRead;

      while ((bytesRead = bs.read(buffer, 0, buffer.length)) != -1) {
        md.update(buffer, 0, bytesRead);
      }
      byte[] digest = md.digest();

      for (byte bite : digest) {
        sb.append(String.format("%02x", bite & 0xff));
      }

    } catch (Exception e) {
      LOG.error(SE_HA_PRODUCIDO_UN_ERROR_AL_GENERAR_EL_HASH_DEL_DOCUMENTO, e);
      throw new ValidationException(SE_HA_PRODUCIDO_UN_ERROR_AL_GENERAR_EL_HASH_DEL_DOCUMENTO);
    } finally {
      try {
        if (bs != null) {
          bs.close();
        }
        if (fs != null) {
          fs.close();
        }
      } catch (IOException e) {
        LOG.error(e);
      }
    }

    return sb.toString();
  }

  public static String hashCodeStream(byte[] contentFile, String algorithm)
      throws ValidationException {
    BufferedInputStream bs = null;
    InputStream fs = null;
    StringBuilder sb = new StringBuilder();
    try {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      fs = new ByteArrayInputStream(contentFile);
      bs = new BufferedInputStream(fs);
      byte[] buffer = new byte[1024];
      int bytesRead;

      while ((bytesRead = bs.read(buffer, 0, buffer.length)) != -1) {
        md.update(buffer, 0, bytesRead);
      }
      byte[] digest = md.digest();

      for (byte bite : digest) {
        sb.append(String.format("%02x", bite & 0xff));
      }

    } catch (Exception e) {
      LOG.error(SE_HA_PRODUCIDO_UN_ERROR_AL_GENERAR_EL_HASH_DEL_DOCUMENTO, e);
      throw new ValidationException(SE_HA_PRODUCIDO_UN_ERROR_AL_GENERAR_EL_HASH_DEL_DOCUMENTO);
    } finally {
      try {
        if (bs != null) {
          bs.close();
        }
        if (fs != null) {
          fs.close();
        }
      } catch (IOException e) {
        LOG.error(e);
      }
    }

    return sb.toString();
  }

}
