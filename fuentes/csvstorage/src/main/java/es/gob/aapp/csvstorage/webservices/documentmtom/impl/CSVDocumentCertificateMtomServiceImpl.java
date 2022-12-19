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

package es.gob.aapp.csvstorage.webservices.documentmtom.impl;

import java.util.List;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.BindingType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.services.business.document.DeleteDocumentBusinessService;
import es.gob.aapp.csvstorage.services.business.document.GetDocumentBusinessService;
import es.gob.aapp.csvstorage.services.business.document.SaveDocumentBusinessService;
import es.gob.aapp.csvstorage.services.business.document.SaveDocumentEniBusinessService;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.webservices.document.model.Aplicacion;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarPermisosDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarPermisosDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ConvertirDocumentoEniRequest;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoPermisoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoPermisoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoReferenciaResponse;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoRevocarPermisoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.EliminarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.EliminarDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoEniResponse;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoEniRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.Response;
import es.gob.aapp.csvstorage.webservices.document.model.TamanioDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.reference.ObtenerReferenciaDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.CSVDocumentCertificateMtomService;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.ConvertirDocumentoEniMtomResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.DocumentoEniMtomResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.DocumentoMtomResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.GuardarDocumentoEniMtomRequest;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.GuardarDocumentoMtomRequest;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.ObtenerDocumentoEniMtomResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.ObtenerDocumentoMtomResponse;



@WebService(serviceName = "CSVDocumentCertificateMtomService",
    portName = "CSVDocumentCertificateMtomServicePort",
    targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:documentmtom:v1.0",
    endpointInterface = "es.gob.aapp.csvstorage.webservices.documentmtom.CSVDocumentCertificateMtomService")
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.BARE, use = Use.LITERAL)
@BindingType(value = "http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true")
public class CSVDocumentCertificateMtomServiceImpl implements CSVDocumentCertificateMtomService {

  /** Logger de la clase. */
  private static final Logger LOG =
      LogManager.getLogger(CSVDocumentCertificateMtomServiceImpl.class);

  /** Inyección de los servicios business de eliminación de docuentos. */
  @Autowired
  private DeleteDocumentBusinessService deleteDocumentBusinessService;

  /** Inyecci�n de los servicios business de almacenamiento de documentos. */
  @Autowired
  private SaveDocumentBusinessService saveDocumentBusinessService;

  /** Inyecci�n de los servicios business de recuperación de documentos. */
  @Autowired
  private GetDocumentBusinessService getDocumentBusinessService;

  /** Inyección de los servicios business de almacenamiento de documentos ENI. */
  @Autowired
  private SaveDocumentEniBusinessService saveDocumentEniBusinessService;

  @Override
  public Response guardar(GuardarDocumentoMtomRequest guardarDocumentoMtom)
      throws CSVStorageException {
    LOG.info("Entramos en guardar. Request: " + guardarDocumentoMtom.toString());
    GuardarDocumentoResponse response = saveDocumentBusinessService.guardar(guardarDocumentoMtom);
    LOG.info("Salimos de guardar.");
    return response.getResponse();
  }

  @Override
  public Response consultar(ConsultarDocumentoRequest consultarDocumento)
      throws CSVStorageException {
    LOG.info("Entramos en consultar. Request: " + consultarDocumento.toString());
    ConsultarDocumentoResponse response = getDocumentBusinessService.consultar(consultarDocumento);
    LOG.info("Salimos de consultar.");
    return response.getResponse();
  }


  @Override
  public Response guardarENI(GuardarDocumentoEniMtomRequest guardarDocumentoENI)
      throws CSVStorageException {
    LOG.info("Entramos en guardarENI. Request: " + guardarDocumentoENI.toString());
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) auth.getPrincipal();

