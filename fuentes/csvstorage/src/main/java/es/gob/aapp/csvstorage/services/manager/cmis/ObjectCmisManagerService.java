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

package es.gob.aapp.csvstorage.services.manager.cmis;

import es.gob.aapp.csvstorage.dao.entity.cmis.ObjectCmisEntity;
import es.gob.aapp.csvstorage.services.exception.ServiceException;

/**
 * Servicios manager para los objetos CMIS.
 * 
 */
public interface ObjectCmisManagerService {


  /**
   * Servicio de búsqueda de objetos CMIS por uuid.
   * 
   * @param uuid UUID
   * @return objeto CMIS
   * @throws ServiceException
   */
  ObjectCmisEntity findByUuid(String uuid) throws ServiceException;


  /**
   * Servicio de búsqueda de objetos CMIS por su ruta o nombre de fichero.
   * 
   * @param csv CVS
   * @param código dir3
   * @return Lista de documentos
   * @throws ServiceException
   */
  ObjectCmisEntity findByFile(String file) throws ServiceException;

  /**
   * Servicio para crear un objeto CMIS.
   * 
   * @param entity
   * @return
   * @throws ServiceException
   */
  ObjectCmisEntity create(ObjectCmisEntity entity) throws ServiceException;

}
