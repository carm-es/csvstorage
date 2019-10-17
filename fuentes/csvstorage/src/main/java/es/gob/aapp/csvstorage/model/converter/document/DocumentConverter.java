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

package es.gob.aapp.csvstorage.model.converter.document;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xml.security.utils.Base64;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.TipoDocumento;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.contenido.TipoContenido;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.contenido.mtom.TipoContenidoMtom;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.documentoe.mtom.TipoDocumentoMtom;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.metadatos.EnumeracionEstadoElaboracion;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.metadatos.TipoDocumental;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.metadatos.TipoEstadoElaboracion;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.metadatos.TipoMetadatos;
import es.gob.aapp.csvstorage.client.ws.eni.firma.Firmas;
import es.gob.aapp.csvstorage.client.ws.eni.firma.TipoFirma;
import es.gob.aapp.csvstorage.client.ws.eni.firma.TipoFirmasElectronicas;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.conversion.TipoDocumentoConversionInside;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.conversion.mtom.TipoDocumentoConversionInsideMtom;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniOrganoEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.model.object.document.DocumentEniObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentInfo;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.util.MimeType;
import es.gob.aapp.csvstorage.util.Util;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.constants.DocumentPermission;
import es.gob.aapp.csvstorage.util.constants.DocumentRestriccion;
import es.gob.aapp.csvstorage.util.constants.IdentificationType;
import es.gob.aapp.csvstorage.util.file.FileUtil;
import es.gob.aapp.csvstorage.util.xml.DocumentoENIUtils;
import es.gob.aapp.csvstorage.util.xml.JAXBMarshaller;
import es.gob.aapp.csvstorage.webservices.document.model.ConvertirDocumentoEniRequest;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoPermisoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoRevocarPermisoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoBigDataRequest;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ListaRestriccion;
import es.gob.aapp.csvstorage.webservices.document.model.ListaTipoIds;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.GuardarDocumentoMtomRequest;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.GuardarDocumentoMtomStreamingRequest;
import es.gob.aapp.eeutil.bigDataTransfer.exception.BigDataTransferException;
import es.gob.aapp.eeutil.bigDataTransfer.service.BigDataTransferService;

/**
 * Clase converter para documentos.
 * 
 * @author carlos.munoz1
 * 
 */
public abstract class DocumentConverter {

  private static final Logger LOG = Logger.getLogger(DocumentConverter.class);

  private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
  public static final String ERROR_AL_OBTENER_EL_CONENIDO_BINARIO =
      "Error al obtener el conenido binario";

  public static DocumentObject convertGuardarDocumentToDocumenObject(
      DocumentoRequest guardarDocumentoRequest, String rutaFichero,
      BigDataTransferService bigDataTransferService) throws IOException, BigDataTransferException {

    DocumentObject documentObject =
        createDocumentObjectFromDocumentoRequest(guardarDocumentoRequest);

    // Obtenemos el contenido a guardar
    if (guardarDocumentoRequest instanceof GuardarDocumentoRequest) {
      LOG.info("Se procede a entra en saveGuardarDocumentoRequest....");
      saveGuardarDocumentoRequest((GuardarDocumentoRequest) guardarDocumentoRequest,
          documentObject);

    } else if (guardarDocumentoRequest instanceof GuardarDocumentoMtomRequest) {
      LOG.info("Se procede a entra en saveGuardarDocumentoMtomRequest....");
      saveGuardarDocumentoMtomRequest((GuardarDocumentoMtomRequest) guardarDocumentoRequest,
          rutaFichero, documentObject);
      // saveGuardarDocumentoMtomStreamingRequest((GuardarDocumentoMtomRequest)
      // guardarDocumentoRequest, rutaFichero, documentObject);
    } else if (guardarDocumentoRequest instanceof GuardarDocumentoMtomStreamingRequest) {
      LOG.info("Se procede a entra en saveGuardarDocumentoMtomStreamingRequest....");
      saveGuardarDocumentoMtomStreamingRequest(
          (GuardarDocumentoMtomStreamingRequest) guardarDocumentoRequest, rutaFichero,
          documentObject);
    } else {
      LOG.info("Se procede a entra en saveLargeFile....");
      saveLargeFile((GuardarDocumentoBigDataRequest) guardarDocumentoRequest, rutaFichero,
          bigDataTransferService, documentObject);
    }

    documentObject.setParametrosAuditoria(guardarDocumentoRequest.parametrosAuditoria());

    return documentObject;
  }

  public static DocumentObject convertGuardarDocumentToDocumenObject(
      DocumentoPermisoRequest guardarDocumentoRequest, String rutaFichero,
      BigDataTransferService bigDataTransferService) throws IOException, BigDataTransferException {


    // Obtenemos el contenido a guardar
    /*
     * if (guardarDocumentoRequest instanceof GuardarDocumentoRequest) {
     * saveGuardarDocumentoRequest((GuardarDocumentoRequest) guardarDocumentoRequest,
     * documentObject);
     * 
     * } else if (guardarDocumentoRequest instanceof GuardarDocumentoMtomRequest) {
     * saveGuardarDocumentoMtomRequest((GuardarDocumentoMtomRequest) guardarDocumentoRequest,
     * documentObject); } else { saveLargeFile((GuardarDocumentoBigDataRequest)
     * guardarDocumentoRequest, rutaFichero, bigDataTransferService, documentObject); }
     */

    return createDocumentObjectFromDocumentoRequest(guardarDocumentoRequest);
  }

  public static DocumentObject convertGuardarDocumentToDocumenObject(
      DocumentoRevocarPermisoRequest guardarDocumentoRequest, String rutaFichero,
      BigDataTransferService bigDataTransferService) throws IOException, BigDataTransferException {


    return createDocumentObjectFromDocumentoRequest(guardarDocumentoRequest);
  }

