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

package es.gob.aapp.csvstorage.services.manager.unit;

import java.util.List;
import es.gob.aapp.csvstorage.model.object.unit.UnitObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.services.exception.ServiceException;

// TODO: Auto-generated Javadoc
/**
 * Servicios manager para aplicaciones consumidoras de los servicios web.
 * 
 * @author carlos.munoz1
 *
 */
public interface UnitManagerService {

  /**
   * Servicio de búsqueda de la unidades orgánicas.
   * 
   * @return Lista de unidades
   * @throws ServiceException error
   */
  List<UnitEntity> findAll() throws ServiceException;

  /**
   * Servicio de búsqueda de la unidad orgánica por dir3.
   *
   * @param dir3 dir3
   * @return unidad
   * @throws ServiceException the service exception
   */
  UnitEntity findByDir3(String dir3) throws ServiceException;

  /**
   * Servicio de búsqueda de la unidad orgánica por dir3.
   *
   * @param dir3 dir3
   * @return unidad
   * @throws ServiceException the service exception
   */
  UnitEntity findById(Long id) throws ServiceException;

  /**
   * Servicio de búsqueda de las unidades orgánicas por dir3. Ahora hemos eliminado la constraint de
   * unidad_organica por lo que findByDir3 puede fallar.
   *
   * @param dir3 dir3
   * @return unidad
   * @throws ServiceException the service exception
   */
  List<UnitEntity> findUnitsByDir3(String dir3) throws ServiceException;

  /**
   * Servicio para crear una unidad.
   *
   * @param entity the entity
   * @return the unit entity
   * @throws ServiceException the service exception
   */
  UnitEntity saveUnit(UnitEntity entity) throws ServiceException;

  /**
   * Servicio para modificar los datos de una unidad existente.
   *
   * @param unitEntity the unit entity
   * @return the unit entity
   * @throws ServiceException the service exception
   */
  UnitEntity updateUnit(UnitEntity entity) throws ServiceException;


  /**
   * Find units by dir3and ambito.
   *
   * @param dir3 the dir3
   * @param ambito the ambito
   * @return the list
   * @throws ServiceException the service exception
   */
  List<UnitEntity> findUnitsByDir3andAmbito(String dir3, String ambito) throws ServiceException;

  /**
   * Listado de unidades.
   * 
   * @return unidades organicas guardadas
   */
  List<UnitEntity> getAllUnits();

  /**
   * Listado de unidades Pageables.
   * 
   * @return unidades organicas guardadas
   */
  Page getAllUnitsPageable(Pageable pageable);

  /**
   * Listado de unidades Pageables usando el buscador.
   * 
   * @return unidades organicas guardadas
   */
  Page getAllUnitsPageableByCodigoOrNombre(Pageable pageable, String codigo, String nombre);

  void deleteById(Long id);

  List<UnitEntity> findLikeNombre(String nombre);
}
