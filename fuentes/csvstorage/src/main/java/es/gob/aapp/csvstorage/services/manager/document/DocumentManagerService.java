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

package es.gob.aapp.csvstorage.services.manager.document;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBException;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentAplicacionEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentIdsEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentNifEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentRestriccionEntity;
import es.gob.aapp.csvstorage.model.object.document.DocumentEniObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.exception.ValidationException;


/**
 * Servicios manager para aplicaciones consumidoras de los servicios web.
 * 
 * @author carlos.munoz1
 *
 */
public interface DocumentManagerService {

  /**
   * Servicio de búsqueda de documentos por csv.
   * 
   * @param csv CVS
   * @param dir3 dir3
   * @return Lista de documentos
   * @throws ServiceException
   */
  List<DocumentEntity> findByDocument(String csv, String dir3, String idEni)
      throws ServiceException;

  /**
   * Indica si existe un documento para los parametros dir3 y csv o idEni
   * 
   * @param dir3 dir3
   * @param csv csv
   * @param idEni idEni
   * @return Indica si existe
   * @throws ServiceException error
   */
  List<DocumentEntity> existDocument(String dir3, String csv, String idEni) throws ServiceException;

  /**
   * Servicio de búsqueda de documentos.
   * 
   * @param csv CSV
   * @param dir3 Ambito de la aplicación
   * @param idENI Identificador ENI
   * @return Lista de documentos
   * @throws ServiceException error
   */
  List<DocumentEntity> findAll(String csv, String dir3, String idENI) throws ServiceException;


  /**
   * Servicio de búsqueda de documento por fecha.
   * 
   * @param createdAt Fecha de creación
   * @return lista de documentor
   * @throws ServiceException error
   */
  List<DocumentEntity> findByCreatedAt(Date createdAt) throws ServiceException;


  /**
   * Servicio para crear un documento.
   * 
   * @param entity
   * @return
   * @throws ServiceException
   */
  DocumentEntity create(DocumentEntity entity) throws ServiceException;

  /**
   * Servicio para crear un documento.
   * 
   * @param entity
   * @return
   * @throws ServiceException
   */
  DocumentEntity create(DocumentEntity entity, DocumentObject documentObject, boolean modificar)
      throws ServiceException, JAXBException, ValidationException;

  /**
   * Servicio para crear un documento ENI.
   * 
   * @param entity
   * @return
   * @throws ServiceException
   */
  DocumentEntity createEni(DocumentEntity entity, DocumentEniObject documentEniObject,
      boolean modificar) throws ServiceException, JAXBException, ValidationException;


  /**
   * Servicio para eliminar un documento.
   * 
   * @param entity
   * @throws ServiceException
   */
  void delete(DocumentEntity entity) throws ServiceException;

  /**
   * Servicio para eliminar un documento Eni.
   * 
   * @param entityList
   * @throws ServiceException
   */
  void deleteEni(List<DocumentEntity> entityList) throws ServiceException, IOException;

  /**
   * Servicio de búsqueda de documentos por uuid.
   * 
   * @param uuid uuid
   * @return Lista de documentos
   * @throws ServiceException
   */
  DocumentEntity findByUuid(String uuid) throws ServiceException;

  /**
   * Servicio de búsqueda los nos NIF que tienen permiso de acceso al documento
   * 
   * @param document
   * @param nif
   * @return Lista de NIFs
   * @throws ServiceException error
   */
  List<DocumentNifEntity> findNifsByDocument(DocumentEntity document, String nif)
      throws ServiceException;


  /**
   * Servicio de búsqueda los nos NIF que tienen permiso de acceso al documento
   * 
   * @param document
   * @return Lista de NIFs
   * @throws ServiceException error
   */
  List<DocumentNifEntity> findNifsByDocument(DocumentEntity document) throws ServiceException;

  /**
   * Servicio de búsqueda los Restricciones que tienen permiso de acceso al documento
   * 
   * @param document
   * @return Lista de Restricciones
   * @throws ServiceException error
   */
  List<DocumentRestriccionEntity> findRestriccionesByDocument(DocumentEntity document)
      throws ServiceException;


  /**
   * Servicio de búsqueda los Restricciones por id que existen
   * 
   * @param document
   * @return Lista de Restricciones id
   * @throws ServiceException error
   */
  List<DocumentIdsEntity> findRestriccionesIDByDocument(DocumentEntity document)
      throws ServiceException;


  /**
   * Servicio de búsqueda los Restricciones por id que existen
   * 
   * @param document
   * @param tipoId
   * @return Lista de Restricciones id
   * @throws ServiceException error
   */
  List<DocumentIdsEntity> findRestriccionesIDByDocument(DocumentEntity document, Integer tipoId)
      throws ServiceException;

  /**
   * Servicio de búsqueda que trae las Restricciones por aplicación que existen
   * 
   * @param document
   * @return Lista de Restricciones id
   * @throws ServiceException error
   */
  List<DocumentAplicacionEntity> findDocumentAplicacionByDocument(DocumentEntity document)
      throws ServiceException;


  /**
   * Servicio de búsqueda que trae las Restricciones por aplicación que existen
   * 
   * @param document
   * @param idAplicacion
   * @return Lista de Restricciones id
   * @throws ServiceException error
   */
  List<DocumentAplicacionEntity> findDocumentAplicacionByDocument(DocumentEntity document,
      Long idAplicacion) throws ServiceException;

  /**
   * Servicio de búsqueda que trae las Restricciones por aplicación que existen
   * 
   * @param document
   * @return Lista de Restricciones id
   * @throws ServiceException error
   */
  List<DocumentAplicacionEntity> findDocumentAplicacionByDocumentAndApplication(
      DocumentEntity document, ApplicationEntity application) throws ServiceException;


  /**
   * Actualiza el tamaño de los documentos
   * 
   * @throws ServiceException error
   */
  void updateFileSize() throws ServiceException;

  void guardarAplicacionesConsulta(List<ApplicationEntity> aplicaciones, DocumentEntity document,
      boolean modificar) throws ServiceException;

  void revocarPermisoConsultaAplicacion(List<DocumentAplicacionEntity> documentAplicacionList)
      throws ServiceException;
}
