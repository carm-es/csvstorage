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

package es.gob.aapp.csvstorage.services.manager.cmis.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.gob.aapp.csvstorage.dao.entity.cmis.ObjectCmisEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.cmis.ObjectCmisManagerService;

/**
 * Implementaciï¿½n de los servicios manager objetos CMIS.
 * 
 * 
 */
@Service("objectCmisManagerService")
public class ObjectCmisManagerServiceImpl implements ObjectCmisManagerService {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(ObjectCmisManagerServiceImpl.class);

  @Autowired
  EntityManager entityManager;

  private BaseRepository<ObjectCmisEntity, Long> objectCmisRepository;

  @PostConstruct
  private void configure() {
    objectCmisRepository =
        new BaseRepositoryImpl<ObjectCmisEntity, Long>(ObjectCmisEntity.class, entityManager);
  }

  /*
   * (non-Javadoc)
   * 
   * es.gob.aapp.csvstorage.services.manager.document.ObjectCmisManagerService
   * #create(es.gob.aapp.csvstorage.dao.entity.document.ObjectCmisEntity)
   */
  @Transactional
  public ObjectCmisEntity create(ObjectCmisEntity entity) throws ServiceException {
    LOG.debug("Entramos en create. ");
    // Si ya existe algón objeto con el mismo uuid no se inserta de nuevo
    ObjectCmisEntity objectCmisEntity = findByUuid(entity.getUuid());
    if (objectCmisEntity == null) {
      objectCmisEntity = (ObjectCmisEntity) objectCmisRepository.save(entity);
    }

    return objectCmisEntity;
  }

  @Override
  public ObjectCmisEntity findByUuid(String uuid) throws ServiceException {
    LOG.debug("Entramos en findByUuid con uuid = " + uuid);

    ObjectCmisEntity result = null;

    try {

      if (uuid != null) {
        ObjectCmisEntity objectCmisEntity = new ObjectCmisEntity();
        objectCmisEntity.setUuid(uuid);

        List<ObjectCmisEntity> list = objectCmisRepository.findListBy(objectCmisEntity);

        if (list != null && list.size() > 0) {
          result = list.get(0);
        }
      }
    } catch (NoSuchMethodException e) {
      LOG.info("No se ha encontrado el objeto con uuid: " + uuid);
      throw new ServiceException("Error al buscar el objeto. ", e);
    } catch (SecurityException e) {
      LOG.error("[findByUuid] Error al buscar el objeto por uuid. ", e);
      throw new ServiceException("Error al buscar el objeto. ", e);
    } catch (IllegalAccessException e) {
      LOG.error("[findByUuid] Error al buscar el objeto por uuid. ", e);
      throw new ServiceException("Error al buscar el objeto. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("[findByUuid] Error al buscar el objeto por uuid. ", e);
      throw new ServiceException("Error al buscar el objeto. ", e);
    } catch (InvocationTargetException e) {
      LOG.error("[findByUuid] Error al buscar el objeto por uuid. ", e);
      throw new ServiceException("Error al buscar el objeto. ", e);
    }

    return result;
  }

  @Override
  public ObjectCmisEntity findByFile(String file) throws ServiceException {
    LOG.debug("Entramos en findByFile con fichero = " + file);

    ObjectCmisEntity result = null;

    try {

      ObjectCmisEntity objectCmisEntity = new ObjectCmisEntity();
      objectCmisEntity.setFichero(file);

      result = (ObjectCmisEntity) objectCmisRepository.findOneBy(objectCmisEntity);

    } catch (NoSuchMethodException e) {
      LOG.error("[findByFile] Error al buscar el objeto por uuid. ", e);
      throw new ServiceException("Error al buscar el objeto. ", e);
    } catch (SecurityException e) {
      LOG.error("[findByFile] Error al buscar el objeto por uuid. ", e);
      throw new ServiceException("Error al buscar el objeto. ", e);
    } catch (IllegalAccessException e) {
      LOG.error("[findByFile] Error al buscar el objeto por uuid. ", e);
      throw new ServiceException("Error al buscar el objeto. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("[findByFile] Error al buscar el objeto por uuid. ", e);
      throw new ServiceException("Error al buscar el objeto. ", e);
    } catch (InvocationTargetException e) {
      LOG.error("[findByFile] Error al buscar el objeto por uuid. ", e);
      throw new ServiceException("Error al buscar el objeto. ", e);
    }

    return result;
  }

}
