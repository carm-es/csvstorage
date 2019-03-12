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

package es.gob.aapp.csvstorage.services.manager.reference.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.dao.entity.reference.VReferenciasAppEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.reference.VReferenciasAppManagerService;

/**
 * Implementación de los servicios manager para obtener las referencias a las aplicaciones
 * 
 * @author carlos.munoz1
 * 
 */
@Service("referenciasAppService")
public class VReferenciasAppManagerServiceImpl implements VReferenciasAppManagerService {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(VReferenciasAppManagerServiceImpl.class);

  @Autowired
  EntityManager entityManager;

  private BaseRepository<VReferenciasAppEntity, Long> referenciasAppRepository;

  @PostConstruct
  private void configure() {
    referenciasAppRepository = new BaseRepositoryImpl<VReferenciasAppEntity, Long>(
        VReferenciasAppEntity.class, entityManager);
  }


  @Override
  public List<VReferenciasAppEntity> findReferenciaAppByReferencia(VReferenciasAppEntity referencia)
      throws ServiceException {
    LOG.debug("Entramos en findReferenciaAppByReferencia");

    List<VReferenciasAppEntity> result = null;

    try {
      VReferenciasAppEntity entity = new VReferenciasAppEntity();
      entity.setIdDocumento(referencia.getIdDocumento());
      entity.setIdUnidadDocumento(referencia.getIdUnidadDocumento());
      entity.setCsv(referencia.getCsv());
      entity.setIdEni(referencia.getIdEni());

      result = referenciasAppRepository.findListBy(entity);
    } catch (Exception e) {
      LOG.error("Error al buscar la referencia de aplicaciones del documento. ", e);
      throw new ServiceException("Error al buscar la referencia de aplicaciones del documento. ",
          e);
    }

    return result;
  }



}
