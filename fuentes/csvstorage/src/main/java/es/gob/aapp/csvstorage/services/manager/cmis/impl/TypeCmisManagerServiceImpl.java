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

import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.dao.entity.cmis.TypeCmisEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.cmis.TypeCmisManagerService;

/**
 * Implementaciï¿½n de los servicios manager objetos CMIS.
 * 
 * 
 */
@Service("typeCmisManagerService")
public class TypeCmisManagerServiceImpl implements TypeCmisManagerService {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(ObjectCmisManagerServiceImpl.class);

  @Autowired
  EntityManager entityManager;

  private BaseRepository<TypeCmisEntity, Long> typeCmisRepository;

  @PostConstruct
  private void configure() {
    typeCmisRepository =
        new BaseRepositoryImpl<TypeCmisEntity, Long>(TypeCmisEntity.class, entityManager);
  }


  @Override
  public Iterator<TypeCmisEntity> findAll() throws ServiceException {
    LOG.debug("Entramos en findAll");

    Iterator<TypeCmisEntity> result = null;

    try {

      result = typeCmisRepository.findAll().iterator();

    } catch (SecurityException e) {
      LOG.error("[findAll] Error al buscar el objeto por uuid. ", e);
      throw new ServiceException("Error al buscar el objeto. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("[findByUuid] Error al buscar el objeto por uuid. ", e);
      throw new ServiceException("Error al buscar el objeto. ", e);
    }

    return result;
  }


}