  private static DocumentObject createDocumentObjectFromDocumentoRequest(
      DocumentoRequest guardarDocumentoRequest) {

    DocumentObject documentObject = new DocumentObject();

    documentObject.setDir3(guardarDocumentoRequest.getDir3());
    documentObject.setIdEni(guardarDocumentoRequest.getIdEni());
    documentObject.setCsv(Util.formatearCsvSinGuiones(guardarDocumentoRequest.getCsv()));
    documentObject.setCodigoHash(guardarDocumentoRequest.getHash());
    documentObject.setAlgoritmoHash(guardarDocumentoRequest.getAlgotirmo());

    LOG.info("PERMISO: " + guardarDocumentoRequest.getTipoPermiso());

    // permisos
    if (guardarDocumentoRequest.getTipoPermiso() != null) {
      documentObject.setTipoPermiso(
          DocumentPermission.valueOf(guardarDocumentoRequest.getTipoPermiso().value()));
    } else {
      documentObject.setTipoPermiso(DocumentPermission.PUBLICO);
    }

    // Restricciones
    LOG.info("Procesamos el metodo: createDocumentObjectFromDocumentoRequest");

    if (guardarDocumentoRequest.getTipoPermiso() != null
        && guardarDocumentoRequest.getTipoPermiso().toString()
            .equals(DocumentPermission.RESTRINGIDO.getDescription())
        && guardarDocumentoRequest.getRestricciones() != null
        && !guardarDocumentoRequest.getRestricciones().getRestriccion().isEmpty()) {

      LOG.info("Entramos en obtener todas las restricciones: "
          + guardarDocumentoRequest.getRestricciones());
      for (ListaRestriccion.Restriccion restriccion : guardarDocumentoRequest.getRestricciones()
          .getRestriccion()) {
        try {
          if (restriccion != null) {
            documentObject.getRestricciones()
                .add(DocumentRestriccion.valueOf(restriccion.value()).getCode());
          }
        } catch (Exception e) {
          documentObject.getRestricciones().clear();
          LOG.error("Restrinccion como nulo o no existe: " + e.getMessage());
          break;
        }
      }
      LOG.info("Restricciones anadidas: " + documentObject.getRestricciones());
    }

    // id's
    if (guardarDocumentoRequest.getTipoIds() != null
        && !guardarDocumentoRequest.getTipoIds().getId().isEmpty()) {

      LOG.info("Entramos en obtener todas las restricciones de tiposID: "
          + guardarDocumentoRequest.getTipoIds());
      for (ListaTipoIds.TipoId id : guardarDocumentoRequest.getTipoIds().getId()) {

        try {
          documentObject.getTipoIds().add(IdentificationType.valueOf(id.value()).getCode());
        } catch (Exception e) {
          documentObject.getTipoIds().clear();
          LOG.error("Restrinccion por ID como nulo o no existe: " + e.getMessage());
          break;
        }
      }

      LOG.info("Restricciones ID anadidas: " + documentObject.getTipoIds());
    }

    // nif's
    if (guardarDocumentoRequest.getNifs() != null
        && !guardarDocumentoRequest.getNifs().getNif().isEmpty()) {
      for (String nif : guardarDocumentoRequest.getNifs().getNif()) {
        documentObject.getNifs().add(nif);
      }

    }

    LOG.info("[FIN] metodo createDocumentObjectFromDocumentoRequest");
    return documentObject;
  }


  private static DocumentObject createDocumentObjectFromDocumentoRequest(
      DocumentoPermisoRequest guardarDocumentoRequest) {

    DocumentObject documentObject = new DocumentObject();

    documentObject.setDir3(guardarDocumentoRequest.getDir3());
    documentObject.setIdEni(guardarDocumentoRequest.getIdEni());
    documentObject.setCsv(Util.formatearCsvSinGuiones(guardarDocumentoRequest.getCsv()));

    List<Integer> restricciones = new ArrayList<>();
    restricciones.add(DocumentRestriccion.RESTRINGIDO_APP.getCode());
    documentObject.setRestricciones(restricciones);


    LOG.info("[FIN] metodo createDocumentObjectFromDocumentoRequest");
    return documentObject;
  }

  private static DocumentObject createDocumentObjectFromDocumentoRequest(
      DocumentoRevocarPermisoRequest guardarDocumentoRequest) {

    DocumentObject documentObject = new DocumentObject();

    documentObject.setDir3(guardarDocumentoRequest.getDir3());
    documentObject.setIdEni(guardarDocumentoRequest.getIdEni());
    documentObject.setCsv(Util.formatearCsvSinGuiones(guardarDocumentoRequest.getCsv()));

    List<Integer> restricciones = new ArrayList<>();
    restricciones.add(DocumentRestriccion.RESTRINGIDO_APP.getCode());
    documentObject.setRestricciones(restricciones);


    LOG.info("[FIN] metodo createDocumentObjectFromDocumentoRequest");
    return documentObject;
  }

  /**
   * Metodo que se encarga de eliminar los documentos guardados en disco.
   * 
   * @param rutaFichero ruta completa
   * @throws IOException
   */
  public static void deleteDocumento(String rutaFichero) throws IOException {

    LOG.info("[INI] deleteDocumento");

    if (StringUtils.isNotEmpty(rutaFichero)) {

      LOG.info("Se procede a eliminar el fichero");
      FileUtil.deleteFile(rutaFichero);
      LOG.info("Documento eliminado: " + rutaFichero);
    }

    LOG.info("[FIN] deleteDocumento");
  }

