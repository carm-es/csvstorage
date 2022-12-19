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

package es.gob.aapp.csvstorage.services.manager.prueba.impl;

import java.lang.reflect.InvocationTargetException;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.dao.entity.PruebaEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.prueba.PruebaManagerService;

@Service("pruebaManagerService")
public class PruebaManagerServiceImpl implements PruebaManagerService {

  private static final Logger LOG = LogManager.getLogger(PruebaManagerServiceImpl.class);

  @Autowired
  EntityManager entityManager;

  private BaseRepository<PruebaEntity, Long> pruebaRepository;

  @PostConstruct
  private void configure() {
    // new GenericRepository<ArchivePhaseEntity, Long>(ArchivePhaseEntity.class, entityManager);
    pruebaRepository =
        new BaseRepositoryImpl<PruebaEntity, Long>(PruebaEntity.class, entityManager);
  }


  public PruebaEntity findByPrueba(PruebaEntity prueba) throws ServiceException {
    PruebaEntity pruebaReturn = null;
    LOG.info("[INI] Entramos en findByPrueba");
    try {
      pruebaReturn = (PruebaEntity) pruebaRepository.findOneBy(prueba);
    } catch (NoSuchMethodException e) {
      LOG.error("Error en findByPrueba. ", e);
    } catch (SecurityException e) {
      LOG.error("Error en findByPrueba. ", e);
    } catch (IllegalAccessException e) {
      LOG.error("Error en findByPrueba. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("Error en findByPrueba. ", e);
    } catch (InvocationTargetException e) {
      LOG.error("Error en findByPrueba. ", e);
      throw new ServiceException("", e);
    }

    LOG.info("[FIN] Salimos de findByPrueba");
    return pruebaReturn;
  }


  public PruebaEntity savePrueba(PruebaEntity prueba) throws ServiceException {
    LOG.info("[INI] Entramos en savePrueba");
    return pruebaRepository.save(prueba);
  }
}
