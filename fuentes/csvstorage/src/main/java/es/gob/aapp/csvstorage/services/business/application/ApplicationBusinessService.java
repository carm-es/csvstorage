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

package es.gob.aapp.csvstorage.services.business.application;

import java.util.List;
import es.gob.aapp.csvstorage.model.object.application.ApplicationObject;
import es.gob.aapp.csvstorage.services.exception.ServiceException;


/**
 * Servicios business para aplicaciones consumidoras.
 * 
 * @author carlos.munoz1
 * 
 */
public interface ApplicationBusinessService {

  /**
   * Servicio de b�squeda de todas las aplicaciones consumidoras dadas de alta.
   *
   * @return List<UnitObject>
   * @throws ServiceException the service exception
   */
  List<ApplicationObject> findAll() throws ServiceException;

  /**
   * Servicio para guardar los datos de las aplicaciones consumidoras. Crea una nueva aplicaci�n si
   * no existe, actualiza si existe previamente.
   *
   * @param aplicacion UnitObject
   * @return UnitObject
   * @throws ServiceException the service exception
   */
  ApplicationObject save(ApplicationObject aplicacion) throws ServiceException;

  /**
   * Delete.
   *
   * @param aplicacion the aplicacion
   * @throws ServiceException the service exception
   */
  void delete(ApplicationObject aplicacion) throws ServiceException;


  /**
   * Find by id aplicacion.
   *
   * @param idAplicacion the id aplicacion
   * @return the application object
   * @throws ServiceException the service exception
   */
  ApplicationObject findByIdAplicacion(String idAplicacion) throws ServiceException;

  /**
   * Obtener la lista de aplicaciones por número de serie
   * 
   * @param numSerie
   * @return
   * @throws ServiceException
   */
  List<ApplicationObject> findBySerialNumber(String numSerie) throws ServiceException;

}