  private static void saveGuardarDocumentoMtomRequest(GuardarDocumentoMtomRequest gdocmtom,
      String rutaFichero, DocumentObject documentObject) throws IOException {
    LOG.info("[INI] saveGuardarDocumentoMtomRequest");

    if (gdocmtom.getContenido() != null && gdocmtom.getContenido().getContenido() != null) {

      // documentObject.setContenido(IOUtils.toByteArray(gdocmtom.getContenido().getContenido().getInputStream()));
      // documentObject.setMimeType(gdocmtom.getContenido().getTipoMIME());

      String path = obtenerRutaFichero(rutaFichero, gdocmtom.getDir3());
      String name = UUID.randomUUID().toString() + Constants.BIG_FILE_NAME;
      String pathFichero = path + System.getProperty("file.separator") + name;

      com.sun.xml.ws.developer.StreamingDataHandler dh = null;
      try {
        LOG.info("Se procede a mover el fichero a la ruta: " + pathFichero);
        dh = new com.sun.xml.ws.encoding.DataSourceStreamingDataHandler(
            gdocmtom.getContenido().getContenido().getDataSource());
        File folder = new File(path);
        folder.mkdirs();
        File file = new File(pathFichero);
        dh.moveTo(file);
        dh.close();

        documentObject.setContenidoPorRef(path + System.getProperty("file.separator") + name);
        documentObject.setMimeType(gdocmtom.getContenido().getTipoMIME());

        LOG.info("El fichero ha sido movido");
      } catch (Exception e) {
        FileUtil.deleteFile(pathFichero);
        throw e;
      } finally {
        if (dh != null) {
          dh.close();
        }
      }

    }

    LOG.info("[FIN] saveGuardarDocumentoMtomRequest");
  }

  private static void saveGuardarDocumentoMtomStreamingRequest(
      GuardarDocumentoMtomStreamingRequest gdocmtomStreaming, String rutaFichero,
      DocumentObject documentObject) throws IOException {
    if (gdocmtomStreaming.getContenido() != null
        && gdocmtomStreaming.getContenido().getContenido() != null) {
      String path = obtenerRutaFichero(rutaFichero, gdocmtomStreaming.getDir3());
      String name = UUID.randomUUID().toString() + Constants.BIG_FILE_NAME;
      String pathFichero = path + System.getProperty("file.separator") + name;

      com.sun.xml.ws.developer.StreamingDataHandler dh = null;
      try {
        dh = new com.sun.xml.ws.encoding.DataSourceStreamingDataHandler(
            gdocmtomStreaming.getContenido().getContenido().getDataSource());
        File folder = new File(path);
        folder.mkdirs();
        File file = new File(pathFichero);
        dh.moveTo(file);
        dh.close();

        documentObject.setContenidoPorRef(path + System.getProperty("file.separator") + name);
        documentObject.setMimeType(gdocmtomStreaming.getContenido().getTipoMIME());


      } catch (Exception e) {
        FileUtil.deleteFile(pathFichero);
        throw e;
      } finally {
        if (dh != null) {
          dh.close();
        }
      }

    }
  }

  private static void saveGuardarDocumentoRequest(GuardarDocumentoRequest gdoc,
      DocumentObject documentObject) {
    if (gdoc.getContenido() != null && gdoc.getContenido().getContenido() != null) {
      documentObject.setContenido(gdoc.getContenido().getContenido());
      documentObject.setMimeType(gdoc.getContenido().getTipoMIME());
    }
  }

  private static void saveLargeFile(GuardarDocumentoBigDataRequest gdocmtomstream,
      String rutaFichero, BigDataTransferService bigDataTransferService,
      DocumentObject documentObject) throws BigDataTransferException, IOException {

    if (gdocmtomstream.getContenido() != null && gdocmtomstream.getContenido().getFiles() != null) {

      String bigFile = bigDataTransferService.reciveFile(gdocmtomstream.getContenido().getFiles());

      String path = obtenerRutaFichero(rutaFichero, gdocmtomstream.getDir3());
      String name = UUID.randomUUID().toString() + Constants.BIG_FILE_NAME;

      try {
        FileUtil.moveFile(bigFile, path, name);
      } catch (Exception e) {
        // Si se produce un error al mover el fichero a la NAS se borra del temporal
        FileUtil.deleteFile(bigFile);
        throw e;
      }

      documentObject.setContenidoPorRef(path + System.getProperty("file.separator") + name);
      documentObject.setMimeType(gdocmtomstream.getContenido().getTipoMIME());
    }
  }


