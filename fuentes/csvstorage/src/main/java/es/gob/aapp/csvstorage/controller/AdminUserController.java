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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import es.gob.aapp.csvstorage.controller.validator.UserValidator;
import es.gob.aapp.csvstorage.model.object.MessageObject;
import es.gob.aapp.csvstorage.model.object.users.UserObject;
import es.gob.aapp.csvstorage.services.business.user.UserBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.util.constants.Constants;


/**
 * Clase controladora de usuarios. Es la primera pestaña de la parte de Administración.
 * 
 * @author carlos.munoz1
 * 
 */
@Controller
public class AdminUserController {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(AdminUserController.class);

  /**
   * Inyección de los servicios de negocio de usuarios.
   */
  @Autowired
  private UserBusinessService userBusinessService;

  /**
   * Inyección de los properties de mensajes.
   */
  @Autowired
  private MessageSource messageSource;

  @Autowired
  private UserValidator userValidator;


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
   * Petición por POST para ver todos los endpoints dados de alta.
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/admin/users", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String view(ModelMap model) {

    LOG.info("Petición POST /admin/users");

    List<UserObject> users = new ArrayList<UserObject>();

    try {
      users = userBusinessService.findAll();
    } catch (ServiceException e) {
      LOG.error("No se puede recuperar los usuarios.");
    }

    // mostramos la lista de todas los endpoints
    model.put("users", users);
    model.addAttribute("user", new UserObject());

    LOG.info("Se muestran los usuarios encontrados: Total: " + users.size());

    LOG.info("Return /admin/users.html");

    return "admin/users";
  }

  @RequestMapping(value = "/admin/users/reload", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String reload(ModelMap model) {

    LOG.info("Petición POST /admin/users/reload");

    List<UserObject> users = new ArrayList<UserObject>();

    try {
      users = userBusinessService.findAll();
    } catch (ServiceException e) {
      LOG.error("No se puede recuperar los usuarios.");
    }

    // mostramos la lista de todas los endpoints
    model.put("users", users);
    model.addAttribute("user", new UserObject());

    LOG.info("Se muestran los usuarios encontrados. Total: " + users.size());

    LOG.info("Return /admin/users.html fragment list-users");

    return "admin/users :: list-users";
  }



  /**
   * Guardar los datos del Endpoint.
   * 
   * @param session
   * @param model
   * @param endpoint
   * @param result
   * @param locale
   * @return
   */
  @RequestMapping(value = "/admin/user/save", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String saveUser(HttpSession session, ModelMap model,
      @ModelAttribute("user") @Valid UserObject user, BindingResult result, Locale locale) {

    LOG.info("Petición POST /admin/user/save");

    MessageObject mensaje = null;

    try {

      // validaciones propias del archivo
      userValidator.validate(user, result);

      if (!result.hasErrors()) {
        LOG.info("Guardamos el usuario.");

        // guardamos la aplicacion si no hay errores en el formulario
        userBusinessService.save(user);

        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_SUCCESS,
            messageSource.getMessage("admin.user.save.success", null, locale));

        LOG.info("Se ha guardado correctamente el usuario.");

      } else {
        LOG.debug("Errores en el formulario.");

        // guadamos los errores para mostrarlos en pantalla
        List<String> listaErrores = new ArrayList<String>();
        for (FieldError fieldError : result.getFieldErrors()) {
          LOG.warn("Error en " + fieldError.getField() + ": "
              + messageSource.getMessage(fieldError, locale));
          listaErrores.add(messageSource.getMessage(fieldError, locale));
        }

        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
            messageSource.getMessage("admin.user.save.error", null, locale), listaErrores);

      }

    } catch (ServiceException e) {
      LOG.error("Error al guardar el usuario. ", e);
      mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
          messageSource.getMessage("admin.user.save.error", null, locale));
    }

    LOG.info("Devuelve MessageObject: " + mensaje.toString());

    model.addAttribute("mensajeUsuario", mensaje);


    return "admin/users :: edit-user";
  }

  /**
   * Borrar un usuario. Cambia el estado a inactivo.
   * 
   * @param politicaConservacion
   * @param result
   * @param locale
   * @return
   */
  @RequestMapping(value = "/admin/user/delete", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public @ResponseBody MessageObject deleteUser(@RequestParam Long id, Locale locale) {

    LOG.info("Petición POST /admin/user/delete");

    MessageObject mensaje = null;

    try {

      if (id != null) {

        UserObject user = new UserObject();
        user.setId(id);

        userBusinessService.delete(user);

        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_SUCCESS,
            messageSource.getMessage("admin.user.delete.success", null, locale));

        LOG.info("Se ha borrado correctamente el usuarios.");

      } else {
        LOG.error("Par�metro identificador de aplicación vacío. ");
        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
            messageSource.getMessage("admin.user.delete.error", null, locale));
      }

    } catch (ServiceException e) {
      LOG.error("Error al borrar la aplicación.", e);
      mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
          messageSource.getMessage("admin.user.delete.error", null, locale));
    }
    LOG.info("Devuelve MessageObject: " + mensaje.toString());

    return mensaje;
  }



  @RequestMapping(value = "/admin/users/edit", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String edit(ModelMap model, @RequestParam String usuario) {

    LOG.info("Petición POST /admin/users/edit");

    UserObject user = null;

    try {
      user = userBusinessService.findById(usuario);
    } catch (ServiceException e) {
      LOG.error("No se puede recuperar los usuarios");
    }

    List<UserObject> users = new ArrayList<UserObject>();

    try {
      users = userBusinessService.findAll();
    } catch (ServiceException e) {
      LOG.error("No se puede recuperar los usuarios");
    }

    // mostramos la lista de todas los endpoints
    model.addAttribute("user", user);

    LOG.info("Se muestran los usuarios encontrados. Total: " + users.size());
    LOG.info("Return /admin/users.html");

    return "admin/users :: edit-user";
  }

}
