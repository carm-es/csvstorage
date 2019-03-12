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

package es.gob.aapp.csvstorage.services.business.unit;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import es.gob.aapp.csvstorage.consumer.dir3.model.Unidades;
import es.gob.aapp.csvstorage.model.object.unit.UnitObject;
import es.gob.aapp.csvstorage.services.exception.ServiceException;

// TODO: Auto-generated Javadoc
/**
 * Servicios business para unidades org�nicas.
 * 
 * @author carlos.munoz1
 * 
 */
public interface UnitBusinessService {

  /**
   * Servicio para guardar en base de datos la lista de unidades org�nicas.
   * 
   * @param unidades Parámetro Unidades obtenido del consumidor de Dir3.
   * @return List<UnitObject> guardada en base de datos.
   * @throws ServiceException
   */
  List<UnitObject> saveList(Unidades unidades) throws ServiceException;

  /**
   * Servicio para obtener la lista de unidades del Dir3 llamando al web service de DescargaUnidades
   * y además guarda la lista en base de datos.
   * 
   * @return número de unidades guardadas
   * 
   * @throws ServiceException
   */
  int saveOrganicUnitFromDir3() throws ServiceException;

  int saveOrganicUnitFromDir3(String date) throws ServiceException;


  /**
   * Servicio para obtener la lista de unidades del Dir3 llamando al web service de DescargaUnidades
   * y además guarda la lista en base de datos.
   * 
   * @return número de unidades guardadas
   * 
   * @throws ServiceException
   */
  int saveAllOrganicUnitFromDir3() throws ServiceException;


  void saveUnit(String unidad) throws ServiceException;

  /**
   * Búsqueda de unidades orgánicas por código.
   * 
   * @param codigo
   * @return
   * @throws ServiceException
   */
  UnitObject findByCodigo(String codigo) throws ServiceException;

  /**
   * Listar todas las unidades organicas
   * 
   * @return lista de unidades organicas guardadas
   * @throws ServiceException
   */
  List<UnitObject> getAllOrganicUnits() throws ServiceException;

  /**
   * Listar todas las unidades organicas
   * 
   * @return lista de unidades organicas guardadas
   * @throws ServiceException
   */
  Page getAllOrganicUnitsPageable(Pageable pageable) throws ServiceException;

  /**
   * Listar todas las unidades organicas by codigo or nombre
   * 
   * @return lista de unidades organicas guardadas
   * @throws ServiceException
   */
  Page getAllOrganicUnitsPageableByCodigoOrNombre(Pageable pageable, String filtro)
      throws ServiceException;

  void deleteUnit(String codigoDir3) throws ServiceException;

  List<UnitObject> findLikeNombre(String nombre);
}
