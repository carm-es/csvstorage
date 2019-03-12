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

package es.gob.aapp.csvstorage.services.manager.document;

import java.util.List;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.services.exception.ServiceException;

/**
 * Servicios manager para aplicaciones consumidoras de los servicios web.
 * 
 * @author carlos.munoz1
 *
 */
public interface DocumentEniManagerService {

  /**
   * Servicio de b�squeda de informes por csv.
   * 
   * @param csv
   * @param organo
   * @return SignReportEntity
   * @throws ServiceException
   */
  List<DocumentEniEntity> findById(String identificador) throws ServiceException;


  List<DocumentEniEntity> findByDocument(Long id, String csv, String dir3) throws ServiceException;

  List<DocumentEniEntity> findByAll(String identificador, String csv, String dir3)
      throws ServiceException;

  List<DocumentEniEntity> existDocument(String dir3, String csv, String idEni)
      throws ServiceException;

  /**
   * Servicio para crear un informe de firma.
   * 
   * @param entity
   * @return
   * @throws ServiceException
   */
  DocumentEniEntity create(DocumentEniEntity entity) throws ServiceException;

  /**
   * Servicio para eliminar un informe de firma.
   * 
   * @param entity
   * @throws ServiceException
   */
  void delete(DocumentEniEntity entity) throws ServiceException;



}
