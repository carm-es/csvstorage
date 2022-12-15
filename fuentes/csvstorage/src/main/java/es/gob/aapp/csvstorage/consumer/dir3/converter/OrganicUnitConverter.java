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

package es.gob.aapp.csvstorage.consumer.dir3.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import es.gob.aapp.csvstorage.consumer.dir3.model.Unidad;
import es.gob.aapp.csvstorage.consumer.dir3.model.Unidades;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.model.object.unit.UnitObject;
import es.gob.aapp.csvstorage.util.constants.Constants;


/**
 * Convertidor de unidades recibidas de los web services del dir3 a objetos de la aplicaci�n.
 * 
 * @author serena.plaza
 * 
 */
public class OrganicUnitConverter {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(OrganicUnitConverter.class);

  /**
   * Convierte una lista de unidades recuperadas del ws a una lista de objetos.
   * 
   * @param unidades Unidades
   * @return List<UnitObject>
   * @throws ConsumerWSException
   */
  public static List<UnitObject> organicUnitToObject(Unidades unidades) throws ConsumerWSException {
    List<UnitObject> listaRetorno = new ArrayList<UnitObject>();
    if (unidades != null && CollectionUtils.isNotEmpty(unidades.getUnidad())) {
      for (Unidad unidadAux : unidades.getUnidad()) {
        listaRetorno.add(organicUnitToObject(unidadAux));
      }
    }
    return listaRetorno;
  }

  /**
   * Convierte una unidad recuperada del ws a un objeto.
   * 
   * @param unidad Unidad
   * @return UnitObject
   * @throws ConsumerWSException
   */
  public static UnitObject organicUnitToObject(Unidad unidad) throws ConsumerWSException {

    UnitObject retorno = new UnitObject();

    retorno.setUnidadOrganica(unidad.getCodigo());
    retorno.setNombre(unidad.getDenominacion());
    retorno.setEstado(unidad.getEstado().value());
    retorno.setNivelAdministracion(unidad.getNivelAdministracion());
    retorno.setNivelJerarquico(unidad.getNivelJerarquico());
    retorno.setCodigoUnidadSuperior(unidad.getCodUnidadSuperior());
    retorno.setNombreUnidadSuperior(unidad.getDenomUnidadSuperior());
    retorno.setCodigoUnidadRaiz(unidad.getCodUnidadRaiz());
    retorno.setNombreUnidadRaiz(unidad.getDenomUnidadRaiz());

    SimpleDateFormat tmpFormat = new SimpleDateFormat(Constants.FORMATO_FECHA);

    try {
      if (unidad.getFechaAltaOficial() != null) {
        retorno.setFechaAlta(tmpFormat.parse(unidad.getFechaAltaOficial()));
      }
      if (unidad.getFechaBajaOficial() != null) {
        retorno.setFechaBaja(tmpFormat.parse(unidad.getFechaBajaOficial()));
      }
      if (unidad.getFechaExtincion() != null) {
        retorno.setFechaExtincion(tmpFormat.parse(unidad.getFechaExtincion()));
      }
      if (unidad.getFechaAnulacion() != null) {
        retorno.setFechaAnulacion(tmpFormat.parse(unidad.getFechaAnulacion()));
      }

    } catch (ParseException e) {
      LOG.error("Error en el parseo de fechas. ", e);
      throw new ConsumerWSException("Error en el parseo de fechas. ", e);
    }


    retorno.setFechaCreacion(new Date());



    return retorno;
  }

}
