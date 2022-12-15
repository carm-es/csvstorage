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

package es.gob.aapp.csvstorage.services.manager.application.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService;

/**
 * Implementación de los servicios manager para aplicaciones consumidoras de los servicios web.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("applicationManagerService")
public class ApplicationManagerServiceImpl implements ApplicationManagerService {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(ApplicationManagerServiceImpl.class);

  @Autowired
  EntityManager entityManager;

  private BaseRepository<ApplicationEntity, Long> applicationRepository;

  @PostConstruct
  private void configure() {
    applicationRepository =
        new BaseRepositoryImpl<ApplicationEntity, Long>(ApplicationEntity.class, entityManager);
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService
   * #findByIdAplicacion(java.lang.String)
   */
  public ApplicationEntity findByIdAplicacion(String idAplicacion) throws ServiceException {

    LOG.debug("Entramos en findByIdAplicacion con idAplicacion = " + idAplicacion);

    ApplicationEntity entity = new ApplicationEntity();

    try {

      entity.setIdAplicacion(idAplicacion);
      entity = (ApplicationEntity) applicationRepository.findOneBy(entity);

    } catch (NoSuchMethodException e) {
      LOG.error("Error al buscar aplicacion.", e);
      throw new ServiceException("Error al buscar aplicacion. ", e);
    } catch (SecurityException e) {
      LOG.error("Error al buscar aplicacion. ", e);
      throw new ServiceException("Error al buscar aplicacion. ", e);
    } catch (IllegalAccessException e) {
      LOG.error("Error al buscar aplicacion. ", e);
      throw new ServiceException("Error al buscar aplicacion. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("Error al buscar aplicacion. ", e);
      throw new ServiceException("Error al buscar aplicacion. ", e);
    } catch (InvocationTargetException e) {
      LOG.error("Error al buscar aplicacion. ", e);
      throw new ServiceException("Error al buscar aplicacion.", e);
    }

    return entity;
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService
   * #findByIdAplicacion(java.lang.String)
   */
  public ApplicationEntity findByIdAplicacionPublico(String idAplicacionPublico)
      throws ServiceException {

    LOG.debug(
        "Entramos en findByIdAplicacionPublico con idAplicacionPublico = " + idAplicacionPublico);

    ApplicationEntity entity = new ApplicationEntity();

    try {

      entity.setIdAplicacionPublico(idAplicacionPublico);
      entity = (ApplicationEntity) applicationRepository.findOneBy(entity);

    } catch (NoSuchMethodException | SecurityException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      LOG.error("Error al buscar aplicacion. ", e);
      throw new ServiceException("Error al buscar aplicacion. ", e);
    }

    return entity;
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService #findAll()
   */
  public List<ApplicationEntity> findAll() {
    LOG.debug("[INI] FindAll para buscar todas las aplicaciones. ");
    return (List<ApplicationEntity>) applicationRepository.findAll();
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService #findAll()
   */
  public List<ApplicationEntity> findListByActive(boolean active) throws ServiceException {

    List<ApplicationEntity> list = null;
    try {

      ApplicationEntity entity = new ApplicationEntity();
      entity.setActivo(active);
      list = applicationRepository.findListBy(entity);

    } catch (NoSuchMethodException e) {
      LOG.error("Error al obtener las aplicaciones: ", e);
      throw new ServiceException("Error al obtener las aplicaciones. ", e);
    } catch (SecurityException e) {
      LOG.error("Error al obtener las aplicaciones. ", e);
      throw new ServiceException("Error al obtener las aplicaciones.  ", e);
    } catch (IllegalAccessException e) {
      LOG.error("Error al obtener las aplicaciones. ", e);
      throw new ServiceException("Error al obtener las aplicaciones.", e);
    } catch (IllegalArgumentException e) {
      LOG.error("Error al obtener las aplicaciones. ", e);
      throw new ServiceException("Error al obtener las aplicaciones: ", e);
    } catch (InvocationTargetException e) {
      LOG.error("Error al obtener las aplicaciones. ", e);
      throw new ServiceException("Error al obtener las aplicaciones.  ", e);
    }
    return list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService
   * #create(es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity)
   */
  public ApplicationEntity create(ApplicationEntity entity) throws ServiceException {

    LOG.debug("Entramos en save(). ");

    // control aplicación existente
    ApplicationEntity appEntity = findByIdAplicacion(entity.getIdAplicacion());
    if (appEntity != null) {
      throw new ServiceException("La aplicación ya existe. ");
    }
    // encriptar password
    entity.setPassword(DigestUtils.sha256Hex(entity.getPassword()));

    return (ApplicationEntity) applicationRepository.save(entity);
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService
   * #update(es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity)
   */
  public ApplicationEntity update(ApplicationEntity entity, boolean encriptarPassword)
      throws ServiceException {

    LOG.debug("Entramos en update(). ");

    // control aplicación existente
    ApplicationEntity appEntity = findByIdAplicacion(entity.getIdAplicacion());
    if (appEntity == null) {
      throw new ServiceException("La aplicación no existe. ");
    }
    // encriptar password
    if (encriptarPassword) {
      entity.setPassword(DigestUtils.sha256Hex(entity.getPassword()));
    }

    return (ApplicationEntity) applicationRepository.save(entity);
  }

  @Override
  public void delete(ApplicationEntity applicationModelToEntity) throws ServiceException {

    LOG.debug("Entramos en delete(). ");

    ApplicationEntity app = applicationRepository.findOne(applicationModelToEntity.getId());

    applicationRepository.delete(app.getId());
  }

  @Override
  public List<ApplicationEntity> findBySerialNumber(String numSerie) throws ServiceException {
    LOG.debug("Entramos en findBySerialNumber con número de serie = " + numSerie);

    List<ApplicationEntity> applicationlist = null;

    try {
      ApplicationEntity entity = new ApplicationEntity();
      entity.setSerialNumberCertificado(numSerie);
      applicationlist = applicationRepository.findListBy(entity);

    } catch (NoSuchMethodException e) {
      LOG.error("Error al buscar aplicacion. ", e);
      throw new ServiceException("Error al buscar aplicacion. ", e);
    } catch (SecurityException e) {
      LOG.error("Error al buscar aplicacion. ", e);
      throw new ServiceException("Error al buscar aplicacion. ", e);
    } catch (IllegalAccessException e) {
      LOG.error("Error al buscar aplicacion. ", e);
      throw new ServiceException("Error al buscar aplicacion. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("Error al buscar aplicacion. ", e);
      throw new ServiceException("Error al buscar aplicacion. ", e);
    } catch (InvocationTargetException e) {
      LOG.error("Error al buscar aplicacion. ", e);
      throw new ServiceException("Error al buscar aplicacion. ", e);
    }

    return applicationlist;
  }

}
