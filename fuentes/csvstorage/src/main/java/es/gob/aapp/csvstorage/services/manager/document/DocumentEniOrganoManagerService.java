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

import java.util.List;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniOrganoEntity;
import es.gob.aapp.csvstorage.services.exception.ServiceException;

/**
 * Servicios manager para organos.
 * 
 * @author carlos.munoz1
 *
 */
public interface DocumentEniOrganoManagerService {

  /**
   * Servicio de búsqueda de órganos.
   * 
   * @param csv
   * @param organo
   * @return SignReportEntity
   * @throws ServiceException
   */
  List<DocumentEniOrganoEntity> findByDocument(Long idDocument) throws ServiceException;


  /**
   * Servicio para crear un órgano.
   * 
   * @param entity
   * @return
   * @throws ServiceException
   */
  DocumentEniOrganoEntity create(DocumentEniOrganoEntity entity) throws ServiceException;

  /**
   * Servicio para crear una lista de órganos.
   * 
   * @param entityList
   * @return
   * @throws ServiceException
   */
  List<DocumentEniOrganoEntity> createOrganos(List<DocumentEniOrganoEntity> entityList)
      throws ServiceException;

  /**
   * Servicio para eliminar un órgano.
   * 
   * @param entity
   * @throws ServiceException
   */
  void delete(DocumentEniOrganoEntity entity) throws ServiceException;

  /**
   * Servicio para eliminar una lista de órganos.
   * 
   * @param entityList
   * @throws ServiceException
   */
  void deleteOrganos(List<DocumentEniOrganoEntity> entityList) throws ServiceException;



}
