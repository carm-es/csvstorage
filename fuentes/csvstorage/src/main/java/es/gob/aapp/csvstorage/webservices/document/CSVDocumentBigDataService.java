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

package es.gob.aapp.csvstorage.webservices.document;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.ResponseWrapper;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.document.model.ContenidoUuidInfo;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoBigDataResponse;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoBigDataRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.Response;
import es.gob.aapp.csvstorage.webservices.document.model.ResponseUuid;
import es.gob.aapp.csvstorage.webservices.document.model.WSCredential;

/**
 * Web service que va a generar el informe de firma, obtener su csv y almacenarlo en un sistema de
 * ficheros.
 * 
 * @author carlos.munoz1
 * 
 */
@WebService(targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:v1.0",
    name = "CSVDocumentBigDataService")
public interface CSVDocumentBigDataService {

  @ResponseWrapper(localName = "guardarDocumentoUuidResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoUuidResponse")
  @WebResult(name = "responseUuid", targetNamespace = "")
  @WebMethod(operationName = "guardarDocumento", action = "urn:guardarDocumento")
  public ResponseUuid guardar(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(name = "guardarDocumentoRequest", targetNamespace = "") @XmlElement(required = true,
          name = "guardarDocumentoRequest") GuardarDocumentoBigDataRequest guardarDocumento)
      throws CSVStorageException;

  @ResponseWrapper(localName = "modificarDocumentoResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoResponse")
  @WebResult(name = "guardarDocumentoUuidResponse", targetNamespace = "")
  @WebMethod(operationName = "modificarDocumento", action = "urn:modificarDocumento")
  public Response modificar(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(name = "modificarDocumentoRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "modificarDocumentoRequest") GuardarDocumentoBigDataRequest modificarDocumento)
      throws CSVStorageException;

  @ResponseWrapper(localName = "obtenerDocumentoResponse",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0",
      className = "es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoBigDataResponse")
  @WebResult(name = "documentoResponse", targetNamespace = "")
  @WebMethod(operationName = "obtenerDocumento", action = "urn:obtenerDocumento")
  public DocumentoBigDataResponse obtener(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(name = "obtenerDocumentoRequest", targetNamespace = "") @XmlElement(required = true,
          name = "obtenerDocumentoRequest") ObtenerDocumentoRequest obtenerDocumento,
      @WebParam(name = "url", targetNamespace = "") @XmlElement(required = true,
          name = "url") String url)
      throws CSVStorageException;

  @WebResult(name = "documentoTamanioResponse", targetNamespace = "")
  @WebMethod(operationName = "obtenerTamanioDocumento", action = "urn:obtenerTamanioDocumento")
  public Long obtenerTamanioDocumento(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(name = "uuid", targetNamespace = "") @XmlElement(required = true,
          name = "uuid") String uuid)
      throws CSVStorageException;

  // @ResponseWrapper(localName = "obtenerInfoContenidoResponse", targetNamespace =
  // "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0", className =
  // "es.gob.aapp.csvstorage.webservices.document.model.ObtenerInfoContenidoResponse")
  // @WebResult(name = "contenidoUuidInfo", targetNamespace = "")
  // @WebMethod(operationName = "obtenerInfoContenido", action = "urn:obtenerInfoContenido")
  // public ContenidoUuidInfo obtenerInfoContenido(
  // @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true, name =
  // "credential") WSCredential info,
  // @WebParam(name = "uuid", targetNamespace = "") @XmlElement(required = true, name = "uuid")
  // String uuid)
  // throws CSVStorageException;

  @WebResult(name = "obtenerHashDocumentoResponse", targetNamespace = "")
  @WebMethod(operationName = "obtenerHashDocumento", action = "urn:obtenerHashDocumento")
  public String obtenerHashDocumento(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(name = "uuid", targetNamespace = "") @XmlElement(required = true,
          name = "uuid") String uuid,
      @WebParam(name = "algoritmo", targetNamespace = "") @XmlElement(required = true,
          name = "algoritmo") String algoritmo)
      throws CSVStorageException;
}
