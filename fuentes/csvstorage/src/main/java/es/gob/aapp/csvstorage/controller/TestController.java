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

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.model.object.MessageObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.services.manager.document.DocumentManagerService;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.constants.DocumentPermission;

/**
 * Clase controladora de la pantalla de pruebas del servicio web.
 * 
 * @author carlos.munoz1
 * 
 */
@Controller
public class TestController {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(TestController.class);

  /**
   * Inyección de los properties de mensajes.
   */
  @Autowired
  private MessageSource messageSource;


  @Autowired
  private DocumentManagerService documentManagerService;

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
   * Petición por GET, significa que se está pidiendo la página por primera vez. Inicio de la página
   * desde header.
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public String initForm(ModelMap model, HttpSession session, HttpServletRequest request) {
    LOG.info("Petición GET /administracion");

    session.setAttribute("menuSelected", 2);

    model.addAttribute("documentRequest", new DocumentObject());

    LOG.info("Return test.html");

    return "test";
  }

  /**
   * Prueba del servicio web de validación y descarga de documentos a partir de un CSV.
   * Opcionalmente recoge una lista de organismos y una lista de procedimientos asociados.
   * 
   * @param model
   * @param session
   * @param documentObject
   * @param result
   * @param locale
   * @return
   */
  @RequestMapping(value = "/test", method = RequestMethod.POST)
  public String testService(ModelMap model, HttpSession session,
      @ModelAttribute("documentRequest") @Valid DocumentObject documentObject, BindingResult result,
      Locale locale) {
    LOG.info("Petición POST /test");

    session.setAttribute("menuSelected", 2);

    es.gob.aapp.csvstorage.model.object.MessageObject mensaje = null;

    try {

      if (!result.hasErrors()) {
        // Buscamos el documento por dir3 y csv o idEni
        List<DocumentEntity> documentEntityList = documentManagerService.findByDocument(
            documentObject.getCsv(), documentObject.getDir3(), documentObject.getIdEni());

        if (documentEntityList != null && !documentEntityList.isEmpty()) {
          DocumentEntity documentEntity = documentEntityList.get(0);

          documentObject.setUuid(documentEntity.getUuid());
          documentObject.setMimeType(documentEntity.getTipoMINE());
          documentObject.setName(documentEntity.getName());
          documentObject
              .setTipoPermiso(DocumentPermission.getPermission(documentEntity.getTipoPermiso()));

          model.addAttribute("response", documentObject);
          model.addAttribute("size", documentEntity.getTamanioFichero());

          mensaje = new MessageObject(Constants.MESSAGE_LEVEL_SUCCESS,
              messageSource.getMessage(Constants.QUERY_DOCUMENT_EXIST_DESC, null, locale));

        } else {
          mensaje = new MessageObject(Constants.MESSAGE_LEVEL_SUCCESS,
              messageSource.getMessage(Constants.QUERY_DOCUMENT_NOT_EXIST_DESC, null, locale));
        }

      } else {
        LOG.warn("Errores en el formulario.");

        for (FieldError fieldError : result.getFieldErrors()) {
          LOG.error("Error en " + fieldError.getField() + ": "
              + messageSource.getMessage(fieldError, locale));
        }

        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR,
            messageSource.getMessage("test.form.field.error", null, locale));
      }

    } catch (Exception e) {
      LOG.error("Error desde Controller. ", e);
      mensaje = new MessageObject(Constants.MESSAGE_LEVEL_ERROR, e.getMessage(), null);

    }

    model.addAttribute("mensajeUsuario", mensaje);
    model.addAttribute("documentRequest", documentObject);

    LOG.info("Return test.html");
    return "test";
  }

}
