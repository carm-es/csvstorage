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

package es.gob.aapp.csvstorage.model.converter.cmis;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import es.gob.aapp.csvstorage.dao.entity.cmis.PropAdditObjectCmisEntity;
import es.gob.aapp.csvstorage.dao.entity.cmis.PropertiesTypeCmisEntity;
import es.gob.aapp.csvstorage.dao.entity.cmis.TypeCmisEntity;
import es.gob.aapp.csvstorage.model.object.cmis.PropAdditObjectCmisObject;
import es.gob.aapp.csvstorage.model.object.cmis.PropertiesTypeCmisObject;
import es.gob.aapp.csvstorage.model.object.cmis.TypeCmisObject;

/**
 * Clase converter para objectos cmis.
 * 
 * @author carlos.munoz1
 * 
 */
public abstract class PropertiesCmisConverter {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(PropertiesCmisConverter.class);

  /**
   * Convertidor de entities a objetos del model.
   * 
   * @param entity ApplicationEntity
   * @return UnitObject
   */
  public static TypeCmisObject typeCmisEntityToModel(TypeCmisEntity entity) {

    TypeCmisObject model = null;

    if (entity != null) {
      model = new TypeCmisObject();
      model.setId(entity.getId());
      model.setName(entity.getName());
      model.setDescription(entity.getDescription());
      model.setBaseType(entity.getBaseType());
      model.setType(entity.getType());
      model.setVersion(entity.getVersion());

    }

    return model;
  }

  /**
   * Convertidor de lista de entidades a lista de modelo. Método sobrecargado.
   * 
   * @param entities List<TypeCmisEntity>
   * @return List<TypeCmisObject>
   */
  public static List<TypeCmisObject> typeCmisEntityToModel(List<TypeCmisEntity> entities) {
    LOG.debug("[INI] typeCmisEntityToModel --> convertidor de listas");
    List<TypeCmisObject> listaReturn = new ArrayList<TypeCmisObject>();
    for (TypeCmisEntity typeCmisEntity : entities) {
      listaReturn.add(typeCmisEntityToModel(typeCmisEntity));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] typeCmisEntityToModel --> convertidor de listas");
    return listaReturn;
  }

  /**
   * Convertidor de objetos modelo a entidades.
   * 
   * @param model UnitObject
   * @return entidad ApplicationEntity
   */
  public static TypeCmisEntity typeCmisModelToEntity(TypeCmisObject model) {

    TypeCmisEntity entity = null;

    if (model != null) {
      entity = new TypeCmisEntity();
      entity.setId(model.getId());
      entity.setName(model.getName());
      entity.setDescription(model.getDescription());
      entity.setBaseType(model.getBaseType());
      entity.setType(model.getType());
      entity.setVersion(model.getVersion());

    }

    return entity;
  }

  /**
   * Convertidor de lista de objetos a lista de entities. Método sobrecargado.
   * 
   * @param entities List<TypeCmisObject>
   * @return List<UnitObject>
   */
  public static List<TypeCmisEntity> typeCmisModelToEntity(List<TypeCmisObject> entities) {
    LOG.debug("[INI] typeCmisEntityToModel --> convertidor de listas");
    List<TypeCmisEntity> listaReturn = new ArrayList<TypeCmisEntity>();
    for (TypeCmisObject typeCmisObject : entities) {
      listaReturn.add(typeCmisModelToEntity(typeCmisObject));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] typeCmisModelToEntity --> convertidor de listas");
    return listaReturn;
  }

  // **********************************



  /**
   * Convertidor de entities a objetos del model.
   * 
   * @param entity ApplicationEntity
   * @return UnitObject
   */
  public static PropertiesTypeCmisObject propAdditTypeEntityToModel(
      PropertiesTypeCmisEntity entity) {

    PropertiesTypeCmisObject model = null;

    if (entity != null) {
      model = new PropertiesTypeCmisObject();
      model.setId(entity.getId());
      model.setName(entity.getName());
      model.setDescription(entity.getDescription());
      model.setIdProperty(entity.getIdProperty());
      model.setType(typeCmisEntityToModel(entity.getType()));
      model.setDatatype(entity.getDatatype());
      model.setCardinality(entity.getCardinality());
      model.setInherited(entity.getInherited());
      model.setOrderable(entity.getOrderable());
      model.setQueryable(entity.getQueryable());
      model.setRequired(entity.getRequired());
      model.setUpdateability(entity.getUpdateability());
    }

    return model;
  }

  /**
   * Convertidor de lista de entidades a lista de modelo. Método sobrecargado.
   * 
   * @param entities List<TypeCmisEntity>
   * @return List<TypeCmisObject>
   */
  public static List<PropertiesTypeCmisObject> propAdditTypeEntityToModel(
      List<PropertiesTypeCmisEntity> entities) {
    LOG.debug("[INI] propAdditTypeEntityToModel --> convertidor de listas");
    List<PropertiesTypeCmisObject> listaReturn = new ArrayList<PropertiesTypeCmisObject>();
    for (PropertiesTypeCmisEntity propAdditTypeCmisEntity : entities) {
      listaReturn.add(propAdditTypeEntityToModel(propAdditTypeCmisEntity));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] propAdditTypeEntityToModel --> convertidor de listas");
    return listaReturn;
  }

