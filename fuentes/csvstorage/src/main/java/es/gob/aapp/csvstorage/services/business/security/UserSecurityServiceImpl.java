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
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.dao.entity.users.UserEntity;
import es.gob.aapp.csvstorage.model.converter.users.UsersConverter;
import es.gob.aapp.csvstorage.model.object.users.UserObject;
import es.gob.aapp.csvstorage.services.manager.users.UserManagerService;
import es.gob.aapp.csvstorage.util.constants.Errors;

/**
 * Servicio de negocio para seguridad de usuarios
 * 
 * @author jcamacho
 * 
 */
@Service("userSecurityService")
public class UserSecurityServiceImpl implements UserDetailsService {
  private static Logger logger = Logger.getLogger(UserSecurityServiceImpl.class);
  @Autowired
  EntityManager entityManager;

  @Autowired
  UserManagerService userManagerService;


  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.debug("UserSecurityService loadUserByUsername:" + username);
    UserObject obj = null;

    try {
      UserEntity userDatabase = userManagerService.findByUsuario(username);
      if (userDatabase != null) {
        obj = UsersConverter.userEntityToModel(userDatabase);

      } else {
        logger.error("El usuario " + username + " no ha sido encontrado");
        throw new UsernameNotFoundException(Errors.USER_NOT_FOUND.getDescription());
      }
    } catch (Exception e) {
      logger.error("El usuario " + username + " no ha sido encontrado", e);
      throw new UsernameNotFoundException(Errors.USER_NOT_FOUND.getDescription());
    }

    String id = obj.getUsuario();
    String password = obj.getPassword();

    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

    if (obj.getIsAdmin()) {
      // GrantedAuthority adminrole1 = new SimpleGrantedAuthority("ADMIN");
      GrantedAuthority adminrole2 = new SimpleGrantedAuthority("ROLE_ADMIN");
      // roles.add(adminrole1);
      roles.add(adminrole2);
    } else {
      GrantedAuthority adminrole = new SimpleGrantedAuthority("ROLE_USER");
      roles.add(adminrole);
    }

    logger.debug("Se intenta logar el usuario : " + username);
    User user = new User(id, password, true, accountNonExpired, credentialsNonExpired,
        accountNonLocked, roles);
    return user;
  }

}
