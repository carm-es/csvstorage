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

package es.gob.aapp.csvstorage.services.business.user.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.gob.aapp.csvstorage.model.converter.users.UsersConverter;
import es.gob.aapp.csvstorage.model.object.users.UserObject;
import es.gob.aapp.csvstorage.services.business.user.UserBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.users.UserManagerService;

/**
 * Implementaci�n de los servicios business para aplicaciones consumidoras.
 * 
 * @author serena.plaza
 * 
 */
@Service("userBusinessService")
public class UserBusinessServiceImpl implements UserBusinessService {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(UserBusinessServiceImpl.class);

  /**
   * Inyecci�n de los servicios de negocio de usuario.
   */
  @Autowired
  private UserManagerService userManagerService;

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.business.user. UserManagerService#findAll()
   */
  public List<UserObject> findAll() throws ServiceException {
    LOG.info("[INI] findAll");
    return UsersConverter.userEntityToModel(userManagerService.findAll());
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.business.user. UserBusinessService
   * #save(es.gob.aapp.csvstorage.model.object.user.UserObject)
   */
  @Transactional(rollbackFor = ServiceException.class)
  public UserObject save(UserObject usuario) throws ServiceException {

    LOG.info("[INI] Entramos en save() para guardar los datos del usuario. ");

    if (usuario != null) {
      if (usuario.getId() != null) {
        usuario = UsersConverter.userEntityToModel(
            userManagerService.update(UsersConverter.userModelToEntity(usuario)));
      } else {
        usuario = UsersConverter.userEntityToModel(
            userManagerService.create(UsersConverter.userModelToEntity(usuario)));
      }
      LOG.debug("Usuario guardado con �xito. ");
    }
    LOG.info("[FIN] Salimos de save usuario. ");

    return usuario;
  }

  @Override
  @Transactional(rollbackFor = ServiceException.class)
  public void delete(UserObject usuario) throws ServiceException {
    LOG.info("[INI] Entramos en delete() para borrar los usuarios.");

    if (usuario != null && usuario.getId() != null) {
      userManagerService.delete(UsersConverter.userModelToEntity(usuario));
    }

    LOG.info("[FIN] Salimos de delete usuario. ");
  }

  @Override
  public UserObject findById(String usuario) throws ServiceException {
    return UsersConverter.userEntityToModel(userManagerService.findByUsuario(usuario));

  }

}
