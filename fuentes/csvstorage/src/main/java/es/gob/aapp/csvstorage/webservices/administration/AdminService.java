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

package es.gob.aapp.csvstorage.webservices.administration;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import es.gob.aapp.csvstorage.webservices.administration.model.Aplicacion;
import es.gob.aapp.csvstorage.webservices.administration.model.InfAdicional;
import es.gob.aapp.csvstorage.webservices.administration.model.WSCredential;
import es.gob.aapp.csvstorage.webservices.administration.model.CSVStorageException;

/**
 * Servicio para administrar las aplicaciones de csvstorage.
 * 
 * @author mario.maldonado
 * 
 */
@WebService(targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:administration:v1.0",
    name = "AdminService")
public interface AdminService {

  @WebMethod(operationName = "altaAplicacion", action = "urn:altaAplicacion")
  @WebResult(name = "Aplicacion", partName = "Aplicacion",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:administration:model:v1.0")
  public Aplicacion altaAplicacion(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(name = "aplicacion", targetNamespace = "") @XmlElement(required = true,
          name = "aplicacion") Aplicacion aplicacion)
      throws CSVStorageException;

  @WebMethod(operationName = "bajaAplicacion", action = "urn:bajaAplicacion")
  @WebResult(name = "Aplicacion", partName = "Aplicacion",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:administration:model:v1.0")
  public Aplicacion bajaAplicacion(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(mode = WebParam.Mode.IN, name = "idAplicacion",
          targetNamespace = "") java.lang.String idAplicacion)
      throws CSVStorageException;

  @WebMethod(operationName = "listaAplicaciones", action = "urn:listaAplicaciones")
  @WebResult(name = "Aplicacion", partName = "Aplicacion",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:administration:model:v1.0")
  public List<Aplicacion> listaAplicaciones(@WebParam(name = "credential",
      targetNamespace = "") @XmlElement(required = true, name = "credential") WSCredential info)
      throws CSVStorageException;

  @WebMethod(operationName = "infAdicionalAltaApp", action = "urn:infAdicionalAltaApp")
  @WebResult(name = "InformacionAdicionalAltaApp", partName = "InformacionAdicionalAltaApp",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:administration:model:v1.0")
  public List<InfAdicional> infAdicionalAltaApp(@WebParam(name = "credential",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:administration:model:v1.0") @XmlElement(
          required = true, name = "credential") WSCredential info)
      throws CSVStorageException;

  @WebMethod(operationName = "eliminaAplicacion", action = "urn:eliminaAplicacion")
  @WebResult(name = "Aplicacion", partName = "Aplicacion",
      targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:administration:model:v1.0")
  public Aplicacion eliminaAplicacion(
      @WebParam(name = "credential", targetNamespace = "") @XmlElement(required = true,
          name = "credential") WSCredential info,
      @WebParam(mode = WebParam.Mode.IN, name = "idAplicacion",
          targetNamespace = "") java.lang.String idAplicacion)
      throws CSVStorageException;

}
