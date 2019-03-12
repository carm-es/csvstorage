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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import org.springframework.web.multipart.MultipartFile;
import es.gob.aapp.csvstorage.controller.validator.ApplicationValidator;
import es.gob.aapp.csvstorage.model.object.MessageObject;
import es.gob.aapp.csvstorage.model.object.application.ApplicationObject;
import es.gob.aapp.csvstorage.model.object.truststore.TruststoreObject;
import es.gob.aapp.csvstorage.model.vo.ComboItemVO;
import es.gob.aapp.csvstorage.services.business.application.ApplicationBusinessService;
import es.gob.aapp.csvstorage.services.business.truckstore.TruststoreBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.constants.ApplicationPermission;

/**
 * Clase controladora de aplicaciones. Es la primera pestaña de la parte de Administración.
 * 
 * @author carlos.munoz1
 * 
 */
@Controller
public class AdminApplicationController {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(AdminApplicationController.class);

  /**
   * Inyección de los servicios de negocio de aplicaciones.
   */
  @Autowired
  private ApplicationBusinessService applicationBusinessService;

  @Autowired
  private ApplicationValidator applicationValidator;

  /**
   * Inyección de los properties de mensajes.
   */
  @Autowired
  private MessageSource messageSource;

  /**
   * Inyección de los properties de mensajes.
   */
  @Autowired
  private TruststoreBusinessService truckstoreBusinessService;

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
   * Petición por GET, significa que se está pidiendo la página por primera vez. Inicio de la pagina
   * desde header.
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = "/admin", method = RequestMethod.GET)
  @Secured("ROLE_ADMIN")
  public String initForm(ModelMap model, HttpSession session, HttpServletRequest request) {
    LOG.info("Petición GET /admin");

    try {
      process(model, session);
    } catch (ServiceException e) {
      LOG.error("Error al buscar todas las aplicaciones", e);
    }

    LOG.info("Return admin.html");

    return "admin/admin";
  }

  /**
   * Si la petición llega por POST, se está recargando la página por situaciones varias: paginación,
   * etc..
   * 
   * @param model
   * @param session
   * @param request
   * @return
   */
  @RequestMapping(value = "/admin", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String processSubmit(ModelMap model, HttpSession session, HttpServletRequest request,
      Locale locale) {
    LOG.info("Petición GET /admin");

    try {
      process(model, session);
    } catch (ServiceException e) {
      LOG.error("Error al buscar todas las aplicaciones. ", e);
      MessageObject mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
          messageSource.getMessage("admin.application.load.error", null, locale));
      model.addAttribute("mensajeUsuario", mensaje);
    }

    LOG.info("Return admin/admin.html");

    return "admin/admin";
  }

  /**
   * Si la petición llega por POST, se está recargando la página por situaciones varias: paginación,
   * etc..
   * 
   * @param model
   * @param session
   * @param request
   * @return
   */
  @RequestMapping(value = "/admin/aplicacion", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String loadApplication(ModelMap model, HttpSession session, HttpServletRequest request,
      Locale locale) {
    LOG.info("Petición GET /admin/aplicacion");

    try {
      process(model, session);
    } catch (ServiceException e) {
      LOG.error("Error al buscar todas las aplicaciones. ", e);
      MessageObject mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
          messageSource.getMessage("admin.application.load.error", null, locale));
      model.addAttribute("mensajeUsuario", mensaje);
    }

    LOG.info("Return admin/applications.html");

    return "admin/applications";
  }

  /**
   * Añade al Model el listado de aplicaciones consumidoras y selecciona la pestaña.
   * 
   * @param model
   * @param session
   */
  private void process(ModelMap model, HttpSession session) throws ServiceException {

    session.setAttribute("menuSelected", 1);

    model.addAttribute("aplicacion", new ApplicationObject());

    model.put("aplicaciones", applicationBusinessService.findAll());
    model.put("loadPermissions", loadPermissions());

  }

  /**
   * Guardar aplicaciones.
   * 
   * @param session
   * @param model
   * @param aplicacion
   * @param result
   * @param locale
   * @return
   */
  @RequestMapping(value = "/admin/aplicacion/guardar", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String saveApplication(HttpSession session, ModelMap model,
      @ModelAttribute("aplicacion") @Valid ApplicationObject aplicacion, BindingResult result,
      Locale locale) {

    LOG.info("Petición POST /admin/aplicacion/guardar");

    MessageObject mensaje = null;

    try {

      // validaciones propias de la aplicación
      applicationValidator.validate(aplicacion, result);

      if (!result.hasErrors()) {
        LOG.info("Guardamos la aplicación.");

        // guardamos la aplicacion si no hay errores en el formulario
        applicationBusinessService.save(aplicacion);

        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_SUCCESS,
            messageSource.getMessage("admin.application.save.success", null, locale));

      } else {
        LOG.debug("Errores en el formulario.");

        // guadamos los errores para mostrarlos en pantalla
        List<String> listaErrores = new ArrayList<>();
        for (FieldError fieldError : result.getFieldErrors()) {
          LOG.warn("Error en " + fieldError.getField() + ": "
              + messageSource.getMessage(fieldError, locale));
          listaErrores.add(messageSource.getMessage(fieldError, locale));
        }

        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
            messageSource.getMessage("admin.application.save.form.field.error", null, locale),
            listaErrores);


      }

    } catch (ServiceException e) {
      LOG.error("Error al guardar la aplicación. ", e);
      mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
          messageSource.getMessage("admin.application.save.error", null, locale));
    }

    // guardamos en model los objetos que se muestran en pantalla
    model.addAttribute("mensajeUsuario", mensaje);
    model.put("loadPermissions", loadPermissions());

    LOG.info("Muestra MessageObject: " + mensaje.toString());

    LOG.info("Return a admin/admin.html");

    return "admin/applications :: edit-applications";
  }

  /**
   * Borrar una aplicación consumidora. Cambia el estado a inactivo.
   * 
   * @param id
   * @param locale
   * @return
   */
  @RequestMapping(value = "/admin/aplicacion/borrar", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public @ResponseBody MessageObject deleteApplication(@RequestParam Long id, Locale locale) {

    LOG.info("Petición POST /administracion/aplicacion/borrar");

    MessageObject mensaje = null;

    try {

      if (id != null) {

        ApplicationObject aplicacion = new ApplicationObject();
        aplicacion.setId(id);

        applicationBusinessService.delete(aplicacion);

        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_SUCCESS,
            messageSource.getMessage("admin.application.delete.success", null, locale));

        LOG.info("Se ha borrado correctamente la aplicación.");

      } else {
        LOG.error("Parámetro identificador de aplicación vacío. ");
        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
            messageSource.getMessage("admin.application.delete.error", null, locale));
      }

    } catch (ServiceException e) {
      LOG.error("Error al borrar la aplicación.", e);
      mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
          messageSource.getMessage("admin.application.delete.error", null, locale));
    }
    LOG.info("Devuelve MessageObject: " + mensaje.toString());

    return mensaje;
  }

  @RequestMapping(value = "/admin/aplicacion/reload", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String reload(ModelMap model) {

    LOG.info("Petición POST /admin/aplicacion/reload");

    try {
      model.put("aplicaciones", applicationBusinessService.findAll());
      model.put("loadPermissions", loadPermissions());
    } catch (ServiceException e) {
      LOG.error("No se puede recuperar las aplicaciones.");
    }


    LOG.info("Return /admin/admin.html fragment listaplicaciones");

    return "admin/applications :: listaplicaciones";
  }

  @RequestMapping(value = "/admin/aplicacion/edit", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String edit(ModelMap model, @RequestParam String idAplicacion) {

    LOG.info("Petición POST /admin/aplicacion/edit");

    ApplicationObject aplicacion = null;

    try {
      aplicacion = applicationBusinessService.findByIdAplicacion(idAplicacion);
      aplicacion.setPassword("");
    } catch (ServiceException e) {
      LOG.error("No se puede recuperar las aplicaciones");
    }

    List<ApplicationObject> applications = new ArrayList<>();

    try {
      applications = applicationBusinessService.findAll();
    } catch (ServiceException e) {
      LOG.error("No se puede recuperar las aplicaciones");
    }

    model.addAttribute("aplicacion", aplicacion);
    model.put("loadPermissions", loadPermissions());

    LOG.info("Se muestran las aplicaciones encontradas. Total: " + applications.size());

    LOG.info("Return /admin/aplicacion/edit.html");
    return "admin/applications :: edit-applications";
  }


  @RequestMapping(value = "/admin/aplicacion/importCertificate", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public @ResponseBody MessageObject importCertificate(ModelMap model,
      @RequestParam("popupSelecCertificado") MultipartFile certificado,
      @RequestParam("popupSelecAlias") String alias, Locale locale) {

    LOG.info("Petición POST /admin/aplicacion/importCertificate");
    MessageObject mensaje = null;
    try {

      if ((certificado == null || certificado.getSize() == 0) && StringUtils.isNotEmpty(alias)) {
        LOG.info("Si viene el alias obtenermos el certificado del almacén");
        TruststoreObject almacen = truckstoreBusinessService.getDetail(alias);
        if (almacen != null) {
          mensaje = new MessageObject(Constants.MESSAGE_LEVEL_SUCCESS, almacen.getSerialNumbre());
          LOG.info("Existe un certificado con el alias introducido.");
        } else {
          mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR, messageSource
              .getMessage("admin.application.data.certificado.error.aliasNotFound", null, locale));
          LOG.error("No existe ningún certificado con el alias introducido.");
        }

      } else if (certificado != null && StringUtils.isNotEmpty(alias)) {

        TruststoreObject almacen = new TruststoreObject();
        almacen.setAlias(alias);
        almacen.setCertificado(certificado.getInputStream());
        try {
          almacen = truckstoreBusinessService.save(almacen);
          mensaje = new MessageObject(Constants.MESSAGE_LEVEL_SUCCESS, almacen.getSerialNumbre());
          LOG.info("Se ha guardado correctamente el certificado.");

        } catch (ServiceException e) {
          LOG.error("Se ha producido un error al importar el certificado.", e);
          mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR, messageSource
              .getMessage("admin.application.data.certificado.error.saveError", null, locale));
        }
      } else {
        LOG.error("Se debe rellenar el alias o seleccionar un certificado a importar.");
        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR, messageSource
            .getMessage("admin.application.data.certificado.error.aliasAndFile", null, locale));
      }
    } catch (Exception e) {
      LOG.error("Se ha producido un error al importar el certificaod. ", e);
      mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR, messageSource
          .getMessage("admin.application.data.certificado.error.saveError", null, locale));
    }

    return mensaje;
  }

  private List<ComboItemVO> loadPermissions() {
    List<ComboItemVO> listaAuthentication = new ArrayList<ComboItemVO>();
    listaAuthentication
        .add(new ComboItemVO(ApplicationPermission.PERMISSION_CREATED_DOCUMENTS.getCode(),
            ApplicationPermission.PERMISSION_CREATED_DOCUMENTS.getDescription()));
    listaAuthentication
        .add(new ComboItemVO(ApplicationPermission.PERMISSION_ALL_DOCUMENTS_BY_CSV.getCode(),
            ApplicationPermission.PERMISSION_ALL_DOCUMENTS_BY_CSV.getDescription()));
    listaAuthentication
        .add(new ComboItemVO(ApplicationPermission.PERMISSION_ALL_DOCUMENTS.getCode(),
            ApplicationPermission.PERMISSION_ALL_DOCUMENTS.getDescription()));

    return listaAuthentication;
  }
}
