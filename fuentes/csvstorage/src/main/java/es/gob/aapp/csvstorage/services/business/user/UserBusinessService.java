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

package es.gob.aapp.csvstorage.services.business.user;

import java.util.List;
import es.gob.aapp.csvstorage.model.object.users.UserObject;
import es.gob.aapp.csvstorage.services.exception.ServiceException;


/**
 * Servicios business para usuarios.
 * 
 * @author carlos.munoz1
 * 
 */
public interface UserBusinessService {

  /**
   * Servicio de b�squeda de todos los usuarios dados de alta.
   *
   * @return List<UserObject>
   * @throws ServiceException the service exception
   */
  List<UserObject> findAll() throws ServiceException;

  /**
   * Servicio de b�squeda del usuario por id.
   *
   * @param usuario
   * @return UserObject
   * @throws ServiceException the service exception
   */
  UserObject findById(String usuario) throws ServiceException;

  /**
   * Servicio para guardar los datos de los usuarios. Crea un nuevo usuario si no existe, actualiza
   * si existe previamente.
   *
   * @param user UserObject
   * @return UserObject
   * @throws ServiceException the service exception
   */
  UserObject save(UserObject user) throws ServiceException;



  /**
   * Servicio para eliminar los usuarios.
   *
   * @param user UserObject
   */
  void delete(UserObject user) throws ServiceException;

}
