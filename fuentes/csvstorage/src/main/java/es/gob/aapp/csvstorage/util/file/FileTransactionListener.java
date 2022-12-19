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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.util.constants.Constants;

/**
 * Esta clase va a controlar la transacionalidad de los fichero. De forma que si se produce algún
 * fallo al guardar, modificar o eliminar un documento (ya sea al guardar en bbdd o en la NAS) se
 * pueda realizar un rollback de los ficheros.
 * 
 * @author carlos.munoz1
 *
 */
public class FileTransactionListener extends TransactionSynchronizationAdapter {
  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(FileTransactionListener.class);

  public static final int ACTION_SAVE = 0;
  public static final int ACTION_UPDATE = 1;
  public static final int ACTION_DELETE = 2;

  private String pathFile;
  private String fileName;
  private int action;
  private boolean contentByRef = false;
  private String separator = System.getProperty("file.separator");

  public FileTransactionListener(String pathFile, String fileName, int action, boolean contentByRef)
      throws ServiceException {
    this.pathFile = pathFile;
    this.fileName = fileName;
    this.action = action;
    this.contentByRef = contentByRef;
    initTransaction();
  }



  private void initTransaction() throws ServiceException {
    // Antes de realizar ninguna operación sobre la NAS, al iniciarse la transación se realizan las
    // siguientes acciones
    if (action == ACTION_UPDATE || action == ACTION_DELETE) {
      // Se realiza un backup del fichero original para reestablecerlo en caso de que falle
      if (FileUtil.backupFile(pathFile, fileName, fileName + Constants.EXTENSION_BACKUP)) {
        LOG.info("Se ha creado el fichero de backup " + fileName + Constants.EXTENSION_BACKUP);
      } else {
        LOG.info(
            "No se ha podido crear el fichero de backup " + fileName + Constants.EXTENSION_BACKUP);
      }

      // Si el contenido es una referencia al fichero original tambien se realiza un backup de ese
      // fichero
      if (contentByRef && FileUtil.backupFile(pathFile, fileName + Constants.BIG_FILE_NAME,
          fileName + Constants.BIG_FILE_NAME + Constants.EXTENSION_BACKUP)) {
        LOG.info("Se ha creado el fichero de backup " + fileName + Constants.BIG_FILE_NAME
            + Constants.EXTENSION_BACKUP);
      } else if (contentByRef) {
        LOG.info("No se ha podido crear el fichero de backup " + fileName + Constants.BIG_FILE_NAME
            + Constants.EXTENSION_BACKUP);
      }
    }
  }

  @Override
  public void afterCompletion(int status) {
    // Una vez terminada la transación se realizan las siguientes acciones
    String backupFileName = fileName + Constants.EXTENSION_BACKUP;
    if (STATUS_COMMITTED == status) {
      // Si todo ha ido bien se borra el fichero de backup
      if (action == ACTION_UPDATE || action == ACTION_DELETE) {
        if (FileUtil.deleteFile(pathFile, backupFileName)) {
          LOG.info("Se ha eliminado el fichero backup " + backupFileName);
        } else {
          LOG.info("No se ha podido eliminar el fichero backup " + backupFileName);
        }

        // Si el contenido es una referencia al fichero original se elimina el backup de ese fichero
        if (contentByRef && FileUtil.deleteFile(pathFile,
            fileName + Constants.BIG_FILE_NAME + Constants.EXTENSION_BACKUP)) {
          LOG.info("Se ha eliminado el fichero backup " + fileName + Constants.BIG_FILE_NAME
              + Constants.EXTENSION_BACKUP);
        } else if (contentByRef) {
          LOG.info("No se ha podido eliminar el fichero backup " + backupFileName
              + Constants.BIG_FILE_NAME);
        }
      }
    }

    if (STATUS_COMMITTED != status) {
      // Si ha fallando la transacción se elimina el documento almacenado si existe
      if (FileUtil.deleteFile(pathFile, fileName)) {
        LOG.info("Se ha eliminar el fichero " + fileName);
      } else {
        LOG.info("No se ha podido eliminar el fichero " + fileName);
      }

      // Si el contenido es una referencia al fichero original tambien se elimina ese documento
      if (contentByRef && FileUtil.deleteFile(pathFile, fileName + Constants.BIG_FILE_NAME)) {
        LOG.info("Se ha eliminar el fichero " + fileName + Constants.BIG_FILE_NAME);
      } else if (contentByRef) {
        LOG.info("No se ha podido eliminar el fichero " + fileName + Constants.BIG_FILE_NAME);
      }

      if (action == ACTION_UPDATE || action == ACTION_DELETE) {
        // Si es una modificación o eliminación se restablece el ficheo de backup
        if (FileUtil.renameFile(pathFile + fileName + Constants.EXTENSION_BACKUP, fileName)) {
          LOG.info("Se ha restablecido el fichero de backup " + fileName);
        } else {
          LOG.info("No se ha podido restablecer el fichero de backup " + fileName);
        }

        if (contentByRef && FileUtil.renameFile(
            pathFile + separator + fileName + Constants.BIG_FILE_NAME + Constants.EXTENSION_BACKUP,
            fileName + Constants.BIG_FILE_NAME)) {
          LOG.info("Se ha restablecido el fichero de backup " + fileName + Constants.BIG_FILE_NAME);
        } else if (contentByRef) {
          LOG.info("No se ha podido restablecer el fichero de backup " + fileName
              + Constants.BIG_FILE_NAME);
        }
      }

    }
  }
}
