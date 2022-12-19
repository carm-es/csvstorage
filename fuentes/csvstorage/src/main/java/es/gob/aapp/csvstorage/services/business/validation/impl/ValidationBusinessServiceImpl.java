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

package es.gob.aapp.csvstorage.services.business.validation.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.Detalle;
import es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.DocumentoEntradaMtom;
import es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.RespuestaValidacionENI;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.TipoDocumento;
import es.gob.aapp.csvstorage.client.ws.eni.firma.Firmas;
import es.gob.aapp.csvstorage.client.ws.eni.firma.TipoFirma;
import es.gob.aapp.csvstorage.client.ws.eni.firma.TipoFirmasElectronicas;
import es.gob.aapp.csvstorage.consumer.eeutil.EeutilConsumer;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.model.converter.document.DocumentConverter;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.services.business.validation.ValPermissionBusinessService;
import es.gob.aapp.csvstorage.services.business.validation.ValidationBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.document.DocumentEniManagerService;
import es.gob.aapp.csvstorage.services.manager.document.DocumentManagerService;
import es.gob.aapp.csvstorage.services.manager.unit.UnitManagerService;
import es.gob.aapp.csvstorage.util.MimeType;
import es.gob.aapp.csvstorage.util.Util;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.constants.DocumentPermission;
import es.gob.aapp.csvstorage.util.constants.DocumentRestriccion;
import es.gob.aapp.csvstorage.util.xml.DocumentoENIUtils;
import es.gob.aapp.csvstorage.webservices.document.model.Response;

