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

package es.gob.aapp.csvstorage.services.business.document;

import java.util.List;
import javax.xml.ws.handler.MessageContext;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentResponseObject;
import es.gob.aapp.csvstorage.webservices.document.model.Aplicacion;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultaResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarPermisosDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ConsultarPermisosDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoReferenciaResponse;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoEniRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoRequest;
import es.gob.aapp.csvstorage.webservices.document.model.TamanioDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.reference.ObtenerReferenciaDocumentoResponse;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.DocumentoMtomStreamingResponse;


/**
 * Servicios business de recuperación de documentos.
 * 
 * @author carlos.munoz1
 * 
 */
public interface GetDocumentBusinessService {

  /**
   * Servicio de recuperación del documento
   * 
   * @param documentObject ObtenerDocumentoRequest
   * @param isMtom indica si la llamada es por mtom
   * @return Object
   * @throws CSVStorageException error
   */
  Object obtener(DocumentObject documentObject, Class<?> className, String url)
      throws CSVStorageException;



  DocumentoMtomStreamingResponse obtenerDocumentoStreaming(ObtenerDocumentoRequest obtenerDocumento)
      throws CSVStorageException;

  DocumentoMtomStreamingResponse obtenerInfoContenidoStreaming(String UUID)
      throws CSVStorageException;

  /**
   * Servicio de recuperación del documento ENI
   * 
   * @param obtenerDocumentoENI
   * @param isMtom indica si la llamada es por mtom
   * @return Object
   * @throws CSVStorageException error
   */
  Object obtenerENI(ObtenerDocumentoEniRequest obtenerDocumentoENI, Class<?> className)
      throws CSVStorageException;

  /**
   * Servicio de consulta del documento.
   * 
   * @param consultarDocumento ConsultarDocumentoRequest
   * @return ConsultarDocumentoResponse
   * @throws CSVStorageException error
   */
  ConsultarDocumentoResponse consultar(ConsultarDocumentoRequest consultarDocumento)
      throws CSVStorageException;


  ObtenerReferenciaDocumentoResponse consultaReferencia(
      ConsultarPermisosDocumentoRequest consultarPermisosDocumento) throws CSVStorageException;

  /**
   * Servicio de consulta del documento por CSV.
   * 
   * @param consultarDocumento ConsultarDocumentoRequest
   * @return ConsultarDocumentoResponse
   * @throws CSVStorageException error
   */
  ConsultaResponse consultarPorCSV(ConsultarDocumentoRequest consultarDocumento)
      throws CSVStorageException;

  /**
   * Servicio de consulta del documento por uuid.
   * 
   * @param consultarDocumento ConsultarDocumentoRequest
   * @return ConsultarDocumentoResponse
   * @throws CSVStorageException error
   */
  DocumentResponseObject obtenerCMIS(String uuid, String idAplicacion, String algoritmoHash);

  DocumentResponseObject consultarPermisoCMIS(String uuid, String idAplicacion);

  /**
   * Servicio de recuperación del documento
   * 
   * @param obtenerDocumento ObtenerDocumentoRequest
   * @param isMtom indica si la llamada es por mtom
   * @return Object
   * @throws CSVStorageException error
   */
  TamanioDocumentoResponse obtenerTamanioDocumento(DocumentObject documentObject)
      throws CSVStorageException;

  /**
   * Servicio de recuperación del documento
   * 
   * @param obtenerDocumento ObtenerDocumentoRequest
   * @param isMtom indica si la llamada es por mtom
   * @return Object
   * @throws CSVStorageException error
   */
  Long obtenerTamanioUuid(String uuid) throws CSVStorageException;



  /**
   * Servicio de obtener todas las restricciones de dicho documento
   * 
   * @param consultarPermisosDocumento ConsultarPermisosDocumentoRequest
   * @return ConsultarPermisosDocumentoResponse
   * @throws CSVStorageException error
   */
  ConsultarPermisosDocumentoResponse consultarPermisosDocumento(
      ConsultarPermisosDocumentoRequest consultarPermisosDocumento) throws CSVStorageException;

  /**
   * 
   * @return
   * @throws CSVStorageException
   */
  public List<Aplicacion> consultarAplicaciones() throws CSVStorageException;

  public DocumentoReferenciaResponse consultaReferenciaGuardado(
      GuardarDocumentoRequest guardarRequest, GuardarDocumentoResponse guardarResponse)
      throws CSVStorageException;
}
