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

package es.gob.aapp.csvstorage.webservices.documentmtom;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.ResponseWrapper;
import es.gob.aapp.csvstorage.webservices.document.model.Aplicacion;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarPermisosDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ConvertirDocumentoEniRequest;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoPermisoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoPermisoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoReferenciaResponse;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoRevocarPermisoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.EliminarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoEniRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.Response;
import es.gob.aapp.csvstorage.webservices.document.model.TamanioDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.reference.ObtenerReferenciaDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.DocumentoEniMtomResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.DocumentoMtomResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.GuardarDocumentoEniMtomRequest;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.GuardarDocumentoMtomRequest;

/**
 * Web service que va a generar el informe de firma, obtener su csv y almacenarlo en un sistema de
 * ficheros.
 * 
 * @author carlos.munoz1
 * 
 */
@WebService(targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:documentmtom:v1.0",
    name = "CSVDocumentWSMtomService")
public interface CSVDocumentWSMtomService {

  @ResponseWrapper(localName = "guardarDocumentoResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoResponse")
  @WebResult(name = "response", targetNamespace = "")
  @WebMethod(operationName = "guardarDocumento", action = "urn:guardarDocumento")
  public Response guardar(
      @WebParam(name = "guardarDocumentoRequest", targetNamespace = "") @XmlElement(required = true,
          name = "guardarDocumentoRequest") GuardarDocumentoMtomRequest guardarDocumentoMtom)
      throws CSVStorageException;