  public static DocumentEniObject convertGuardarDocumentoEniToDocumentEni(
      DocumentObject documentObject) throws IOException, ServiceException {

    TipoDocumento tipoDocumento = documentObject.getTipoDocumento();
    byte[] contenido = documentObject.getContenido();

    DocumentEniObject documentEniObject = new DocumentEniObject();
    byte[] contenidoBinario = null;

    documentEniObject.setDir3(documentObject.getDir3());
    documentEniObject.setCsv(Util.formatearCsvSinGuiones(documentObject.getCsv()));
    documentEniObject.setAlgoritmoHash(documentObject.getAlgoritmoHash());
    documentEniObject.setCodigoHash(documentObject.getCodigoHash());

    // permisos
    if (documentObject.getTipoPermiso() != null) {
      documentEniObject.setTipoPermiso(documentObject.getTipoPermiso());
    } else {
      documentEniObject.setTipoPermiso(DocumentPermission.PUBLICO);
    }

    documentEniObject.setRestricciones(documentObject.getRestricciones());
    documentEniObject.setNifs(documentObject.getNifs());
    documentEniObject.setTipoIds(documentObject.getTipoIds());
    documentEniObject.setAplicaciones(documentObject.getAplicaciones());

    documentEniObject.setContenidoPorRef(documentObject.getContenidoPorRef());

    if (tipoDocumento == null) {
      LOG.info("Tipo de documento viene NULO");
      return documentEniObject;
    }

    boolean valorBinario = false;

    if (tipoDocumento.getContenido() != null) {

      if (StringUtils.isNotEmpty(tipoDocumento.getContenido().getNombreFormato())) {
        documentEniObject
            .setExtension(tipoDocumento.getContenido().getNombreFormato().toLowerCase());
      }

      // Si el contenido binario del documento no es nulo,
      if (tipoDocumento.getContenido().getValorBinario() != null) {
        valorBinario = true;
        contenidoBinario = tipoDocumento.getContenido().getValorBinario();
        // tipoDocumento.getContenido().setValorBinario("test".getBytes()); //????
      }

    }

    DocumentEniEntity documentEniEntity = getDocumentEniEntity(tipoDocumento, valorBinario);
    documentEniObject.setDocumentEniEntity(documentEniEntity);
    documentEniObject.setIdEni(documentEniEntity.getIdentificador());
    // Rellenamos la lista de DocumentEniOrganoEntity
    documentEniObject.setOrganosList(
        getDocumentEniOrganoEntityList(tipoDocumento.getMetadatos(), documentEniEntity));
    documentEniObject.setTipoDocumento(tipoDocumento);

    Firmas firma = tipoDocumento.getFirmas();
    if (firma != null && firma.getFirma() != null && !firma.getFirma().isEmpty()) {
      documentEniObject.setFirmado(true);

      for (TipoFirmasElectronicas tipoFirmasElectronicas : firma.getFirma()) {
        if (tipoFirmasElectronicas.getTipoFirma().equals(TipoFirma.TF_01)) {
          String firmaCsv = tipoFirmasElectronicas.getContenidoFirma().getCSV().getValorCSV();
          documentEniObject.setCsv(Util.formatearCsvSinGuiones(firmaCsv));
        }
      }


    } else {
      documentEniObject.setFirmado(false);
    }


    try {
      byte[] docEni;
      if (contenido == null) {

        String xmlTipoDocumento = JAXBMarshaller.marshallDataDocument(tipoDocumento,
            "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e", "documento",
            "enidoc");
        docEni = xmlTipoDocumento.getBytes();

        if (valorBinario) {
          tipoDocumento.getContenido().setValorBinario(contenidoBinario);
        }

      } else {
        docEni = contenido;
      }

      // El contenido del documento va a ser todo el documento ENI
      documentEniObject.setContenido(docEni);
      documentEniObject.setParametrosAuditoria(documentObject.getParametrosAuditoria());
      return documentEniObject;

    } catch (Exception e) {
      LOG.error("se ha producido un error al hacer el marshall del documento ENI", e);
      throw new ServiceException("se ha producido un error al hacer el marshall del documento ENI",
          e);
    }
  }

  public static TipoDocumentoConversionInsideMtom.MetadatosEni metadatosToInside(
      ConvertirDocumentoEniRequest.MetadatosEni metadatosInside) {

    TipoDocumentoConversionInsideMtom.MetadatosEni metadatosEni =
        new TipoDocumentoConversionInsideMtom.MetadatosEni();
    TipoEstadoElaboracion estadoElaboracion = new TipoEstadoElaboracion();
    if (metadatosInside.getEstadoElaboracion() != null) {
      estadoElaboracion.setValorEstadoElaboracion(EnumeracionEstadoElaboracion
          .fromValue(metadatosInside.getEstadoElaboracion().getValorEstadoElaboracion().value()));
      estadoElaboracion.setIdentificadorDocumentoOrigen(
          metadatosInside.getEstadoElaboracion().getIdentificadorDocumentoOrigen());
    }
    metadatosEni.setEstadoElaboracion(estadoElaboracion);
    metadatosEni.setFechaCaptura((metadatosInside.getFechaCaptura()));
    metadatosEni.setIdentificador(metadatosInside.getIdentificador());
    metadatosEni
        .setOrigenCiudadanoAdministracion(metadatosInside.isOrigenCiudadanoAdministracion());
    metadatosEni
        .setTipoDocumental(TipoDocumental.fromValue(metadatosInside.getTipoDocumental().value()));
    metadatosEni.setVersionNTI(metadatosInside.getVersionNTI());
    metadatosEni.getOrgano().addAll(metadatosInside.getOrgano());
    return metadatosEni;
  }


  /**
   * Rellena el objeto DocumentEniEntity
   */
  private static DocumentEniEntity getDocumentEniEntity(Object tipoDocumento,
      boolean valorBinario) {
    DocumentEniEntity documentEniEntity = new DocumentEniEntity();
    if (tipoDocumento instanceof TipoDocumento) {
      TipoDocumento pd = (TipoDocumento) tipoDocumento;
      TipoMetadatos tm = pd.getMetadatos();

      documentEniEntity.setValorBinario(valorBinario);
      documentEniEntity.setNombreFormato(pd.getContenido().getNombreFormato());
      documentEniEntity.setReferencia(pd.getContenido().getReferenciaFichero());

      documentEniEntity.setVersionNti(tm.getVersionNTI());
      documentEniEntity.setOrigenCiudadanoAdministracion(tm.isOrigenCiudadanoAdministracion());
      documentEniEntity.setCreatedAt(new Date());
      documentEniEntity.setIdentificador(tm.getIdentificador());
      Calendar cal = XmlGregorianCalendarConverter.calendarToXmlCalendar(tm.getFechaCaptura());
      if (cal != null) {
        documentEniEntity.setFechaCaptura(cal.getTime());
      }
      if (tm.getEstadoElaboracion() != null) {
        documentEniEntity
            .setEstadoElaboracion(tm.getEstadoElaboracion().getValorEstadoElaboracion().value());
        documentEniEntity
            .setIdDocumentoOrigen(tm.getEstadoElaboracion().getIdentificadorDocumentoOrigen());
      }
      documentEniEntity.setTipoDocumental(tm.getTipoDocumental().value());

    } else {

      TipoDocumentoMtom pd = (TipoDocumentoMtom) tipoDocumento;
      TipoMetadatos tm2 = pd.getMetadatos();

      documentEniEntity.setValorBinario(valorBinario);
      documentEniEntity.setNombreFormato(pd.getContenidoMtom().getNombreFormato());
      documentEniEntity.setReferencia(pd.getContenidoMtom().getReferenciaFichero());

      documentEniEntity.setVersionNti(tm2.getVersionNTI());
      documentEniEntity.setOrigenCiudadanoAdministracion(tm2.isOrigenCiudadanoAdministracion());
      documentEniEntity.setCreatedAt(new Date());
      documentEniEntity.setIdentificador(tm2.getIdentificador());
      Calendar cal = XmlGregorianCalendarConverter.calendarToXmlCalendar(tm2.getFechaCaptura());
      if (cal != null) {
        documentEniEntity.setFechaCaptura(cal.getTime());
      }
      if (tm2.getEstadoElaboracion() != null) {
        documentEniEntity
            .setEstadoElaboracion(tm2.getEstadoElaboracion().getValorEstadoElaboracion().value());
        documentEniEntity
            .setIdDocumentoOrigen(tm2.getEstadoElaboracion().getIdentificadorDocumentoOrigen());
      }
      documentEniEntity.setTipoDocumental(tm2.getTipoDocumental().value());
    }
    return documentEniEntity;
  }

