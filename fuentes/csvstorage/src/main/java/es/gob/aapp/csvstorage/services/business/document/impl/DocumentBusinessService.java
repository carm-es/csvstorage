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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.activation.DataHandler;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.conversion.mtom.TipoDocumentoConversionInsideMtom;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.mtom.file.DocumentoEniFileInsideMtom;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.consumer.ginside.mtom.GInsideUserTokenMtomConsumer;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentIdsEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentNifEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentRestriccionEntity;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.model.object.document.DocumentEniObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.model.object.document.ResponseObject;
import es.gob.aapp.csvstorage.services.business.validation.ValidationBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.exception.ValidationException;
import es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService;
import es.gob.aapp.csvstorage.services.manager.audit.AuditManagerService;
import es.gob.aapp.csvstorage.services.manager.document.DocumentEniManagerService;
import es.gob.aapp.csvstorage.services.manager.document.DocumentEniOrganoManagerService;
import es.gob.aapp.csvstorage.services.manager.document.DocumentManagerService;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.constants.Operation;
import es.gob.aapp.csvstorage.util.file.FileUtil;

/**
 * Implementación de los servicios business de almacenamiento de documentos.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("documentBusinessService")
@PropertySource("file:${csvstorage.config.path}/csvstorage.properties")
public class DocumentBusinessService {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(DocumentBusinessService.class);

  @Value("${sistemaficheros.ruta}")
  protected String rutaFichero;

  @Autowired
  protected MessageSource messageSource;

  @Autowired
  protected DocumentManagerService documentManagerService;

  @Autowired
  protected DocumentEniManagerService documentEniManagerService;

  @Autowired
  protected DocumentEniOrganoManagerService documentEniOrganoManagerService;

  @Autowired
  protected ValidationBusinessService validationBusinessService;

  @Autowired
  protected ApplicationManagerService applicationManagerService;

  @Autowired
  protected GInsideUserTokenMtomConsumer ginsideUserTokenMtomConsumer;

  @Autowired
  private AuditManagerService auditManagerService;

  protected static final Locale locale = LocaleContextHolder.getLocale();

  protected void guardarDocumento(DocumentEntity documentEntity, DocumentObject documentObject,
      UnitEntity unidad, ApplicationEntity applicacion, boolean modificar)
      throws ServiceException, ValidationException {
    LOG.debug("Entramos en guardar el documento. ");
    try {

      documentEntity.setCsv(documentObject.getCsv());
      documentEntity.setIdEni(documentObject.getIdEni());
      documentEntity.setUnidadOrganica(unidad);
      documentEntity.setRutaFichero(documentObject.getPathFile());
      documentEntity.setUuid(documentObject.getUuid());
      documentEntity.setTipoMINE(documentObject.getMimeType());
      documentEntity.setName(documentObject.getName());
      if (documentEntity.getId() == null) {
        documentEntity.setCreatedAt(Calendar.getInstance().getTime());
        documentEntity.setCreatedBy(applicacion);
      } else {
        documentEntity.setModifiedAt(Calendar.getInstance().getTime());
        documentEntity.setModifiedBy(applicacion);
      }
      documentEntity.setTipoPermiso(documentObject.getTipoPermiso().getCode());

      documentManagerService.create(documentEntity, documentObject, modificar);

      if (modificar && StringUtils.isNotEmpty(documentObject.getContenidoPorRef())) {

        String documentoNuevo = documentObject.getContenidoPorRef();
        String documentoViejo =
            documentEntity.getRutaFichero() + System.getProperty("file.separator")
                + documentEntity.getUuid() + Constants.BIG_FILE_NAME;

        modificarFile(documentoViejo, documentoNuevo);

      }

      // Para la auditoria
      String operacion = Operation.GUARDAR.value();
      if (modificar) {
        operacion = Operation.MODIFICAR.value();
      }
      auditManagerService.create(applicacion, documentEntity.getId(),
          documentObject.getParametrosAuditoria(), operacion);


    } catch (JAXBException e) {
      LOG.error("Se Ha producido un error al guardar el documento en la NAS");
      throw new ServiceException("Se Ha producido un error al guardar el documento en la NAS", e);
    }

    LOG.debug("Salimos de guardar el documento. ");
  }


  protected void guardarDocumentoENI(DocumentEntity documentEntity,
      DocumentEniObject documentEniObject, UnitEntity unidad, String path, String uuid,
      ApplicationEntity applicacion, boolean modificar)
      throws ServiceException, ValidationException {
    LOG.debug("Entramos en guardar el documento ENI. ");
    // Formateamos el csv si viene con guiones
    String csv = documentEniObject.getCsv();
    try {
      documentEntity.setCsv(csv);
      documentEntity.setIdEni(documentEniObject.getIdEni());
      documentEntity.setUnidadOrganica(unidad);
      documentEntity.setRutaFichero(path);
      documentEntity.setUuid(uuid);
      documentEntity.setTipoMINE("application/xml");
      if (documentEntity.getId() == null) {
        documentEntity.setCreatedAt(Calendar.getInstance().getTime());
        documentEntity.setCreatedBy(applicacion);
      } else {
        documentEntity.setModifiedAt(Calendar.getInstance().getTime());
        documentEntity.setModifiedBy(applicacion);
      }
      if (documentEniObject.getTipoPermiso() != null) {// viene de la operación
                                                       // convertirDocumentoAENI
        documentEntity.setTipoPermiso(documentEniObject.getTipoPermiso().getCode());
      }

      // Guardamos el DocumentEntity en bbdd
      documentManagerService.createEni(documentEntity, documentEniObject, modificar);

      // Guardamos la auditoría
      String operacion = "";
      if (documentEniObject.getCalledFrom() != null) {
        switch (documentEniObject.getCalledFrom()) {
          case Constants.CALLED_FROM_GUARDAR_DOCUMENTO:
            operacion = modificar ? Operation.MODIFICAR.value() : Operation.GUARDAR.value();
            break;
          case Constants.CALLED_FROM_CONVERTIR_A_ENI:
            operacion = Operation.CONVERTIR_ENI.value();
            break;
          default:
            break;
        }
      } else {// desde las operaciones propias de ENI, calleFrom viene vacío.
        operacion = modificar ? Operation.MODIFICAR_ENI.value() : Operation.GUARDAR_ENI.value();
      }
      auditManagerService.create(applicacion, documentEntity.getId(),
          documentEniObject.getParametrosAuditoria(), operacion);

    } catch (JAXBException e) {
      LOG.error("Se ha producido un error al guardar el documento en la NAS");
      throw new ServiceException("Se pa producido un error al convertir el DocumentInfo", e);
    }

    LOG.debug("Salimos de guardar el documento ENI. ");
  }

  /**
   * Si el documento viene por referencia realizar la sustitucion de los archivos 1. eliminar el
   * viejo 2. renombrar el nuevo con el nombre del viejo
   * 
   * @param documentoViejo
   * @param documentoNuevo
   */
  private void modificarFile(String documentoViejo, String documentoNuevo) {

    LOG.info("INI modificarFile");

    // elimino el viejo
    try {
      FileUtil.deleteFile(documentoViejo);

      // renombro el nuevo con el nombre del viejo
      FileUtil.renameFileToPath(documentoViejo, documentoNuevo);

    } catch (IOException e) {
      LOG.error(e.getMessage());
    }



    LOG.info("FIN modificarFile");

  }

  protected DocumentoEniFileInsideMtom convertirDocumentoAEni(
      TipoDocumentoConversionInsideMtom.MetadatosEni metadatosEni, DataHandler contenido,
      String csv) throws ConsumerWSException {
    DocumentoEniFileInsideMtom documentoEniFileInside;
    LOG.info("ConvertirDocumentoAEni: csv=" + csv + "; metadatosEni=" + metadatosEni);
    try {
      TipoDocumentoConversionInsideMtom.Csv csvInside = new TipoDocumentoConversionInsideMtom.Csv();
      csvInside.setRegulacionCSV(Constants.REGULACION_CSV);
      csvInside.setValorCSV(csv);

      TipoDocumentoConversionInsideMtom tipoDocumentoConversionInside =
          new TipoDocumentoConversionInsideMtom();
      tipoDocumentoConversionInside.setContenido(contenido);
      tipoDocumentoConversionInside.setCsv(csvInside);
      tipoDocumentoConversionInside.setFirmadoConCertificado(false);
      tipoDocumentoConversionInside.setMetadatosEni(metadatosEni);

      documentoEniFileInside = ginsideUserTokenMtomConsumer
          .convertirDocumentoAEni(tipoDocumentoConversionInside, null, false);

      LOG.info("[FIN] Salimos de validarDocumentoEni. ");

    } catch (Exception e) {
      throw new ConsumerWSException("Error en convertirDocumentoAEni: " + e.getMessage(), e);
    }

    return documentoEniFileInside;
  }

  protected void printOpenFileDescriptor(String method, long fdIni) {
    long fdFin = FileUtil.getFdOpen();
    if (fdFin > fdIni) {
      LOG.warn("FD " + method + ": " + (fdFin - fdIni));
    }
  }

  protected DocumentEntity buscarDocumento(String csv, String dir3, String idEni,
      String recuperacionOriginal, String documentoEni, ResponseObject documentResponseObject)
      throws ServiceException {

    LOG.info("Buscamos el documento. csv=" + csv + "; dir3=" + dir3 + "; idEni=" + idEni
        + "; recuperacionOriginal=" + recuperacionOriginal + "; documentResponseObject="
        + documentResponseObject);

    DocumentEntity documentEntity = null;

    List<DocumentEntity> documentEntityList =
        documentManagerService.findByDocument(csv, dir3, idEni);

    if (documentEntityList != null && documentEntityList.size() == 1) {

      documentEntity =
          handleOneDocument(recuperacionOriginal, documentResponseObject, documentEntityList);

    } else if (documentEntityList != null && documentEntityList.size() == 2) {

      documentEntity = handleTwoDocuments(documentoEni, documentEntityList);
    } else {
      handleNoDocuments(documentResponseObject);
    }

    return documentEntity;
  }


  protected DocumentObject buscarRestriccion(DocumentObject documentObject,
      DocumentEntity documentEntity) throws ServiceException {

    // Obtengo los Nifs
    List<DocumentNifEntity> nifBbddList =
        documentManagerService.findNifsByDocument(documentEntity, null);

    List<String> listaNif = new ArrayList<>();
    for (DocumentNifEntity documentNifEntity : nifBbddList) {

      listaNif.add(documentNifEntity.getNif());
    }

    // Obtengo las Restricciones
    List<DocumentRestriccionEntity> restriccionesBbddList =
        documentManagerService.findRestriccionesByDocument(documentEntity);

    List<Integer> listaRest = new ArrayList<>();
    for (DocumentRestriccionEntity documentRestriccionEntity : restriccionesBbddList) {
      listaRest.add(documentRestriccionEntity.getRestriccion());
    }

    // Obtengo las Restricciones ID
    List<DocumentIdsEntity> idsBbddList =
        documentManagerService.findRestriccionesIDByDocument(documentEntity);

    List<Integer> listaid = new ArrayList<>();
    for (DocumentIdsEntity documentIdsEntity : idsBbddList) {
      listaid.add(documentIdsEntity.getTipoId());
    }

    documentObject.setNifs(listaNif);
    documentObject.setRestricciones(listaRest);
    documentObject.setTipoIds(listaid);

    return documentObject;

  }


  private void handleNoDocuments(ResponseObject documentResponseObject) {
    documentResponseObject.setCodigo(String.valueOf(Constants.QUERY_DOCUMENT_NOT_EXIST));
    documentResponseObject.setDescripcion(
        messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale));
  }

  private DocumentEntity handleTwoDocuments(String documentoEni,
      List<DocumentEntity> documentEntityList) {
    DocumentEntity documentEntity = null;

    boolean obtenerEni = StringUtils.isNotEmpty(documentoEni) && documentoEni.equals("S");

    if (obtenerEni) {
      if (Arrays.asList(Constants.MIME_TYPE_ENI)
          .contains(documentEntityList.get(0).getTipoMINE())) {
        documentEntity = documentEntityList.get(0);
      } else {
        documentEntity = documentEntityList.get(1);
      }
    } else {
      if (Arrays.asList(Constants.MIME_TYPE_ENI)
          .contains(documentEntityList.get(0).getTipoMINE())) {
        documentEntity = documentEntityList.get(1);
      } else {
        documentEntity = documentEntityList.get(0);
      }
    }
    return documentEntity;
  }

  private DocumentEntity handleOneDocument(String recuperacionOriginal,
      ResponseObject documentResponseObject, List<DocumentEntity> documentEntityList)
      throws ServiceException {

    DocumentEntity documentEntity;
    documentEntity = documentEntityList.get(0);
    DocumentEniEntity documentEniEntity = null;

    // Buscamos el documento ENI si tiene informado el IdentificadoDocumentoOrigen se recupera ese
    // valor

    if (Constants.ConstantesDir3Consumer.VALOR_SI.equals(recuperacionOriginal)) {
      documentEniEntity = obtenerDocumentoOrigen(documentEntity, documentResponseObject);
    }

    if (documentEniEntity != null) {
      documentEntity = documentEniEntity.getDocumento();
    } else if (StringUtils.isNotEmpty(documentResponseObject.getCodigo())) {
      documentEntity = null;
    }
    return documentEntity;
  }


  protected DocumentEniEntity buscarDocumentoEni(String csv, String dir3, String idEni,
      ResponseObject documentResponseObject) throws ServiceException {

    List<DocumentEniEntity> documentEniEntityList =
        documentEniManagerService.findByAll(idEni, csv, dir3);
    DocumentEniEntity documentEniEntity = null;

    // Si existe un unico documento
    if (documentEniEntityList != null && documentEniEntityList.size() == 1) {
      documentEniEntity = documentEniEntityList.get(0);
      // multiples documentos
    } else if (documentEniEntityList != null && documentEniEntityList.size() > 1) {
      documentResponseObject.setCodigo(String.valueOf(Constants.RETURN_MULTIPLE_RESULTS));
      documentResponseObject.setDescripcion(
          messageSource.getMessage(Constants.RETURN_MULTIPLE_RESULTS_DESC, null, locale));
      // no documents
    } else {
      handleNoDocuments(documentResponseObject);
    }

    return documentEniEntity;
  }

  private DocumentEniEntity obtenerDocumentoOrigen(DocumentEntity documentEntity,
      ResponseObject documentResponseObject) throws ServiceException {

    LOG.info("Obteniendo documento origen de documentEntity=" + documentEntity);

    DocumentEniEntity documentEniEntity = null;

    if (Arrays.asList(Constants.MIME_TYPE_ENI).contains(documentEntity.getTipoMINE())) {

      documentEniEntity = buscarDocumentoEni(documentEntity.getCsv(),
          documentEntity.getUnidadOrganica().getUnidadOrganica(), documentEntity.getIdEni(),
          documentResponseObject);

      if (documentEniEntity != null
          && StringUtils.isNotEmpty(documentEniEntity.getIdDocumentoOrigen())) {

        documentEniEntity =
            buscarDocumentoEni(null, documentEntity.getUnidadOrganica().getUnidadOrganica(),
                documentEniEntity.getIdDocumentoOrigen(), documentResponseObject);

      }
    }
    LOG.info(documentEniEntity == null ? "Documento no encontrado"
        : "Encontrado documento=" + documentEniEntity);

    return documentEniEntity;
  }

}
