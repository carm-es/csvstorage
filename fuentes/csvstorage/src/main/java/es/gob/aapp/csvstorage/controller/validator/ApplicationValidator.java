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

import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.model.object.unit.UnitObject;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.unit.UnitManagerService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.gob.aapp.csvstorage.model.object.application.ApplicationObject;
import java.util.List;


/**
 * Clase de validaciones propias de los datos de una aplicación.
 */
@Component
public class ApplicationValidator implements Validator {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(ApplicationObject.class);
  public static final String UNIDAD_CODIGO = "unidad.unidadOrganica";

  @Autowired
  private UnitManagerService unitManagerService;

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.validation.Validator#supports(java.lang.Class)
   */
  public boolean supports(Class<?> clazz) {
    return ApplicationObject.class.equals(clazz);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.validation.Validator#validate(java.lang.Object,
   * org.springframework.validation.Errors)
   */
  public void validate(Object target, Errors errors) {

    LOG.info("[INI] Inicio validación.");

    ApplicationObject application = (ApplicationObject) target;

    // validación del password
    if (application.getId() == null && StringUtils.isEmpty(application.getPassword())) {

      LOG.error("Error password vacío o nulo.");
      errors.rejectValue("password", "NotBlank.aplicacion.password");

    }

    // validamos que el ID privado no sea el mismo que el Id publico
    if (application.getIdAplicacion().equals(application.getIdAplicacionPublico())) {
      LOG.error("El IdPublico debe ser distinto que el IdPrivado. ");
      errors.rejectValue("idAplicacionPublico", "NotEquals.aplicacion.id");
    }

    // validación de unidad
    if (application.getUnidad() != null
        && !StringUtils.isBlank(application.getUnidad().getUnidadOrganica())) {

      // cogemos solo el código de la unidad, ya que viene con el formato "codigo - nombre"
      application.getUnidad()
          .setUnidadOrganica(application.getUnidad().getUnidadOrganica().split("-")[0].trim());

      // validación de código de unidad
      UnitEntity unit;
      try {

        unit =
            unitManagerService.findByDir3(application.getUnidad().getUnidadOrganica().toString());
        if (unit == null) {
          LOG.error("Error código de unidad incorrecto. ");
          errors.rejectValue(UNIDAD_CODIGO, "NotValid.endpoint.unidad.codigo");
        }

      } catch (ServiceException e) {
        LOG.error("Error al buscar código de unidad. ", e);
        errors.rejectValue(UNIDAD_CODIGO, "NotValid.endpoint.unidad.codigo");
      }

    } else {
      LOG.error("Error unidad vacía o nula. ");
      errors.rejectValue(UNIDAD_CODIGO, "NotBlank.aplicacion.dir3");
    }



    LOG.info("[FIN] Fin validacións. Total errores: " + errors.getErrorCount());
  }


}
