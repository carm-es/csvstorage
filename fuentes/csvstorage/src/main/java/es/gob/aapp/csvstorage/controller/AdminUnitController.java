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

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import es.gob.aapp.csvstorage.model.vo.SelectItemVO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import es.gob.aapp.csvstorage.model.object.unit.UnitObject;
import es.gob.aapp.csvstorage.model.pagination.FilterPageRequest;
import es.gob.aapp.csvstorage.services.business.unit.UnitBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;

/**
 * Clase controladora de unidades orgánicas. Es la segunda pestaña de la parte de Administración.
 * 
 * @author carlos.munoz1
 * 
 */
@Controller
public class AdminUnitController {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(AdminUnitController.class);

  private static final int DEFAULT_PAGE = 1;
  private static final int DEFAULT_SIZE = 25;

  /**
   * Inyección de los servicios de negocio de unidades orgánicas.
   */
  @Autowired
  private UnitBusinessService unitBusinessService;


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
  @RequestMapping(value = "/admin/units", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String view(ModelMap model) {

    LOG.info("Petición POST /admin/units");

    LOG.info("Return /admin/units.html");

    return "admin/units";
  }

  @RequestMapping("/admin/units/list/datatable.jquery")
  public @ResponseBody Map<Object, Object> datatable(Model model,
      @RequestParam("draw") Integer draw,
      @RequestParam(value = "search", defaultValue = "") String search, HttpServletRequest request,
      Locale locale) {
    Map<Object, Object> data = new HashMap<>();
    Page<?> page = null;

    int defaultSize = StringUtils.isNotEmpty(request.getParameter("length"))
        ? Integer.parseInt(request.getParameter("length"))
        : DEFAULT_SIZE;
    int defaultpage = StringUtils.isNotEmpty(request.getParameter("page"))
        ? Integer.parseInt(request.getParameter("page"))
        : DEFAULT_PAGE;
    FilterPageRequest filter = new FilterPageRequest(search, defaultpage, defaultSize, null);

    try {

      if (StringUtils.isEmpty(search)) {
        page = unitBusinessService.getAllOrganicUnitsPageable(filter);
      } else {
        page = unitBusinessService.getAllOrganicUnitsPageableByCodigoOrNombre(filter, search);
      }
    } catch (ServiceException e) {
      LOG.error("Error al obtener unidades. ", e);
      data.put("total", e.toString());
    }
    data.put("data", page.getContent());
    data.put("draw", draw);
    data.put("recordsTotal", page.getTotalElements());
    data.put("recordsFiltered", page.getTotalElements());
    return data;
  }

  /**
   * Petición por POST para consultar una unidad dada de alta.
   * 
   * @param unidadOrganica
   * @return
   */
  @RequestMapping(value = "/admin/units/consultar", method = RequestMethod.GET)
  @Secured("ROLE_ADMIN")
  public @ResponseBody UnitObject consultar(@RequestParam String unidadOrganica) {

    LOG.info("Petición POST /admin/units/consultar");

    LOG.info("Return /admin/units.html");

    UnitObject unitObject = null;
    try {
      unitObject = unitBusinessService.findByCodigo(unidadOrganica);
    } catch (ServiceException e) {
      LOG.error("Error al obtener unidades. ", e);
    }

    return unitObject;
  }

  /**
   * Este método busca las nuevas unidades orgánicas y actualiza la bbdd.
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/admin/units/save", method = RequestMethod.POST)
  public String saveOrganicUnits(ModelMap model) {

    LOG.info("Petición POST /saveOrganicUnits");

    int total = 0;

    try {

      total = unitBusinessService.saveOrganicUnitFromDir3();

      model.put("total", total);
    } catch (DataIntegrityViolationException ex) {
      LOG.error("Se ha intentado introducir una unidad orgánica ya existente. ", ex);
      model.put("total", total);
    } catch (ServiceException e) {
      LOG.error("Error a obtener unidades. ", e);
      model.put("total", e.getLocalizedMessage());

    }

    LOG.info("Return units.html");

    return "admin/units";
  }


  /**
   * Este busca todas las unidades orgánicas. Actualiza las que ya tenga y guarda las nuevas.
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/admin/units/saveAll", method = RequestMethod.POST)
  public String saveAllOrganicUnits(ModelMap model) {

    LOG.info("Petición POST /saveAllOrganicUnits");

    int total = 0;

    try {

      total = unitBusinessService.saveAllOrganicUnitFromDir3();

      model.put("total", total);
    } catch (DataIntegrityViolationException ex) {
      LOG.error("Se ha intentado introducir una unidad organica ya existente. ", ex);
      model.put("total", total);
    } catch (ServiceException e) {
      LOG.error("Error a obtener unidades. ", e);
      model.put("total", e.getLocalizedMessage());

    }

    LOG.info("Return units.html");

    return "admin/units";
  }

  /**
   * Guardar una unidad.
   */
  @RequestMapping(value = "/admin/units/manualSave", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public ResponseEntity<Void> saveUnit(@RequestParam String codigo) throws ServiceException {
    LOG.info("Petición POST /manualSave");
    unitBusinessService.saveUnit(codigo);
    return ResponseEntity.accepted().build();
  }

  /**
   * Guardar una unidad.
   */
  @RequestMapping(value = "/admin/units/manualDelete", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public ResponseEntity<Void> deleteUnit(@RequestParam String codigo) throws ServiceException {
    LOG.info("Petición POST /manualDelete");
    unitBusinessService.deleteUnit(codigo);
    return ResponseEntity.accepted().build();
  }

  // admin/units/saveByDate?fechaIni=14/06/2018
  @RequestMapping(value = "/admin/units/saveByDate", method = RequestMethod.GET)
  @Secured("ROLE_ADMIN")
  public String saveOrganicUnits(@RequestParam(value = "fechaIni") String fechaIni,
      ModelMap model) {

    LOG.info("[INI] Entramos en saveOrganicUnits: " + fechaIni);

    int total = 0;
    try {
      total = unitBusinessService.saveOrganicUnitFromDir3(fechaIni);

      model.put("total", total);
    } catch (DataIntegrityViolationException ex) {
      LOG.error("Se ha intentado introducir una unidad orgánica ya existente. ", ex);
      model.put("total", total);
    } catch (ServiceException e) {
      LOG.error("Error a obtener unidades. ", e);
      model.put("total", e.getLocalizedMessage());

    }

    LOG.info("Return unitsByDate.html");

    return "admin/unitsByDate";
  }

  @RequestMapping(value = "/admin/units/saveUnitsByDate", method = RequestMethod.POST)
  @Secured("ROLE_ADMIN")
  public String saveUnitsByDate(@RequestParam String fecha, ModelMap model)
      throws ServiceException {
    LOG.info("Petición POST /saveUnitsByDate");
    LOG.info("Desde fecha: " + fecha);

    int total = 0;

    try {
      if (!StringUtils.isEmpty(fecha)) {
        total = unitBusinessService.saveOrganicUnitFromDir3(fecha);
      }

      model.put("total", total);
    } catch (Exception ex) {
      LOG.error("Se ha producido un error durante la consulta. ", ex);
      model.put("total", total);
    }

    return "admin/units";
  }
}
