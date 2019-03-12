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

package es.gob.aapp.csvstorage.services.manager.users;

import java.util.List;
import org.hibernate.service.spi.ServiceException;
import es.gob.aapp.csvstorage.dao.entity.users.UserEntity;



/**
 * Servicios manager para Usuarios.
 * 
 * @author mario.maldonado
 * 
 */
public interface UserManagerService {

  /**
   * Recoge todos los usuarios.
   *
   * @return the list
   * @throws ServiceException the service exception
   */
  List<UserEntity> findAll() throws ServiceException;


  /**
   * Servicio de búsqueda de usuarios por usuario.
   *
   * @param usuario the usuario
   * @return UserEntity
   * @throws ServiceException the service exception
   */
  UserEntity findByUsuario(String usuario) throws ServiceException;

  /**
   * Servicio para crear un usuario.
   *
   * @param entity the entity
   * @return the user entity
   * @throws ServiceException the service exception
   */
  UserEntity create(UserEntity entity) throws ServiceException;

  /**
   * Servicio para modificar los datos de un usuario existente.
   *
   * @param userModelToEntity the user model to entity
   * @return the user entity
   * @throws ServiceException the service exception
   */
  UserEntity update(UserEntity userModelToEntity) throws ServiceException;



  /**
   * Servicio para eliminar un usuario.
   *
   * @param userModelToEntity the user model to entity
   */
  void delete(UserEntity userModelToEntity) throws ServiceException;


}
