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

package es.gob.aapp.csvstorage.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import es.gob.aapp.csvstorage.util.constants.Constants;

/**
 * Clase de utilidades para cadenas.
 * 
 * @author carlos.munoz1
 *
 */
public class Util {

  protected static final Log logger = LogFactory.getLog(Util.class);

  public static void getUnZippedFile(FileInputStream fBase64, String file) throws IOException {
    logger.debug("Inicio getUnZippedFile");

    FileOutputStream retorno = new FileOutputStream(file);
    byte[] data = IOUtils.toByteArray(fBase64);
    byte[] base64 = Base64.decodeBase64(data);
    fBase64.close();

    retorno.write(base64);
    retorno.close();

    logger.debug("Fin getUnZippedFile");
  }

  /**
   * M�todo que valida un valor en base a una expresi�n regular.
   * 
   * @param regex String con la expresi�n regular
   * @param value String con el valor a validar
   * @return boolean true/false
   */
  public static boolean validateRegex(String regex, String value) {
    boolean retorno = false;
    if (!StringUtils.isEmpty(regex) && !StringUtils.isEmpty(value)) {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(value);
      if (matcher.matches()) {
        retorno = true;
      }
    }
    return retorno;
  }


  public static void deleteBefore(String path, String txtFile, String zipFile, String pathTemp) {
    deleteFile(path + txtFile);
    deleteFile(path + zipFile);
    deleteFile(path + pathTemp);
  }

  public static void deleteFile(String path) {
    File file = new File(path);
    if (file.exists()) {
      file.delete();
    }
  }