  @ResponseWrapper(localName = "guardarDocumentoEniResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoEniResponse")
  @WebResult(name = "response", targetNamespace = "")
  @WebMethod(operationName = "guardarDocumentoENI", action = "urn:guardarDocumentoENI")
  public Response guardarENI(
      @WebParam(name = "guardarDocumentoEniRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "guardarDocumentoEniRequest") GuardarDocumentoEniMtomRequest guardarDocumentoENI)
      throws CSVStorageException;

  @ResponseWrapper(localName = "obtenerDocumentoMtomResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoMtomResponse")
  @WebResult(name = "documentoResponse", targetNamespace = "")
  @WebMethod(operationName = "obtenerDocumento", action = "urn:obtenerDocumento")
  public DocumentoMtomResponse obtener(
      @WebParam(name = "obtenerDocumentoRequest", targetNamespace = "") @XmlElement(required = true,
          name = "obtenerDocumentoRequest") ObtenerDocumentoRequest obtenerDocumento)
      throws CSVStorageException;

  @ResponseWrapper(localName = "obtenerDocumentoEniResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoEniMtomResponse")
  @WebResult(name = "documentoEniResponse", targetNamespace = "")
  @WebMethod(operationName = "obtenerDocumentoENI", action = "urn:obtenerDocumentoENI")
  public DocumentoEniMtomResponse obtenerENI(
      @WebParam(name = "obtenerDocumentoEniRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "obtenerDocumentoEniRequest") ObtenerDocumentoEniRequest obtenerDocumentoENI)
      throws CSVStorageException;

  @ResponseWrapper(localName = "consultarDocumentoResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.ConsultarDocumentoResponse")
  @WebResult(name = "response", targetNamespace = "")
  @WebMethod(operationName = "consultarDocumento", action = "urn:consultarDocumento")
  public Response consultar(
      @WebParam(name = "consultarDocumentoRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "consultarDocumentoRequest") ConsultarDocumentoRequest consultarDocumento)
      throws CSVStorageException;


  @ResponseWrapper(localName = "eliminarDocumentoResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.EliminarDocumentoResponse")
  @WebResult(name = "response", targetNamespace = "")
  @WebMethod(operationName = "eliminarDocumento", action = "urn:eliminarDocumento")
  public Response eliminarDocumento(
      @WebParam(name = "eliminarDocumentoRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "eliminarDocumentoRequest") EliminarDocumentoRequest eliminarDocumento)
      throws CSVStorageException;

  @ResponseWrapper(localName = "modificarDocumentoResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoResponse")
  @WebResult(name = "response", targetNamespace = "")
  @WebMethod(operationName = "modificarDocumento", action = "urn:modificarDocumento")
  public Response modificar(
      @WebParam(name = "modificarDocumentoRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "modificarDocumentoRequest") GuardarDocumentoMtomRequest modificarDocumentoMtom)
      throws CSVStorageException;

  @ResponseWrapper(localName = "modificarDocumentoEniResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoEniResponse")
  @WebResult(name = "response", targetNamespace = "")
  @WebMethod(operationName = "modificarDocumentoENI", action = "urn:modificarDocumentoENI")
  public Response modificarENI(
      @WebParam(name = "modificarDocumentoEniRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "modificarDocumentoEniRequest") GuardarDocumentoEniMtomRequest modificarDocumentoENI)
      throws CSVStorageException;

  @ResponseWrapper(localName = "convertirDocumentoEniResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.documentmtom.model.ConvertirDocumentoEniMtomResponse")
  @WebResult(name = "documentoEniResponse", targetNamespace = "")
  @WebMethod(operationName = "convertirDocumentoAEni", action = "urn:convertirDocumentoAEni")
  public DocumentoEniMtomResponse convertirAEni(
      @WebParam(name = "convertirDocumentoEniRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "convertirDocumentoEniRequest") ConvertirDocumentoEniRequest convertirDocumentoENI)
      throws CSVStorageException;

  @ResponseWrapper(localName = "obtenerTamanioDocumentoResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
  @WebResult(name = "documentoTamanioResponse", targetNamespace = "")
  @WebMethod(operationName = "obtenerTamanioDocumento", action = "urn:obtenerTamanioDocumento")
  public TamanioDocumentoResponse obtenerTamanioDocumento(
      @WebParam(name = "obtenerDocumentoRequest", targetNamespace = "") @XmlElement(required = true,
          name = "obtenerDocumentoRequest") ObtenerDocumentoRequest obtenerDocumento)
      throws CSVStorageException;

  @ResponseWrapper(localName = "otorgarPermisoConsultaResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoResponse")
  @WebResult(name = "response", targetNamespace = "")
  @WebMethod(operationName = "otorgarPermisoConsulta", action = "urn:otorgarPermisoConsulta")
  public Response otorgarPermisoConsulta(
      @WebParam(name = "otorgarPermisoConsultaRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "otorgarPermisoConsultaRequest") DocumentoPermisoRequest otorgarPermisoConsulta)
      throws CSVStorageException;

  @ResponseWrapper(localName = "revocarPermisoConsultaResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoResponse")
  @WebResult(name = "response", targetNamespace = "")
  @WebMethod(operationName = "revocarPermisoConsulta", action = "urn:otorgarPermisoConsulta")
  public Response revocarPermisoConsulta(
      @WebParam(name = "revocarPermisoConsultaRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "revocarPermisoConsultaRequest") DocumentoRevocarPermisoRequest revocarPermisoConsulta)
      throws CSVStorageException;

  @WebMethod(operationName = "consultarAplicaciones", action = "urn:consultarAplicaciones")
  @WebResult(name = "Aplicacion", partName = "Aplicacion",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
  public List<Aplicacion> consultarAplicaciones() throws CSVStorageException;

  @ResponseWrapper(localName = "consultarPermisosDocumentoResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.ConsultarPermisosDocumentoResponse")
  @WebResult(name = "documentoPermisoResponse", targetNamespace = "")
  @WebMethod(operationName = "consultarPermisosDocumento",
      action = "urn:consultarPermisosDocumento")
  public DocumentoPermisoResponse consultarPermisosDocumento(
      @WebParam(name = "consultarPermisosDocumentoRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "consultarPermisosDocumentoRequest") ConsultarPermisosDocumentoRequest consultarPermisosDocumento)
      throws CSVStorageException;

  // @ResponseWrapper(localName = "obtenerReferenciaDocumentoResponse", targetNamespace =
  // "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0", className =
  // "es.gob.aapp.csvstorage.webservices.document.model.reference.ObtenerReferenciaDocumentoResponse")
  @WebResult(name = "referenciaResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
  @WebMethod(operationName = "obtenerReferenciaDocumento",
      action = "urn:obtenerReferenciaDocumento")
  public ObtenerReferenciaDocumentoResponse obtenerReferenciaDocumento(
      @WebParam(name = "consultarPermisosDocumentoRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "consultarPermisosDocumentoRequest") ConsultarPermisosDocumentoRequest consultarPermisosDocumento)
      throws CSVStorageException;


  // @ResponseWrapper(localName = "guardarDocumentoResponse", targetNamespace =
  // "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0", className =
  // "es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoResponse")
  @WebResult(name = "guardarReferenciaResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
  @WebMethod(operationName = "guardarDocumentoReferencia",
      action = "urn:guardarDocumentoReferencia")
  public DocumentoReferenciaResponse guardarDocumentoReferencia(
      @WebParam(name = "guardarDocumentoRequest", targetNamespace = "") @XmlElement(required = true,
          name = "guardarDocumentoRequest") GuardarDocumentoRequest guardarDocumento)
      throws CSVStorageException;
}