/**
 * Implementación de los servicios business de almacenamiento de informes de firma.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("validationBusinessService")
public class ValidationBusinessServiceImpl implements ValidationBusinessService {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(ValidationBusinessServiceImpl.class);
  public static final String DOCUMENTO_NO_VALIDO = "Documento no válido. response=";

  @Autowired
  private UnitManagerService unitManagerService;

  @Autowired
  private EeutilConsumer eeutilConsumer;

  @Autowired
  private ValPermissionBusinessService valPermissionBusinessService;

  @Autowired
  private DocumentEniManagerService documentEniManagerService;

  @Autowired
  private DocumentManagerService documentManagerService;

  @Autowired
  protected MessageSource messageSource;

  protected static final Locale locale = LocaleContextHolder.getLocale();

  @Override
  public String validarDocumentoEni(DataHandler contenido) {
    LOG.info("[INI] Entramos de validarDocumentoEni. ");
    StringBuilder textError = new StringBuilder("");
    try {

      DocumentoEntradaMtom documentoEntradaMtom = new DocumentoEntradaMtom();
      documentoEntradaMtom.setContenido(contenido);
      documentoEntradaMtom.setMime("application/xml");

      LOG.info("Se procede a validar contra Eeutil. ");
      RespuestaValidacionENI respuestaValidacionENI =
          eeutilConsumer.validarDocumentoENI(documentoEntradaMtom);

      if (!respuestaValidacionENI.getGlobal().contains(Constants.EEUTIL_CODIGO_000)) {

        for (Detalle resul : respuestaValidacionENI.getDetalle()) {
          if (!resul.getCodigoRespuesta().contains(Constants.EEUTIL_CODIGO_000)) {
            textError.append(resul.getCodigoRespuesta()).append(" - ")
                .append(resul.getDetalleRespuesta()).append("\n");
          }
        }
      }

      LOG.info("[FIN] Salimos de validarDocumentoEni. ");

    } catch (ConsumerWSException e) {
      LOG.error("Error en validarDocumentoEni: " + e.getMessage(), e);
      textError.append("Se ha producido un error en el proceso de validación del documento eni");
    }
    return textError.toString();
  }

  @Override
  public UnitEntity findUnidadOrganicaByDir3(String dir3) throws ConsumerWSException {
    UnitEntity unit = null;
    try {
      LOG.info("[INI] Entramos en findUnidadOrganicaByDir3. dir3:" + dir3);

      List<UnitEntity> unitList = unitManagerService.findUnitsByDir3(dir3);

      // Si la unidad no está guardada en bbdd lanzamos una excepción. Qué excepción???
      if (!unitList.isEmpty()) {
        unit = unitList.get(0);
      }

    } catch (ServiceException e) {
      LOG.error("Error en findUnidadOrganicaByDir3: " + e.getMessage(), e);
    }
    LOG.info("[FIN] Salimos de findUnidadOrganicaByDir3. UnidadOrganica:" + unit);
    return unit;
  }

  @Override
  public boolean validarMetadatosEni(DocumentEniEntity documentEniEntity, String dir3,
      Response response) throws ServiceException {
    /*
     * //NO ES NECESARIO COMPROBAR QUE EL DOCUMENTO ORIGEN EXISTE EN CSVSTORAGE, PUEDE EXISTIR EN
     * OTRO SITIO
     * 
     * boolean isValid = true; //Si viene informado el metadato IdentificadorDocumentoOrigen
     * validamos si existe ese csv en bbdd if (documentEniEntity != null &&
     * StringUtils.isNotEmpty(documentEniEntity.getIdDocumentoOrigen())) {
     * 
     * List<DocumentEniEntity> documentEniEntityList =
     * documentEniManagerService.findByAll(documentEniEntity.getIdDocumentoOrigen(), null, dir3);
     * 
     * if (documentEniEntityList == null || documentEniEntityList.isEmpty()) {
     * LOG.warn("No existe el identificador origen al que hace referencia: " +
     * documentEniEntity.getIdDocumentoOrigen());
     * response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENTENI_VAL_ERROR));
     * response.setDescripcion(messageSource.getMessage(Constants.
     * SAVE_DOCUMENTENI_ERROR_ID_ORIGIN_DESC, null, locale)); isValid = false; } } return isValid;
     */
    return true;
  }

  public boolean validarDatosObligatorios(Response response, DocumentObject documentObject)
      throws ConsumerWSException {
    boolean isValido = true;
    // Validamos si se han rellenado los campos obligatorio
    if (StringUtils.isEmpty(documentObject.getDir3())
        || (documentObject.getContenido() == null && documentObject.getContenidoPorRef() == null)
        || (StringUtils.isEmpty(documentObject.getCsv())
            && StringUtils.isEmpty(documentObject.getIdEni()))) {
      // Falta por rellenar alguno de los campos obligatorios
      LOG.error("Falta por rellenar alguno de los campos obligatorios. ");
      response.setCodigo(String.valueOf(Constants.MANDATORY_FIELDS));
      response
          .setDescripcion(messageSource.getMessage(Constants.MANDATORY_FIELDS_DESC, null, locale));
      isValido = false;
    }

    if (isValido && documentObject.getTipoPermiso() != null
        && documentObject.getRestricciones().contains(DocumentRestriccion.RESTRINGIDO_NIF.getCode())
        && documentObject.getNifs().isEmpty()) {
      // Validamos que se han introducido al menos un NIF si el tipo de
      LOG.info("No se ha adjuntado ningun NIF");
      response.setCodigo(String.valueOf(Constants.EMPTY_NIFS_ERROR));
      response
          .setDescripcion(messageSource.getMessage(Constants.EMPTY_NIFS_ERROR_DESC, null, locale));

      isValido = false;

    }

    if (isValido && !Util.compruebaNif(documentObject.getNifs())) {
      response.setCodigo(String.valueOf(Constants.ERROR_NIFS_ERROR));
      response
          .setDescripcion(messageSource.getMessage(Constants.ERROR_NIFS_ERROR_DESC, null, locale));
      isValido = false;
    }


    if (isValido && (!validaPermisosRestringido(response, documentObject)
        || !validaPermisosRestringidoID(response, documentObject))) {

      isValido = false;

    }

    LOG.info("Validaciones: " + isValido);
    return isValido;
  }


  public boolean validarDatosObligatoriosPermisos(Response response,
      DocumentObject documentObject) {

    boolean isValido = true;

    // Validamos si se han rellenado los campos obligatorio
    if (StringUtils.isEmpty(documentObject.getDir3())
        || (StringUtils.isEmpty(documentObject.getCsv())
            && StringUtils.isEmpty(documentObject.getIdEni()))) {

      LOG.error("Falta por rellenar alguno de los campos obligatorios. ");
      response.setCodigo(String.valueOf(Constants.MANDATORY_FIELDS));
      response
          .setDescripcion(messageSource.getMessage(Constants.MANDATORY_FIELDS_DESC, null, locale));
      isValido = false;
    }

    if (isValido && (documentObject.getAplicaciones() == null
        || documentObject.getAplicaciones().isEmpty())) {
      // Validamos que se han introducido al menos un id de aplicacion si el tipo de
      LOG.info("No se ha adjuntado ningun permiso de aplicaciones");
      response.setCodigo(String.valueOf(Constants.EMPTY_FIELDS_APPLICATIONS_QUERY));
      response.setDescripcion(
          messageSource.getMessage(Constants.EMPTY_FIELDS_APPLICATIONS_QUERY_DES, null, locale));
      isValido = false;

    }

    LOG.info("Validaciones: " + isValido);
    return isValido;
  }


  private boolean validaPermisosRestringido(Response response, DocumentObject documentObject) {

    if (documentObject.getTipoPermiso() != null
        && documentObject.getTipoPermiso().getCode() == DocumentPermission.RESTRINGIDO.getCode()
        && (documentObject.getRestricciones() == null
            || documentObject.getRestricciones().isEmpty())) {

      LOG.info("No se ha adjuntado los permisos restringidos");
      response.setCodigo(String.valueOf(Constants.EMPTY_REST_ERROR));
      response
          .setDescripcion(messageSource.getMessage(Constants.EMPTY_REST_ERROR_DESC, null, locale));
      return false;
    }

    return true;
  }

  private boolean validaPermisosRestringidoID(Response response, DocumentObject documentObject) {

    if (documentObject.getRestricciones().contains(DocumentRestriccion.RESTRINGIDO_ID.getCode())
        && (documentObject.getTipoIds() == null || documentObject.getTipoIds().isEmpty()
            || documentObject.getTipoIds().contains(null))) {

      LOG.info("No se ha adjuntado los permisos para restricciones por ID");
      response.setCodigo(String.valueOf(Constants.EMPTY_REST_ERROR));
      response
          .setDescripcion(messageSource.getMessage(Constants.EMPTY_IDS_ERROR_DESC, null, locale));
      return false;
    }

    return true;
  }

  @Override
  public DocumentEntity validacionModificado(Response response, ApplicationEntity aplicacion,
      DocumentObject documentObject) throws ConsumerWSException, ServiceException {

    DocumentEntity documentoExistente = null;
    boolean permitirCsvDuplicado = false;

    LOG.info("Se validan los datos obligatorios: " + documentObject);
    if (!validarDatosObligatorios(response, documentObject)) {
      LOG.info("Se ha producido un error");
      return documentoExistente;
    }

    // Obtenemos la extensión a partir del tipo MIME definido
    String extension =
        MimeType.getInstance().extractExtensionFromMIMEType(documentObject.getMimeType());

    if (StringUtils.isEmpty(extension)) {
      LOG.error("El tipo MIME " + documentObject.getMimeType() + " no es válido");
      response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENTENI_VAL_ERROR));
      response.setDescripcion(
          messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_MIMETYPE, null, locale));
      return documentoExistente;
    }


    LOG.info("Validamos si la aplicación permite guardar solo documentos PDF y ENI ");
    if (/*
         * StringUtils.isEmpty(documentObject.getContenidoPorRef()) &&
         */ aplicacion.getDocumentosPdfYEni()) {

      boolean isPdf = isDocumentoPDF(response, documentObject.getMimeType(),
          documentObject.getCsv(), (documentObject.getContenidoPorRef() != null));
      boolean isEni = false;
      if (!isPdf) {
        if (StringUtils.isNotEmpty(response.getCodigo())) {
          return documentoExistente;
        }

        isEni = isDocumentoEni(response, documentObject, documentObject.getMimeType(),
            aplicacion.getValidarDocumentoENI());
        if (!isEni && StringUtils.isNotEmpty(response.getCodigo())) {
          return documentoExistente;
        }
      }

      if (!isPdf && !isEni) {
        response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENTENI_VAL_ERROR));
        response.setDescripcion(
            messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_MIMETYPE_PDF_CSV, null, locale));
        return documentoExistente;
      }
      permitirCsvDuplicado = true;
    }

    LOG.info("DIR3: " + documentObject.getDir3() + " CSV: " + documentObject.getCsv() + " IDENI: "
        + documentObject.getIdEni());
    List<DocumentEntity> documentoExistentes = documentManagerService.existDocument(
        documentObject.getDir3(), documentObject.getCsv(), documentObject.getIdEni());

    documentoExistente =
        validarModificacion(documentObject, documentoExistentes, aplicacion, permitirCsvDuplicado);

    if (documentoExistente == null) {
      response.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST));
      response.setDescripcion(
          messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale));
    } else {

      // validamos si la applicacion es la propietaria del documento para su modificacion
      if (!valPermissionBusinessService.permisoModificar(aplicacion, documentoExistente)) {
        response.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_NOT_PERMISSION));
        response.setDescripcion(
            messageSource.getMessage(Constants.DOCUMENT_NOT_PERMISSION_DESC, null, locale));
        documentoExistente = null;
      }

    }

    return documentoExistente;
  }

  @Override
  public DocumentEntity validacionOtorgarPermiso(Response response, ApplicationEntity aplicacion,
      DocumentObject documentObject) throws ConsumerWSException, ServiceException {

    LOG.info(String.format("Procedemos a validar otorgar permisos a aplicaciones", response,
        aplicacion, documentObject));

    DocumentEntity documentoExistente = null;

    LOG.info("Se validan los datos obligatorios: " + documentObject);
    if (!validarDatosObligatoriosPermisos(response, documentObject)) {
      LOG.info("Se ha producido un error");
      return documentoExistente;
    }

    LOG.info("DIR3: " + documentObject.getDir3() + " CSV: " + documentObject.getCsv() + " IDENI: "
        + documentObject.getIdEni());
    List<DocumentEntity> documentoExistentes = documentManagerService.existDocument(
        documentObject.getDir3(), documentObject.getCsv(), documentObject.getIdEni());


    if (CollectionUtils.isNotEmpty(documentoExistentes)
        && CollectionUtils.size(documentoExistentes) == 1) {
      documentoExistente = documentoExistentes.get(0);
    }

    if (documentoExistente == null) {
      response.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST));
      response.setDescripcion(
          messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale));
    } else {

      // validamos si la aplicacion consultora tiene el permiso de aplicacion para otorgar permisos
      if (!valPermissionBusinessService.permisOtorgar(aplicacion, documentoExistente)) {
        response.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_NOT_PERMISSION));
        response.setDescripcion(
            messageSource.getMessage(Constants.DOCUMENT_NOT_PERMISSION_DESC, null, locale));
        documentoExistente = null;
      }


    }

    LOG.info(String.format("finalizamos validar otorgar permisos a aplicaciones", response,
        aplicacion, documentObject));

    return documentoExistente;
  }

  @Override
  public boolean validacionGuardado(Response response, ApplicationEntity aplicacion,
      DocumentObject documentObject) throws ConsumerWSException, ServiceException {

    LOG.info(String.format("Validación de guardado. response=%s; aplicacion=%s; documentObject=%s",
        response, aplicacion, documentObject));

    boolean permitirCsvDuplicado = false;

    if (!validarDatosObligatorios(response, documentObject)) {
      return false;
    }

    // Obtenemos la extensión a partir del tipo MIME definido
    String extension =
        MimeType.getInstance().extractExtensionFromMIMEType(documentObject.getMimeType());

    if (StringUtils.isEmpty(extension)) {
      LOG.error("El tipo MIME " + documentObject.getMimeType() + " no es valido");
      response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENTENI_VAL_ERROR));
      response.setDescripcion(
          messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_MIMETYPE, null, locale));
      return false;
    }

    // Validamos si la aplicación permite guardar solo documentos PDF y ENI
    if (/* StringUtils.isEmpty(documentObject.getContenidoPorRef()) && */ aplicacion
        .getDocumentosPdfYEni()) {
      LOG.info("Documento con contenido por referencia: " + documentObject.getContenidoPorRef());
      boolean isPdf = isDocumentoPDF(response, documentObject.getMimeType(),
          documentObject.getCsv(), (documentObject.getContenidoPorRef() != null));
      boolean isEni = false;

      if (!isPdf) {

        if (StringUtils.isNotEmpty(response.getCodigo())) {
          LOG.info(DOCUMENTO_NO_VALIDO + response);
          return false;
        }

        isEni = isDocumentoEni(response, documentObject, documentObject.getMimeType(),
            aplicacion.getValidarDocumentoENI());
        if (!isEni && StringUtils.isNotEmpty(response.getCodigo())) {
          LOG.info(DOCUMENTO_NO_VALIDO + response);
          return false;
        }
      }

      if (StringUtils.isEmpty(documentObject.getContenidoPorRef())) {
        if (!isPdf && !isEni) {
          response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENTENI_VAL_ERROR));
          response.setDescripcion(
              messageSource.getMessage(Constants.SAVE_DOCUMENT_ERROR_PDF_O_ENI, null, locale));
          LOG.info(DOCUMENTO_NO_VALIDO + response);
          return false;
        }

        permitirCsvDuplicado = true;
      }
    }

    List<DocumentEntity> documentoExistentes = documentManagerService.existDocument(
        documentObject.getDir3(), documentObject.getCsv(), documentObject.getIdEni());

    return validarAlta(response, documentObject, documentoExistentes, permitirCsvDuplicado);

  }

  private boolean validarTipoDocumento(Response response, TipoDocumento tipoDocumento,
      DocumentObject documentObject) {
    LOG.info("Entra en validarTipoDocumento");
    boolean isValid = true;
    String csvTipoDocumento = null;
    Firmas firma = tipoDocumento.getFirmas();
    if (firma != null && firma.getFirma() != null && !firma.getFirma().isEmpty()) {

      for (TipoFirmasElectronicas tipoFirmasElectronicas : firma.getFirma()) {
        if (tipoFirmasElectronicas.getTipoFirma().equals(TipoFirma.TF_01)) {
          String firmaCsv = tipoFirmasElectronicas.getContenidoFirma().getCSV().getValorCSV();
          csvTipoDocumento = Util.formatearCsvSinGuiones(firmaCsv);
        }
      }

    }

    documentObject.setTipoDocumento(tipoDocumento);
    documentObject.setIsEni(true);

    if (StringUtils.isEmpty(documentObject.getCsv())
        && StringUtils.isNotEmpty(documentObject.getCsv())) {
      documentObject.setCsv(csvTipoDocumento);

    } else if (StringUtils.isEmpty(documentObject.getContenidoPorRef())
        && StringUtils.isNotEmpty(csvTipoDocumento)
        && StringUtils.isNotEmpty(documentObject.getCsv())
        && !Util.formatearCsvSinGuiones(documentObject.getCsv()).equals(csvTipoDocumento)) {
      isValid = false;
      // Si no coicide en CSV pasado como parametro y el del documento ENI no es válido
      response.setCodigo(String.valueOf(Constants.CONVERT_DOCUMENT_ENI_INVALID));
      response.setDescripcion(messageSource
          .getMessage(Constants.CONVERT_DOCUMENT_ENI_ERROR_CSV_INVALID_DESC, null, locale));

    }

    if (StringUtils.isEmpty(documentObject.getIdEni())) {
      documentObject.setIdEni(tipoDocumento.getMetadatos().getIdentificador());

    }
    if (StringUtils.isEmpty(documentObject.getContenidoPorRef())
        && StringUtils.isNotEmpty(documentObject.getIdEni())
        && !tipoDocumento.getMetadatos().getIdentificador().equals(documentObject.getIdEni())) {
      isValid = false;
      // Si no coicide en idENI pasado como parametro y el del documento ENI no es válido
      response.setCodigo(String.valueOf(Constants.CONVERT_DOCUMENT_ENI_INVALID));
      response.setDescripcion(messageSource
          .getMessage(Constants.CONVERT_DOCUMENT_ENI_ERROR_ID_INVALID_DESC, null, locale));
    }

    return isValid;
  }

  private DocumentEntity validarModificacion(DocumentObject nuevoDocumento,
      List<DocumentEntity> documentosExistentes, ApplicationEntity aplicacion,
      boolean permitirCsvDuplicado) {
    LOG.info("validarModificacion: nuevoDocumento: " + nuevoDocumento + " documentosExistentes "
        + documentosExistentes + " aplicacion " + aplicacion);
    DocumentEntity documentoExistente = null;

    // Si es una modificación se valida que exista el documento
    if (CollectionUtils.isNotEmpty(documentosExistentes)
        && CollectionUtils.size(documentosExistentes) == 1 && valPermissionBusinessService
            .permisoLectura(aplicacion, documentosExistentes.get(0), null, null, null)) {
      documentoExistente = documentosExistentes.get(0);
    } else if (permitirCsvDuplicado && CollectionUtils.isNotEmpty(documentosExistentes)
        && CollectionUtils.size(documentosExistentes) == 2) {
      for (DocumentEntity doc : documentosExistentes) {
        if (doc.getTipoMINE().equals(nuevoDocumento.getMimeType())
            && valPermissionBusinessService.permisoLectura(aplicacion, doc, null, null, null)) {
          documentoExistente = doc;
        }
      }
    }

    return documentoExistente;
  }

  private boolean validarAlta(Response response, DocumentObject nuevoDocumento,
      List<DocumentEntity> documentosExistentes, boolean permitirCsvDuplicado) {

    DocumentEntity documentoExistente;

    if (CollectionUtils.isEmpty(documentosExistentes)) {
      LOG.info(String.format("No existen otros docs con CSV=%s e idENI=%s", nuevoDocumento.getCsv(),
          nuevoDocumento.getIdEni()));
      return true; // no hay que validar los duplicados
    } else if (documentosExistentes.size() > 2) {
      LOG.fatal(String.format("Atención el documento con CSV=%s e idENI=%s está repetido %d veces",
          nuevoDocumento.getCsv(), nuevoDocumento.getIdEni(), documentosExistentes.size()));
      response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENT_ALREADY_EXIST)); // No valido
      response.setDescripcion(
          messageSource.getMessage(Constants.SAVE_DOCUMENT_ALREADY_EXIST_DESC, null, locale));
      return false;
    } else if (CollectionUtils.size(documentosExistentes) == 2) {
      LOG.warn(String.format("Atención el documento con CSV=%s e idENI=%s está repetido %d veces",
          nuevoDocumento.getCsv(), nuevoDocumento.getIdEni(), documentosExistentes.size()));
      response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENT_ALREADY_EXIST)); // No valido
      response.setDescripcion(
          messageSource.getMessage(Constants.SAVE_DOCUMENT_ALREADY_EXIST_DESC, null, locale));
      return false;
    } else {// Si tiene solo 1
      documentoExistente = documentosExistentes.get(0);
    }

    // Se permite que existan dos documentos con el mismo csv siempre y cuando uno sea un PDF y otro
    // ENI
    if (existeCsvDuplicado(nuevoDocumento, permitirCsvDuplicado, documentoExistente)
        && tienenMimeTypeValidoParaCsvDuplicados(nuevoDocumento, documentoExistente)) {
      LOG.info("Permitimos insertar otra versión del documento");
      return true;
    }

    response.setCodigo(String.valueOf(Constants.SAVE_DOCUMENT_ALREADY_EXIST)); // No valido
    response.setDescripcion(
        messageSource.getMessage(Constants.SAVE_DOCUMENT_ALREADY_EXIST_DESC, null, locale));
    LOG.info(DOCUMENTO_NO_VALIDO + response);
    return false;
  }

  private boolean tienenMimeTypeValidoParaCsvDuplicados(DocumentObject nuevoDocumento,
      DocumentEntity documentoExistente) {
    return (Arrays.asList(Constants.MIME_TYPE_ENI).contains(nuevoDocumento.getMimeType())
        && Constants.MIME_TYPE_PDF.equals(documentoExistente.getTipoMINE()))
        || (Constants.MIME_TYPE_PDF.equals(nuevoDocumento.getMimeType())
            && Arrays.asList(Constants.MIME_TYPE_ENI).contains(documentoExistente.getTipoMINE()));
  }

  private boolean existeCsvDuplicado(DocumentObject nuevoDocumento, boolean permitirCsvDuplicado,
      DocumentEntity documentoExistente) {
    return permitirCsvDuplicado && documentoExistente != null
        && StringUtils.isNotEmpty(documentoExistente.getCsv())
        && StringUtils.isNotEmpty(nuevoDocumento.getCsv());
  }

  private boolean isDocumentoEni(Response response, DocumentObject documentObject, String mimeType,
      boolean validarEni) {

    LOG.info("Comprobando si es un documento ENI. CSV:" + documentObject.getCsv() + "; idEni:"
        + documentObject.getIdEni());

    if (!Arrays.asList(Constants.MIME_TYPE_ENI).contains(mimeType)) {
      return false;
    }

    boolean isEni = false;
    TipoDocumento tipoDocumento;

    try {
      byte[] contenido = null;
      if (StringUtils.isEmpty(documentObject.getContenidoPorRef())) {
        contenido = documentObject.getContenido();
      } else {
        contenido =
            DocumentoENIUtils.documentoEniContentRemover(documentObject.getContenidoPorRef());
      }

      if (contenido.length < (8 * 1024 * 1024)) {
        tipoDocumento = DocumentConverter.getTipoDocumentoFromXmlFile(contenido);


        LOG.info("ValidarEni=" + validarEni);
        if (validarEni) {
          DataHandler source = new DataHandler(new ByteArrayDataSource(contenido, mimeType));

          String messageError = validarDocumentoEni(source);

          if (StringUtils.isNotEmpty(messageError)) {
            LOG.info("No es un documento ENI válido" + messageError);
            // Si no coicide en idENI pasado como parametro y el del documento ENI no es válido
            response.setCodigo(String.valueOf(Constants.CONVERT_DOCUMENT_ENI_INVALID));
            response.setDescripcion(messageSource
                .getMessage(Constants.CONVERT_DOCUMENT_ENI_ERROR_CSV_INVALID_DESC, null, locale));
            return false;
          }
        }

        if (validarTipoDocumento(response, tipoDocumento, documentObject)) {
          isEni = true;
        }

      }

    } catch (Exception e) {
      LOG.info("No es un documento ENI", e);
    }

    return isEni;
  }

  private boolean isDocumentoPDF(Response response, String mimeType, String csv,
      boolean contenidoPorReferencia) {
    boolean isPDF = false;
    if (Constants.MIME_TYPE_PDF.equals(mimeType)) {
      if (contenidoPorReferencia) {
        isPDF = true;
      } else if (StringUtils.isEmpty(csv)) {
        response.setCodigo(String.valueOf(Constants.MANDATORY_FIELDS));
        response.setDescripcion(
            messageSource.getMessage(Constants.MANDATORY_FIELDS_DESC, null, locale));
      } else {
        isPDF = true;
      }

    }
    return isPDF;
  }



}
