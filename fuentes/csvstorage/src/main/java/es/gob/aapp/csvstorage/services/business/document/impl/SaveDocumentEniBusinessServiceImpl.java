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
import java.util.List;
import java.util.UUID;
import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.TipoDocumento;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.conversion.mtom.TipoDocumentoConversionInsideMtom;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.mtom.file.DocumentoEniFileInsideMtom;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.model.converter.document.DocumentConverter;
import es.gob.aapp.csvstorage.model.converter.document.DocumentResponseConverter;
import es.gob.aapp.csvstorage.model.object.document.DocumentEniObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentEniResponseObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.services.business.document.SaveDocumentEniBusinessService;
import es.gob.aapp.csvstorage.services.business.validation.ValPermissionBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.audit.AuditManagerService;
import es.gob.aapp.csvstorage.util.Util;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.constants.DocumentPermission;
import es.gob.aapp.csvstorage.util.file.FileUtil;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.document.model.ConvertirDocumentoEniRequest;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoEniRequest;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoEniResponse;
import es.gob.aapp.csvstorage.webservices.document.model.Response;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.GuardarDocumentoEniMtomRequest;

/**
 * Implementación de los servicios business de almacenamiento de documentos.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("saveDocumentEniBusinessService")
public class SaveDocumentEniBusinessServiceImpl extends DocumentBusinessService
    implements SaveDocumentEniBusinessService {

  private static final Logger LOG = LogManager.getLogger(SaveDocumentEniBusinessServiceImpl.class);
  private static final String FALTA_POR_RELLENAR_ALGUNO_DE_LOS_CAMPOS_OBLIGATORIOS =
      "Falta por rellenar alguno de los campos obligatorios. ";
  private static final String ERROR_EN_SERVICIO_DE_ALMACENAMIENDO_DEL_DOCUMENTO =
      "Error en servicio de almacenamiendo del documento. ";

  @Autowired
  private ValPermissionBusinessService valPermissionService;

  @Autowired
  private AuditManagerService auditManagerService;

  @Override
  public GuardarDocumentoEniResponse guardarENI(Object guardarDocumentoENI, String idAplicacion)
      throws CSVStorageException {

    LOG.info(String.format("[INI] Entramos en guardarENI. Aplicacion: %s; guardarDocumentoENI: %s",
        idAplicacion, guardarDocumentoENI));

    Response response = new Response();
    GuardarDocumentoEniResponse guardarDocumentoEniResponse = new GuardarDocumentoEniResponse();
    String codigo;
    String descripcion;
    long fdIni = FileUtil.getFdOpen();

    try {

      DocumentEniObject documentEniObject = createEniDocumentFromRequest(guardarDocumentoENI);
      LOG.info(String.format("Guardando ENI: idEni=%s; csv=%s; dir3=%s",
          documentEniObject.getIdEni(), documentEniObject.getCsv(), documentEniObject.getDir3()));

      ApplicationEntity application = applicationManagerService.findByIdAplicacion(idAplicacion);
      if (checkMandatoryFields(response, guardarDocumentoEniResponse, documentEniObject))
        return guardarDocumentoEniResponse;


      // Validamos si la unidad orgánica es válida
      UnitEntity unidad =
          validationBusinessService.findUnidadOrganicaByDir3(documentEniObject.getDir3());

      if (checkDir3(response, guardarDocumentoEniResponse, documentEniObject, unidad)) {
        return guardarDocumentoEniResponse;
      }

      // Validamos el documento ENI
      if (application.getValidarDocumentoENI()) {

        DataHandler source =
            new DataHandler(new ByteArrayDataSource(documentEniObject.getContenido(),
                documentEniObject.getMimeType()));

        String messageError = validationBusinessService.validarDocumentoEni(source);

        if (!StringUtils.isEmpty(messageError)) {
          descripcion =
              messageSource.getMessage(Constants.SAVE_DOCUMENTENI_VAL_ERROR_DESC, null, locale)
                  + " " + messageError;
          response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENTENI_VAL_ERROR));
          response.setDescripcion(descripcion);
          guardarDocumentoEniResponse.setResponse(response);

          return guardarDocumentoEniResponse;
        }
      }

      // Validamos si existe un documento con ese idENI y si ese idEni está ya asociado a un dir3
      List<DocumentEntity> documentEntList = documentManagerService.existDocument(
          documentEniObject.getDir3(), documentEniObject.getCsv(), documentEniObject.getIdEni());

      if (CollectionUtils.isNotEmpty(documentEntList)) {
        // Si existe algún de un documento se muestra un mensaje de error
        codigo = String.valueOf(Constants.SAVE_DOCUMENT_ALREADY_EXIST);
        descripcion =
            messageSource.getMessage(Constants.SAVE_DOCUMENT_ALREADY_EXIST_DESC, null, locale);

      } else {
        DocumentEntity documentEntity = new DocumentEntity();

        String path = StringUtils.isNotEmpty(documentEniObject.getContenidoPorRef())
            ? documentEniObject.getContenidoPorRef().substring(0,
                documentEniObject.getContenidoPorRef().lastIndexOf(Constants.FILE_SEPARATOR))
            : DocumentConverter.obtenerRutaFichero(this.getRutaFicheroCarm(idAplicacion),
                documentEniObject.getDir3());
        String uuid = UUID.randomUUID().toString();
        LOG.debug("Guardamos el documento ENI con el uuid: " + uuid);

        if (validationBusinessService.validarMetadatosEni(documentEniObject.getDocumentEniEntity(),
            documentEniObject.getDir3(), response)) {
          guardarDocumentoENI(documentEntity, documentEniObject, unidad, path, uuid, application,
              false);
          codigo = String.valueOf(Constants.SAVE_DOCUMENT_SUCCESS);
          descripcion =
              messageSource.getMessage(Constants.SAVE_DOCUMENT_SUCCESS_DESC, null, locale);

        } else {
          guardarDocumentoEniResponse.setResponse(response);
          return guardarDocumentoEniResponse;
        }
      }


    } catch (Exception e) {
      LOG.error(ERROR_EN_SERVICIO_DE_ALMACENAMIENDO_DEL_DOCUMENTO, e);
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_DESC, null, locale);
    }

    LOG.debug("response: " + codigo + " - " + descripcion);
    response.setCodigo(codigo);
    response.setDescripcion(descripcion);

    guardarDocumentoEniResponse.setResponse(response);

    printOpenFileDescriptor("guardarENI", fdIni);

    LOG.info("[FIN] Salimos de guardarENI. ");
    return guardarDocumentoEniResponse;
  }

  private boolean checkDir3(Response response,
      GuardarDocumentoEniResponse guardarDocumentoEniResponse, DocumentEniObject documentEniObject,
      UnitEntity unidad) {
    if (unidad == null) {
      LOG.error("La unidad " + documentEniObject.getDir3() + " no es válida");
      response.setCodigo(String.valueOf(Constants.ORGANIC_UNIT_NOT_EXIST));
      response.setDescripcion(
          messageSource.getMessage(Constants.ORGANIC_UNIT_NOT_EXIST_DESC, null, locale));

      guardarDocumentoEniResponse.setResponse(response);

      return true;
    }
    return false;
  }

  private boolean checkMandatoryFields(Response response,
      GuardarDocumentoEniResponse guardarDocumentoEniResponse,
      DocumentEniObject documentEniObject) {
    // Validamos si se han rellenado los campos obligatorio
    if (StringUtils.isEmpty(documentEniObject.getDir3())
        || documentEniObject.getDocumentEniEntity() == null) {
      LOG.error(FALTA_POR_RELLENAR_ALGUNO_DE_LOS_CAMPOS_OBLIGATORIOS);
      response.setCodigo(String.valueOf(Constants.MANDATORY_FIELDS));
      response
          .setDescripcion(messageSource.getMessage(Constants.MANDATORY_FIELDS_DESC, null, locale));

      guardarDocumentoEniResponse.setResponse(response);

      return true;

    }
    return false;
  }

  private DocumentEniObject createEniDocumentFromRequest(Object guardarDocumentoENI)
      throws ConsumerWSException, IOException, ServiceException {

    DocumentObject documentObject = new DocumentObject();

    TipoDocumento tipDocumento;
    if (guardarDocumentoENI instanceof GuardarDocumentoEniRequest) {
      GuardarDocumentoEniRequest gdEni = (GuardarDocumentoEniRequest) guardarDocumentoENI;
      tipDocumento = gdEni.getDocumento();
      documentObject.setCsv(gdEni.getCsv());
      documentObject.setDir3(gdEni.getDir3());
      documentObject.setParametrosAuditoria(gdEni.parametrosAuditoria());
    } else {
      GuardarDocumentoEniMtomRequest gdEni = (GuardarDocumentoEniMtomRequest) guardarDocumentoENI;
      tipDocumento =
          DocumentConverter.convertTipoDocumentoMtomToTipoDocumento(gdEni.getDocumento());
      documentObject.setCsv(gdEni.getCsv());
      documentObject.setDir3(gdEni.getDir3());
      documentObject.setParametrosAuditoria(gdEni.parametrosAuditoria());
    }

    documentObject.setTipoDocumento(tipDocumento);

    return DocumentConverter.convertGuardarDocumentoEniToDocumentEni(documentObject);
  }

  @Override
  public GuardarDocumentoEniResponse modificarENI(Object guardarDocumentoEniRequest,
      String idAplicacion) throws CSVStorageException {

    LOG.info("[INI] Entramos en modificarENI. idAplicacion:" + idAplicacion);

    Response response = new Response();
    GuardarDocumentoEniResponse guardarDocumentoEniResponse = new GuardarDocumentoEniResponse();
    String separator = System.getProperty("file.separator");

    String codigo;
    String descripcion;
    long fdIni = FileUtil.getFdOpen();
    try {

      DocumentEniObject documentEniObject =
          createEniDocumentFromRequest(guardarDocumentoEniRequest);
      LOG.info(String.format("Modificar ENI: idEni=%s; csv=%s; dir3=%s",
          documentEniObject.getIdEni(), documentEniObject.getCsv(), documentEniObject.getDir3()));
      ApplicationEntity application = applicationManagerService.findByIdAplicacion(idAplicacion);

      // Validamos si se han rellenado los campos obligatorio
      if (checkMandatoryFields(response, guardarDocumentoEniResponse, documentEniObject)) {
        return guardarDocumentoEniResponse;
      }

      // Validamos si la unidad orgánica es válida
      UnitEntity unidad =
          validationBusinessService.findUnidadOrganicaByDir3(documentEniObject.getDir3());

      if (checkDir3(response, guardarDocumentoEniResponse, documentEniObject, unidad))
        return guardarDocumentoEniResponse;


      // Validamos el documento ENI
      if (application.getValidarDocumentoENI()) {

        GuardarDocumentoEniResponse eniValidation =
            validateEniDocument(response, guardarDocumentoEniResponse, documentEniObject);
        if (eniValidation != null) {
          return eniValidation;
        }
      }

      boolean exists = false;
      // Validamos si existe un documento con ese idENI y si ese idEni está ya asociado a un dir3
      List<DocumentEntity> documentList = documentManagerService.existDocument(
          documentEniObject.getDir3(), documentEniObject.getCsv(), documentEniObject.getIdEni());
      List<DocumentEniEntity> documentEntList = null;
      if (CollectionUtils.isNotEmpty(documentList) && documentList.size() == 1) {
        documentEntList = documentEniManagerService.existDocument(documentEniObject.getDir3(),
            documentEniObject.getCsv(), documentEniObject.getIdEni());
        if (CollectionUtils.isNotEmpty(documentEntList) && documentEntList.size() == 1) {
          exists = true;
        }
      }

      if (exists && valPermissionService.permisoLectura(application,
          documentEntList.get(0).getDocumento(), null, null, null)) {

        DocumentEntity documentEntity = documentEntList.get(0).getDocumento();

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

        documentEniObject.getDocumentEniEntity().setId(documentEntList.get(0).getId());

        // Guardar los nuevos datos
        if (validationBusinessService.validarMetadatosEni(documentEniObject.getDocumentEniEntity(),
            documentEniObject.getDir3(), response)) {
          guardarDocumentoENI(documentEntity, documentEniObject, unidad, path, uuid, application,
              true);
          codigo = String.valueOf(Constants.SAVE_DOCUMENT_SUCCESS);
          descripcion =
              messageSource.getMessage(Constants.SAVE_DOCUMENT_SUCCESS_DESC, null, locale);

        } else {
          guardarDocumentoEniResponse.setResponse(response);
          return guardarDocumentoEniResponse;
        }



      } else {
        codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST);
        descripcion =
            messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale);
      }

    } catch (Exception e) {
      LOG.error(ERROR_EN_SERVICIO_DE_ALMACENAMIENDO_DEL_DOCUMENTO, e);
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_DESC, null, locale);
    }

    LOG.debug("response: " + codigo + " - " + descripcion);
    response.setCodigo(codigo);
    response.setDescripcion(descripcion);

    guardarDocumentoEniResponse.setResponse(response);

    printOpenFileDescriptor("modificarENI", fdIni);

    LOG.info("[FIN] Salimos de modificarENI. ");
    return guardarDocumentoEniResponse;
  }

  private GuardarDocumentoEniResponse validateEniDocument(Response response,
      GuardarDocumentoEniResponse guardarDocumentoEniResponse,
      DocumentEniObject documentEniObject) {

    String descripcion;
    DataHandler source = new DataHandler(
        new ByteArrayDataSource(documentEniObject.getContenido(), documentEniObject.getMimeType()));

    String messageError = validationBusinessService.validarDocumentoEni(source);

    if (!StringUtils.isEmpty(messageError)) {
      LOG.error("El documento ENI no es válido: " + messageError);
      descripcion =
          messageSource.getMessage(Constants.SAVE_DOCUMENTENI_VAL_ERROR_DESC, null, locale) + " "
              + messageError;

      response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENTENI_VAL_ERROR));
      response.setDescripcion(descripcion);
      guardarDocumentoEniResponse.setResponse(response);

      return guardarDocumentoEniResponse;
    }
    return null;
  }

  @Override
  public Object convertirAEni(ConvertirDocumentoEniRequest convertirDocumentoEni,
      String idAplicacion, boolean isMtom) throws CSVStorageException {

    LOG.info(
        "[INI] Entramos en convertirAEni. idAplicacion:" + idAplicacion + "; isMtom:" + isMtom);
    Object response;
    DocumentEniResponseObject obtenerDocumentoEniResponse = new DocumentEniResponseObject();
    String codigo;
    String descripcion;
    long fdIni = FileUtil.getFdOpen();
    try {
      // Validamos si ya existe un documento con este dir3 y csv
      String csv = convertirDocumentoEni.getCsv();
      String dir3 = convertirDocumentoEni.getDir3();
      String idEni = convertirDocumentoEni.getMetadatosEni().getIdentificador();

      // Validamos si se han rellenado los campos obligatorio
      if (StringUtils.isEmpty(csv) || StringUtils.isEmpty(dir3)) {
        LOG.error(FALTA_POR_RELLENAR_ALGUNO_DE_LOS_CAMPOS_OBLIGATORIOS);
        codigo = String.valueOf(Constants.MANDATORY_FIELDS);
        descripcion = messageSource.getMessage(Constants.MANDATORY_FIELDS_DESC, null, locale);
        return devolverError(isMtom, codigo, descripcion);
      }

      // Formateamos el csv si viene con guiones
      csv = Util.formatearCsvSinGuiones(csv);
      ApplicationEntity aplicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

      List<DocumentEntity> documentEntityList =
          documentManagerService.existDocument(dir3, csv, idEni);

      // Validamos si el documento existe
      if (documentEntityList == null || CollectionUtils.isEmpty(documentEntityList)) {
        LOG.error("El documento no existe");
        codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST);
        descripcion =
            messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale);
        return devolverError(isMtom, codigo, descripcion);
      }

      // Validamos que no haya más de un documento
      if (CollectionUtils.size(documentEntityList) > 1) {
        codigo = String.valueOf(Constants.CONVERT_DOCUMENT_ENI_ALREADY_EXIST);
        descripcion = messageSource
            .getMessage(Constants.CONVERT_DOCUMENT_ENI_ALREADY_EXIST_ID_ENI_DESC, null, locale);
        return devolverError(isMtom, codigo, descripcion);
      }

      // Si ya está en formato ENI:
      List<DocumentEniEntity> documentEniEntityList =
          documentEniManagerService.existDocument(dir3, csv, idEni);

      if (documentEniEntityList != null && !documentEniEntityList.isEmpty()) {
        LOG.warn("El documento ya es de tipo ENI");
        codigo = String.valueOf(Constants.CONVERT_DOCUMENT_ENI_ALREADY_EXIST);
        descripcion = messageSource.getMessage(Constants.CONVERT_DOCUMENT_ENI_ALREADY_EXIST_DESC,
            null, locale);

        return devolverError(isMtom, codigo, descripcion);
      }

      DocumentEntity documentEntity = documentEntityList.get(0);

      String separator = "/";
      String path;
      if (documentEntity.getUuid() != null) {
        path = documentEntity.getRutaFichero() + separator + documentEntity.getUuid();
      } else {
        path = documentEntity.getRutaFichero();
      }

      LOG.debug("Obtenemos el contenido. ");
      byte[] content =
          DocumentConverter.obtenerContenido(path, documentEntity.getContenidoPorRef());

      // validamos la Unidad orgánica
      UnitEntity unidad = validationBusinessService.findUnidadOrganicaByDir3(dir3);

      if (unidad == null) {

        obtenerDocumentoEniResponse.setCodigo(String.valueOf(Constants.ORGANIC_UNIT_NOT_EXIST));
        obtenerDocumentoEniResponse.setDescripcion(
            messageSource.getMessage(Constants.ORGANIC_UNIT_NOT_EXIST_DESC, null, locale));

        response = DocumentResponseConverter
            .convertObjectToConvertirDocumentoEniResponse(obtenerDocumentoEniResponse, isMtom);

        return response;
      }

      DataHandler source =
          new DataHandler(new ByteArrayDataSource(content, documentEntity.getTipoMINE()));

      TipoDocumentoConversionInsideMtom.MetadatosEni metadatosEni =
          DocumentConverter.metadatosToInside(convertirDocumentoEni.getMetadatosEni());

      DocumentoEniFileInsideMtom documentoEniFileInside =
          convertirDocumentoAEni(metadatosEni, source, csv);

      // Convertimos el TipoDocumentoMtom devuelto por GInside a un TipoDocumento
      TipoDocumento tipoDocumento = DocumentConverter
          .convertTipoDocumentoMtomToTipoDocumento(documentoEniFileInside.getDocumentoMtom());

      DocumentObject documentObject = new DocumentObject();
      documentObject.setTipoDocumento(tipoDocumento);
      documentObject.setContenido(
          IOUtils.toByteArray(documentoEniFileInside.getDocumentoEniBytes().getInputStream()));
      documentObject.setCsv(dir3);
      documentObject.setDir3(dir3);
      if (documentEntity.getTipoPermiso() != null) {
        documentObject
            .setTipoPermiso(DocumentPermission.getPermission(documentEntity.getTipoPermiso()));
      }

      // Creamos el objeto DocumentEniObject que vamos a utilizar para guardar el documento ENI en
      // la NAS y en la bbdd
      DocumentEniObject documentEniObject =
          DocumentConverter.convertGuardarDocumentoEniToDocumentEni(documentObject);

      String uuid;
      if (documentEntity.getUuid() != null) {
        path = documentEntity.getRutaFichero();
        uuid = documentEntity.getUuid();
      } else {
        path = documentEntity.getRutaFichero().substring(0,
            documentEntity.getRutaFichero().lastIndexOf(separator));
        uuid = documentEntity.getRutaFichero()
            .substring(documentEntity.getRutaFichero().lastIndexOf(separator) + 1);
      }

      LOG.debug("Guardamos el documento ENI con uuid: " + uuid);
      documentEniObject.setCalledFrom(Constants.CALLED_FROM_CONVERTIR_A_ENI);
      documentEniObject.setParametrosAuditoria(convertirDocumentoEni.parametrosAuditoria());
      guardarDocumentoENI(documentEntity, documentEniObject, unidad, path, uuid, aplicacion, true);

      if (isMtom) {
        obtenerDocumentoEniResponse.setDocumento(documentoEniFileInside.getDocumentoMtom());
      } else {
        obtenerDocumentoEniResponse.setDocumento(tipoDocumento);
      }

      codigo = String.valueOf(Constants.CONVERT_DOCUMENT_ENI_SUCCESS);
      descripcion =
          messageSource.getMessage(Constants.CONVERT_DOCUMENT_ENI_SUCCESS_DESC, null, locale);
    } catch (Exception e) {
      LOG.error("Error en servicio de conversión a documento ENI. ", e);
      codigo = String.valueOf(Constants.QUERY_DOCUMENT_ERROR);
      descripcion =
          messageSource.getMessage(Constants.CONVERT_DOCUMENT_ENI_ERROR_DESC, null, locale);

    }

    obtenerDocumentoEniResponse.setCodigo(codigo);
    obtenerDocumentoEniResponse.setDescripcion(descripcion);

    response = DocumentResponseConverter
        .convertObjectToConvertirDocumentoEniResponse(obtenerDocumentoEniResponse, isMtom);

    printOpenFileDescriptor("convertirAEni", fdIni);

    LOG.info("[FIN] Salimos de convertirAEni. ");
    return response;
  }

  private Object devolverError(boolean isMtom, String codigo, String descripcion) {
    DocumentEniResponseObject obtenerDocumentoEniResponse = new DocumentEniResponseObject();
    obtenerDocumentoEniResponse.setCodigo(codigo);
    obtenerDocumentoEniResponse.setDescripcion(descripcion);

    return DocumentResponseConverter
        .convertObjectToConvertirDocumentoEniResponse(obtenerDocumentoEniResponse, isMtom);
  }

}
