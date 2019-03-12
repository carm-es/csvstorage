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
import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import es.gob.aapp.csvstorage.model.object.MessageObject;
import es.gob.aapp.csvstorage.model.object.truststore.TruststoreObject;
import es.gob.aapp.csvstorage.services.business.truckstore.TruststoreBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.util.constants.Constants;

/**
 * Clase controladora de almacen.
 * 
 * @author carlos.munoz1
 * 
 */
@Controller
public class AdminTruststoreController {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(AdminTruststoreController.class);

  /**
   * Inyección de los servicios de negocio de usuarios.
   */
  @Autowired
  private TruststoreBusinessService truststoreBusinessService;

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
   * Petición por POST para ver todos los certificados.
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/admin/truststore", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String view(ModelMap model) {

    LOG.info("Petición POST /admin/truststore");

    List<String> certs = new ArrayList<String>();

    // mostramos la lista
    model.put("aliasList", certs);
    model.addAttribute("almacen", new TruststoreObject());

    LOG.info("Se muestran los certs encontrados. Total: " + certs.size());

    LOG.info("nuevoReturn /admin/truststore.html");

    return "admin/truststore";
  }

  @RequestMapping(value = "/admin/truststore/reload", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String reload(ModelMap model) {

    LOG.info("Petici�n POST /admin/truststore/reload");
    List<String> certs = new ArrayList<String>();

    try {
      certs = truststoreBusinessService.findAll();
    } catch (ServiceException e) {
      LOG.error("No se puede recuperar los certs");
    }

    TruststoreObject almacen = new TruststoreObject();

    // mostramos la lista de todas los endpoints
    model.put("aliasList", certs);
    model.addAttribute("almacen", almacen);

    LOG.info("Se muestran los certs encontrados. Total: " + certs.size());

    LOG.info("Return /admin/truststore.html fragment list-alias");
    return "admin/truststore :: list-alias";
  }

  @RequestMapping(value = "/admin/truststore/save", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String save(ModelMap model, @RequestParam("certificado") MultipartFile certificado,
      @RequestParam("alias") String alias, Locale locale) {

    LOG.info("Petici�n POST /admin/truststore/save");

    MessageObject mensaje = null;

    try {

      if (alias == null) {
        LOG.error("Parametro alias vacio. ");
        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
            messageSource.getMessage("admin.cert.save.error", null, locale));
      } else {

        TruststoreObject almacen = new TruststoreObject();
        almacen.setAlias(alias);
        almacen.setCertificado(certificado.getInputStream());

        almacen = truststoreBusinessService.save(almacen);

        LOG.info("Se ha guardado correctamente el certificado.");

        model.addAttribute("almacen", almacen);
      }
    } catch (Exception e) {
      LOG.error("Error al guardar el usuario. ", e);
      mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
          messageSource.getMessage("admin.cert.save.error", null, locale));
    }

    model.addAttribute("mensajeUsuario", mensaje);

    return "admin/truststore :: edit-cert";
  }

  @RequestMapping(value = "/admin/truststore/delete", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public @ResponseBody MessageObject delete(@RequestParam("alias") String alias, Locale locale) {

    LOG.info("Petición POST /admin/truststore/delete");

    MessageObject mensaje = null;

    try {

      if (alias != null) {

        TruststoreObject cert = new TruststoreObject();
        cert.setAlias(alias);

        truststoreBusinessService.delete(cert);

        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_SUCCESS,
            messageSource.getMessage("admin.cert.delete.success", null, locale));

        LOG.info("Se ha borrado correctamente el certificado.");

      } else {
        LOG.error("Par�metro identificador de aplicaci�n vac�o. ");
        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
            messageSource.getMessage("admin.user.delete.error", null, locale));
      }

    } catch (ServiceException e) {
      LOG.error("Error al borrar la aplicaci�n.", e);
      mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
          messageSource.getMessage("admin.user.delete.error", null, locale));
    }
    LOG.info("Devuelve MessageObject: " + mensaje.toString());

    return mensaje;
  }

  /**
   * Obtiene el detalle del certificado
   * 
   * @param alias String con el alias del certificado del que queremos obtener el detalle
   * @return TruckstoreObject
   */
  @RequestMapping(value = "/admin/truststore/detail", method = RequestMethod.GET)
  @Secured("ROLE_ADMIN")
  public @ResponseBody TruststoreObject getCertificateDetail(@RequestParam String alias) {

    LOG.info("[INI] Entramos en getCertificateDetail. ");

    // Se obtiene el certificado
    TruststoreObject almacenObject = null;
    try {
      almacenObject = truststoreBusinessService.getDetail(alias);

    } catch (ServiceException e) {
      LOG.error("Se ha producido un error al obtener el detalle del certificado");
    }

    LOG.info("[FIN] Salimos de getCertificateDetail.");

    return almacenObject;
  }


}
