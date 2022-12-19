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

package es.gob.aapp.csvstorage.services.business.security;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService;

/**
 * Implementación de la interfaz UserDetailsService para la autenticación de aplicaciones
 * consumidoras.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("applicationUserDetailService")
public class ApplicationUserDetailServiceImpl implements UserDetailsService {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(ApplicationUserDetailServiceImpl.class);

  /** Inyección de los servicios manager de aplicaciones. */
  @Autowired
  private ApplicationManagerService applicationManagerService;

  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.security.core.userdetails.UserDetailsService#
   * loadUserByUsername(java.lang.String)
   */
  public UserDetails loadUserByUsername(String idAplicacion) throws UsernameNotFoundException {

    User user = null;

    // buscamos la aplicacion
    ApplicationEntity appEntity;
    try {
      appEntity = applicationManagerService.findByIdAplicacion(idAplicacion);

      if (appEntity != null && appEntity.getActivo()) {
        LOG.debug("Se intenta logar la aplicación : " + appEntity.getIdAplicacion());
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

        // Permisos CMIS
        if (appEntity.getLecturaCmis()) {
          GrantedAuthority readcmisrole = new SimpleGrantedAuthority("ROLE_CMIS_READ");
          roles.add(readcmisrole);
        }
        if (appEntity.getEscrituraCmis()) {
          GrantedAuthority writecmisrole = new SimpleGrantedAuthority("ROLE_CMIS_WRITE");
          roles.add(writecmisrole);

        }

        if (appEntity.getIsAdmin()) {
          GrantedAuthority adminrole = new SimpleGrantedAuthority("ADMIN");
          roles.add(adminrole);
          user = new User(appEntity.getIdAplicacion(), appEntity.getPassword(), true, true, true,
              true, roles);
        } else {
          GrantedAuthority adminrole = new SimpleGrantedAuthority("USER");
          roles.add(adminrole);
          user = new User(appEntity.getIdAplicacion(), appEntity.getPassword(), true, true, true,
              true, roles);
        }

        LOG.debug("Aplicación logada con éxito. ");
      } else {
        LOG.error("La aplicacion no existe. ");
        throw new UsernameNotFoundException("La aplicacion no existe. ");
      }

    } catch (ServiceException e) {
      LOG.error("Error al intentar buscar la aplicación. ", e);
      throw new UsernameNotFoundException("Error al intentar buscar la aplicación. ", e);
    } catch (Exception e) {
      LOG.error("Error al intentar buscar la aplicaci�n. ", e);
      throw new UsernameNotFoundException("Error al intentar buscar la aplicación. ", e);
    }

    return user;
  }

}