  /**
   * Rellena el listado de Organismos
   * 
   * @param tipoMetadato TipoMetadato
   * @return Lista de DocumentEniOrganoEntity
   */
  private static List<DocumentEniOrganoEntity> getDocumentEniOrganoEntityList(Object tipoMetadato,
      DocumentEniEntity documentEniEntity) {
    List<DocumentEniOrganoEntity> organosList = new ArrayList<DocumentEniOrganoEntity>();
    List<String> organos = ((TipoMetadatos) tipoMetadato).getOrgano();

    if (organos != null) {
      for (String organo : organos) {
        DocumentEniOrganoEntity organoEntity = new DocumentEniOrganoEntity();
        organoEntity.setDocumentoEni(documentEniEntity);
        organoEntity.setOrgano(organo);
        organosList.add(organoEntity);
      }
    }

    return organosList;
  }

  public static TipoDocumento convertTipoDocumentoMtomToTipoDocumento(
      TipoDocumentoMtom documentoMtom) throws ConsumerWSException {
    TipoDocumento documento = new TipoDocumento();

    try {
      LOG.info("[INI] Entramos en convertirTipoDocumentoMtomATipoDocumento. ");

      // Convertimos el TipoDocumentoMtom a TipoDocumento
      TipoMetadatos tipoMetadatosMtom = documentoMtom.getMetadatos();

      TipoMetadatos metadatosEni = new TipoMetadatos();
      TipoEstadoElaboracion estadoElaboracion = new TipoEstadoElaboracion();
      if (tipoMetadatosMtom.getEstadoElaboracion() != null) {
        estadoElaboracion.setValorEstadoElaboracion(EnumeracionEstadoElaboracion.fromValue(
            tipoMetadatosMtom.getEstadoElaboracion().getValorEstadoElaboracion().value()));
        estadoElaboracion.setIdentificadorDocumentoOrigen(
            tipoMetadatosMtom.getEstadoElaboracion().getIdentificadorDocumentoOrigen());
      }
      metadatosEni.setEstadoElaboracion(estadoElaboracion);
      metadatosEni.setFechaCaptura(tipoMetadatosMtom.getFechaCaptura());

      metadatosEni.setIdentificador(tipoMetadatosMtom.getIdentificador());
      metadatosEni.setId(tipoMetadatosMtom.getId());
      metadatosEni
          .setOrigenCiudadanoAdministracion(tipoMetadatosMtom.isOrigenCiudadanoAdministracion());
      metadatosEni.setTipoDocumental(
          TipoDocumental.fromValue(tipoMetadatosMtom.getTipoDocumental().value()));
      metadatosEni.setVersionNTI(tipoMetadatosMtom.getVersionNTI());
      metadatosEni.getOrgano().addAll(tipoMetadatosMtom.getOrgano());

      TipoContenidoMtom tipoContenidoMtom = documentoMtom.getContenidoMtom();

      TipoContenido tipoContenido = new TipoContenido();
      tipoContenido.setId(tipoContenidoMtom.getId());
      tipoContenido.setDatosXML(tipoContenidoMtom.getDatosXML());
      tipoContenido.setNombreFormato(tipoContenidoMtom.getNombreFormato());
      tipoContenido.setReferenciaFichero(tipoContenidoMtom.getReferenciaFichero());

      if (tipoContenidoMtom.getValorBinario() != null) {
        tipoContenido.setValorBinario(
            IOUtils.toByteArray(tipoContenidoMtom.getValorBinario().getInputStream()));
      }

      // Reellenamos los datos para validar el documento eni
      documento.setMetadatos(metadatosEni);
      documento.setFirmas(documentoMtom.getFirmas());
      documento.setId(documentoMtom.getId());
      documento.setContenido(tipoContenido);

      LOG.info("[FIN] Salimos de convertirTipoDocumentoMtomATipoDocumento. ");
    } catch (IOException e) {
      LOG.error(ERROR_AL_OBTENER_EL_CONENIDO_BINARIO);
      throw new ConsumerWSException(ERROR_AL_OBTENER_EL_CONENIDO_BINARIO, e);
    }

    return documento;
  }

