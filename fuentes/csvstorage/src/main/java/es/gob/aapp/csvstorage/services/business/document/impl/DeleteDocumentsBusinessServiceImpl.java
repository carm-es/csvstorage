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

package es.gob.aapp.csvstorage.services.business.document.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.services.business.document.DeleteDocumentBusinessService;
import es.gob.aapp.csvstorage.services.business.validation.ValPermissionBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService;
import es.gob.aapp.csvstorage.services.manager.audit.AuditManagerService;
import es.gob.aapp.csvstorage.services.manager.document.DocumentManagerService;
import es.gob.aapp.csvstorage.util.Util;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.constants.Operation;
import es.gob.aapp.csvstorage.util.file.FileUtil;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.document.model.EliminarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.EliminarDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.Response;

/**
 * Implementación de los servicios business de almacenamiento de documentos.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("deleteDocumentsBusinessService")
@PropertySource("file:${csvstorage.config.path}/csvstorage.properties")
public class DeleteDocumentsBusinessServiceImpl implements DeleteDocumentBusinessService {

  /**
   * Inyección de los properties de mensajes.
   */
  @Autowired
  private MessageSource messageSource;

  @Autowired
  private DocumentManagerService documentManagerService;

  @Autowired
  private ValPermissionBusinessService valPermissionBusinessService;

  @Autowired
  protected ApplicationManagerService applicationManagerService;

  @Autowired
  private AuditManagerService auditManagerService;

  private static final Locale locale = LocaleContextHolder.getLocale();

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(DeleteDocumentsBusinessServiceImpl.class);

  @Override
  public EliminarDocumentoResponse eliminar(EliminarDocumentoRequest eliminarDocumento)
      throws CSVStorageException {
    LOG.info("[INI] Entramos en eliminar. ");
    Response response = new Response();
    EliminarDocumentoResponse eliminarDocumentoResponse = new EliminarDocumentoResponse();
    String codigo = null;
    String descripcion = null;
    long fdIni = FileUtil.getFdOpen();
    try {

      String dir3 = eliminarDocumento.getDir3();
      String csv = eliminarDocumento.getCsv();
      String idEni = eliminarDocumento.getIdENI();

      // Validamos si se han rellenado los campos obligatorio
      LOG.info("Validamos si se han rellenado los campos obligatorio");
      if (StringUtils.isEmpty(csv) && StringUtils.isEmpty(idEni)) {
        if (StringUtils.isEmpty(dir3)) {
          // Falta por rellenar el dir3
          codigo = String.valueOf(Constants.MANDATORY_FIELD_DIR3);
          descripcion = messageSource.getMessage(Constants.MANDATORY_FIELD_DIR3_DESC, null, locale);

        } else {
          // Falta por rellenar csv o idEni
          codigo = String.valueOf(Constants.EMPTY_FIELDS_CSV_IDENI);
          descripcion =
              messageSource.getMessage(Constants.EMPTY_FIELDS_CSV_IDENI_DESC, null, locale);
        }
      } else {

        // Formateamos el csv si viene con guiones
        csv = Util.formatearCsvSinGuiones(csv);

        // Validamos si ya existe un informe con este csv
        LOG.info("Validamos si ya existe un informe con este csv");
        List<DocumentEntity> documentEntityList = documentManagerService.findAll(csv, dir3, idEni);

        if (documentEntityList != null && !documentEntityList.isEmpty()) {

          // Se realiza comprobaciones para controlar quien elimina el documento
          LOG.info("Se realiza comprobaciones para controlar quien elimina el documento");
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          String idAplicacion = auth.getName();

          ApplicationEntity aplicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

          // porque el csv es unico
          if (valPermissionBusinessService.permisoDelete(aplicacion, documentEntityList.get(0))) {

            if (!documentManagerService.findDocumentAplicacionByDocument(documentEntityList.get(0))
                .isEmpty()) {
              LOG.info(
                  "No se puede eliminar el documento. Contiene aplicaciones que pueden consultarlo.");
              codigo = String.valueOf(Constants.DELETE_DOCUMENT_ERROR);
              descripcion = messageSource.getMessage(Constants.DELETE_DOCUMENT_APP_RESTRICTION_DESC,
                  null, locale);
            } else {

              // Se procede a eliminar el documento
              documentManagerService.deleteEni(documentEntityList);
              LOG.info("Se procede a eliminar el documento");

              for (DocumentEntity doc : documentEntityList) {
                auditManagerService.create(aplicacion, doc.getId(),
                    eliminarDocumento.parametrosAuditoria(), Operation.ELIMINAR.value());
              }

              codigo = String.valueOf(Constants.DELETE_DOCUMENT_SUCCESS);
              descripcion =
                  messageSource.getMessage(Constants.DELETE_DOCUMENT_SUCCESS_DESC, null, locale);
            }
          } else {

            codigo = String.valueOf(Constants.DELETE_DOCUMENT_ERROR);
            descripcion = messageSource.getMessage(Constants.DELETE_DOCUMENT_NOT_PERMISSION_DESC,
                null, locale);

          }

        } else {
          codigo = String.valueOf(Constants.DELETE_DOCUMENT_NOT_EXIST);
          descripcion =
              messageSource.getMessage(Constants.DELETE_DOCUMENT_NOT_EXIST_DESC, null, locale);
        }
      }

    } catch (ServiceException e) {
      LOG.error("Error en servicio de borrado del documento. ", e);
      codigo = String.valueOf(Constants.DELETE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.DELETE_DOCUMENT_ERROR_DESC, null, locale);
    } catch (IOException e) {
      LOG.error("Error en servicio de borrado del documento de la NAS. ", e);
      codigo = String.valueOf(Constants.DELETE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.DELETE_DOCUMENT_ERROR_DESC, null, locale);
    }

    response.setCodigo(codigo);
    response.setDescripcion(descripcion);

    eliminarDocumentoResponse.setResponse(response);
    printOpenFileDescriptor("eliminar", fdIni);

    LOG.info("[FIN] Salimos de eliminar. ");
    return eliminarDocumentoResponse;
  }


  @Override
  public Response eliminarCMIS(String uuid) {
    LOG.info("[INI] Entramos en eliminarCMIS. ");
    Response response = new Response();
    String codigo = null;
    String descripcion = null;
    long fdIni = FileUtil.getFdOpen();
    try {
      DocumentEntity documentEntity = documentManagerService.findByUuid(uuid);


      if (documentEntity != null) {
        List<DocumentEntity> entityList = new ArrayList<>();
        entityList.add(documentEntity);
        documentManagerService.deleteEni(entityList);

        codigo = String.valueOf(Constants.DELETE_DOCUMENT_SUCCESS);
        descripcion =
            messageSource.getMessage(Constants.DELETE_DOCUMENT_SUCCESS_DESC, null, locale);

      } else {
        codigo = String.valueOf(Constants.DELETE_DOCUMENT_NOT_EXIST);
        descripcion =
            messageSource.getMessage(Constants.DELETE_DOCUMENT_NOT_EXIST_DESC, null, locale);
      }


    } catch (ServiceException e) {
      LOG.error("Error en servicio de borrado del documento. ", e);
      codigo = String.valueOf(Constants.DELETE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.DELETE_DOCUMENT_ERROR_DESC, null, locale);
    } catch (IOException e) {
      LOG.error("Error en servicio de borrado del documento de la NAS. ", e);
      codigo = String.valueOf(Constants.DELETE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.DELETE_DOCUMENT_ERROR_DESC, null, locale);
    }


    response.setCodigo(codigo);
    response.setDescripcion(descripcion);

    printOpenFileDescriptor("eliminarCMIS", fdIni);

    LOG.info("[FIN] Salimos de eliminarCMIS. ");
    return response;
  }

  private void printOpenFileDescriptor(String method, long fdIni) {
    long fdFin = FileUtil.getFdOpen();
    if (fdFin > fdIni) {
      LOG.warn("FD " + method + ": " + (fdFin - fdIni));
    }
  }

}
