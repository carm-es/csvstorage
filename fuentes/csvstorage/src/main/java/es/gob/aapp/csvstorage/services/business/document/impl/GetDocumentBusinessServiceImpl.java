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
import java.util.List;
import javax.activation.DataHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentAplicacionEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniOrganoEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentIdsEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentNifEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentRestriccionEntity;
import es.gob.aapp.csvstorage.dao.entity.reference.VReferenciasAppEntity;
import es.gob.aapp.csvstorage.model.converter.application.ApplicationConverter;
import es.gob.aapp.csvstorage.model.converter.document.DocumentConverter;
import es.gob.aapp.csvstorage.model.converter.document.DocumentResponseConverter;
import es.gob.aapp.csvstorage.model.object.document.DocumentEniResponseObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentResponseObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentTamanioResponseObject;
import es.gob.aapp.csvstorage.services.business.document.GetDocumentBusinessService;
import es.gob.aapp.csvstorage.services.business.validation.ValPermissionBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.exception.ValidationException;
import es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService;
import es.gob.aapp.csvstorage.services.manager.audit.AuditManagerService;
import es.gob.aapp.csvstorage.services.manager.reference.VReferenciasAppManagerService;
import es.gob.aapp.csvstorage.util.PdfUtils;
import es.gob.aapp.csvstorage.util.Util;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.constants.DocumentPermission;
import es.gob.aapp.csvstorage.util.constants.DocumentRestriccion;
import es.gob.aapp.csvstorage.util.constants.IdentificationType;
import es.gob.aapp.csvstorage.util.constants.Operation;
import es.gob.aapp.csvstorage.util.file.FileUtil;
import es.gob.aapp.csvstorage.util.xml.JAXBMarshaller;
import es.gob.aapp.csvstorage.webservices.document.model.Aplicacion;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultaResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarPermisosDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarPermisosDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ContenidoInfo;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoPermisoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoReferenciaResponse;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ListaAplicaciones;
import es.gob.aapp.csvstorage.webservices.document.model.ListaNifs;
import es.gob.aapp.csvstorage.webservices.document.model.ListaRestriccion;
import es.gob.aapp.csvstorage.webservices.document.model.ListaTipoIds;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoEniRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerTamanioDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.Response;
import es.gob.aapp.csvstorage.webservices.document.model.TamanioDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.reference.ObjectFactory;
import es.gob.aapp.csvstorage.webservices.document.model.reference.ObtenerReferenciaDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoAplicaciones;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoEmisor;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoIdentificaciones;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoIdentificacionesPorCertificado;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoIdentificador;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoNifs;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoPermisos;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoPrivado;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoPublico;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoReceptor;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoReferenciaDocumento;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoRestringido;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.DocumentoMtomStreamingResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.ObtenerDocumentoMtomStreamingResponse;

