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

import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentResponseObject;
import es.gob.aapp.csvstorage.webservices.document.model.*;


/**
 * Servicios business de almacenamiento de documentos.
 * 
 * @author carlos.munoz1
 * 
 */
public interface SaveDocumentBusinessService {

  /**
   * Servicio de almacenamiento de documentos.
   * 
   * @param guardarDocumento Object
   * @return GuardarDocumentoResponse
   * @throws CSVStorageException error
   */
  GuardarDocumentoResponse guardar(DocumentoRequest guardarDocumento) throws CSVStorageException;

  // DocumentoReferenciaResponse guardarDocumentoReferencia(DocumentoRequest guardarDocumento)
  // throws CSVStorageException;

  GuardarDocumentoUuidResponse guardarStreaming(DocumentoRequest guardarDocumento)
      throws CSVStorageException;



  /**
   * Servicio de almacenamiento de documentos.
   * 
   * @param guardarDocumento Object
   * @return GuardarDocumentoResponse
   * @throws CSVStorageException error
   */
  GuardarDocumentoUuidResponse guardarUuid(DocumentoRequest guardarDocumento)
      throws CSVStorageException;

  /**
   * Servicio de modificación del documento.
   * 
   * @param guardarDocumento Object
   * @return GuardarDocumentoResponse
   * @throws CSVStorageException error
   */
  GuardarDocumentoResponse modificar(DocumentoRequest guardarDocumento) throws CSVStorageException;

  /**
   * Servicio de almacenamiento de documentos CMIS
   * 
   * @param documentCMIS
   * @return uuid del documento
   * @throws CSVStorageException error
   */
  DocumentResponseObject guardarCMIS(DocumentObject documentCMIS, boolean overwrite);

  /**
   * Modificación de documentos CMIS
   * 
   * @param documentCMIS
   * @param modificarContenido Indica si se modifica el contenido o los metadatos
   * @return
   */
  DocumentResponseObject modificarCMIS(DocumentObject documentCMIS, boolean modificarContenido);


  /**
   * Servicio de modificación del documento.
   *
   * @param guardarDocumento Object
   * @return GuardarDocumentoResponse
   * @throws CSVStorageException error
   */
  GuardarDocumentoResponse otorgarPermisos(DocumentoPermisoRequest guardarDocumento)
      throws CSVStorageException;

  /**
   * Servicio de modificación del documento.
   *
   * @param guardarDocumento Object
   * @return GuardarDocumentoResponse
   * @throws CSVStorageException error
   */
  GuardarDocumentoResponse revocarPermisos(DocumentoRevocarPermisoRequest guardarDocumento)
      throws CSVStorageException;


}