  public static void unZipFile(String zipFile, String outputFolder) {

    byte[] buffer = new byte[1024];

    try {

      // create output directory is not exists
      File folder = new File(outputFolder);
      folder.mkdir();

      // get the zip file content
      ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
      // get the zipped file list entry
      ZipEntry ze = zis.getNextEntry();

      while (ze != null) {

        String fileName = ze.getName();
        File newFile = new File(outputFolder + File.separator + fileName);

        logger.debug("file unzip : " + newFile.getAbsoluteFile());

        // create all non exists folders
        // else you will hit FileNotFoundException for compressed folder
        new File(newFile.getParent()).mkdirs();

        FileOutputStream fos = new FileOutputStream(newFile);

        int len;
        while ((len = zis.read(buffer)) > 0) {
          fos.write(buffer, 0, len);
        }

        fos.close();
        ze = zis.getNextEntry();
      }

      zis.closeEntry();
      zis.close();

      logger.debug("unZipFile Done");

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void writeDataBase64(String data, String path, String file) throws IOException {
    FileOutputStream fileOut = new FileOutputStream(path + file);
    fileOut.write(data.getBytes());
    fileOut.close();
  }

  public static String readFile(String filePath) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filePath));
    String line = null;
    StringBuilder stringBuilder = new StringBuilder();
    String ls = Constants.LINE_SEPARATOR;
    while ((line = reader.readLine()) != null) {
      stringBuilder.append(line);
      stringBuilder.append(ls);
    }
    reader.close();
    return stringBuilder.toString();
  }

  public static String formatearCsvSinGuiones(final String csv) {
    String csvResultante = null;

    if (StringUtils.isNotBlank(csv)) {
      // Tanto para almacenar como para recuperar se eliminan los guines del csv
      if (StringUtils.countMatches(csv, "-") > 1) {
        String ambito = csv.substring(0, csv.indexOf("-"));
        String csvSinAmbito = csv.substring(csv.indexOf("-") + 1).replace("-", "");
        csvResultante = ambito + "-" + csvSinAmbito;
      } else {
        csvResultante = csv;
      }

    }

    return csvResultante;
  }

  public static String comprobarCSVConAmbito(final String csv) {
    String csvResultante = null;

    if (StringUtils.isNotBlank(csv)) {
      String cuatroPrimeroDigitos = csv.substring(0, 4);
      String siguientesDigitos = csv.substring(4, csv.length());
      Pattern pat = Pattern.compile("^[a-zA-Z]{3}-+");
      Matcher mat = pat.matcher(cuatroPrimeroDigitos);
      if (mat.matches()) {
        csvResultante = cuatroPrimeroDigitos.concat(siguientesDigitos.replace("-", ""));
      } else {
        csvResultante = csv;
      }
    }

    return csvResultante;
  }

  public static String objectXMLToString(Object obj) {
    String s = null;
    if (obj instanceof String) {
      s = (String) obj;
    } else if (obj instanceof Element) {
      Element e = (Element) obj;
      try {
        javax.xml.transform.Transformer transformer =
            TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(e);
        transformer.transform(source, result);

        s = result.getWriter().toString();
      } catch (TransformerException ex) {
        logger.error("Se ha producido un error al convertir el objeto a string", ex);
      }

    } else {
      s = obj.toString();
    }
    return s;
  }


  public static Object stringToObjectXML(byte[] content) {
    Object element = null;
    try {
      javax.xml.parsers.DocumentBuilderFactory dbf =
          javax.xml.parsers.DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(false);
      javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.newDocument();

      doc = db.parse(new ByteArrayInputStream(content));

      element = (Object) doc.getDocumentElement();
    } catch (ParserConfigurationException e) {
      logger.error("Se ha producido un error al convertir el string a objeto", e);
    } catch (SAXException e) {
      logger.error("Se ha producido un error al convertir el string a objeto", e);
    } catch (IOException e) {
      logger.error("Se ha producido un error al convertir el string a objeto", e);
    }

    return element;
  }

  // public static String toJson(Object request) {
  // final String CONTENIDO_OBJECT = "contenido";
  // final String CONTENIDO_PROPERTY = "contenido";
  //
  // JsonObject jsonRequest = (JsonObject) new GsonBuilder().create().toJsonTree(request);
  //
  // //En caso de que se introduzca un contenido, se eliminará:
  // JsonObject jsonContenido = (JsonObject) jsonRequest.get(CONTENIDO_OBJECT);
  // if(jsonContenido != null) {
  // //se elimina el contenido{contenido} pero se mantiene el contenido{tipoMIME}
  // jsonContenido.remove(CONTENIDO_PROPERTY);
  // jsonRequest.remove(CONTENIDO_OBJECT);
  // jsonRequest.add(CONTENIDO_OBJECT, jsonContenido);
  // }
  // return new Gson().toJson(jsonRequest);

  // return null;
  // }

  public static String json2prettyText(String json) {
    return json.replace("{", "").replace("}", "").replace("\"", "").replace(":", "=")
        .replace(",", "; ").replace("restriccion=", "").replace("tipoId=", "").replace("nif=", "")
        .replace("aplicacion=", "").replace("contenido=", "contenido:");
  }


  public static boolean compruebaNif(List<String> nifs) {

    for (String nif : nifs) {
      if (!compruebaNif(nif)) {
        return false;
      }
    }

    return true;
  }

  public static boolean compruebaNif(String nif) {

    boolean valido = true;

    String expresion_regular_dni = "^[XYZ]?\\d{5,8}[A-Z]$";

    nif = nif.toUpperCase();

    Pattern pattern = Pattern.compile(expresion_regular_dni);
    Matcher matcher = pattern.matcher(nif);

    if (matcher.matches()) {

      String letra = nif.substring(nif.length() - 1);
      String letraIni = nif.substring(0, 1);
      String numStr = nif.substring(1, nif.length() - 1);

      if (letraIni.equalsIgnoreCase("X")) {
        numStr = "0" + numStr;
      } else if (letraIni.equalsIgnoreCase("Y")) {
        numStr = "1" + numStr;
      } else if (letraIni.equalsIgnoreCase("Z")) {
        numStr = "2" + numStr;
      } else {
        numStr = letraIni + numStr;
      }

      Long num = Long.parseLong(numStr);
      num = num % 23;

      String cadena = "TRWAGMYFPDXBNJZSQVHLCKET";
      String letraOK = cadena.substring(num.intValue(), num.intValue() + 1);

      if (!letra.equalsIgnoreCase(letraOK)) {
        valido = false;
      }

    } else {
      valido = false;
    }

    return valido;
  }


}
