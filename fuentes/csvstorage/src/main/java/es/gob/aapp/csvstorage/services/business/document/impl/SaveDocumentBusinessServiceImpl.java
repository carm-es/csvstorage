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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import es.gob.aapp.csvstorage.webservices.document.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentAplicacionEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.model.converter.document.DocumentConverter;
import es.gob.aapp.csvstorage.model.object.document.DocumentEniObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentResponseObject;
import es.gob.aapp.csvstorage.services.business.document.SaveDocumentBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.exception.ValidationException;
import es.gob.aapp.csvstorage.services.manager.audit.AuditManagerService;
import es.gob.aapp.csvstorage.util.Util;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.constants.DocumentPermission;
import es.gob.aapp.csvstorage.util.constants.DocumentRestriccion;
import es.gob.aapp.csvstorage.util.constants.Operation;
import es.gob.aapp.csvstorage.util.file.FileUtil;
import es.gob.aapp.eeutil.bigDataTransfer.exception.BigDataTransferException;

/**
 * Implementación de los servicios business de almacenamiento de documentos.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("saveDocumentBusinessService")
public class SaveDocumentBusinessServiceImpl extends DocumentBusinessService
    implements SaveDocumentBusinessService {

  private static final Logger logger =
      LoggerFactory.getLogger(SaveDocumentBusinessServiceImpl.class);
  private static final String ERROR_EN_SERVICIO_DE_ALMACENAMIENDO_DEL_DOCUMENTO =
      "Error en servicio de almacenamiendo del documento. ";

  @Autowired
  private BigFileTransferService bigFileTransferService;

  @Autowired
  private AuditManagerService auditManagerService;

  @Override
  public GuardarDocumentoResponse guardar(DocumentoRequest guardarDocumento)
      throws CSVStorageException {

    logger.info("[INI] Entramos en guardar. ");

    GuardarDocumentoResponse guardarDocumentoResponse = new GuardarDocumentoResponse();
    GuardarDocumentoUuidResponse guardarDocumentoUuidResponse = guardarUuid(guardarDocumento);

    Response response = guardarDocumentoUuidResponse.getUuidResponse();
    guardarDocumentoResponse.setResponse(response);
    logger.info("[FIN] Salimos en guardar. ");

    return guardarDocumentoResponse;
  }

  @Override
  public GuardarDocumentoUuidResponse guardarStreaming(DocumentoRequest guardarDocumento)
      throws CSVStorageException {

    logger.info("[INI] Entramos en guardarStreaming. ");

    logger.info("Tipo de Permiso: " + guardarDocumento.getTipoPermiso());

    GuardarDocumentoUuidResponse guardarDocumentoUuidResponse = guardarUuid(guardarDocumento);

    logger.info("[FIN] Entramos en guardarStreaming. ");

    return guardarDocumentoUuidResponse;
  }

  private String getRutaFicheroCarm(String idApp) {
    StringBuilder retVal = new StringBuilder();
    String app = idApp;
    if ((null == idApp) || (0 == idApp.trim().length())) {
      app = "_unknown_";
    }
    retVal.append(rutaFichero);
    retVal.append(File.separator);
    retVal.append(app.toUpperCase());

    return retVal.toString();
  }

  private List<ApplicationEntity> getRestriccionAplicaciones(DocumentObject documentObject,
      ListaAplicaciones listaAplicaciones, Response response) throws ServiceException {
    List<ApplicationEntity> result = null;

    // Se convierte las restricciones por Aplicaciones en caso de que existan
    // Se validan si las aplicaciones son válidas
    // --se realiza en esta parte para tener acceso a BD
    logger.info("procedemos a validar restricciones por aplicaciones.");
    if (documentObject.getRestricciones().contains(DocumentRestriccion.RESTRINGIDO_APP.getCode())
        && (listaAplicaciones == null || listaAplicaciones.getAplicacion().contains(""))) {
      logger
          .info("No se han especificado aplicaciones de consulta o ha especificado alguna vacía.");
      response.setCodigo(String.valueOf(Constants.EMPTY_FIELDS_APPLICATIONS_QUERY));
      response.setDescripcion(
          messageSource.getMessage(Constants.EMPTY_FIELDS_APPLICATIONS_QUERY_DES, null, locale));
      return result;
    } else if (listaAplicaciones != null && !listaAplicaciones.getAplicacion().contains("")) {
      List<ApplicationEntity> aplis = new ArrayList<>();
      logger.info("Listado de Aplicaciones a dar permisos de consulta: {}",
          listaAplicaciones.getAplicacion());
      for (String apli : listaAplicaciones.getAplicacion()) {
        ApplicationEntity foundApli = applicationManagerService.findByIdAplicacionPublico(apli);
        if (foundApli == null) {
          logger.info("No se ha encontrado la aplicación: {} ", apli);
          response.setCodigo(String.valueOf(Constants.NOT_EXISTS_APPLICATIONS_QUERY));
          response.setDescripcion(
              messageSource.getMessage(Constants.NOT_EXISTS_APPLICATIONS_QUERY_DES, null, locale));
          return result;
        } else {
          aplis.add(foundApli);
        }
      }
      result = aplis;
    }

    return result;
  }

  private boolean restriccionesBienConstruidas(DocumentObject documentObject, Response response)
      throws ServiceException {
    boolean result = true;

    // Se comprueba que si vienen listas de restringidos (lista nifs, lista apps, lista ids)
    // vienen acompañados de su restricción (restringido_nif, restringido_app, restringido_id)
    // y el Permiso es Restringido

    // Se comprueba la lista de nifs
    if (documentObject.getNifs() != null && !documentObject.getNifs().isEmpty()) {
      // Ha añadido una lista de nifs
      // se comprueba si ha añadido restringido_nif
      // y si el permiso es Restringido
      if (documentObject.getRestricciones() == null
          || !documentObject.getRestricciones()
              .contains(DocumentRestriccion.RESTRINGIDO_NIF.getCode())
          || !documentObject.getTipoPermiso().equals(DocumentPermission.RESTRINGIDO)) {
        logger.info(
            "Restricciones mal comunicadas. Se ha encontrado una lista de NIFs. Debe establecer el tipo de permiso como RESTRINGIDO e incluir RESTRINGIDO_NIF a la lista de restricciones.");
        response.setCodigo(String.valueOf(Constants.MANDATORY_FIELDS));
        response.setDescripcion(
            messageSource.getMessage(Constants.MALFORMED_RESTRICTION_NIF, null, locale));
        return false;
      }
    }

    // Se comprueba la lista de ids
    if (documentObject.getTipoIds() != null && !documentObject.getTipoIds().isEmpty()) {
      // Ha añadido una lista de ids
      // se comprueba si ha añadido restringido_id
      // y si el permiso es Restringido
      if (documentObject.getRestricciones() == null
          || !documentObject.getRestricciones()
              .contains(DocumentRestriccion.RESTRINGIDO_ID.getCode())
          || !documentObject.getTipoPermiso().equals(DocumentPermission.RESTRINGIDO)) {
        logger.info(
            "Restricciones mal comunicadas. Se ha encontrado una lista de IDs. Debe establecer el tipo de permiso como RESTRINGIDO e incluir RESTRINGIDO_ID a la lista de restricciones.");
        response.setCodigo(String.valueOf(Constants.MANDATORY_FIELDS));
        response.setDescripcion(
            messageSource.getMessage(Constants.MALFORMED_RESTRICTION_ID, null, locale));
        return false;
      }
    }

    // Se comprueba la lista de aplicaciones
    if (documentObject.getAplicaciones() != null && !documentObject.getAplicaciones().isEmpty()) {
      // Ha añadido una lista de aplicaciones
      // se comprueba si ha añadido restringido_app
      // y si el permiso es Restringido
      if (documentObject.getRestricciones() == null
          || !documentObject.getRestricciones()
              .contains(DocumentRestriccion.RESTRINGIDO_APP.getCode())
          || !documentObject.getTipoPermiso().equals(DocumentPermission.RESTRINGIDO)) {
        logger.info(
            "Restricciones mal comunicadas. Se ha encontrado una lista de Aplicaciones. Debe establecer el tipo de permiso como RESTRINGIDO e incluir RESTRINGIDO_APP a la lista de restricciones.");
        response.setCodigo(String.valueOf(Constants.MANDATORY_FIELDS));
        response.setDescripcion(
            messageSource.getMessage(Constants.MALFORMED_RESTRICTION_APP, null, locale));
        return false;
      }
    }

    return result;
  }

  @Override
  public GuardarDocumentoUuidResponse guardarUuid(DocumentoRequest guardarDocumento)
      throws CSVStorageException {

    String idAplicacion = SecurityContextHolder.getContext().getAuthentication().getName();

    logger.info("[INI] Entramos en guardarUuid. idAplicacion: {}", idAplicacion);

    ResponseUuid response = new ResponseUuid();
    GuardarDocumentoUuidResponse guardarDocumentoResponse = new GuardarDocumentoUuidResponse();

    String codigo;
    String descripcion;
    String path;
    long fdIni = FileUtil.getFdOpen();

    try {

      DocumentObject documentObject = DocumentConverter.convertGuardarDocumentToDocumenObject(
          guardarDocumento, getRutaFicheroCarm(idAplicacion), bigFileTransferService);
      ApplicationEntity aplicacion = applicationManagerService.findByIdAplicacion(idAplicacion);
      // Se convierten y validan la aplicaciones que pueden consultar el documento
      documentObject.setAplicaciones(
          getRestriccionAplicaciones(documentObject, guardarDocumento.getAplicaciones(), response));
      if (response.getDescripcion() != null) {
        DocumentConverter.deleteDocumento(documentObject.getContenidoPorRef());
        guardarDocumentoResponse.setResponseUuid(response);
        return guardarDocumentoResponse;
      }

      // Se valida que si vienen listas de restringidos (lista nifs, lista apps, lista ids)
      // vienen acompañados de su restricción (restringido_nif, restringido_app, restringido_id)
      // y el Permiso es Restringido
      if (!restriccionesBienConstruidas(documentObject, response)) {
        DocumentConverter.deleteDocumento(documentObject.getContenidoPorRef());
        guardarDocumentoResponse.setResponseUuid(response);
        return guardarDocumentoResponse;
      }

      // Validamos si la unidad orgánica es válida
      UnitEntity unidad =
          validationBusinessService.findUnidadOrganicaByDir3(documentObject.getDir3());
      logger.debug("Unidad: {}", unidad);

      if (unidad == null) {
        logger.info("No existe el DIR3: " + documentObject.getDir3());
        DocumentConverter.deleteDocumento(documentObject.getContenidoPorRef());
        response.setCodigo(String.valueOf(Constants.ORGANIC_UNIT_NOT_EXIST));
        response.setDescripcion(
            messageSource.getMessage(Constants.ORGANIC_UNIT_NOT_EXIST_DESC, null, locale));
        guardarDocumentoResponse.setResponseUuid(response);
        return guardarDocumentoResponse;
      }

      if (!validationBusinessService.validacionGuardado(response, aplicacion, documentObject)) {
        logger.info("Error en validacionGuardado: ");
        DocumentConverter.deleteDocumento(documentObject.getContenidoPorRef());
        guardarDocumentoResponse.setResponseUuid(response);
        return guardarDocumentoResponse;
      }

      // Si no existe se guarda
      DocumentEntity documentEntity = new DocumentEntity();

      String uuid = UUID.randomUUID().toString();
      path = DocumentConverter.obtenerRutaFichero(getRutaFicheroCarm(idAplicacion),
          documentObject.getDir3());
      logger.info("guardamos el documento con uuid: {}", uuid);

      documentObject.setPathFile(path);
      documentObject.setUuid(uuid);

      if (documentObject.getIsEni()) {

        // Creamos el objeto DocumentEniObject que vamos a utilizar para guardar el documento ENI en
        // la NAS y en la bbdd
        DocumentEniObject documentEniObject =
            DocumentConverter.convertGuardarDocumentoEniToDocumentEni(documentObject);
        documentEniObject.setCalledFrom(Constants.CALLED_FROM_GUARDAR_DOCUMENTO);

        if (validationBusinessService.validarMetadatosEni(documentEniObject.getDocumentEniEntity(),
            guardarDocumento.getDir3(), response)) {
          guardarDocumentoENI(documentEntity, documentEniObject, unidad, path, uuid, aplicacion,
              false);
          // Si es bigdata se devuelve el GuardarDocumentoUuidResponse
          response.setUuid(uuid);

        } else {
          DocumentConverter.deleteDocumento(documentObject.getContenidoPorRef());
          guardarDocumentoResponse.setResponseUuid(response);
          return guardarDocumentoResponse;
        }

      } else {
        guardarDocumento(documentEntity, documentObject, unidad, aplicacion, false);
        // Si es bigdata se devuelve el GuardarDocumentoUuidResponse
        response.setUuid(uuid);
      }

      codigo = String.valueOf(Constants.SAVE_DOCUMENT_SUCCESS);
      descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_SUCCESS_DESC, null, locale);

    } catch (ServiceException | ConsumerWSException | IOException | BigDataTransferException e) {
      logger.error(ERROR_EN_SERVICIO_DE_ALMACENAMIENDO_DEL_DOCUMENTO, e);
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_DESC, null, locale);
    } catch (ValidationException e) {
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = e.getMessage();
    }

    response.setCodigo(codigo);
    response.setDescripcion(descripcion);
    guardarDocumentoResponse.setResponseUuid(response);

    printOpenFileDescriptor("guardarUuid", fdIni);

    logger.info("[FIN] Salimos de guardarUuid. ");
    return guardarDocumentoResponse;
  }

  @Override
  public GuardarDocumentoResponse modificar(DocumentoRequest guardarDocumento)
      throws CSVStorageException {

    logger.info("[INI] Entramos en modificar. ");

    Response response = new Response();
    GuardarDocumentoResponse guardarDocumentoResponse = new GuardarDocumentoResponse();
    String separator = System.getProperty("file.separator");
    long fdIni = FileUtil.getFdOpen();
    String codigo;
    String descripcion;

    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String idAplicacion = auth.getName();
      DocumentObject documentObject = DocumentConverter.convertGuardarDocumentToDocumenObject(
          guardarDocumento, getRutaFicheroCarm(idAplicacion), bigFileTransferService);
      ApplicationEntity aplicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

      // Se convierten y validan la aplicaciones que pueden consultar el documento
      documentObject.setAplicaciones(
          getRestriccionAplicaciones(documentObject, guardarDocumento.getAplicaciones(), response));
      if (response.getDescripcion() != null) {
        guardarDocumentoResponse.setResponse(response);
        return guardarDocumentoResponse;
      }

      // Se valida que si vienen listas de restringidos (lista nifs, lista apps, lista ids)
      // vienen acompañados de su restricción (restringido_nif, restringido_app, restringido_id)
      // y el Permiso es Restringido
      if (!restriccionesBienConstruidas(documentObject, response)) {
        guardarDocumentoResponse.setResponse(response);
        return guardarDocumentoResponse;
      }

      // Validamos si la unidad orgánica es válida
      UnitEntity unidad =
          validationBusinessService.findUnidadOrganicaByDir3(documentObject.getDir3());
      logger.info("Buscamos la unidad");

      if (unidad == null) {
        response.setCodigo(String.valueOf(Constants.ORGANIC_UNIT_NOT_EXIST));
        response.setDescripcion(
            messageSource.getMessage(Constants.ORGANIC_UNIT_NOT_EXIST_DESC, null, locale));
        guardarDocumentoResponse.setResponse(response);
        return guardarDocumentoResponse;
      }

      logger.info("Procedemos a realizar validacionModificado");
      DocumentEntity documentEntity =
          validationBusinessService.validacionModificado(response, aplicacion, documentObject);
      logger.info("Salimos de realizar validacionModificado");

      if (StringUtils.isNotEmpty(response.getCodigo())) {
        logger
            .info("Devolvemos error: " + response.getDescripcion() + " - " + response.getCodigo());
        guardarDocumentoResponse.setResponse(response);
        return guardarDocumentoResponse;
      }

      String uuid;
      String path;
      if (documentEntity.getUuid() != null) {
        path = documentEntity.getRutaFichero();
        uuid = documentEntity.getUuid();
      } else {
        path = documentEntity.getRutaFichero().substring(0,
            documentEntity.getRutaFichero().lastIndexOf(separator));
        uuid = documentEntity.getRutaFichero()
            .substring(documentEntity.getRutaFichero().lastIndexOf(separator) + 1);
      }

      logger.info("modificamos el documento con uuid: {}", uuid);

      documentObject.setPathFile(path);
      documentObject.setUuid(uuid);
      documentObject.setDir3(guardarDocumento.getDir3());
      documentObject.setCsv(Util.formatearCsvSinGuiones(documentObject.getCsv()));

      if (documentObject.getIsEni()) {

        // Creamos el objeto DocumentEniObject que vamos a utilizar para guardar el documento ENI en
        // la NAS y en la bbdd
        DocumentEniObject documentEniObject =
            DocumentConverter.convertGuardarDocumentoEniToDocumentEni(documentObject);

        List<DocumentEniEntity> documentEniEntityList =
            documentEniManagerService.findByDocument(documentEntity.getId(),
                documentEntity.getCsv(), documentEntity.getUnidadOrganica().getUnidadOrganica());

        if (!documentEniEntityList.isEmpty()) {
          documentEniObject.getDocumentEniEntity().setId(documentEniEntityList.get(0).getId());
        }

        if (validationBusinessService.validarMetadatosEni(documentEniObject.getDocumentEniEntity(),
            guardarDocumento.getDir3(), response)) {
          guardarDocumentoENI(documentEntity, documentEniObject, unidad, path, uuid, aplicacion,
              true);
        } else {
          guardarDocumentoResponse.setResponse(response);
          return guardarDocumentoResponse;
        }

      } else {
        guardarDocumento(documentEntity, documentObject, unidad, aplicacion, true);
      }

      codigo = String.valueOf(Constants.SAVE_DOCUMENT_SUCCESS);
      descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_SUCCESS_DESC, null, locale);

    } catch (IOException | ServiceException | ConsumerWSException | BigDataTransferException e) {
      logger.error(ERROR_EN_SERVICIO_DE_ALMACENAMIENDO_DEL_DOCUMENTO, e);
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_DESC, null, locale);
    } catch (ValidationException e) {
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = e.getMessage();
    }

    response.setCodigo(codigo);
    response.setDescripcion(descripcion);

    guardarDocumentoResponse.setResponse(response);

    printOpenFileDescriptor("modificar", fdIni);

    logger.info("[FIN] Salimos de modificar. ");
    return guardarDocumentoResponse;
  }


  @Override
  @Transactional
  public DocumentResponseObject guardarCMIS(DocumentObject documentCMIS, boolean overwrite) {

    logger.info("[INI] Entramos en guardarCMIS. ");
    DocumentResponseObject response = new DocumentResponseObject();
    Response responseAux = new Response();
    String codigo = null;
    String descripcion = null;
    String uuid = null;
    long fdIni = FileUtil.getFdOpen();

    try {
      ApplicationEntity aplicacion =
          applicationManagerService.findByIdAplicacion(documentCMIS.getCreatedBy());

      // Se validan los datos obligatorios y el tipo MIME
      if (!validationBusinessService.validarDatosObligatorios(responseAux, documentCMIS)) {
        response.setCodigo(responseAux.getCodigo());
        response.setDescripcion(responseAux.getDescripcion());

        return response;
      }

      // Validamos si la unidad orgánica es válida

      UnitEntity unidad =
          validationBusinessService.findUnidadOrganicaByDir3(documentCMIS.getDir3());
      logger.debug("Unidad Buscada: {}", unidad);

      if (unidad == null) {
        response.setCodigo(String.valueOf(Constants.ORGANIC_UNIT_NOT_EXIST));
        response.setDescripcion(
            messageSource.getMessage(Constants.ORGANIC_UNIT_NOT_EXIST_DESC, null, locale));

        return response;
      }


      // Validamos si ya existe un documento con esta unidad y csv o idEni
      List<DocumentEntity> documentEntityList = documentManagerService
          .existDocument(documentCMIS.getDir3(), documentCMIS.getCsv(), documentCMIS.getIdEni());


      DocumentEntity documentEntity;
      if (documentEntityList == null || CollectionUtils.size(documentEntityList) == 0) {
        // Si no existe se guarda
        documentEntity = new DocumentEntity();

        uuid = UUID.randomUUID().toString();
        String path = documentCMIS.getPathFile();
        documentCMIS.setPathFile(path);
        documentCMIS.setUuid(uuid);

        logger.debug("guardamos el documento con uuid: {}", uuid);
        guardarDocumento(documentEntity, documentCMIS, unidad, aplicacion, false);

        codigo = String.valueOf(Constants.SAVE_DOCUMENT_SUCCESS);
        descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_SUCCESS_DESC, null, locale);
      } else if (CollectionUtils.isNotEmpty(documentEntityList) && documentEntityList.size() == 1) {

        documentEntity = documentEntityList.get(0);

        if (overwrite && !documentEntity.getName().equals(documentCMIS.getName())) {
          // Si se puede sobreescribir y los nombres de los documentos son distintos se modifica el
          // documeto
          documentEntity.setTipoMINE(documentCMIS.getMimeType());
          uuid = documentEntity.getUuid();

          DocumentObject documentBbdd =
              DocumentConverter.convertDocumentEntityToObject(documentEntity);

          guardarDocumento(documentEntity, documentBbdd, documentEntity.getUnidadOrganica(),
              aplicacion, false);


          codigo = String.valueOf(Constants.SAVE_DOCUMENT_SUCCESS);
          descripcion =
              messageSource.getMessage(Constants.SAVE_DOCUMENT_SUCCESS_DESC, null, locale);
        } else {
          // Si existe se devuelve un error
          codigo = String.valueOf(Constants.SAVE_DOCUMENT_ALREADY_EXIST);
          descripcion =
              messageSource.getMessage(Constants.SAVE_DOCUMENT_ALREADY_EXIST_DESC, null, locale);
        }

      }


    } catch (ServiceException | ConsumerWSException e) {
      logger.error(ERROR_EN_SERVICIO_DE_ALMACENAMIENDO_DEL_DOCUMENTO, e);
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_DESC, null, locale);
    } catch (ValidationException e) {
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = e.getMessage();
    }

    response.setCodigo(codigo);
    response.setDescripcion(descripcion);
    response.setUuid(uuid);

    printOpenFileDescriptor("guardarCMIS", fdIni);

    logger.info("[FIN] Salimos de guardarCMIS. ");
    return response;
  }

  @Override
  @Transactional
  public DocumentResponseObject modificarCMIS(DocumentObject documentCMIS,
      boolean modificarContenido) {

    logger.info("[INI] Entramos en modificarCMIS. ");
    DocumentResponseObject response = new DocumentResponseObject();

    String codigo = null;
    String descripcion = null;
    String uuid = null;
    long fdIni = FileUtil.getFdOpen();

    try {

      ApplicationEntity applicacion = null;
      if (documentCMIS.getModifiedBy() != null) {
        applicacion = applicationManagerService.findByIdAplicacion(documentCMIS.getModifiedBy());
      }

      // Validamos si ya existe un documento con esta unidad y csv o idEni
      List<DocumentEntity> documentEntityList = documentManagerService
          .existDocument(documentCMIS.getDir3(), documentCMIS.getCsv(), documentCMIS.getIdEni());

      DocumentEntity documentEntity;
      if (documentEntityList == null || CollectionUtils.size(documentEntityList) == 0) {
        // Si no existe se devuelve un error
        response.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST));
        response.setDescripcion(
            messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale));

        return response;
      }

      documentEntity = documentEntityList.get(0);
      if (modificarContenido) {
        // Si solo se modifica el contenido
        documentEntity.setTipoMINE(documentCMIS.getMimeType());
        DocumentObject documentBbdd =
            DocumentConverter.convertDocumentEntityToObject(documentEntity);
        guardarDocumento(documentEntity, documentBbdd, documentEntity.getUnidadOrganica(),
            applicacion, true);

        codigo = String.valueOf(Constants.SAVE_DOCUMENT_SUCCESS);
        descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_SUCCESS_DESC, null, locale);
      } else {
        // Si solo se modifican los metadatos
        if (documentEntity.getUnidadOrganica().getUnidadOrganica().equals(documentCMIS.getDir3())) {

          documentEntity.setCsv(documentCMIS.getCsv());
          documentEntity.setIdEni(documentCMIS.getIdEni());
          documentEntity.setModifiedAt(Calendar.getInstance().getTime());
          documentEntity.setModifiedBy(applicacion);

          documentManagerService.create(documentEntity);

          codigo = String.valueOf(Constants.SAVE_DOCUMENT_SUCCESS);
          descripcion =
              messageSource.getMessage(Constants.SAVE_DOCUMENT_SUCCESS_DESC, null, locale);
        } else {
          codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST);
          descripcion = "No se puede modificar el dir3";

        }
      }


    } catch (ServiceException e) {
      logger.error(ERROR_EN_SERVICIO_DE_ALMACENAMIENDO_DEL_DOCUMENTO, e);
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_DESC, null, locale);
    } catch (ValidationException e) {
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = e.getMessage();
    }

    logger.debug("response: {}-{}", codigo, descripcion);
    response.setCodigo(codigo);
    response.setDescripcion(descripcion);
    response.setUuid(uuid);

    printOpenFileDescriptor("modificarCMIS", fdIni);

    logger.info("[FIN] Salimos de modificarCMIS. ");
    return response;
  }

  @Override
  @Transactional
  public GuardarDocumentoResponse otorgarPermisos(DocumentoPermisoRequest guardarDocumento)
      throws CSVStorageException {

    logger.info("[INI] Entramos en otorgarPermisos. ");

    Response response = new Response();
    GuardarDocumentoResponse guardarDocumentoResponse = new GuardarDocumentoResponse();
    long fdIni = FileUtil.getFdOpen();
    String codigo = "";
    String descripcion = "";

    try {

      logger.info("DocumentoPermisoRequest Aplicaciones: " + guardarDocumento.getAplicaciones());
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      final String idAplicacion = auth.getName();

      DocumentObject documentObject = DocumentConverter.convertGuardarDocumentToDocumenObject(
          guardarDocumento, getRutaFicheroCarm(idAplicacion), bigFileTransferService);

      final ApplicationEntity aplicacion =
          applicationManagerService.findByIdAplicacion(idAplicacion);

      // Se convierten y validan la aplicaciones que pueden consultar el documento
      documentObject.setAplicaciones(
          getRestriccionAplicaciones(documentObject, guardarDocumento.getAplicaciones(), response));
      if (response.getDescripcion() != null) {
        guardarDocumentoResponse.setResponse(response);
        return guardarDocumentoResponse;
      }

      // Validamos si la unidad orgánica es válida
      UnitEntity unidad =
          validationBusinessService.findUnidadOrganicaByDir3(documentObject.getDir3());
      logger.info("Buscamos la unidad: {}", unidad);

      if (unidad == null) {
        response.setCodigo(String.valueOf(Constants.ORGANIC_UNIT_NOT_EXIST));
        response.setDescripcion(
            messageSource.getMessage(Constants.ORGANIC_UNIT_NOT_EXIST_DESC, null, locale));
        guardarDocumentoResponse.setResponse(response);
        return guardarDocumentoResponse;
      }

      logger.info("Procedemos a realizar validacionModificado");
      DocumentEntity documentEntity =
          validationBusinessService.validacionOtorgarPermiso(response, aplicacion, documentObject);
      logger.info("Salimos de realizar validacionModificado");

      if (StringUtils.isNotEmpty(response.getCodigo())) {
        logger
            .info("Devolvemos error: " + response.getDescripcion() + " - " + response.getCodigo());
        guardarDocumentoResponse.setResponse(response);
        return guardarDocumentoResponse;
      }

      if (documentEntity != null) {

        // obtenemos la lista de restricciones
        documentObject = formaListaRestriccion(documentObject, documentEntity);

        logger.info("realizo el guardado de las restricciones por aplicacion con restricciones:");
        documentManagerService.guardarAplicacionesConsulta(documentObject.getAplicaciones(),
            documentEntity, true);

        codigo = String.valueOf(Constants.SAVE_DOCUMENT_SUCCESS);
        descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_RESTRICTION_APP_SUCCESS_DESC,
            null, locale);

        auditManagerService.create(aplicacion, documentEntity.getId(),
            guardarDocumento.parametrosAuditoria(), Operation.OTORGAR_CONSULTA.value());
      }

    } catch (IOException | ServiceException | ConsumerWSException | BigDataTransferException e) {
      logger.error(ERROR_EN_SERVICIO_DE_ALMACENAMIENDO_DEL_DOCUMENTO, e);
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_DESC, null, locale);
    }

    response.setCodigo(codigo);
    response.setDescripcion(descripcion);

    guardarDocumentoResponse.setResponse(response);
    printOpenFileDescriptor("otorgarPermisos", fdIni);

    logger.info("[INI] Salimos en otorgarPermisos. ");
    return guardarDocumentoResponse;
  }

  @Override
  @Transactional
  public GuardarDocumentoResponse revocarPermisos(DocumentoRevocarPermisoRequest revocarPermisos)
      throws CSVStorageException {

    logger.info("[INI] Entramos en revocarPermisos. ");

    Response response = new Response();
    GuardarDocumentoResponse guardarDocumentoResponse = new GuardarDocumentoResponse();
    String codigo = "";
    String descripcion = "";

    try {

      logger.info("DocumentoRevocarPermisoRequest: {}", revocarPermisos);

      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      final String idAplicacion = auth.getName();

      DocumentObject documentObject = DocumentConverter.convertGuardarDocumentToDocumenObject(
          revocarPermisos, getRutaFicheroCarm(idAplicacion), bigFileTransferService);

      final ApplicationEntity aplicacion =
          applicationManagerService.findByIdAplicacion(idAplicacion);

      // Validamos si la unidad orgánica es válida
      UnitEntity unidad =
          validationBusinessService.findUnidadOrganicaByDir3(documentObject.getDir3());
      logger.info("Buscamos la unidad: {}", unidad);

      if (unidad == null) {
        response.setCodigo(String.valueOf(Constants.ORGANIC_UNIT_NOT_EXIST));
        response.setDescripcion(
            messageSource.getMessage(Constants.ORGANIC_UNIT_NOT_EXIST_DESC, null, locale));
        guardarDocumentoResponse.setResponse(response);
        return guardarDocumentoResponse;
      }

      List<DocumentEntity> documentoExistentes = documentManagerService.existDocument(
          documentObject.getDir3(), documentObject.getCsv(), documentObject.getIdEni());

      DocumentEntity documentEntity = null;
      if (CollectionUtils.isNotEmpty(documentoExistentes)) {
        documentEntity = documentoExistentes.get(0);
      }
      if (documentEntity == null) {
        response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENT_ERROR));
        response.setDescripcion(messageSource
            .getMessage(Constants.SAVE_DOCUMENT_REVOKE_APP_DOC_NOT_EXISTS_DESC, null, locale));
        guardarDocumentoResponse.setResponse(response);
        return guardarDocumentoResponse;
      }

      List<DocumentAplicacionEntity> documentAplicacionList = documentManagerService
          .findDocumentAplicacionByDocumentAndApplication(documentEntity, aplicacion);
      if (documentAplicacionList.isEmpty()) {
        response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENT_ERROR));
        response.setDescripcion(messageSource
            .getMessage(Constants.SAVE_DOCUMENT_REVOKE_APP_NOT_PERMISSION_DESC, null, locale));
        guardarDocumentoResponse.setResponse(response);
        return guardarDocumentoResponse;

      }

      documentManagerService.revocarPermisoConsultaAplicacion(documentAplicacionList);

      codigo = String.valueOf(Constants.SAVE_DOCUMENT_SUCCESS);
      descripcion =
          messageSource.getMessage(Constants.SAVE_DOCUMENT_REVOKE_APP_SUCCESS_DESC, null, locale);

      auditManagerService.create(aplicacion, documentEntity.getId(),
          revocarPermisos.parametrosAuditoria(), Operation.REVOCAR_CONSULTA.value());
    } catch (IOException | ServiceException | ConsumerWSException | BigDataTransferException e) {
      logger.error(ERROR_EN_SERVICIO_DE_ALMACENAMIENDO_DEL_DOCUMENTO, e);
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_DESC, null, locale);
    }

    response.setCodigo(codigo);
    response.setDescripcion(descripcion);

    guardarDocumentoResponse.setResponse(response);

    logger.info("[INI] Salimos de revocarPermisos.");
    return guardarDocumentoResponse;
  }

  private DocumentObject formaListaRestriccion(DocumentObject documentObject,
      DocumentEntity documentEntity) {

    // existe el documento, obtengo todas las restricciones por APP
    // y transformo la lista de nuevo para guardar
    List<DocumentAplicacionEntity> listBbdd = null;
    try {

      listBbdd = documentManagerService.findDocumentAplicacionByDocument(documentEntity);

    } catch (ServiceException e) {
      logger.error("Error al obtener la lista de restricciones: findDocumentAplicacionByDocument");
    }

    List<ApplicationEntity> listaApp = new ArrayList<>();

    for (DocumentAplicacionEntity app : listBbdd) {
      listaApp.add(app.getAplicacion());
    }
    for (ApplicationEntity app : documentObject.getAplicaciones()) {
      if (!listaApp.contains(app)) {
        listaApp.add(app);
      }
    }

    documentObject.setAplicaciones(listaApp);

    return documentObject;

  }



}
