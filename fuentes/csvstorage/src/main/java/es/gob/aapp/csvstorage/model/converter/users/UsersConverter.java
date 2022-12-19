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

package es.gob.aapp.csvstorage.model.converter.users;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import es.gob.aapp.csvstorage.dao.entity.users.UserEntity;
import es.gob.aapp.csvstorage.model.object.users.UserObject;


/**
 * Convertidor para unidades orgánicas.
 * 
 * @author serena.plaza
 * 
 */
public abstract class UsersConverter {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(UsersConverter.class);

  /** Formato de fecha del dir3. */
  public static final String FORMATO_FECHA = "dd/MM/yyyy";

  /**
   * Convertidor de entities a objetos del model.
   * 
   * @param entity UnitEntity
   * @return UnitObject
   */
  public static UserObject userEntityToModel(UserEntity entity) {

    UserObject model = null;

    if (entity != null) {
      model = new UserObject();
      model.setId(entity.getId());
      model.setIsAdmin(entity.getIsAdmin());
      model.setPassword(entity.getPassword());
      model.setUsuario(entity.getUsuario());
    }

    return model;
  }

  /**
   * Convertidor de lista de entidades a lista de modelo. Método sobrecargado.
   * 
   * @param iterable the entities
   * @return List<UnitObject>
   */
  public static List<UserObject> userEntityToModel(Iterable<UserEntity> iterable) {
    LOG.debug("[INI] unitEntityToModel --> convertidor de listas");
    List<UserObject> listaReturn = new ArrayList<UserObject>();
    for (UserEntity unitEntity : iterable) {
      listaReturn.add(userEntityToModel(unitEntity));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] unitEntityToModel --> convertidor de listas");
    return listaReturn;
  }

  /**
   * Convertidor de objetos modelo a entidades.
   * 
   * @param model UserObject
   * @return entidad UserEntity
   */
  public static UserEntity userModelToEntity(UserObject model) {

    UserEntity entity = null;

    if (model != null) {
      entity = new UserEntity();
      entity.setId(model.getId());
      entity.setIsAdmin(model.getIsAdmin());
      entity.setPassword(model.getPassword());
      entity.setUsuario(model.getUsuario());
    }

    return entity;
  }

  /**
   * Convertidor de lista de objetos a lista de entities. Método sobrecargado.
   * 
   * @param entity List<<UserEntity>
   * @return List<UserObject>
   */
  public static List<UserEntity> userModelToEntity(List<UserObject> listaObjetos) {
    LOG.debug("[INI] userModelToEntity --> convertidor de listas");
    List<UserEntity> listaReturn = new ArrayList<UserEntity>();
    for (UserObject userAux : listaObjetos) {
      listaReturn.add(userModelToEntity(userAux));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] userModelToEntity --> convertidor de listas");
    return listaReturn;
  }

}
