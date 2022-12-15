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

package es.gob.aapp.csvstorage.model.converter.unit;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.model.object.unit.UnitObject;

/**
 * Clase converter para unidade org�nicas.
 * 
 * @author carlos.munoz1
 * 
 */
public abstract class UnitConverter {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(UnitConverter.class);

  /**
   * Convertidor de entities a objetos del model.
   * 
   * @param entity UnitEntity
   * @return UnitObject
   */
  public static UnitObject unitEntityToModel(UnitEntity entity) {

    UnitObject model = null;

    if (entity != null) {
      model = new UnitObject();
      model.setId(entity.getId());
      model.setUnidadOrganica(entity.getUnidadOrganica());
      model.setCodigoSia(entity.getCodigoSia());
      model.setAmbito(entity.getAmbito());
      model.setNombre(entity.getNombre());
      model.setEstado(entity.getEstado());
      model.setNivelAdministracion(entity.getNivelAdministracion());
      model.setNivelJerarquico(entity.getNivelJerarquico());
      model.setCodigoUnidadSuperior(entity.getCodigoUnidadSuperior());
      model.setNombreUnidadSuperior(entity.getNombreUnidadSuperior());
      model.setCodigoUnidadRaiz(entity.getCodigoUnidadRaiz());
      model.setNombreUnidadRaiz(entity.getNombreUnidadRaiz());
      model.setFechaAlta(entity.getFechaAlta());
      model.setFechaBaja(entity.getFechaBaja());
      model.setFechaExtincion(entity.getFechaExtincion());
      model.setFechaAnulacion(entity.getFechaAnulacion());
      model.setFechaCreacion(entity.getFechaCreacion());
    }

    return model;
  }

  /**
   * Convertidor de lista de entidades a lista de modelo. M�todo sobrecargado.
   * 
   * @param entity List<UnitEntity>
   * @return List<UnitObject>
   */
  public static List<UnitObject> unitEntityToModel(List<UnitEntity> entities) {
    LOG.debug("[INI] UnitEntityToModel --> convertidor de listas");
    List<UnitObject> listaReturn = new ArrayList<UnitObject>();
    for (UnitEntity unitEntity : entities) {
      listaReturn.add(unitEntityToModel(unitEntity));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] UnitEntityToModel --> convertidor de listas");
    return listaReturn;
  }

  /**
   * Convertidor de objetos modelo a entidades.
   * 
   * @param model UnitObject
   * @return entidad UnitEntity
   */
  public static UnitEntity unitModelToEntity(UnitObject model) {

    UnitEntity entity = null;

    if (model != null) {
      entity = new UnitEntity();
      entity.setId(model.getId());
      entity.setUnidadOrganica(model.getUnidadOrganica());;
      entity.setCodigoSia(model.getCodigoSia());
      entity.setAmbito(model.getAmbito());
      entity.setNombre(model.getNombre());
      entity.setEstado(model.getEstado());
      entity.setNivelAdministracion(model.getNivelAdministracion());
      entity.setNivelJerarquico(model.getNivelJerarquico());
      entity.setCodigoUnidadSuperior(model.getCodigoUnidadSuperior());
      entity.setNombreUnidadSuperior(model.getNombreUnidadSuperior());
      entity.setCodigoUnidadRaiz(model.getCodigoUnidadRaiz());
      entity.setNombreUnidadRaiz(model.getNombreUnidadRaiz());
      entity.setFechaAlta(model.getFechaAlta());
      entity.setFechaBaja(model.getFechaBaja());
      entity.setFechaExtincion(model.getFechaExtincion());
      entity.setFechaAnulacion(model.getFechaAnulacion());
      entity.setFechaCreacion(model.getFechaCreacion());
    }

    return entity;
  }

  /**
   * Convertidor de lista de objetos a lista de entities. M�todo sobrecargado.
   * 
   * @param entity List<<UnitEntity>
   * @return List<UnitObject>
   */
  public static List<UnitEntity> unitModelToEntity(List<UnitObject> listaObjetos) {
    LOG.debug("[INI] applicationModelToEntity --> convertidor de listas");
    List<UnitEntity> listaReturn = new ArrayList<UnitEntity>();
    for (UnitObject unitAux : listaObjetos) {
      listaReturn.add(unitModelToEntity(unitAux));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] unitModelToEntity --> convertidor de listas");
    return listaReturn;
  }

}