  public static TipoDocumentoMtom convertTipoDocumentoToTipoDocumentoMtom(
      TipoDocumento tipoDocumento) throws ConsumerWSException {
    TipoDocumentoMtom tipoDocumentoMtom = new TipoDocumentoMtom();

    try {
      LOG.info("[INI] Entramos en convertTipoDocumentoToTipoDocumentoMtom. ");

      // Convertimos el TipoDocumento a TipoDocumentoMtom
      TipoMetadatos tipoMetadatosMtom = tipoDocumento.getMetadatos();

      TipoMetadatos metadatosEni = new TipoMetadatos();
      TipoEstadoElaboracion estadoElaboracion = new TipoEstadoElaboracion();
      if (tipoMetadatosMtom.getEstadoElaboracion() != null) {
        estadoElaboracion.setValorEstadoElaboracion(EnumeracionEstadoElaboracion.fromValue(
            tipoMetadatosMtom.getEstadoElaboracion().getValorEstadoElaboracion().value()));
        estadoElaboracion.setIdentificadorDocumentoOrigen(
            tipoMetadatosMtom.getEstadoElaboracion().getIdentificadorDocumentoOrigen());
      }
      metadatosEni.setEstadoElaboracion(estadoElaboracion);
      metadatosEni.setFechaCaptura(tipoMetadatosMtom.getFechaCaptura());

      metadatosEni.setIdentificador(tipoMetadatosMtom.getIdentificador());
      metadatosEni.setId(tipoMetadatosMtom.getId());
      metadatosEni
          .setOrigenCiudadanoAdministracion(tipoMetadatosMtom.isOrigenCiudadanoAdministracion());
      metadatosEni.setTipoDocumental(
          TipoDocumental.fromValue(tipoMetadatosMtom.getTipoDocumental().value()));
      metadatosEni.setVersionNTI(tipoMetadatosMtom.getVersionNTI());
      metadatosEni.getOrgano().addAll(tipoMetadatosMtom.getOrgano());

      TipoContenido tipoContenido = tipoDocumento.getContenido();

      TipoContenidoMtom tipoContenidoMtom = new TipoContenidoMtom();
      tipoContenidoMtom.setId(tipoContenido.getId());
      tipoContenidoMtom.setDatosXML(tipoContenido.getDatosXML());
      tipoContenidoMtom.setNombreFormato(tipoContenido.getNombreFormato());
      tipoContenidoMtom.setReferenciaFichero(tipoContenido.getReferenciaFichero());
      if (tipoContenido.getValorBinario() != null) {
        DataSource source = new ByteArrayDataSource(tipoContenido.getValorBinario(),
            MimeType.getInstance().extractTypeFromExtension(tipoContenido.getNombreFormato()));
        tipoContenidoMtom.setValorBinario(new DataHandler(source));
      }

      // Reellenamos los datos para validar el documento eni
      tipoDocumentoMtom.setMetadatos(metadatosEni);
      tipoDocumentoMtom.setFirmas(tipoDocumento.getFirmas());
      tipoDocumentoMtom.setId(tipoDocumento.getId());
      tipoDocumentoMtom.setContenidoMtom(tipoContenidoMtom);

      LOG.info("[FIN] Salimos de convertTipoDocumentoToTipoDocumentoMtom. ");
    } catch (Exception e) {
      LOG.error(ERROR_AL_OBTENER_EL_CONENIDO_BINARIO);
      throw new ConsumerWSException(ERROR_AL_OBTENER_EL_CONENIDO_BINARIO, e);
    }

    return tipoDocumentoMtom;
  }


  public static Object convertDocumentoEni(DocumentEniEntity documentEniEntity,
      List<DocumentEniOrganoEntity> listOrganos, boolean isMtom) throws ConsumerWSException {
    Object documento = null;

    try {
      LOG.info("[INI] Entramos en convertirTipoDocumentoMtomATipoDocumento. ");

      String separator = "/";
      String path;
      DocumentEntity documentEntity = documentEniEntity.getDocumento();

      if (documentEntity.getUuid() != null) {
        path = documentEntity.getRutaFichero() + separator + documentEntity.getUuid();
      } else {
        path = documentEntity.getRutaFichero();
      }

      // Si el tipo Mime es "application/xml" es porque el documento almacenado en la NAS es el
      // documento ENI completo
      if (documentEntity.getTipoMINE().equals("application/xml")) {
        byte[] content = obtenerContenido(path, documentEntity.getContenidoPorRef());

        // TipoDocumento tipoDocumento = JAXBMarshaller.unmarshallDataDocument(content);
        TipoDocumento tipoDocumento =
            DocumentoENIUtils.getTipoDocumentoByDocumentoEni(new ByteArrayInputStream(content));

        if (isMtom) {
          documento = convertTipoDocumentoToTipoDocumentoMtom(tipoDocumento);
        } else {
          documento = tipoDocumento;
        }

      } else {
        // Si el tipo Mime no es "application/xml" es porque el documento almacenado en la NAS es el
        // contenido binario
        documento = convertDocumentoEniByContent(documentEniEntity, listOrganos, isMtom);
      }


      LOG.info("[FIN] Salimos de validarDocumentoEni. ");

    } catch (Exception e) {
      LOG.error(ERROR_AL_OBTENER_EL_CONENIDO_BINARIO);
      throw new ConsumerWSException(ERROR_AL_OBTENER_EL_CONENIDO_BINARIO, e);
    }

    return documento;
  }

