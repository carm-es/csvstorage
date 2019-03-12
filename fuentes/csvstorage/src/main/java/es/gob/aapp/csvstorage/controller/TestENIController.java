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
import es.gob.aapp.csvstorage.model.object.MessageObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentEniObject;
import es.gob.aapp.csvstorage.services.business.document.GetDocumentBusinessService;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoEniRequest;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoEniResponse;

/**
 * Clase controladora de la pantalla de pruebas del servicio web.
 * 
 * @author carlos.munoz1
 * 
 */
@Controller
public class TestENIController {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(TestENIController.class);

  /**
   * Inyección de los properties de mensajes.
   */
  @Autowired
  private MessageSource messageSource;

  @Autowired
  private GetDocumentBusinessService getDocumentBusinessService;

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
  @RequestMapping(value = "/testENI", method = RequestMethod.GET)
  public String initForm(ModelMap model, HttpSession session, HttpServletRequest request) {
    LOG.info("Petición GET /administracion");

    session.setAttribute("menuSelected", 3);

    model.addAttribute("documentEniObject", new DocumentEniObject());

    LOG.info("Return testENI.html");

    return "testENI";
  }

  /**
   * Prueba del servicio web de validación y descarga de documentos a partir de un CSV.
   * Opcionalmente recoge una lista de organismos y una lista de procedimientos asociados.
   * 
   * @param model
   * @param session
   * @param validationRequest
   * @param result
   * @param locale
   * @return
   */
  @RequestMapping(value = "/testENI", method = RequestMethod.POST)
  public String testService(ModelMap model, HttpSession session,
      @ModelAttribute("documentEniObject") @Valid DocumentEniObject documentEniObject,
      BindingResult result, Locale locale) {
    LOG.info("Petición POST /testENI");

    session.setAttribute("menuSelected", 3);

    ObtenerDocumentoEniResponse response = null;
    es.gob.aapp.csvstorage.model.object.MessageObject mensaje = null;

    try {

      if (!result.hasErrors()) {
        String size = null;
        ObtenerDocumentoEniRequest request = new ObtenerDocumentoEniRequest();
        request.setCsv(documentEniObject.getCsv());
        request.setDir3(documentEniObject.getDir3());
        request.setIdENI(documentEniObject.getIdEni());

        // Obtener documento ENI
        response = (ObtenerDocumentoEniResponse) getDocumentBusinessService.obtenerENI(request,
            ObtenerDocumentoEniResponse.class);

        if (response.getDocumentoEniResponse() != null
            && response.getDocumentoEniResponse().getDocumento() != null
            && response.getDocumentoEniResponse().getDocumento().getContenido() != null
            && response.getDocumentoEniResponse().getDocumento().getContenido()
                .getValorBinario() != null) {
          size = String.valueOf(response.getDocumentoEniResponse().getDocumento().getContenido()
              .getValorBinario().length);
        }

        model.addAttribute("response", response.getDocumentoEniResponse());
        model.addAttribute("size", size);

        mensaje = new MessageObject(Constants.MESSAGE_LEVEL_SUCCESS,
            response.getDocumentoEniResponse().getDescripcion());

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

    model.addAttribute("documentEniObject", documentEniObject);
    model.addAttribute("mensajeUsuario", mensaje);

    LOG.info("Muestra MessageObject: " + mensaje.toString());
    LOG.info("Return testENI.html");

    return "testENI";
  }

}
