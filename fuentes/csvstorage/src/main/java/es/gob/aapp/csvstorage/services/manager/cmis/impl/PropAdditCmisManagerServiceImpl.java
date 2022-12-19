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

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.gob.aapp.csvstorage.dao.entity.cmis.PropAdditObjectCmisEntity;
import es.gob.aapp.csvstorage.dao.entity.cmis.PropertiesTypeCmisEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.cmis.PropAdditCmisManagerService;

/**
 * Implementaciï¿½n de los servicios manager objetos CMIS.
 * 
 * 
 */
@Service("propAdditCmisManagerService")
public class PropAdditCmisManagerServiceImpl implements PropAdditCmisManagerService {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(PropAdditCmisManagerServiceImpl.class);

  @Autowired
  EntityManager entityManager;

  private BaseRepository<PropertiesTypeCmisEntity, Long> propTypeCmisRepository;

  private BaseRepository<PropAdditObjectCmisEntity, Long> propDocCmisRepository;

  @PostConstruct
  private void configure() {
    propTypeCmisRepository = new BaseRepositoryImpl<PropertiesTypeCmisEntity, Long>(
        PropertiesTypeCmisEntity.class, entityManager);

    propDocCmisRepository = new BaseRepositoryImpl<PropAdditObjectCmisEntity, Long>(
        PropAdditObjectCmisEntity.class, entityManager);
  }

  @Override
  public List<PropertiesTypeCmisEntity> findByType(Long type) throws ServiceException {
    PropertiesTypeCmisEntity param = new PropertiesTypeCmisEntity();
    param.setId(type);
    List<PropertiesTypeCmisEntity> list;
    try {
      list = propTypeCmisRepository.findListBy(param);

    } catch (Exception e) {
      LOG.error("[findTypeByIdProperty] Error al buscar el tipo cmis por idproperty. ", e);
      throw new ServiceException("Error al buscar el tipo cmis por idproperty. ", e);
    }

    return list;
  }

  @Override
  public PropertiesTypeCmisEntity findTypeByIdProperty(String property) throws ServiceException {
    PropertiesTypeCmisEntity param = new PropertiesTypeCmisEntity();
    param.setIdProperty(property);
    try {
      List<PropertiesTypeCmisEntity> list = propTypeCmisRepository.findListBy(param);

      if (list != null && list.size() > 0) {
        param = list.get(0);
      }
    } catch (Exception e) {
      LOG.error("[findTypeByIdProperty] Error al buscar el tipo cmis por idproperty. ", e);
      throw new ServiceException("Error al buscar el tipo cmis por idproperty. ", e);
    }

    return param;
  }

  @Override
  @Transactional
  public PropAdditObjectCmisEntity create(PropAdditObjectCmisEntity entity)
      throws ServiceException {


    PropAdditObjectCmisEntity param = new PropAdditObjectCmisEntity();
    param.setUuid(entity.getUuid());
    param.setProperty(entity.getProperty());

    PropAdditObjectCmisEntity result = null;
    try {
      List<PropAdditObjectCmisEntity> list = propDocCmisRepository.findListBy(param);
      // Si existe se modifica la propiedad existente
      if (list != null && list.size() > 0) {
        entity.setId(list.get(0).getId());
      }

      result = propDocCmisRepository.save(entity);

    } catch (Exception e) {
      LOG.error("[create] Error al crear la propiedad cmis. ", e);
      throw new ServiceException("Error al crear la propiedad cmis. ", e);
    }

    return result;
  }

  @Override
  public List<PropAdditObjectCmisEntity> findProperiesByUuid(String uuid) throws ServiceException {
    PropAdditObjectCmisEntity param = new PropAdditObjectCmisEntity();
    param.setUuid(uuid);
    List<PropAdditObjectCmisEntity> list = new ArrayList<PropAdditObjectCmisEntity>();
    try {
      if (uuid != null) {
        list = propDocCmisRepository.findListBy(param);
      }
    } catch (Exception e) {
      LOG.error("[findProperiesByDocument] Error al buscar la propiedad cmis por uuid. ", e);
      throw new ServiceException("Error al buscar la propiedad cmis por uuid. ", e);
    }

    return list;
  }
}

