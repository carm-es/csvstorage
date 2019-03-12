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
import es.gob.aapp.csvstorage.webservices.document.model.*;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.DocumentoMtomResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.DocumentoMtomStreamingResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.GuardarDocumentoMtomStreamingRequest;

/**
 * Web service que va a generar el informe de firma, obtener su csv y almacenarlo en un sistema de
 * ficheros.
 * 
 * @author carlos.munoz1
 * 
 */
@WebService(targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:v1.0",
    name = "BIGFILEStreamingService")
public interface BIGFILEStreamingService {

  // @ResponseWrapper(localName = "guardarDocumentoUuidResponse", targetNamespace =
  // "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0", className =
  // "es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoUuidResponse")
  @WebResult(name = "responseUuid", targetNamespace = "")
  @WebMethod(operationName = "guardarDocumentoStreaming", action = "urn:guardarDocumentoStreaming")
  public GuardarDocumentoUuidResponse guardarDocumentoStreaming(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(name = "guardarDocumentoRequest", targetNamespace = "") @XmlElement(required = true,
          name = "guardarDocumentoRequest") GuardarDocumentoMtomStreamingRequest guardarDocumento)
      throws CSVStorageException;


  @WebResult(name = "responseUuid", targetNamespace = "")
  @WebMethod(operationName = "obtenerDocumentoStreaming", action = "urn:obtenerDocumentoStreaming")
  public DocumentoMtomStreamingResponse obtenerDocumentoStreaming(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(name = "obtenerDocumentoRequest", targetNamespace = "") @XmlElement(required = true,
          name = "obtenerDocumentoRequest") ObtenerDocumentoRequest obtenerDocumento)
      throws CSVStorageException;


  @WebResult(name = "responseUuid", targetNamespace = "")
  @WebMethod(operationName = "obtenerInfoContenidoStreaming",
      action = "urn:obtenerInfoContenidoStreaming")
  public DocumentoMtomStreamingResponse obtenerInfoContenidoStreaming(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(name = "obtenerDocumentoRequest", targetNamespace = "") @XmlElement(required = true,
          name = "obtenerDocumentoRequest") String UUID)
      throws CSVStorageException;

}
