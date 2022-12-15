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

package es.gob.aapp.csvstorage.webservices.document.impl;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
// import org.apache.chemistry.opencmis.client.api.CmisObject;
// import org.apache.chemistry.opencmis.client.api.Document;
// import org.apache.chemistry.opencmis.client.api.DocumentType;
// import org.apache.chemistry.opencmis.client.api.Session;
// import org.apache.chemistry.opencmis.commons.enums.BindingType;
// import org.apache.chemistry.opencmis.commons.exceptions.CmisBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentResponseObject;
import es.gob.aapp.csvstorage.services.business.document.GetDocumentBusinessService;
import es.gob.aapp.csvstorage.services.business.document.SaveDocumentBusinessService;
import es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.webservices.document.CSVDocumentBigDataService;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoBigDataResponse;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoBigDataRequest;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoUuidResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoBigDataResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.Response;
import es.gob.aapp.csvstorage.webservices.document.model.ResponseUuid;
import es.gob.aapp.csvstorage.webservices.document.model.WSCredential;

@WebService(serviceName = "CSVDocumentBigDataService", portName = "CSVDocumentBigDataServicePort",
    targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:v1.0",
    endpointInterface = "es.gob.aapp.csvstorage.webservices.document.CSVDocumentBigDataService")
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.BARE, use = Use.LITERAL)
public class CSVDocumentBigDataServiceImpl implements CSVDocumentBigDataService {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(CSVDocumentBigDataServiceImpl.class);


  /** Inyección de los servicios business de almacenamiento de documentos. */
  @Autowired
  private SaveDocumentBusinessService saveDocumentBusinessService;

  /** Inyección de los servicios business de recuperación de documentos. */
  @Autowired
  private GetDocumentBusinessService getDocumentBusinessService;

  /** Inyección de los servicios business de las aplicaciones */
  @Autowired
  private ApplicationManagerService applicationManagerService;



  @Override
  public ResponseUuid guardar(WSCredential info, GuardarDocumentoBigDataRequest guardarDocumento)
      throws CSVStorageException {
    LOG.debug("[BigData] Entramos en guardar. Request: " + guardarDocumento.toString());

    GuardarDocumentoUuidResponse response =
        saveDocumentBusinessService.guardarUuid(guardarDocumento);
    LOG.debug("[BigData] Salimos de guardar.");

    return response.getUuidResponse();
  }


  @Override
  public Response modificar(WSCredential info, GuardarDocumentoBigDataRequest modificarDocumento)
      throws CSVStorageException {
    LOG.debug("Entramos en modificar. Request: " + modificarDocumento.toString());

    GuardarDocumentoResponse response = saveDocumentBusinessService.modificar(modificarDocumento);
    LOG.debug("Salimos de modificar.");

    return response.getResponse();
  }


  @Override
  public DocumentoBigDataResponse obtener(WSCredential info,
      ObtenerDocumentoRequest obtenerDocumento, String url) throws CSVStorageException {
    LOG.info("Entramos en obtener. Request: " + obtenerDocumento.toString());

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
    ObtenerDocumentoBigDataResponse response =
        (ObtenerDocumentoBigDataResponse) getDocumentBusinessService.obtener(documentObject,
            ObtenerDocumentoBigDataResponse.class, url);
    LOG.info("Salimos de obtener.");

    return response.getDocumentoResponse();
  }


  @Override
  public Long obtenerTamanioDocumento(WSCredential info, String uuid) throws CSVStorageException {

    return getDocumentBusinessService.obtenerTamanioUuid(uuid);
  }


  // @Override
  // public ContenidoUuidInfo obtenerInfoContenido(WSCredential info, String uuid)
  // throws CSVStorageException {
  //
  // ContenidoUuidInfo contenidoUuidInfo = new ContenidoUuidInfo();
  //
  // ObtenerInfoContenidoResponse contenidoResponse;
  //
  // try {
  //
  // final Document document = (Document) getCmisObject(uuid, null, info.getIdaplicacion(),
  // info.getPassword());
  // String url = document.getContentUrl();
  //
  // contenidoUuidInfo.setNombre(document.getName());
  // contenidoUuidInfo.setTipoMIME(document.getContentStreamMimeType());
  // contenidoUuidInfo.setUrl(url);
  //
  // contenidoResponse = new ObtenerInfoContenidoResponse();
  // contenidoResponse.setContenidoUuidInfo(contenidoUuidInfo);
  // } catch (RepositoryCmisException e) {
  // throw new CSVStorageException("Se ha producido un error al recuperar la URL");
  // } catch (Exception e) {
  // throw new CSVStorageException("Se ha producido un error al recuperar la URL");
  // }
  //
  // return contenidoResponse.getContenidoUuidInfo();
  // }

  /**
   * @param objectId
   * @param version
   * @return
   * @throws CmisBaseException
   * @throws RepositoryCmisException
   */
  // private CmisObject getCmisObject(final String objectId, final String version, String
  // idAplication, String password)
  // throws RepositoryCmisException
  // {
  // if (!sessionManager.isSessionAlive()) {
  //
  // ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentContextPath();
  // URI newUri = builder.build().toUri();
  // String url = newUri.toString() + "/cmis/atom11";
  // sessionManager.setUsername(idAplication);
  // sessionManager.setPassword(password);
  // sessionManager.setBindingType(BindingType.ATOMPUB.value());
  // sessionManager.setUrl(url);
  //
  // sessionManager.authenticate();
  // }
  // final Session session = sessionManager.getSession();
  // CmisObject cmisObject = session.getObject(objectId);
  // if (!StringUtils.isEmpty(version)) {
  // if (((DocumentType) cmisObject.getType()).isVersionable()) {
  // final Document document = (Document) cmisObject;
  // for (final Document docVersion : document.getAllVersions()) {
  // if (version.equals(docVersion.getVersionSeriesId())) {
  // cmisObject = docVersion;
  // break;
  // }
  // }
  // }
  // }
  // return cmisObject;
  // }


  @Override
  public String obtenerHashDocumento(WSCredential info, String uuid, String algoritmo)
      throws CSVStorageException {
    DocumentResponseObject consultarResponse =
        getDocumentBusinessService.obtenerCMIS(uuid, info.getIdaplicacion(), algoritmo);
    String hashCode = null;
    if ("0".equals(consultarResponse.getCodigo())) {
      DocumentObject documentObject = (DocumentObject) consultarResponse.getContenido();
      hashCode = documentObject.getCodigoHash();
    }
    return hashCode;
  }
}