/**
 * Implementación de los servicios business de obtención de documentos.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("getDocumentBusinessService")
@PropertySource("file:${csvstorage.config.path}/csvstorage.properties")
public class GetDocumentBusinessServiceImpl extends DocumentBusinessService
    implements GetDocumentBusinessService {

  private static final Logger LOG = Logger.getLogger(GetDocumentBusinessServiceImpl.class);
  private static final String SEPARATOR = System.getProperty("file.separator");
  private static final String OBTENEMOS_EL_CONTENIDO = "Obtenemos el contenido. ";
  private static final String ERROR_EN_SERVICIO_DE_OBTENER_DOCUMENTO =
      "Error en servicio de obtener documento. ";
  private static final String ERROR_EN_SERVICIO_DE_CONSULTA_DEL_DOCUMENTO =
      "Error en servicio de consulta del documento. ";
  private static final String FIN_SALIMOS_DE_CONSULTAR = "[FIN] Salimos de consultar. ";

  @Value("${url.consultar.sistema.referencia}")
  protected String direccionSistemaReferencia;

  @Autowired
  private AuditManagerService auditManagerService;

  @Autowired
  private ValPermissionBusinessService valPermissionBusinessService;

  @Autowired
  private BigFileTransferService bigFileTransferService;

  @Autowired
  private PdfUtils pdfUtils;

  @Autowired
  private ApplicationManagerService applicationManagerService;

  @Autowired
  private VReferenciasAppManagerService referenciasAppService;


  @Override
  public Object obtener(DocumentObject documentObject, Class<?> className, String url)
      throws CSVStorageException {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    LOG.info("[INI] Entramos en obtener documentObject: " + documentObject + "; className:"
        + className + "; url: " + url + "; idAplicacion:" + auth.getName());

    Object obtenerDocumentoResponse;
    DocumentResponseObject documentResponseObject = new DocumentResponseObject();
    long fdIni = FileUtil.getFdOpen();

    DataHandler content = null;
    String mimeType = null;
    String path = null;
    try {
      String dir3 = documentObject.getDir3();
      String csv = documentObject.getCsv();
      String idEni = documentObject.getIdEni();

      String idAplicacion = auth.getName();
      ApplicationEntity applicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

      // TODO ???
      String nif = null;
      if (!documentObject.getNifs().isEmpty()) {
        nif = documentObject.getNifs().get(0);
      }

      Object obtenerDocumentoResponseMandatoryFieldsValidation = validateMandatoryFields(className,
          url, documentResponseObject, content, mimeType, path, dir3, csv, idEni);
      if (obtenerDocumentoResponseMandatoryFieldsValidation != null) {
        return obtenerDocumentoResponseMandatoryFieldsValidation;
      }


      boolean calledFromDocumentService = (!StringUtils.isEmpty(documentObject.getCalledFrom())
          && documentObject.getCalledFrom().equals(Constants.CALLED_FROM_DOCUMENT_SERVICE));

      DocumentEntity documentEntity = null;

      // Si la peticion procede del queryDocument se anula del dir3
      if (calledFromDocumentService) {
        documentEntity = buscarDocumento(csv, dir3, idEni, documentObject.getRecuperacionOriginal(),
            documentObject.getDocumentoEni(), documentResponseObject);

      } else {
        documentEntity = buscarDocumento(csv, null, idEni, documentObject.getRecuperacionOriginal(),
            documentObject.getDocumentoEni(), documentResponseObject);
      }

      LOG.info("Documento Buscado: " + documentEntity);
      if (documentEntity != null) {

        if (valPermissionBusinessService.permisoLectura(applicacion, documentEntity,
            documentObject.getTipoIdentificacion(), nif, documentObject.getCalledFrom())) {

          if (documentEntity.getUuid() != null) {
            path = documentEntity.getRutaFichero() + SEPARATOR + documentEntity.getUuid();
          } else {
            path = documentEntity.getRutaFichero();
          }

          LOG.info(OBTENEMOS_EL_CONTENIDO);
          content = DocumentConverter.obtenerContenidoDH(path, documentEntity.getTipoMINE());
          if (documentEntity.getContenidoPorRef()) {
            path = DocumentConverter.obtenerReferenciaContenido(content);
            content = DocumentConverter.obtenerContenidoBigFileDH(path);
          }
          mimeType = documentEntity.getTipoMINE();

          documentResponseObject.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_EXIST));
          documentResponseObject.setDescripcion(
              messageSource.getMessage(Constants.QUERY_DOCUMENT_EXIST_DESC, null, locale));

          LOG.info("se crean los registros de auditoría");
          if (!StringUtils.isEmpty(documentObject.getCalledFrom())) {
            switch (documentObject.getCalledFrom()) {
              case Constants.CALLED_FROM_DOCUMENT_SERVICE:
                auditManagerService.create(applicacion, documentEntity.getId(),
                    documentObject.getParametrosAuditoria(), Operation.OBTENER.value());
                break;
              case Constants.CALLED_FROM_QUERY_SERVICE:
                auditManagerService.create(applicacion, documentEntity.getId(),
                    documentObject.getParametrosAuditoria(), Operation.QUERY_DOCUMENT.value());
                break;
              case Constants.CALLED_FROM_QUERY_SECURITY_SERVICE:
                auditManagerService.create(applicacion, documentEntity.getId(),
                    documentObject.getParametrosAuditoria(),
                    Operation.QUERY_DOCUMENT_SECURITY.value());
                break;
              default:
                break;
            }
          }

        } else {
          LOG.info("La aplicacion no tiene permiso para consulta el documento.");
          documentResponseObject.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_NOT_PERMISSION));
          documentResponseObject.setDescripcion(
              messageSource.getMessage(Constants.DOCUMENT_NOT_PERMISSION_DESC, null, locale));

        }

      } else {
        LOG.info("Documento no encontrado.");
        documentResponseObject.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST));
        documentResponseObject.setDescripcion(
            messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale));
      }

    } catch (IOException | ServiceException e) {
      LOG.error(ERROR_EN_SERVICIO_DE_OBTENER_DOCUMENTO, e);
      documentResponseObject.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_ERROR));
      documentResponseObject.setDescripcion(
          messageSource.getMessage(Constants.QUERY_DOCUMENT_ERROR_DESC, null, locale));
    }


    obtenerDocumentoResponse = DocumentResponseConverter.convertObjectToDocumentoResponse(
        documentResponseObject, content, mimeType, className, bigFileTransferService, path, url);

    printOpenFileDescriptor("obtener", fdIni);

    LOG.info("[FIN] Salimos de obtener. ");

    return obtenerDocumentoResponse;
  }

  @Override
  public DocumentoMtomStreamingResponse obtenerDocumentoStreaming(
      ObtenerDocumentoRequest obtenerDocumento) throws CSVStorageException {
    LOG.info("[INI] Entramos en obtenerDocumentoStreaming. ");

    DocumentObject documentObject = new DocumentObject();
    BeanUtils.copyProperties(obtenerDocumento, documentObject);
    if (obtenerDocumento.getRecuperacionOriginal() != null) {
      documentObject.setRecuperacionOriginal(obtenerDocumento.getRecuperacionOriginal().value());
    }

    if (obtenerDocumento.getDocumentoEni() != null) {
      documentObject.setDocumentoEni(obtenerDocumento.getDocumentoEni().value());
    }

    documentObject.setCalledFrom(Constants.CALLED_FROM_DOCUMENT_SERVICE);
    documentObject.setParametrosAuditoria(obtenerDocumento.parametrosAuditoria());

    ObtenerDocumentoMtomStreamingResponse response =
        (ObtenerDocumentoMtomStreamingResponse) obtener(documentObject,
            ObtenerDocumentoMtomStreamingResponse.class, null);


    LOG.info("[FIN] Entramos en obtenerDocumentoStreaming. ");

    return response.getDocumentoMtomResponse();
  }


  @Override
  public DocumentoMtomStreamingResponse obtenerInfoContenidoStreaming(String uuid)
      throws CSVStorageException {
    LOG.info("[INI] Entramos en obtenerInfoContenidoStreaming. ");
    ObtenerDocumentoMtomStreamingResponse response = null;

    // buscamos en bbdd por uuid
    if (uuid != null && !StringUtils.isEmpty(uuid)) {

      try {
        DocumentEntity documentEntity = documentManagerService.findByUuid(uuid);
        DocumentObject documentObject = new DocumentObject();

        BeanUtils.copyProperties(documentEntity, documentObject);
        response = (ObtenerDocumentoMtomStreamingResponse) obtener(documentObject,
            ObtenerDocumentoMtomStreamingResponse.class, null);

      } catch (ServiceException e) {
        LOG.error(e.getMessage());
      }

    }

    LOG.info("[FIN] Salimos en obtenerInfoContenidoStreaming. ");

    return response.getDocumentoMtomResponse();
  }


  private Object validateMandatoryFields(Class<?> className, String url,
      DocumentResponseObject documentResponseObject, DataHandler content, String mimeType,
      String path, String dir3, String csv, String idEni) throws CSVStorageException {

    String codigo;
    String descripcion;
    Object obtenerDocumentoResponse;// Validamos si se han rellenado los campos obligatorio
    // if (StringUtils.isEmpty(dir3) || (StringUtils.isEmpty(csv) && StringUtils.isEmpty(idEni))) {
    if (StringUtils.isEmpty(csv) && StringUtils.isEmpty(idEni)) {
      // if (StringUtils.isEmpty(dir3)) {
      // // Falta por rellenar el dir3
      // codigo = String.valueOf(Constants.MANDATORY_FIELD_DIR3);
      // descripcion = messageSource.getMessage(Constants.MANDATORY_FIELD_DIR3_DESC, null, locale);
      //
      // } else {
      // Falta por rellenar csv o idEni
      codigo = String.valueOf(Constants.EMPTY_FIELDS_CSV_IDENI);
      descripcion = messageSource.getMessage(Constants.EMPTY_FIELDS_CSV_IDENI_DESC, null, locale);
      // }
      documentResponseObject.setCodigo(codigo);
      documentResponseObject.setDescripcion(descripcion);

      obtenerDocumentoResponse = DocumentResponseConverter.convertObjectToDocumentoResponse(
          documentResponseObject, content, mimeType, className, bigFileTransferService, path, url);

      return obtenerDocumentoResponse;

    }
    return null;
  }


  @Override
  public Object obtenerENI(ObtenerDocumentoEniRequest obtenerDocumentoENI, Class<?> className)
      throws CSVStorageException {
    LOG.info("[INI] Entramos en obtenerENI. ");
    Object response = null;
    DocumentEniResponseObject obtenerDocumentoEniResponse = new DocumentEniResponseObject();
    String codigo;
    String descripcion;
    long fdIni = FileUtil.getFdOpen();

    List<DocumentEniOrganoEntity> listOrganos = null;
    DocumentEniEntity documentEniEntity = null;
    try {
      // Validamos si ya existe un informe con este csv
      String idENI = obtenerDocumentoENI.getIdENI();
      String csv = obtenerDocumentoENI.getCsv();
      String dir3 = obtenerDocumentoENI.getDir3();

      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String idAplicacion = auth.getName();

      ApplicationEntity applicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

      // Validamos si se han rellenado los campos obligatorio
      if (StringUtils.isEmpty(dir3) || StringUtils.isEmpty(idENI)) {
        // Falta por rellenar alguno de los campos obligatorios
        obtenerDocumentoEniResponse.setCodigo(String.valueOf(Constants.MANDATORY_FIELDS));
        obtenerDocumentoEniResponse.setDescripcion(
            messageSource.getMessage(Constants.MANDATORY_FIELDS_DESC, null, locale));
        response = DocumentResponseConverter.convertObjectToDocumentoEniResponse(
            obtenerDocumentoEniResponse, documentEniEntity, listOrganos, className);
        return response;
      }

      // Formateamos el csv si viene con guiones
      csv = Util.formatearCsvSinGuiones(csv);

      documentEniEntity = buscarDocumentoEni(csv, dir3, idENI, obtenerDocumentoEniResponse);

      // Si existe un unico documento
      if (documentEniEntity != null) {

        if (valPermissionBusinessService.permisoLectura(applicacion,
            documentEniEntity.getDocumento(), null, null, null)) {
          listOrganos = documentEniOrganoManagerService.findByDocument(documentEniEntity.getId());

          codigo = String.valueOf(Constants.QUERY_DOCUMENT_EXIST);
          descripcion = messageSource.getMessage(Constants.QUERY_DOCUMENT_EXIST_DESC, null, locale);

          auditManagerService.create(applicacion, documentEniEntity.getId(),
              obtenerDocumentoENI.parametrosAuditoria(), Operation.OBTENER_ENI.value());
        } else {
          codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST);
          descripcion =
              messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale);
        }
        obtenerDocumentoEniResponse.setCodigo(codigo);
        obtenerDocumentoEniResponse.setDescripcion(descripcion);
      }


    } catch (ServiceException e) {
      LOG.error(ERROR_EN_SERVICIO_DE_CONSULTA_DEL_DOCUMENTO, e);
      obtenerDocumentoEniResponse.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_ERROR));
      obtenerDocumentoEniResponse.setDescripcion(
          messageSource.getMessage(Constants.QUERY_DOCUMENT_ERROR_DESC, null, locale));

    }

    response = DocumentResponseConverter.convertObjectToDocumentoEniResponse(
        obtenerDocumentoEniResponse, documentEniEntity, listOrganos, className);

    printOpenFileDescriptor("obtenerENI", fdIni);

    LOG.info("[FIN] Salimos de obtenerENI. ");
    return response;
  }

  public ObtenerReferenciaDocumentoResponse consultaReferencia(
      ConsultarPermisosDocumentoRequest consultarPermisosDocumento) throws CSVStorageException {

    ConsultarPermisosDocumentoResponse consulta =
        consultarPermisosDocumento(consultarPermisosDocumento);

    ObtenerReferenciaDocumentoResponse response = new ObtenerReferenciaDocumentoResponse();
    TipoReferenciaDocumento miReferencia = new TipoReferenciaDocumento();

    // Compruebo si tiene permiso la aplicacion
    if (consulta == null || !consulta.getDocumentoPermisoResponse().getCodigo()
        .equals(String.valueOf(Constants.VALIDATION_OK))) {
      LOG.info("La aplicacion no tiene permiso para consultar.");
      response.setCodigo(consulta.getDocumentoPermisoResponse().getCodigo());
      response.setDescripcion(consulta.getDocumentoPermisoResponse().getDescripcion());
      return response;
    }

    LOG.info("Se procede a obtener el sistema por referencia");

    // se traen los emisores y receptores
    List<VReferenciasAppEntity> referenciasApp = null;
    try {
      referenciasApp = referenciasAppService.findReferenciaAppByReferencia(
          new VReferenciasAppEntity(consultarPermisosDocumento.getDir3(),
              consultarPermisosDocumento.getCsv(), consultarPermisosDocumento.getIdENI()));
    } catch (ServiceException e) {
      LOG.error("Error al buscar las referencias a aplicaciones: " + e.getMessage());
    }
    List<VReferenciasAppEntity> emisores = new ArrayList<>();
    List<VReferenciasAppEntity> receptores = new ArrayList<>();
    if (referenciasApp != null && !referenciasApp.isEmpty()) {
      for (VReferenciasAppEntity refAp : referenciasApp) {
        if (refAp.getTipo().equals("EMISOR")) {
          emisores.add(refAp);
        } else if (refAp.getTipo().equals("RECEPTOR")) {
          receptores.add(refAp);
        }
      }
    }

    // Identificador
    ObjectFactory factory = new ObjectFactory();
    miReferencia.setIdentificador(new TipoIdentificador());

    if (consulta.getDocumentoPermisoResponse().getValorCSV() != null) {
      miReferencia.getIdentificador()
          .getEEMGDEFirmaFormatoFirmaValorCSVOrEEMGDEIdentificadorSecuenciaIdentificador()
          .add(factory.createTipoIdentificadorEEMGDEFirmaFormatoFirmaValorCSV(
              consulta.getDocumentoPermisoResponse().getValorCSV()));
    }

    if (consulta.getDocumentoPermisoResponse().getIdEni() != null) {

      miReferencia.getIdentificador()
          .getEEMGDEFirmaFormatoFirmaValorCSVOrEEMGDEIdentificadorSecuenciaIdentificador()
          .add(factory.createTipoIdentificadorEEMGDEIdentificadorSecuenciaIdentificador(
              consulta.getDocumentoPermisoResponse().getIdEni()));
    }

    // compruebo el permiso del documento
    miReferencia.setPermiso(new TipoPermisos());
    if (consulta.getDocumentoPermisoResponse().getTipoPermiso().value()
        .equals(DocumentoRequest.TipoPermiso.PRIVADO.value())) {
      miReferencia.getPermiso().setPrivado(TipoPrivado.PRIVADO);
    } else if (consulta.getDocumentoPermisoResponse().getTipoPermiso().value()
        .equals(DocumentoRequest.TipoPermiso.RESTRINGIDO.value())) {

      miReferencia.getPermiso().setRestringido(new TipoRestringido());

      // por NIFS
      if (consulta.getDocumentoPermisoResponse().getRestringidoNif() != null
          && !consulta.getDocumentoPermisoResponse().getRestringidoNif().getNif().isEmpty()) {

        TipoNifs nifs = new TipoNifs();

        for (String nif : consulta.getDocumentoPermisoResponse().getRestringidoNif().getNif()) {
          nifs.getNif().add(nif);
        }

        miReferencia.getPermiso().getRestringido().setRestringidoNif(nifs);
      }

      // por IDENTIFICACION
      if (consulta.getDocumentoPermisoResponse().getRestringidoPorIdentificacion() != null
          && !consulta.getDocumentoPermisoResponse().getRestringidoPorIdentificacion().getId()
              .isEmpty()) {

        TipoIdentificaciones identificaciones = new TipoIdentificaciones();

        for (ListaTipoIds.TipoId ident : consulta.getDocumentoPermisoResponse()
            .getRestringidoPorIdentificacion().getId()) {

          TipoIdentificacionesPorCertificado tipo =
              TipoIdentificacionesPorCertificado.fromValue(ident.value());

          if (tipo != null) {
            identificaciones.getIdentificacion().add(tipo);
          }
        }

        miReferencia.getPermiso().getRestringido()
            .setRestringidoPorIdentificacion(identificaciones);

      }

      // por APLICACIONES
      if (consulta.getDocumentoPermisoResponse().getAplicaciones() != null
          && !consulta.getDocumentoPermisoResponse().getAplicaciones().getAplicacion().isEmpty()) {

        TipoAplicaciones tipoApp = new TipoAplicaciones();

        for (String app : consulta.getDocumentoPermisoResponse().getAplicaciones()
            .getAplicacion()) {
          tipoApp.getIdAplicacion().add(app);
        }

        miReferencia.getPermiso().getRestringido().setRestringidoAplicaciones(tipoApp);
      }

      // por EMPLEADO PUBLICO
      miReferencia.getPermiso().getRestringido()
          .setEmpleadoPublico(consulta.getDocumentoPermisoResponse().getRestricciones()
              .getRestriccion().contains(ListaRestriccion.Restriccion.RESTRINGIDO_PUB));

    } else {
      miReferencia.getPermiso().setPublico(TipoPublico.PUBLICO);
    }

    // emisores
    // mete datos si no es PRIVADO
    if (miReferencia.getPermiso().getPrivado() == null && !emisores.isEmpty()) {
      miReferencia.setEmisor(new TipoEmisor());
      for (VReferenciasAppEntity refAp : emisores) {
        if (StringUtils.isNotEmpty(refAp.getDir3Aplicacion())) {
          miReferencia.getEmisor().getOrganismo().add(refAp.getDir3Aplicacion());
        }
      }
    }

    // receptores
    // mete datos si es RESTRINGIDO
    if (miReferencia.getPermiso().getRestringido() != null && !receptores.isEmpty()) {
      miReferencia.setReceptor(new TipoReceptor());
      for (VReferenciasAppEntity refAp : receptores) {
        if (StringUtils.isNotEmpty(refAp.getDir3Aplicacion())) {
          miReferencia.getReceptor().getOrganismo().add(refAp.getDir3Aplicacion());
        }
      }
    }

    response.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_EXIST));
    response.setDescripcion(
        messageSource.getMessage(Constants.QUERY_DOCUMENT_EXIST_DESC, null, locale));
    response.setTipoReferenciaDocumento(miReferencia);

    response.getTipoReferenciaDocumento().setDireccion(direccionSistemaReferencia);
    response.getTipoReferenciaDocumento().setURLVisible(direccionSistemaReferencia);

    return response;
  }



  @Override
  public ConsultarDocumentoResponse consultar(ConsultarDocumentoRequest consultarDocumento)
      throws CSVStorageException {
    LOG.info("[INI] Entramos en consultar.");
    Response response = new Response();
    ConsultarDocumentoResponse consultarDocumentoResponse = new ConsultarDocumentoResponse();
    long fdIni = FileUtil.getFdOpen();
    try {
      String dir3 = consultarDocumento.getDir3();
      String csv = consultarDocumento.getCsv();
      String idEni = consultarDocumento.getIdENI();
      String codigo;
      String descripcion;

      // Validamos si se han rellenado los campos obligatorio
      LOG.info("Se valida que se ha rellenado los campos obligatorios.");
      if (StringUtils.isEmpty(dir3) || (StringUtils.isEmpty(csv) && StringUtils.isEmpty(idEni))) {
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String idAplicacion = auth.getName();

        ApplicationEntity aplicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

        // Formateamos el csv si viene con guiones
        csv = Util.formatearCsvSinGuiones(csv);

        // Validamos si ya existe un informe con este csv
        List<DocumentEntity> documentEntityList = documentManagerService.findAll(csv, dir3, idEni);

        // Si existe se sobreescribe el contenido del informe y los metadatos del existentes
        if (documentEntityList != null && !documentEntityList.isEmpty()) {

          if (valPermissionBusinessService.permisoLectura(aplicacion, documentEntityList.get(0),
              null, null, null)) {

            DocumentEntity documentEntity = documentEntityList.get(0);

            LOG.info("Se obtiene la ruta del fichero encontrado.");

            String path;
            if (documentEntity.getUuid() != null) {
              path = documentEntity.getRutaFichero() + SEPARATOR + documentEntity.getUuid();
            } else {
              path = documentEntity.getRutaFichero();
            }

            File file = new File(path);

            if (file.exists() && file.isFile()) {
              codigo = String.valueOf(Constants.QUERY_DOCUMENT_EXIST);
              descripcion =
                  messageSource.getMessage(Constants.QUERY_DOCUMENT_EXIST_DESC, null, locale);

              auditManagerService.create(aplicacion, documentEntity.getId(),
                  consultarDocumento.parametrosAuditoria(), Operation.CONSULTAR.value());

            } else {
              codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST);
              descripcion =
                  messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale);
            }

          } else {
            LOG.info("La aplicacion no tiene permiso para consulta el documento.");
            codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_PERMISSION);
            descripcion =
                messageSource.getMessage(Constants.DOCUMENT_NOT_PERMISSION_DESC, null, locale);
          }

        } else {
          LOG.info("Documento no encontrado.");
          codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST);
          descripcion =
              messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale);
        }
      }

      response.setCodigo(codigo);
      response.setDescripcion(descripcion);
      consultarDocumentoResponse.setResponse(response);

      printOpenFileDescriptor("consultar", fdIni);

      LOG.info(FIN_SALIMOS_DE_CONSULTAR);
    } catch (ServiceException e) {
      LOG.error(ERROR_EN_SERVICIO_DE_CONSULTA_DEL_DOCUMENTO, e);

    }
    return consultarDocumentoResponse;
  }


  public ConsultarPermisosDocumentoResponse consultarPermisosDocumento(
      ConsultarPermisosDocumentoRequest consultarPermisosDocumento) throws CSVStorageException {

    LOG.info("[INI] Entramos en consultarPermisosDocumento.");
    DocumentoPermisoResponse response = new DocumentoPermisoResponse();

    ConsultarPermisosDocumentoResponse consultarPermisosDocumentReponse =
        new ConsultarPermisosDocumentoResponse();

    try {

      String dir3 = consultarPermisosDocumento.getDir3();
      String csv = consultarPermisosDocumento.getCsv();
      String idEni = consultarPermisosDocumento.getIdENI();
      String codigo = "";
      String descripcion = "";

      boolean correcto = true;


      // Validamos si se han rellenado los campos obligatorio
      if (StringUtils.isEmpty(dir3) || (StringUtils.isEmpty(csv) && StringUtils.isEmpty(idEni))) {
        if (StringUtils.isEmpty(dir3)) {
          // Falta por rellenar el dir3
          codigo = String.valueOf(Constants.MANDATORY_FIELD_DIR3);
          descripcion = messageSource.getMessage(Constants.MANDATORY_FIELD_DIR3_DESC, null, locale);
          correcto = false;
        } else {
          // Falta por rellenar csv o idEni
          codigo = String.valueOf(Constants.EMPTY_FIELDS_CSV_IDENI);
          descripcion =
              messageSource.getMessage(Constants.EMPTY_FIELDS_CSV_IDENI_DESC, null, locale);
          correcto = false;
        }
      }

      if (correcto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String idAplicacion = auth.getName();

        ApplicationEntity aplicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

        // Formateamos el csv si viene con guiones
        csv = Util.formatearCsvSinGuiones(csv);

        // Validamos si ya existe un informe con este csv
        List<DocumentEntity> documentEntityList = documentManagerService.findAll(csv, dir3, idEni);

        if (documentEntityList != null && documentEntityList.size() == 1) {

          response.setValorCSV(documentEntityList.get(0).getCsv());
          response.setIdEni(documentEntityList.get(0).getIdEni());
          codigo = String.valueOf(Constants.QUERY_DOCUMENT_EXIST);
          descripcion = messageSource.getMessage(Constants.QUERY_DOCUMENT_EXIST_DESC, null, locale);

          LOG.info("Se procede a consultar el creador de la aplicacion");
          if (aplicacion.getId().equals(documentEntityList.get(0).getCreatedBy().getId())) {

            LOG.info("Coinciden los usuarios");
            if (documentEntityList.get(0).getTipoPermiso() != null) {
              response.setTipoPermiso(DocumentoRequest.TipoPermiso.fromValue(DocumentPermission
                  .getPermission(documentEntityList.get(0).getTipoPermiso()).getDescription()));
            }


            // Obtengo todas las Restricciones que tiene el documento
            LOG.info("Se procede a obtener todas las Restricciones que tiene el documento");
            List<DocumentRestriccionEntity> restricciones =
                documentManagerService.findRestriccionesByDocument(documentEntityList.get(0));

            if (restricciones != null && !restricciones.isEmpty()) {
              response.setRestricciones(new ListaRestriccion());
              response.getRestricciones()
                  .setRestriccion(new ArrayList<ListaRestriccion.Restriccion>());
              for (DocumentRestriccionEntity restriccion : restricciones) {
                response.getRestricciones().getRestriccion()
                    .add(ListaRestriccion.Restriccion.fromValue(DocumentRestriccion
                        .getRestriccion(restriccion.getRestriccion()).getDescription()));
              }
            }

            // Obtengo todos los nif que tiene el documento
            LOG.info("Se procede a obtener todos los nif que tiene el documento");
            List<DocumentNifEntity> nifs =
                documentManagerService.findNifsByDocument(documentEntityList.get(0));

            if (nifs != null && !nifs.isEmpty()) {
              response.setRestringidoNif(new ListaNifs());
              response.getRestringidoNif().setNif(new ArrayList<String>());
              for (DocumentNifEntity nif : nifs) {
                response.getRestringidoNif().getNif().add(nif.getNif());
              }
            }

            // Obtengo todos los ID
            LOG.info("Se procede a obtener todos los ID");
            List<DocumentIdsEntity> tipoIds =
                documentManagerService.findRestriccionesIDByDocument(documentEntityList.get(0));

            if (tipoIds != null && !tipoIds.isEmpty()) {
              response.setRestringidoPorIdentificacion(new ListaTipoIds());
              response.getRestringidoPorIdentificacion()
                  .setId(new ArrayList<ListaTipoIds.TipoId>());
              for (DocumentIdsEntity tipoId : tipoIds) {
                response.getRestringidoPorIdentificacion().getId()
                    .add(ListaTipoIds.TipoId.fromValue(IdentificationType
                        .getType(tipoId.getTipoId().toString()).getDescription()));
              }
            }

            // Obtengo todas las aplicaciones que tiene permisos a dicho documento
            LOG.info(
                "Se procede a obtener todas las aplicaciones que tiene permisos a dicho documento");
            List<DocumentAplicacionEntity> aplicaciones =
                documentManagerService.findDocumentAplicacionByDocument(documentEntityList.get(0));

            if (aplicaciones != null && !aplicaciones.isEmpty()) {
              response.setAplicaciones(new ListaAplicaciones());
              response.getAplicaciones().setAplicacion(new ArrayList<String>());
              for (DocumentAplicacionEntity app : aplicaciones) {
                response.getAplicaciones().getAplicacion()
                    .add(app.getAplicacion().getIdAplicacionPublico());
              }
            }

            auditManagerService.create(aplicacion, documentEntityList.get(0).getId(),
                consultarPermisosDocumento.parametrosAuditoria(),
                Operation.CONSULTAR_PERMISOS_DOCUMENTO.value());
          } else {
            codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_PERMISSION);
            descripcion =
                messageSource.getMessage(Constants.DOCUMENT_NOT_PERMISSION_DESC, null, locale);
          }
        } else {
          codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST);
          descripcion =
              messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale);
        }
      }

      consultarPermisosDocumentReponse.setDocumentoPermisoResponse(response);
      consultarPermisosDocumentReponse.getDocumentoPermisoResponse().setCodigo(codigo);
      consultarPermisosDocumentReponse.getDocumentoPermisoResponse().setDescripcion(descripcion);

    } catch (ServiceException e) {
      LOG.error(ERROR_EN_SERVICIO_DE_CONSULTA_DEL_DOCUMENTO, e);
    }

    LOG.info("[FIN] Salimos de consultarPermisosDocumento");

    return consultarPermisosDocumentReponse;
  }


  @Override
  public ConsultaResponse consultarPorCSV(ConsultarDocumentoRequest consultarDocumento)
      throws CSVStorageException {
    LOG.info("[INI] Entramos en consultarPorCSV. ");

    ConsultaResponse consultarDocumentoResponse = new ConsultaResponse();
    try {
      String dir3 = consultarDocumento.getDir3();
      String csv = consultarDocumento.getCsv();
      String idEni = consultarDocumento.getIdENI();
      String codigo;
      String descripcion;

      // Validamos si se han rellenado los campos obligatorio
      if (StringUtils.isEmpty(dir3) || (StringUtils.isEmpty(csv) && StringUtils.isEmpty(idEni))) {
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String idAplicacion = auth.getName();

        ApplicationEntity aplicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

        // Formateamos el csv si viene con guiones
        csv = Util.formatearCsvSinGuiones(csv);

        // Validamos si ya existe un informe con este csv
        List<DocumentEntity> documentEntityList = documentManagerService.findAll(csv, dir3, idEni);

        // Si existe se sobreescribe el contenido del informe y los
        // metadatos del existentes
        if (documentEntityList != null && documentEntityList.size() == 1
            && valPermissionBusinessService.permisoLectura(aplicacion, documentEntityList.get(0),
                null, null, null)) {

          codigo = String.valueOf(Constants.QUERY_DOCUMENT_EXIST);
          descripcion = messageSource.getMessage(Constants.QUERY_DOCUMENT_EXIST_DESC, null, locale);

          ContenidoInfo contenido = new ContenidoInfo();
          contenido.setContenido(pdfUtils.createPdfConsultaPorCSV(documentEntityList.get(0)));
          contenido.setTipoMIME("application/pdf");
          consultarDocumentoResponse.setCertificado(contenido);

        } else if (documentEntityList != null && documentEntityList.size() > 1) {
          codigo = String.valueOf(Constants.RETURN_MULTIPLE_RESULTS);
          descripcion =
              messageSource.getMessage(Constants.RETURN_MULTIPLE_RESULTS_DESC, null, locale);
        } else {
          codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST);
          descripcion =
              messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale);
        }
      }

      consultarDocumentoResponse.setCodigo(codigo);
      consultarDocumentoResponse.setDescripcion(descripcion);

      LOG.info(FIN_SALIMOS_DE_CONSULTAR);
    } catch (ServiceException e) {
      LOG.error(ERROR_EN_SERVICIO_DE_CONSULTA_DEL_DOCUMENTO, e);

    }
    return consultarDocumentoResponse;
  }


  @Override
  public DocumentResponseObject obtenerCMIS(String uuid, String idAplicacion,
      String algoritmoHash) {
    LOG.info("[INI] Entramos en obtenerCMIS. ");
    DocumentResponseObject documentResponseObject = new DocumentResponseObject();

    String codigo;
    String descripcion;
    DataHandler contentDH;
    try {

      ApplicationEntity aplicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

      // Validamos si se han rellenado los campos obligatorio
      if (StringUtils.isEmpty(uuid)) {
        // Falta por rellenar el dir3
        codigo = String.valueOf(Constants.MANDATORY_FIELDS);
        descripcion = messageSource.getMessage(Constants.MANDATORY_FIELDS_DESC, null, locale);

      } else {
        LOG.debug("Buscamos el documento. ");
        // Buscamos el documento por uuid
        DocumentEntity documentEntity = documentManagerService.findByUuid(uuid);

        if (documentEntity != null) {

          if (valPermissionBusinessService.permisoLectura(aplicacion, documentEntity, null, null,
              null)) {

            String path;
            if (documentEntity.getUuid() != null) {
              path = documentEntity.getRutaFichero() + SEPARATOR + documentEntity.getUuid();
            } else {
              path = documentEntity.getRutaFichero();
            }

            LOG.debug(OBTENEMOS_EL_CONTENIDO);

            DocumentObject documentObject = new DocumentObject();

            LOG.debug(OBTENEMOS_EL_CONTENIDO);
            contentDH = DocumentConverter.obtenerContenidoDH(path, documentEntity.getTipoMINE());

            if (documentEntity.getContenidoPorRef()) {
              path = DocumentConverter.obtenerReferenciaContenido(contentDH);
              documentObject.setContenidoPorRef(path);
              documentObject.setTamanio(documentEntity.getTamanioFichero() * 1000);
            } else {
              documentObject.setContenido(IOUtils.toByteArray(contentDH.getInputStream()));
              documentObject.setTamanio((long) documentObject.getContenido().length);
            }

            if (StringUtils.isEmpty(documentEntity.getName())) {

              String name =
                  StringUtils.isNotEmpty(documentEntity.getIdEni()) ? documentEntity.getIdEni()
                      : documentEntity.getCsv();

              documentObject.setName(name);
            } else {
              documentObject.setName(documentEntity.getName());
            }
            documentObject.setPathFile(path);
            documentObject.setDir3(documentEntity.getUnidadOrganica().getUnidadOrganica());
            documentObject.setCsv(documentEntity.getCsv());
            documentObject.setIdEni(documentEntity.getIdEni());
            documentObject.setMimeType(documentEntity.getTipoMINE());
            documentObject.setUuid(uuid);
            documentObject.setCreatedAt(documentEntity.getCreatedAt());
            if (documentEntity.getCreatedBy() != null) {
              documentObject.setCreatedBy(documentEntity.getCreatedBy().getIdAplicacion());
            }
            documentObject.setModifiedAt(documentEntity.getModifiedAt());
            if (documentEntity.getModifiedBy() != null) {
              documentObject.setModifiedBy(documentEntity.getModifiedBy().getIdAplicacion());
            }

            if (StringUtils.isNotEmpty(algoritmoHash)) {
              documentObject.setCodigoHash(FileUtil.hashCodeStream(path, algoritmoHash));
            }


            documentResponseObject.setContenido(documentObject);

            codigo = String.valueOf(Constants.QUERY_DOCUMENT_EXIST);
            descripcion =
                messageSource.getMessage(Constants.QUERY_DOCUMENT_EXIST_DESC, null, locale);
          } else {
            codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_PERMISSION);
            descripcion =
                messageSource.getMessage(Constants.DOCUMENT_NOT_PERMISSION_DESC, null, locale);
          }
        } else {
          codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST);
          descripcion =
              messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale);
        }
      }


    } catch (IOException | ServiceException e) {
      LOG.error(ERROR_EN_SERVICIO_DE_OBTENER_DOCUMENTO, e);
      codigo = String.valueOf(Constants.QUERY_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.QUERY_DOCUMENT_ERROR_DESC, null, locale);

    } catch (ValidationException e) {
      codigo = String.valueOf(Constants.SAVE_DOCUMENT_ERROR);
      descripcion = e.getMessage();
    }

    documentResponseObject.setCodigo(codigo);
    documentResponseObject.setDescripcion(descripcion);

    LOG.info("[FIN] Salimos de obtenerCMIS. ");

    return documentResponseObject;
  }


  @Override
  public DocumentResponseObject consultarPermisoCMIS(String uuid, String idAplicacion) {
    LOG.info("[INI] Entramos en obtenerCMIS. ");
    DocumentResponseObject documentResponseObject = new DocumentResponseObject();

    String codigo = null;
    String descripcion = null;

    try {

      ApplicationEntity aplicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

      // Validamos si se han rellenado los campos obligatorio
      if (StringUtils.isEmpty(uuid)) {
        // Falta por rellenar el dir3
        codigo = String.valueOf(Constants.MANDATORY_FIELDS);
        descripcion = messageSource.getMessage(Constants.MANDATORY_FIELDS_DESC, null, locale);

      } else {
        LOG.debug("Buscamos el documento. ");
        // Buscamos el documento por uuid
        DocumentEntity documentEntity = documentManagerService.findByUuid(uuid);

        if (documentEntity != null && valPermissionBusinessService.permisoLectura(aplicacion,
            documentEntity, null, null, null)) {

          codigo = String.valueOf(Constants.QUERY_DOCUMENT_EXIST);
          descripcion = messageSource.getMessage(Constants.QUERY_DOCUMENT_EXIST_DESC, null, locale);
        } else {
          codigo = String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST);
          descripcion =
              messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale);
        }
      }

    } catch (Exception e) {
      LOG.error(ERROR_EN_SERVICIO_DE_OBTENER_DOCUMENTO, e);
      codigo = String.valueOf(Constants.QUERY_DOCUMENT_ERROR);
      descripcion = messageSource.getMessage(Constants.QUERY_DOCUMENT_ERROR_DESC, null, locale);
    }

    documentResponseObject.setCodigo(codigo);
    documentResponseObject.setDescripcion(descripcion);

    LOG.info("[FIN] Salimos de obtenerCMIS. ");

    return documentResponseObject;
  }

  @Override
  public TamanioDocumentoResponse obtenerTamanioDocumento(DocumentObject documentObject)
      throws CSVStorageException {
    LOG.info("[INI] Entramos en obtenerTamanioDocumento. ");
    Long tamanio = null;

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    LOG.info("[INI] Entramos en obtenerTamanioDocumento documentObject: " + documentObject
        + "; idAplicacion:" + auth.getName());

    DocumentTamanioResponseObject documentResponseObject = new DocumentTamanioResponseObject();

    try {
      String dir3 = documentObject.getDir3();
      String csv = documentObject.getCsv();
      String idEni = documentObject.getIdEni();

      String idAplicacion = auth.getName();
      ApplicationEntity applicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

      // Validamos si se han rellenado los campos obligatorio
      if (StringUtils.isEmpty(dir3) || (StringUtils.isEmpty(csv) && StringUtils.isEmpty(idEni))) {
        LOG.info("No se han rellenado los datos obligatorios");
        TamanioDocumentoResponse documentoResponse = new TamanioDocumentoResponse();
        documentoResponse.setTamanioDocumento(null);
        documentoResponse.setCodigo(String.valueOf(Constants.EMPTY_FIELDS_CSV_IDENI));
        documentoResponse.setDescripcion(
            messageSource.getMessage(Constants.EMPTY_FIELDS_CSV_IDENI_DESC, null, locale));
        return documentoResponse;
      }

      DocumentEntity documentEntity =
          buscarDocumento(csv, dir3, idEni, documentObject.getRecuperacionOriginal(),
              documentObject.getDocumentoEni(), documentResponseObject);

      LOG.info("Documento Buscado: " + documentEntity);
      if (documentEntity != null) {

        if (valPermissionBusinessService.permisoLectura(applicacion, documentEntity,
            documentObject.getTipoIdentificacion(), null, documentObject.getCalledFrom())) {

          tamanio = documentEntity.getTamanioFichero();

          documentResponseObject.setTamanioDocumento(tamanio);
          documentResponseObject.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_EXIST));
          documentResponseObject.setDescripcion(
              messageSource.getMessage(Constants.QUERY_DOCUMENT_EXIST_DESC, null, locale));

          LOG.info("se crean los registros de auditoría");
          auditManagerService.create(applicacion, documentEntity.getId(),
              documentObject.getParametrosAuditoria(), Operation.OBTENER_TAMANIO.value());

        } else {
          LOG.info("No tiene permisos sobre el documento");
          documentResponseObject.setTamanioDocumento(null);
          documentResponseObject.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_NOT_PERMISSION));
          documentResponseObject.setDescripcion(messageSource
              .getMessage(Constants.SAVE_DOCUMENT_REVOKE_APP_NOT_PERMISSION_DESC, null, locale));
        }
      } else {
        LOG.info("No se ha encontrado el documento");
        documentResponseObject.setTamanioDocumento(null);
        documentResponseObject.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST));
        documentResponseObject.setDescripcion(
            messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale));
      }

    } catch (ServiceException e) {
      LOG.error("Error en servicio de obtenerTamanioDocumento", e);
      documentResponseObject.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_ERROR));
      documentResponseObject.setDescripcion(
          messageSource.getMessage(Constants.QUERY_DOCUMENT_ERROR_DESC, null, locale));
    }

    LOG.info("[FIN] Salimos de obtenerTamanioDocumento. ");

    ObtenerTamanioDocumentoResponse obtenerDocumentoResponse =
        new ObtenerTamanioDocumentoResponse();
    obtenerDocumentoResponse.setDocumentoResponse(new TamanioDocumentoResponse());
    obtenerDocumentoResponse.getDocumentoResponse().setCodigo(documentResponseObject.getCodigo());
    obtenerDocumentoResponse.getDocumentoResponse()
        .setDescripcion(documentResponseObject.getDescripcion());
    obtenerDocumentoResponse.getDocumentoResponse()
        .setTamanioDocumento(documentResponseObject.getTamanioDocumento());

    return obtenerDocumentoResponse.getDocumentoResponse();
  }

  @Override
  public Long obtenerTamanioUuid(String uuid) throws CSVStorageException {
    LOG.info("[INI] Entramos en consultar - obtenerTamanioUuid");
    Long tamanio = 0L;
    try {

      // Validamos si se han rellenado los campos obligatorio
      if (StringUtils.isEmpty(uuid)) {
        LOG.warn("No existe");
      } else {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String idAplicacion = auth.getName();

        ApplicationEntity aplicacion = applicationManagerService.findByIdAplicacion(idAplicacion);

        // Buscamos el documento por uuid
        DocumentEntity documentEntity = documentManagerService.findByUuid(uuid);

        // Si existe se sobreescribe el contenido del informe y los
        // metadatos del existentes
        if (documentEntity != null && valPermissionBusinessService.permisoLectura(aplicacion,
            documentEntity, null, null, null)) {

          tamanio = documentEntity.getTamanioFichero();
          if (tamanio > 0) {
            tamanio = tamanio / 1024;
          }

        }
      }

      LOG.info(FIN_SALIMOS_DE_CONSULTAR);
    } catch (ServiceException e) {
      LOG.error(ERROR_EN_SERVICIO_DE_CONSULTA_DEL_DOCUMENTO, e);

    }
    return tamanio;
  }

  public List<Aplicacion> consultarAplicaciones() throws CSVStorageException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    final String idAplicacion = auth.getName();
    ApplicationEntity aplicacion = null;
    try {
      aplicacion = applicationManagerService.findByIdAplicacion(idAplicacion);
    } catch (ServiceException e) {
      LOG.info("Error al consultar los Ids Públicos de las aplicaciones: " + e.getMessage());
      throw new CSVStorageException(e.getMessage());
    }

    auditManagerService.create(aplicacion, null, "", Operation.CONSULTAR_APLICACIONES.value());

    return ApplicationConverter.applicationEntityToWSIdPublic(applicationManagerService.findAll());
  }

  public DocumentoReferenciaResponse consultaReferenciaGuardado(
      GuardarDocumentoRequest guardarRequest, GuardarDocumentoResponse guardarResponse)
      throws CSVStorageException {
    DocumentoReferenciaResponse response = new DocumentoReferenciaResponse();
    response.setCodigo(guardarResponse.getResponse().getCodigo());
    response.setDescripcion(guardarResponse.getResponse().getDescripcion());

    if (guardarResponse.getResponse().getCodigo()
        .equals(String.valueOf(Constants.SAVE_DOCUMENT_SUCCESS))) {
      ConsultarPermisosDocumentoRequest consultarRequest = new ConsultarPermisosDocumentoRequest();
      consultarRequest.setCsv(guardarRequest.getCsv());
      consultarRequest.setDir3(guardarRequest.getDir3());
      consultarRequest.setIdENI(guardarRequest.getIdEni());
      ObtenerReferenciaDocumentoResponse consultaResponse =
          this.consultaReferencia(consultarRequest);

      try {
        String referenciaDocumentoXML = JAXBMarshaller.marshallDataDocument(
            consultaResponse.getTipoReferenciaDocumento(), null, "ReferenciaDocumento", "");

        response.setReferenciaDocumento(referenciaDocumentoXML.getBytes());
      } catch (Exception e) {
        LOG.error("Error al convertir TipoReferenciaDocumento a XML");
        throw new CSVStorageException(e.getMessage());
      }
    }


    return response;
  }
}
