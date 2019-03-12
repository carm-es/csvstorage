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

package es.gob.aapp.csvbroker.webservices.querydocument;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import es.gob.aapp.csvbroker.webservices.querydocument.model.v1.CSVQueryDocumentRequest;
import es.gob.aapp.csvbroker.webservices.querydocument.model.v1.CSVQueryDocumentResponse;
import es.gob.aapp.csvbroker.webservices.querydocument.model.v1.CSVQueryDocumentSecurityRequest;
import es.gob.aapp.csvbroker.webservices.querydocument.model.v1.CSVQueryDocumentSecurityResponse;
import es.gob.aapp.csvbroker.webservices.querydocument.model.v1.WSCredential;



/**
 * Web service CSVQueryDocumentService para integrarse como Productor de CSV Broker
 * 
 * @author serena.plaza
 * 
 */
@WebService(targetNamespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:v1.0",
    name = "CSVQueryDocumentService")
public interface CSVQueryDocumentService {

  @WebMethod(operationName = "csvQueryDocument", action = "urn:csvQueryDocument")
  @WebResult(name = "CSVQueryDocumentResponse",
      targetNamespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0")
  public CSVQueryDocumentResponse csvQueryDocument(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(name = "queryDocumentRequest", targetNamespace = "") @XmlElement(required = true,
          name = "queryDocumentRequest") CSVQueryDocumentRequest csvQueryDocumentRequest)
      throws CSVQueryDocumentException;

  @WebMethod(operationName = "csvQueryDocumentSecurity", action = "urn:csvQueryDocumentSecurity")
  @WebResult(name = "CSVQueryDocumentSecurityResponse",
      targetNamespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0")
  public CSVQueryDocumentSecurityResponse csvQueryDocumentSecurity(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(name = "queryDocumentSecurityRequest", targetNamespace = "") @XmlElement(
          required = true,
          name = "queryDocumentSecurityRequest") CSVQueryDocumentSecurityRequest csvQueryDocumentSecurityRequest)
      throws CSVQueryDocumentException;

}
