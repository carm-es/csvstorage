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

package es.gob.aapp.csvstorage.controller;

import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import es.gob.aapp.csvstorage.model.object.MessageObject;
import es.gob.aapp.csvstorage.model.object.ScanObject;
import es.gob.aapp.csvstorage.services.manager.document.DocumentManagerService;
import es.gob.aapp.csvstorage.util.constants.Constants;

/**
 * Clase controladora de analizar los documentos de la NAS.
 * 
 * @author carlos.munoz1
 * 
 */
@Controller
public class AdminScanController {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(AdminScanController.class);

  @Autowired
  private DocumentManagerService documentManagerService;

  /**
   * Inyección de los properties de mensajes.
   */
  @Autowired
  private MessageSource messageSource;

  /**
   * Inicialización del WebDataBinder.
   * 
   * @param binder
   */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    // Trim Strings and transform an empty string into a null value.
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  /**
   * Petición por POST para redirigir a la pantalla de Scan.
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/admin/scan", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String view(ModelMap model) {

    LOG.info("Petición POST /admin/scan");
    model.addAttribute("analizar", new ScanObject());
    LOG.info("nuevoReturn /admin/scan.html");

    return "admin/scan";
  }



  @RequestMapping(value = "/admin/scan/scanAll", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String scanFile(ModelMap model, Locale locale) {

    LOG.info("Petición POST /admin/scan/scanAll");

    MessageObject mensaje = null;

    try {

      documentManagerService.updateFileSize();

    } catch (es.gob.aapp.csvstorage.services.exception.ServiceException e) {
      LOG.error("error al analizar el tamaño de los ficheros: " + e.getMessage(), e);
      mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
          messageSource.getMessage("admin.scan.error", null, locale));
    }

    model.addAttribute("mensajeUsuario", mensaje);

    return "admin/scan :: edit-scan";
  }


}