    GuardarDocumentoEniResponse response =
        saveDocumentEniBusinessService.guardarENI(guardarDocumentoENI, user.getUsername());
    LOG.info("Salimos de guardarENI.");
    return response.getResponse();
  }

  @Override
  public DocumentoEniMtomResponse obtenerENI(ObtenerDocumentoEniRequest obtenerDocumentoENI)
      throws CSVStorageException {
    LOG.info("Entramos en obtenerENI. Request: " + obtenerDocumentoENI.toString());
    ObtenerDocumentoEniMtomResponse response =
        (ObtenerDocumentoEniMtomResponse) getDocumentBusinessService.obtenerENI(obtenerDocumentoENI,
            ObtenerDocumentoEniMtomResponse.class);
    LOG.info("Salimos de obtenerENI.");
    return response.getDocumentoEniMtomResponse();
  }

  @Override
  public DocumentoMtomResponse obtener(ObtenerDocumentoRequest obtenerDocumento)
      throws CSVStorageException {
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
    ObtenerDocumentoMtomResponse response =
        (ObtenerDocumentoMtomResponse) getDocumentBusinessService.obtener(documentObject,
            ObtenerDocumentoMtomResponse.class, null);
    LOG.info("Salimos de obtener.");
    return response.getDocumentoMtomResponse();
  }


  @Override
  public Response eliminarDocumento(EliminarDocumentoRequest eliminarDocumento)
      throws CSVStorageException {
    LOG.info("Entramos en eliminarDocumento. Request: " + eliminarDocumento.toString());
    EliminarDocumentoResponse response = deleteDocumentBusinessService.eliminar(eliminarDocumento);
    LOG.info("Salimos de eliminarDocumento.");
    return response.getResponse();
  }

  @Override
  public Response modificar(GuardarDocumentoMtomRequest modificarDocumentoMtom)
      throws CSVStorageException {
    LOG.info("Entramos en modificar. Request: " + modificarDocumentoMtom.toString());
    GuardarDocumentoResponse response =
        saveDocumentBusinessService.modificar(modificarDocumentoMtom);
    LOG.info("Salimos de modificar.");

    return response.getResponse();
  }

  @Override
  public Response modificarENI(GuardarDocumentoEniMtomRequest modificarDocumentoENI)
      throws CSVStorageException {
    LOG.info("Entramos en modificarENI. Request: " + modificarDocumentoENI.toString());

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) auth.getPrincipal();

    GuardarDocumentoEniResponse response =
        saveDocumentEniBusinessService.modificarENI(modificarDocumentoENI, user.getUsername());
    LOG.info("Salimos de modificarENI.");

    return response.getResponse();
  }

  @Override
  public DocumentoEniMtomResponse convertirAEni(ConvertirDocumentoEniRequest convertirDocumentoENI)
      throws CSVStorageException {
    LOG.info("Entramos en convertirAEni. Request: " + convertirDocumentoENI.toString());
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) auth.getPrincipal();

    ConvertirDocumentoEniMtomResponse response =
        (ConvertirDocumentoEniMtomResponse) saveDocumentEniBusinessService
            .convertirAEni(convertirDocumentoENI, user.getUsername(), true);

    LOG.info("Salimos de convertirAEni.");

    return response.getDocumentoEniMtomResponse();
  }

  @Override
  public TamanioDocumentoResponse obtenerTamanioDocumento(ObtenerDocumentoRequest obtenerDocumento)
      throws CSVStorageException {
    DocumentObject documentObject = new DocumentObject();
    BeanUtils.copyProperties(obtenerDocumento, documentObject);

    documentObject.setParametrosAuditoria(obtenerDocumento.parametrosAuditoria());
    return getDocumentBusinessService.obtenerTamanioDocumento(documentObject);
  }

  @Override
  public Response otorgarPermisoConsulta(DocumentoPermisoRequest otorgarPermisoConsulta)
      throws CSVStorageException {

    LOG.info("Entramos en otorgarPermisoConsulta. Request: " + otorgarPermisoConsulta.toString());
    GuardarDocumentoResponse response =
        saveDocumentBusinessService.otorgarPermisos(otorgarPermisoConsulta);

    LOG.info("Salimos de otorgarPermisoConsulta.");
    return response.getResponse();

  }

  @Override
  public Response revocarPermisoConsulta(DocumentoRevocarPermisoRequest revocarPermisoConsulta)
      throws CSVStorageException {

    LOG.info("Entramos en revocarPermisoConsulta. Request: " + revocarPermisoConsulta.toString());
    GuardarDocumentoResponse response =
        saveDocumentBusinessService.revocarPermisos(revocarPermisoConsulta);

    LOG.info("Salimos de revocarPermisoConsulta.");
    return response.getResponse();

  }

  @Override
  public List<Aplicacion> consultarAplicaciones() throws CSVStorageException {
    LOG.info("Entramos en consultarAplicaciones");
    return getDocumentBusinessService.consultarAplicaciones();
  }

  @Override
  public DocumentoPermisoResponse consultarPermisosDocumento(
      ConsultarPermisosDocumentoRequest consultarPermisosDocumento) throws CSVStorageException {

    LOG.info("Entramos en consultarPermisosDocumento");

    ConsultarPermisosDocumentoResponse response =
        getDocumentBusinessService.consultarPermisosDocumento(consultarPermisosDocumento);

    LOG.info("Salimos de consultarPermisosDocumento.");
    return response.getDocumentoPermisoResponse();
  }

  @Override
  public ObtenerReferenciaDocumentoResponse obtenerReferenciaDocumento(
      ConsultarPermisosDocumentoRequest consultarPermisosDocumento) throws CSVStorageException {
    LOG.info("Entramos en obtenerReferenciaDocumento. Request: "
        + consultarPermisosDocumento.toString());

    ObtenerReferenciaDocumentoResponse response =
        getDocumentBusinessService.consultaReferencia(consultarPermisosDocumento);

    LOG.info("Salimos de obtenerReferenciaDocumento. ");
    return response;
  }

  @Override
  public DocumentoReferenciaResponse guardarDocumentoReferencia(
      GuardarDocumentoRequest guardarDocumento) throws CSVStorageException {
    LOG.debug("Entramos en guardarDocumentoReferencia: " + guardarDocumento.toString());
    GuardarDocumentoResponse guardarResponse =
        saveDocumentBusinessService.guardar(guardarDocumento);

    DocumentoReferenciaResponse response =
        getDocumentBusinessService.consultaReferenciaGuardado(guardarDocumento, guardarResponse);

    LOG.debug("Salimos de guardarDocumentoReferencia.");

    return response;
  }
}
