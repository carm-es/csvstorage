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

package es.gob.aapp.csvstorage.services.manager.audit;

import java.util.List;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.audit.AuditParamEntity;
import es.gob.aapp.csvstorage.dao.entity.audit.QueryAuditEntity;
import es.gob.aapp.csvstorage.services.exception.ServiceException;

/**
 * Servicios manager para aplicaciones consumidoras de los servicios web.
 * 
 * @author carlos.munoz1
 *
 */
public interface AuditManagerService {

  /**
   * Servicio de búsqueda de auditoria de las consultas.
   * 
   * @param csv CSV
   * @param dir3 Ambito de la aplicación
   * @param idENI Identificador ENI
   * @return Lista de documentos
   * @throws ServiceException error
   */
  List<QueryAuditEntity> findAll() throws ServiceException;

  /**
   * Servicio de búsqueda de los parámetros de una auditoría
   * 
   * @param auditoria
   * @return
   * @throws ServiceException
   */
  public List<AuditParamEntity> findAuditParamByAudit(QueryAuditEntity auditoria)
      throws ServiceException;

  /**
   * 
   * @param aplicacion
   * @param idDocumento
   * @param parametrosAuditoria
   * @param operacion
   * @return
   */
  public QueryAuditEntity create(ApplicationEntity aplicacion, Long idDocumento,
      String parametrosAuditoria, String operacion);


}
