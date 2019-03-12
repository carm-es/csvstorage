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

package es.gob.aapp.csvstorage.services.business.application.impl;

import java.util.Calendar;
import java.util.List;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.services.manager.unit.UnitManagerService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.users.UserEntity;
import es.gob.aapp.csvstorage.model.converter.application.ApplicationConverter;
import es.gob.aapp.csvstorage.model.object.application.ApplicationObject;
import es.gob.aapp.csvstorage.services.business.application.ApplicationBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService;
import es.gob.aapp.csvstorage.services.manager.users.UserManagerService;

/**
 * Implementación de los servicios business para aplicaciones consumidoras.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("applicationBusinessService")
public class ApplicationBusinessServiceImpl implements ApplicationBusinessService {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(ApplicationBusinessServiceImpl.class);

  /**
   * Inyección de los servicios de negocio de archivo.
   */
  @Autowired
  private ApplicationManagerService applicationManagerService;

  /**
   * Inyecci�n de los servicios de negocio de usuario.
   */
  @Autowired
  private UserManagerService userManagerService;

  @Autowired
  private UnitManagerService unitManagerService;


  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.business.application. UnitBusinessService#findAll()
   */
  public List<ApplicationObject> findAll() throws ServiceException {
    LOG.info("[INI] findAll");
    return ApplicationConverter.applicationEntityToModel(applicationManagerService.findAll());
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.business.application. UnitBusinessService
   * #save(es.gob.aapp.csvstorage.model.object.application.ApplicationObject)
   */
  @Transactional(rollbackFor = ServiceException.class)
  public ApplicationObject save(ApplicationObject aplicacion) throws ServiceException {

    LOG.debug("[INI] Entramos en save() para guardar los datos del archivo. ");

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String name = auth.getName();

    UserEntity userEntity = userManagerService.findByUsuario(name);

    // Obtener el id a partir de la Unidad organica
    UnitEntity dir3 = unitManagerService
        .findByDir3(aplicacion.getUnidad().getUnidadOrganica().split("-")[0].trim());

    if (aplicacion != null) {
      ApplicationEntity applicationEntity =
          ApplicationConverter.applicationModelToEntity(aplicacion);
      applicationEntity.setUnidad(dir3);

      aplicacion.setActivo(true);
      if (aplicacion.getId() != null) {
        ApplicationEntity aplicacionEnBBDD =
            applicationManagerService.findByIdAplicacion(aplicacion.getIdAplicacion());

        if (aplicacionEnBBDD != null) {
          // Si el password es nulo o vacio es que se deja el que tenía en bbdd
          boolean encriptarPassword = true;
          if (StringUtils.isEmpty(aplicacion.getPassword())) {
            applicationEntity.setPassword(aplicacionEnBBDD.getPassword());
            encriptarPassword = false;
          }
          applicationEntity.setCreatedAt(aplicacionEnBBDD.getCreatedAt());
          applicationEntity.setCreatedBy(aplicacionEnBBDD.getCreatedBy());

          applicationEntity.setModifiedAt(Calendar.getInstance().getTime());
          applicationEntity.setModifiedBy(userEntity);
          aplicacion = ApplicationConverter.applicationEntityToModel(
              applicationManagerService.update(applicationEntity, encriptarPassword));
        }
      } else {
        ApplicationEntity aplicacionEnBBDD =
            applicationManagerService.findByIdAplicacion(aplicacion.getIdAplicacion());

        if (aplicacionEnBBDD == null) {
          ApplicationEntity newAplicacion =
              ApplicationConverter.applicationModelToEntity(aplicacion);
          newAplicacion.setUnidad(dir3);

          newAplicacion.setCreatedAt(Calendar.getInstance().getTime());
          newAplicacion.setCreatedBy(userEntity);
          LOG.info("Aplicacion ha insertar: " + newAplicacion + " - " + newAplicacion.getUnidad());
          aplicacion = ApplicationConverter
              .applicationEntityToModel(applicationManagerService.create(newAplicacion));

        } else {
          applicationEntity.setId(aplicacionEnBBDD.getId());

          applicationEntity.setCreatedAt(aplicacionEnBBDD.getCreatedAt());
          applicationEntity.setCreatedBy(aplicacionEnBBDD.getCreatedBy());

          applicationEntity.setModifiedAt(Calendar.getInstance().getTime());
          applicationEntity.setModifiedBy(userEntity);
          applicationManagerService.update(applicationEntity, true);
        }
      }
      LOG.debug("Aplicacion guardada con éxito. ");
    }
    LOG.debug("[FIN] Salimos de save aplicacion. ");

    return aplicacion;
  }

  @Override
  @Transactional(rollbackFor = ServiceException.class)
  public void delete(ApplicationObject aplicacion) throws ServiceException {
    LOG.debug("[INI] Entramos en delete() para borrar las aplicaciones. ");

    if (aplicacion != null) {
      if (aplicacion.getId() != null || aplicacion.getIdAplicacion() != null) {
        applicationManagerService.delete(ApplicationConverter.applicationModelToEntity(aplicacion));
      }
    }

    LOG.debug("[FIN] Salimos de delete aplicacion. ");
  }


  @Override
  public ApplicationObject findByIdAplicacion(String idAplicacion) throws ServiceException {
    return ApplicationConverter
        .applicationEntityToModel(applicationManagerService.findByIdAplicacion(idAplicacion));

  }

  @Override
  public List<ApplicationObject> findBySerialNumber(String numSerie) throws ServiceException {
    return ApplicationConverter
        .applicationEntityToModel(applicationManagerService.findBySerialNumber(numSerie));
  }

}