  /**
   * Convertidor de objetos modelo a entidades.
   * 
   * @param model UnitObject
   * @return entidad ApplicationEntity
   */
  public static PropertiesTypeCmisEntity propAdditTypeCmisModelToEntity(
      PropertiesTypeCmisObject model) {

    PropertiesTypeCmisEntity entity = null;

    if (model != null) {
      entity = new PropertiesTypeCmisEntity();
      entity.setId(model.getId());
      entity.setName(model.getName());
      entity.setDescription(model.getDescription());
      entity.setIdProperty(model.getIdProperty());
      entity.setType(typeCmisModelToEntity(model.getType()));
      entity.setDatatype(model.getDatatype());
      entity.setCardinality(model.getCardinality());
      entity.setInherited(model.getInherited());
      entity.setOrderable(model.getOrderable());
      entity.setQueryable(model.getQueryable());
      entity.setRequired(model.getRequired());
      entity.setUpdateability(model.getUpdateability());

    }

    return entity;
  }

  /**
   * Convertidor de lista de objetos a lista de entities. Método sobrecargado.
   * 
   * @param entities List<<ApplicationEntity>
   * @return List<UnitObject>
   */
  public static List<PropertiesTypeCmisEntity> propAdditTypeCmisModelToEntity(
      List<PropertiesTypeCmisObject> entities) {
    LOG.debug("[INI] typeCmisEntityToModel --> convertidor de listas");
    List<PropertiesTypeCmisEntity> listaReturn = new ArrayList<PropertiesTypeCmisEntity>();
    for (PropertiesTypeCmisObject propAdditTypeCmisObject : entities) {
      listaReturn.add(propAdditTypeCmisModelToEntity(propAdditTypeCmisObject));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] typeCmisModelToEntity --> convertidor de listas");
    return listaReturn;
  }

  // **********************************

  /**
   * Convertidor de entities a objetos del model.
   * 
   * @param entity ApplicationEntity
   * @return UnitObject
   */
  public static PropAdditObjectCmisObject propAdditDocumentEntityToModel(
      PropAdditObjectCmisEntity entity) {

    PropAdditObjectCmisObject model = null;

    if (entity != null) {
      model = new PropAdditObjectCmisObject();
      model.setId(entity.getId());
      model.setProperty(propAdditTypeEntityToModel(entity.getProperty()));
      model.setUuid(entity.getUuid());
      model.setValue(entity.getValue());
    }

    return model;
  }

  /**
   * Convertidor de lista de entidades a lista de modelo. Método sobrecargado.
   * 
   * @param entities List<PropAdditDocumentCmisEntity>
   * @return List<PropAdditDocumentCmisObject>
   */
  public static List<PropAdditObjectCmisObject> propAdditDocumentEntityToModel(
      List<PropAdditObjectCmisEntity> entities) {
    LOG.debug("[INI] propAdditDocumentEntityToModel --> convertidor de listas");
    List<PropAdditObjectCmisObject> listaReturn = new ArrayList<PropAdditObjectCmisObject>();
    for (PropAdditObjectCmisEntity propAdditDocCmisEntity : entities) {
      listaReturn.add(propAdditDocumentEntityToModel(propAdditDocCmisEntity));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] propAdditDocumentEntityToModel --> convertidor de listas");
    return listaReturn;
  }

  /**
   * Convertidor de objetos modelo a entidades.
   * 
   * @param model UnitObject
   * @return entidad ApplicationEntity
   */
  public static PropAdditObjectCmisEntity propAdditDocumentCmisModelToEntity(
      PropAdditObjectCmisObject model) {

    PropAdditObjectCmisEntity entity = null;

    if (model != null) {
      entity = new PropAdditObjectCmisEntity();
      entity.setId(model.getId());
      entity.setProperty(propAdditTypeCmisModelToEntity(model.getProperty()));
      entity.setUuid(model.getUuid());
      entity.setValue(model.getValue());

    }

    return entity;
  }

  /**
   * Convertidor de lista de objetos a lista de entities. Método sobrecargado.
   * 
   * @param model List<<ApplicationEntity>
   * @return List<UnitObject>
   */
  public static List<PropAdditObjectCmisEntity> propAdditDocumentCmisModelToEntity(
      List<PropAdditObjectCmisObject> model) {
    LOG.debug("[INI] typeCmisEntityToModel --> convertidor de listas");
    List<PropAdditObjectCmisEntity> listaReturn = new ArrayList<PropAdditObjectCmisEntity>();
    for (PropAdditObjectCmisObject propAdditDocCmisObject : model) {
      listaReturn.add(propAdditDocumentCmisModelToEntity(propAdditDocCmisObject));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] typeCmisModelToEntity --> convertidor de listas");
    return listaReturn;
  }
}
