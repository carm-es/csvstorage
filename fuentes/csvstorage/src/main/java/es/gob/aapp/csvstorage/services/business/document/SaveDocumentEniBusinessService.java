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

import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.document.model.ConvertirDocumentoEniRequest;
import es.gob.aapp.csvstorage.webservices.document.model.GuardarDocumentoEniResponse;



/**
 * Servicios business de almacenamiento de documentos.
 * 
 * @author carlos.munoz1
 * 
 */
public interface SaveDocumentEniBusinessService {

  /**
   * Servicio de almacenamiento de documentos ENI.
   * 
   * @param guardarDocumentoENI Object
   * @idAplicacion idAplicacion
   * @return GuardarDocumentoEniResponse
   * @throws CSVStorageException error
   */
  GuardarDocumentoEniResponse guardarENI(Object guardarDocumentoENI, String idAplicacion)
      throws CSVStorageException;

  /**
   * Servicio de modificación de documentos ENI.
   * 
   * @param guardarDocumentoENI Object
   * @param idAplicacion idAplicacion
   * @return GuardarDocumentoEniResponse
   * @throws CSVStorageException error
   */
  GuardarDocumentoEniResponse modificarENI(Object guardarDocumentoENI, String idAplicacion)
      throws CSVStorageException;

  /**
   * Servicio de conversión de documentos a ENI
   * 
   * @param convertirDocumentoEni ConvertirDocumentoEniRequest
   * @param idAplicacion
   * @return
   * @throws CSVStorageException
   */
  Object convertirAEni(ConvertirDocumentoEniRequest convertirDocumentoEni, String idAplicacion,
      boolean isMtom) throws CSVStorageException;

}
