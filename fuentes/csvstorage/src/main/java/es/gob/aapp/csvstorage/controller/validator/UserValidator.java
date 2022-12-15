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

package es.gob.aapp.csvstorage.controller.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.gob.aapp.csvstorage.model.object.users.UserObject;


/**
 * Clase de validaciones propias de los datos de un usuario.
 */
@Component
public class UserValidator implements Validator {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(UserValidator.class);

  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.validation.Validator#supports(java.lang.Class)
   */
  public boolean supports(Class<?> clazz) {
    return UserObject.class.equals(clazz);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.validation.Validator#validate(java.lang.Object,
   * org.springframework.validation.Errors)
   */
  public void validate(Object target, Errors errors) {

    LOG.info("[INI] Inicio validación.");

    UserObject user = (UserObject) target;

    // validación del password
    if (user.getId() == null && StringUtils.isEmpty(user.getPassword())) {

      LOG.error("Error password vacío o nulo. ");
      errors.rejectValue("password", "NotBlank.aplicacion.password");

    }

    LOG.info("[FIN] Fin validacións. Total errores: " + errors.getErrorCount());
  }


}
