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

package es.gob.aapp.csvstorage.services.manager.audit.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.audit.AuditParamEntity;
import es.gob.aapp.csvstorage.dao.entity.audit.QueryAuditEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.audit.AuditManagerService;
import es.gob.aapp.csvstorage.util.constants.Constants;

/**
 * Implementación de los servicios manager para guardar los registros de auditoría
 * 
 * @author carlos.munoz1
 * 
 */
@Service("auditManagerService")
public class AuditManagerServiceImpl implements AuditManagerService {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(AuditManagerServiceImpl.class);

  @Autowired
  EntityManager entityManager;

  private BaseRepository<QueryAuditEntity, Long> queryAuditRepository;
  private BaseRepository<AuditParamEntity, Long> auditParamRepository;

  @PostConstruct
  private void configure() {
    queryAuditRepository =
        new BaseRepositoryImpl<QueryAuditEntity, Long>(QueryAuditEntity.class, entityManager);
    auditParamRepository =
        new BaseRepositoryImpl<AuditParamEntity, Long>(AuditParamEntity.class, entityManager);
  }


  @Override
  public List<QueryAuditEntity> findAll() throws ServiceException {

    LOG.debug("Entramos en findAll");

    List<QueryAuditEntity> result = null;

    try {

      QueryAuditEntity entity = new QueryAuditEntity();
      result = queryAuditRepository.findListBy(entity);

    } catch (Exception e) {
      LOG.error("Error al buscar los registro de auditoría. ", e);
      throw new ServiceException("Error al buscar los registro de auditoría. ", e);
    }

    return result;
  }

  @Override
  public List<AuditParamEntity> findAuditParamByAudit(QueryAuditEntity auditoria)
      throws ServiceException {

    LOG.debug("Entramos en findAuditParamByAudit");

    List<AuditParamEntity> result = null;

    try {

      AuditParamEntity entity = new AuditParamEntity();
      entity.setAuditoria(auditoria);
      result = auditParamRepository.findListBy(entity);

    } catch (Exception e) {
      LOG.error("Error al buscar los registro de auditoría. ", e);
      throw new ServiceException("Error al buscar los registro de auditoría. ", e);
    }

    return result;
  }

  @Override
  @Transactional
  public QueryAuditEntity create(ApplicationEntity aplicacion, Long idDocumento,
      String parametrosAuditoria, String operacion) {
    LOG.info("Entramos en create (QueryAuditEntity con parámetros).");

    QueryAuditEntity audit = new QueryAuditEntity();

    audit.setFecha(Calendar.getInstance().getTime());
    audit.setIdDocumento(idDocumento);
    audit.setIdAplicacion(aplicacion.getId());
    audit.setIdAplicacionTxt(aplicacion.getIdAplicacion());
    audit.setOperacion(operacion);

    queryAuditRepository.save(audit);

    // Se transformas los parámetros de auditoría y se guardan
    guardarParametrosAuditoria(audit, parametrosAuditoria);

    return audit;
  }

  private void guardarParametrosAuditoria(QueryAuditEntity auditoria, String parametrosAuditoria) {
    LOG.info("Entramos en guardarParametrosAuditoria.");
    if (parametrosAuditoria != null && !parametrosAuditoria.isEmpty()) {
      List<String[]> listParam = convertParametrosAuditoria(parametrosAuditoria);
      for (String[] reg : listParam) {
        if (reg.length == 2) {
          auditParamRepository.save(new AuditParamEntity(auditoria, reg[0], reg[1]));
        }
      }
    }

    LOG.info("Salimos de guardarParametrosAuditoria.");
  }

  private static List<String[]> convertParametrosAuditoria(String parametrosAuditoria) {
    LOG.info("convertParametrosAuditoria: " + parametrosAuditoria);
    List<String[]> listParam = new ArrayList<>();
    String[] registros = StringUtils.split(parametrosAuditoria, Constants.AUDIT_SEPARATOR_ROW);
    for (String reg : registros) {
      String[] paramValor = StringUtils.split(reg, Constants.AUDIT_SEPARATOR_VALUE);
      listParam.add(paramValor);
    }
    return listParam;
  }

}
