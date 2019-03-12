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

package es.gob.aapp.csvstorage.services.manager.application;

import java.util.List;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.services.exception.ServiceException;

/**
 * Servicios manager para aplicaciones consumidoras de los servicios web.
 * 
 * @author carlos.munoz1
 *
 */
public interface ApplicationManagerService {

  /**
   * Servicio de búsqueda de aplicaciones por idAplicacion.
   * 
   * @param idAplicacion
   * @return ApplicationEntity
   * @throws ServiceException
   */
  ApplicationEntity findByIdAplicacion(String idAplicacion) throws ServiceException;

  /**
   * Servicio de búsqueda de aplicaciones por idAplicacionPublico.
   * 
   * @param idAplicacionPublico
   * @return ApplicationEntity
   * @throws ServiceException
   */
  ApplicationEntity findByIdAplicacionPublico(String idAplicacionPublico) throws ServiceException;

  /**
   * Busca todas las aplicaciones.
   * 
   * @return List<ApplicationEntity>
   */
  List<ApplicationEntity> findAll();

  /**
   * Obtiene las aplicación segun el estado.
   * 
   * @param active
   * @return
   * @throws ServiceException
   */
  List<ApplicationEntity> findListByActive(boolean active) throws ServiceException;

  /**
   * Servicio para crear una aplicación.
   * 
   * @param entity
   * @return
   * @throws ServiceException
   */
  ApplicationEntity create(ApplicationEntity entity) throws ServiceException;

  /**
   * Servicio para modificar los datos de una aplicación existente.
   * 
   * @param applicationModelToEntity applicationModelToEntity
   * @param encriptarPassword indica si se tiene que encriptar las password
   * @return ApplicationEntity
   * @throws ServiceException
   */
  ApplicationEntity update(ApplicationEntity applicationModelToEntity, boolean encriptarPassword)
      throws ServiceException;


  /**
   * Delete.
   *
   * @param applicationModelToEntity the application model to entity
   */
  void delete(ApplicationEntity applicationModelToEntity) throws ServiceException;

  /**
   * ç Devuelve la lista de aplicaciones por número de serie.
   * 
   * @param numSerie
   * @return
   * @throws ServiceException
   */
  List<ApplicationEntity> findBySerialNumber(String numSerie) throws ServiceException;

}
