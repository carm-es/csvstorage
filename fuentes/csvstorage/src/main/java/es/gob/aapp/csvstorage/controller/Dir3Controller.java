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
import es.gob.aapp.csvstorage.model.object.unit.UnitObject;
import es.gob.aapp.csvstorage.model.vo.SelectItemVO;
import es.gob.aapp.csvstorage.services.business.unit.UnitBusinessService;
import es.gob.aapp.csvstorage.services.business.unit.impl.UnitBusinessServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Controlador para las funciones relacionadas con Dir3.
 * 
 * @author serena.plaza
 * 
 */
@Controller
public class Dir3Controller {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(Dir3Controller.class);

  /**
   * Inyeccion de los servicios de negocio de unidades organicas.
   */
  @Autowired
  private UnitBusinessService unitBusinessService;

  /**
   * Recupera las unidades DIR3 para el autocompletado.
   * 
   * @param codigo String con el codigo a completar o parte de el
   * @return List<SelectItemVO> lista a mostrar en el autocomplete
   */
  @RequestMapping(value = "/autocomplete/dir3", method = RequestMethod.GET)
  public @ResponseBody List<SelectItemVO> autocompleteCodigoDIR(
      @RequestParam(value = "term") final String codigo) {

    LOG.info("[INI] Entramos en autocompleteCodigoDIR. ");

    // Se obtienen las unidades que cumplen con la busqueda
    LOG.info("Buscamos las unidades...");
    List<UnitObject> unidades = unitBusinessService.findLikeNombre(codigo);

    // Se carga la lista a devolver convirtiendo los resultados
    LOG.info("Transformamos en tipo SelectIntemVO para mostrar lista de autocompletado...");
    List<SelectItemVO> listaReturn = new ArrayList<>();
    for (UnitObject unidad : unidades) {
      listaReturn.add(new SelectItemVO(unidad.getUnidadOrganica(), unidad.getNombre()));
    }

    LOG.info("[FIN] Salimos de autocompleteCodigoDIR. Total a mostrar: " + listaReturn.size());

    return listaReturn;
  }

}