  // Inicialmente en la NAS lo que se almacenaba no era el documento ENI completo sino solo el
  // contenido binario.
  // Este método se mantiene para poder recuperar esos documentos, y poder generar el ENI a partir
  // de los datos de bbdd y el contenido binario guardado en la NAS
  private static Object convertDocumentoEniByContent(DocumentEniEntity documentEniEntity,
      List<DocumentEniOrganoEntity> listOrganos, boolean isMtom) throws ConsumerWSException {
    LOG.info("[INI] Entramos en convertDocumentoEniGInside. ");
    Object response = null;
    try {

      DocumentEntity documentEntity = documentEniEntity.getDocumento();

      String separator = "/";
      String path;
      if (documentEntity.getUuid() != null) {
        path = documentEntity.getRutaFichero() + separator + documentEntity.getUuid();
      } else {
        path = documentEntity.getRutaFichero();
      }

      if (isMtom) {
        TipoDocumentoConversionInsideMtom.MetadatosEni metadatosEni =
            new TipoDocumentoConversionInsideMtom.MetadatosEni();
        metadatosEni.setEstadoElaboracion(new TipoEstadoElaboracion());
        metadatosEni.getEstadoElaboracion().setValorEstadoElaboracion(
            EnumeracionEstadoElaboracion.fromValue(documentEniEntity.getEstadoElaboracion()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(documentEniEntity.getFechaCaptura());
        metadatosEni.setFechaCaptura(XmlGregorianCalendarConverter.calendarToXmlCalendar(cal));
        metadatosEni.setIdentificador(documentEniEntity.getIdentificador());
        metadatosEni
            .setOrigenCiudadanoAdministracion(documentEniEntity.getOrigenCiudadanoAdministracion());
        metadatosEni.setVersionNTI(documentEniEntity.getVersionNti());
        metadatosEni
            .setTipoDocumental(TipoDocumental.fromValue(documentEniEntity.getTipoDocumental()));

        for (DocumentEniOrganoEntity organo : listOrganos) {
          metadatosEni.getOrgano().add(organo.getOrgano());
        }


        TipoDocumentoMtom tipodoc = new TipoDocumentoMtom();
        tipodoc.setContenidoMtom(new TipoContenidoMtom());
        tipodoc.getContenidoMtom().setNombreFormato(documentEniEntity.getNombreFormato());

        if (documentEniEntity.getReferencia() == null) {
          if (documentEniEntity.getValorBinario()) {
            DataSource source = new FileDataSource(path);
            tipodoc.getContenidoMtom().setValorBinario(new DataHandler(source));
          } else {
            byte[] content = FileUtil.getContentFile(path);
            Object obj = Util.stringToObjectXML(content);
            tipodoc.getContenidoMtom().setDatosXML(obj);
          }
        } else {
          tipodoc.getContenidoMtom().setReferenciaFichero(documentEniEntity.getReferencia());
        }

        tipodoc.setMetadatos(new TipoMetadatos());
        tipodoc.getMetadatos().setIdentificador(metadatosEni.getIdentificador());
        tipodoc.getMetadatos().setVersionNTI(metadatosEni.getVersionNTI());

        response = tipodoc;

      } else {

        TipoDocumentoConversionInside.MetadatosEni metadatosEni =
            new TipoDocumentoConversionInside.MetadatosEni();

        metadatosEni.setEstadoElaboracion(new TipoEstadoElaboracion());
        metadatosEni.getEstadoElaboracion().setValorEstadoElaboracion(
            EnumeracionEstadoElaboracion.fromValue(documentEniEntity.getEstadoElaboracion()));

        Calendar cal = Calendar.getInstance();
        cal.setTime(documentEniEntity.getFechaCaptura());
        metadatosEni.setFechaCaptura(XmlGregorianCalendarConverter.calendarToXmlCalendar(cal));
        metadatosEni.setIdentificador(documentEniEntity.getIdentificador());
        metadatosEni
            .setOrigenCiudadanoAdministracion(documentEniEntity.getOrigenCiudadanoAdministracion());
        metadatosEni.setVersionNTI(documentEniEntity.getVersionNti());
        metadatosEni
            .setTipoDocumental(TipoDocumental.fromValue(documentEniEntity.getTipoDocumental()));

        for (DocumentEniOrganoEntity organo : listOrganos) {
          metadatosEni.getOrgano().add(organo.getOrgano());
        }

        TipoDocumento tipodoc = new TipoDocumento();
        tipodoc.setContenido(new TipoContenido());
        tipodoc.getContenido().setNombreFormato(documentEniEntity.getNombreFormato());

        if (documentEniEntity.getReferencia() == null) {
          byte[] content = FileUtil.getContentFile(path);
          if (documentEniEntity.getValorBinario()) {
            tipodoc.getContenido().setValorBinario(content);
          } else {
            Object obj = Util.stringToObjectXML(content);
            tipodoc.getContenido().setDatosXML(obj);
          }
        } else {
          tipodoc.getContenido().setReferenciaFichero(documentEniEntity.getReferencia());
        }

        tipodoc.setMetadatos(new TipoMetadatos());
        tipodoc.getMetadatos().setIdentificador(metadatosEni.getIdentificador());
        tipodoc.getMetadatos().setVersionNTI(metadatosEni.getVersionNTI());
        tipodoc.getMetadatos().setFechaCaptura(metadatosEni.getFechaCaptura());

        response = tipodoc;
      }
      LOG.info("[FIN] Salimos de convertDocumentoEniGInside. ");
      return response;

    } catch (Exception e) {
      LOG.error("Se ha producido un error en convertDocumentoEniGInside", e);
      throw new ConsumerWSException(e.getMessage());
    }
  }

  public byte[] getXmlBytesFromTipoDocumento(TipoDocumento tipoDocumento)
      throws ConsumerWSException {
    byte[] document = null;

    try {
      byte[] valorBinario = tipoDocumento.getContenido().getValorBinario();
      tipoDocumento.getContenido().setValorBinario(null);

      final String docMarshall = JAXBMarshaller.marshallDataDocument(tipoDocumento,
          "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e", "documento",
          "enidoc");

      String searchString = "<contenido Id=\"CONTENIDO_DOCUMENTO\">";
      StringBuilder stringReplace = new StringBuilder(searchString);
      stringReplace.append("<ValorBinario>");
      stringReplace.append(Base64.encode(valorBinario));
      stringReplace.append("</ValorBinario>");
      document = StringUtils.replace(docMarshall, searchString, stringReplace.toString())
          .getBytes(UTF8_CHARSET);
    } catch (Exception e) {
      LOG.error("Se ha producido un error en getXmlBytesFromTipoDocumento", e);
      throw new ConsumerWSException(e.getMessage());
    }

    return document;
  }

  public static TipoDocumento getTipoDocumentoFromXmlFile(byte[] contenido)
      throws ConsumerWSException {

    TipoDocumento tipoDocumento;
    try {

      // tipoDocumento = JAXBMarshaller.unmarshallDataDocument(contenido);
      tipoDocumento =
          DocumentoENIUtils.getTipoDocumentoByDocumentoEni(new ByteArrayInputStream(contenido));

    } catch (Exception e) {
      LOG.error("Se ha producido un error en getTipoDocumentoFromXmlFile", e);
      throw new ConsumerWSException(e.getMessage());
    }

    return tipoDocumento;
  }

  public static DocumentObject convertDocumentEntityToObject(DocumentEntity entity) {

    DocumentObject response = new DocumentObject();
    response.setUuid(entity.getUuid());
    response.setCsv(entity.getCsv());
    response.setDir3(entity.getUnidadOrganica().getUnidadOrganica());
    response.setIdEni(entity.getIdEni());
    response.setMimeType(entity.getTipoMINE());
    response.setName(entity.getName());
    response.setPathFile(entity.getRutaFichero());

    return response;
  }

  public static byte[] obtenerContenido(String path, boolean contentByRef) throws IOException {
    byte[] content = FileUtil.getContentFile(path);
    InputStream input = null;
    try {

      input = new FileInputStream(path);
      String contentStr = IOUtils.toString(input);
      // DocumentInfo documentInfo = JAXBMarshaller.unmarshallRootElement(contentStr,
      // DocumentInfo.class);
      DocumentInfo documentInfo = DocumentoENIUtils.parseToDocumentInfo(contentStr);
      content = documentInfo.getContenido();
      try {

        if (contentByRef) {
          InputStream stream = new ByteArrayInputStream(content);
          String contentToStr = IOUtils.toString(stream);

          String pathContent = contentToStr.replace(Constants.REF_FILE, "");
          content = FileUtil.getContentFile(pathContent);
        }

      } catch (Exception e) {
        LOG.info("No es una referencia");
      }

    } catch (/* JAXBException | */ XMLStreamException e) {
      LOG.info("No es un DocumentInfo");
    } finally {
      if (input != null) {
        input.close();
      }
    }
    return content;
  }

  public static DataHandler obtenerContenidoDH(String path, String mime) throws IOException {

    InputStream input = null;
    DataHandler dh;
    byte[] content;

    try {

      input = new FileInputStream(path);
      String contentStr = IOUtils.toString(input);
      // DocumentInfo documentInfo = JAXBMarshaller.unmarshallRootElement(contentStr,
      // DocumentInfo.class);
      DocumentInfo documentInfo = DocumentoENIUtils.parseToDocumentInfo(contentStr);
      content = documentInfo.getContenido();

      dh = new DataHandler(new ByteArrayDataSource(content, documentInfo.getTipoMime()));

    } catch (/* JAXBException | */ XMLStreamException e) {
      LOG.info("No es un DocumentInfo");
      content = FileUtil.getContentFile(path);
      dh = new DataHandler(new ByteArrayDataSource(content, mime));
    } finally {
      if (input != null) {
        input.close();
      }
    }
    return dh;
  }

  public static DataHandler obtenerContenidoBigFileDH(String path) throws IOException {
    LOG.info("[INI] Entramos en obtenerContenidoBigFileDH");
    return new DataHandler(new FileDataSource(path));

  }

  public static String obtenerReferenciaContenido(DataHandler content) throws IOException {

    String pathContent = null;
    try {

      String contentToStr = IOUtils.toString(content.getInputStream());
      pathContent = contentToStr.replace(Constants.REF_FILE, "");

    } catch (IOException e) {
      LOG.info("No es un DocumentInfo");
    }
    return pathContent;
  }

  /**
   * Parche #19 Obtiene una cadena con formato yyyy/MM/dd/HHX, donde yyyy es el año actual, MM dos
   * dígitos correspondientes al mes en curso, dd es el día del mes, HH es la hora en la que se
   * llama a la función (en formato HH24) y X es la fracción de los minutos, de manera que desde el
   * minuto 0 a 9 =0, desde el 10 al 19 es=1, etc...
   * 
   * @return Devuevle una cadena con formato yyyy/MM/dd/HHX, en base a la fecha actual
   */
  private static String obtenerRutaPorFecha() {
    Date date = new Date();
    // Obtener ruta inicial (Parche #15)
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH");
    String fecha = sdf.format(date);

    // Ahora obtener la fracción del minuto
    int m = 0;
    try {
      SimpleDateFormat minuto = new SimpleDateFormat("mm");
      m = Integer.parseInt(minuto.format(date));
    } catch (Exception x) {
      // Uff, mal rollo aquí una exception, pero la ignoramos...
    }
    int fraccion = m / 10;
    return fecha + fraccion;
  }

  /**
   * Devuelve la ruta donde se van a almacenar los documentos
   * 
   * @param dir3 dir3
   * @return ruta
   * @throws IOException error
   */
  public static String obtenerRutaFichero(String rutaFichero, String dir3) {
    StringBuilder path = new StringBuilder("");
    String separator = System.getProperty("file.separator");

    // Si no existe almacenamos el documento y guardamos su
    // ruta, csv y organismo en bbdd.
    // Parche #19
    String fecha = obtenerRutaPorFecha();

    // añadir a la rutas del sistema de ficheros la estructura
    // /Año/Mes/Día/HoraFracionMinuto/dir3
    path.append(rutaFichero);
    path.append(separator);
    path.append(fecha.replace("/", separator));
    path.append(separator);
    path.append(dir3);

    return path.toString();

  }

  public static byte[] eliminarValorBinarioEni(byte[] contenido) {

    StringBuilder contentBuild;
    String contentString = new String(contenido);

    int initialPos = contentString.indexOf("<ValorBinario>") + 15;
    if (initialPos > -1) {
      int finalPos = contentString.indexOf("</ValorBinario>");
      contentBuild = new StringBuilder(contentString.substring(0, initialPos));
      contentBuild.append("text".toCharArray());
      contentBuild.append(contentString.substring(finalPos, contentString.length()));
    } else {
      contentBuild = new StringBuilder(contentString);
    }


    return contentBuild.toString().getBytes(Constants.UTF8_CHARSET);
  }

}
