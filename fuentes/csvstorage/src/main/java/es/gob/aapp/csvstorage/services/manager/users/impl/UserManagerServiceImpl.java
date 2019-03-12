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

package es.gob.aapp.csvstorage.services.manager.users.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.dao.entity.users.UserEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.services.manager.users.UserManagerService;



/**
 * Implementaci�n de los servicios manager para usuarios.
 * 
 * @author mario.maldonado
 * 
 */
@Service("userManagerService")
public class UserManagerServiceImpl implements UserManagerService {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(UserManagerServiceImpl.class);

  /** The entity manager. */
  @Autowired
  EntityManager entityManager;


  /** Repositorio de unidades. */
  private BaseRepository<UserEntity, Long> userRepository;

  /**
   * Configure.
   */
  @PostConstruct
  private void configure() {
    userRepository = new BaseRepositoryImpl<UserEntity, Long>(UserEntity.class, entityManager);
  }

  @Override
  public List<UserEntity> findAll() throws ServiceException {
    return (List<UserEntity>) userRepository.findAll();
  }

  @Override
  public UserEntity findByUsuario(String usuario) throws ServiceException {
    LOG.debug("Entramos en findByUsuario con usuario = " + usuario);

    UserEntity entity = new UserEntity();

    try {

      entity.setUsuario(usuario);
      entity = (UserEntity) userRepository.findOneBy(entity);

    } catch (NoSuchMethodException e) {
      LOG.error("Error al buscar usuario. ", e);
      throw new ServiceException("Error al buscar usuario. ", e);
    } catch (SecurityException e) {
      LOG.error("Error al buscar usuario. ", e);
      throw new ServiceException("Error al buscar usuario. ", e);
    } catch (IllegalAccessException e) {
      LOG.error("Error al buscar usuario. ", e);
      throw new ServiceException("Error al buscar usuario. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("Error al buscar usuario. ", e);
      throw new ServiceException("Error al buscar usuario. ", e);
    } catch (InvocationTargetException e) {
      LOG.error("Error al buscar usuario. ", e);
      throw new ServiceException("Error al buscar usuario. ", e);
    }
    return entity;
  }

  @Override
  public UserEntity create(UserEntity entity) throws ServiceException {
    LOG.debug("Entramos en save(). ");

    // control usuario existente
    UserEntity userEntity = findByUsuario(entity.getUsuario());
    if (userEntity != null) {
      throw new ServiceException("El usuario ya existe. ");
    }
    // encriptar password
    entity.setPassword(DigestUtils.sha256Hex(entity.getPassword()));

    return (UserEntity) userRepository.save(entity);
  }

  @Override
  public UserEntity update(UserEntity userModelToEntity) throws ServiceException {
    LOG.debug("Entramos en update(). ");

    // control usuario existente
    UserEntity userEntity = findByUsuario(userModelToEntity.getUsuario());
    if (userEntity == null) {
      throw new ServiceException("El usuario no existe. ");
    }
    // encriptar password
    if (StringUtils.isNotEmpty(userModelToEntity.getPassword())) {
      userModelToEntity.setPassword(DigestUtils.sha256Hex(userModelToEntity.getPassword()));
    } else {
      // Si la password es nula se deja la que hay en bbdd
      userModelToEntity.setPassword(userEntity.getPassword());
    }

    return (UserEntity) userRepository.save(userModelToEntity);
  }

  @Override
  public void delete(UserEntity userModelToEntity) throws ServiceException {
    LOG.debug("Entramos en delete(). ");

    UserEntity user = userRepository.findOne(userModelToEntity.getId());

    userRepository.delete(user.getId());

  }

}
