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

package es.gob.aapp.csvstorage.services.manager.document.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.document.DocumentEniManagerService;

/**
 * Implementación de los servicios manager para aplicaciones consumidoras de los servicios web.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("documentEniManagerService")
@PropertySource("classpath:query.properties")
public class DocumentEniManagerServiceImpl implements DocumentEniManagerService {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(DocumentEniManagerServiceImpl.class);

  @Autowired
  EntityManager entityManager;

  private BaseRepository<DocumentEniEntity, Long> documentEniRepository;

  @Value("${DocumentEni.findDocumentEniByCvsOrIdEni}")
  private String queryFindDocumentEniByCvsOrIdEni;

  @PostConstruct
  private void configure() {
    documentEniRepository =
        new BaseRepositoryImpl<DocumentEniEntity, Long>(DocumentEniEntity.class, entityManager);
  }

  @Override
  public List<DocumentEniEntity> findById(String identificador) throws ServiceException {

    LOG.debug("Entramos en findById con identificador = " + identificador);

    List<DocumentEniEntity> result = null;

    try {
      DocumentEniEntity entity = new DocumentEniEntity();
      entity.setIdentificador(identificador);
      result = documentEniRepository.findListBy(entity);
    } catch (NoSuchMethodException e) {
      LOG.error("Error al buscar el informe. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    } catch (SecurityException e) {
      LOG.error("Error al buscar el informe. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    } catch (IllegalAccessException e) {
      LOG.error("Error al buscar el informe. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("Error al buscar el informe. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    } catch (InvocationTargetException e) {
      LOG.error("Error al buscar el informe. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    }

    return result;
  }

  @Override
  @Transactional
  public DocumentEniEntity create(DocumentEniEntity entity) throws ServiceException {

    LOG.debug("Entramos en create. ");

    return (DocumentEniEntity) documentEniRepository.save(entity);
  }

  @Override
  @Transactional
  public void delete(DocumentEniEntity entity) throws ServiceException {
    LOG.debug("Entramos en delete. ");

    documentEniRepository.delete(entity.getId());
  }

  @Override
  public List<DocumentEniEntity> findByDocument(Long id, String csv, String dir3)
      throws ServiceException {
    LOG.debug("Entramos en findByIdDocument con Document");

    List<DocumentEniEntity> result = null;

    try {
      DocumentEniEntity entity = new DocumentEniEntity();
      entity.setDocumento(new DocumentEntity());
      entity.getDocumento().setId(id);
      entity.getDocumento().setCsv(csv);
      if (dir3 != null) {
        entity.getDocumento().setUnidadOrganica(new UnitEntity());
        entity.getDocumento().getUnidadOrganica().setUnidadOrganica(dir3);
      }
      result = documentEniRepository.findListBy(entity);
    } catch (Exception e) {
      LOG.error("Error al buscar el informe. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    }
    return result;
  }


  @Override
  public List<DocumentEniEntity> findByAll(String identifier, String csv, String dir3)
      throws ServiceException {
    LOG.debug("Entramos en findByAll");

    List<DocumentEniEntity> result = null;

    try {
      DocumentEniEntity entity = new DocumentEniEntity();
      entity.setIdentificador(identifier);
      if (!StringUtils.isEmpty(csv) || !StringUtils.isEmpty(dir3)) {
        entity.setDocumento(new DocumentEntity());
        if (!StringUtils.isEmpty(csv)) {
          entity.getDocumento().setCsv(csv);
        }

        if (dir3 != null) {
          entity.getDocumento().setUnidadOrganica(new UnitEntity());
          entity.getDocumento().getUnidadOrganica().setUnidadOrganica(dir3);
        }

      }

      result = documentEniRepository.findListBy(entity);

    } catch (Exception e) {
      LOG.error("Error al buscar el informe. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    }
    return result;
  }

  @Override
  public List<DocumentEniEntity> existDocument(String dir3, String csv, String idEni)
      throws ServiceException {

    LOG.debug("Entramos en existDocument con dir3= " + dir3 + ", csv= " + csv + ", idEni=" + idEni);
    List<DocumentEniEntity> result = null;

    try {
      DocumentEniEntity entity = new DocumentEniEntity();
      entity.setDocumento(new DocumentEntity());
      if (dir3 != null) {
        entity.getDocumento().setUnidadOrganica(new UnitEntity());
        entity.getDocumento().getUnidadOrganica().setUnidadOrganica(dir3);
      }
      entity.setIdentificador(idEni);

      // Si se han informado el csv y el idEni, se busca que no exista un documento con ese csv o
      // ese idEni
      if (StringUtils.isNotEmpty(csv) && StringUtils.isNotEmpty(idEni)) {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("dir3", dir3);
        data.put("csv", csv);
        data.put("idEni", idEni);

        List<Object> resultList =
            documentEniRepository.findListByQuery(queryFindDocumentEniByCvsOrIdEni, data, 2);

        if (resultList.size() > 0) {
          result = new ArrayList<DocumentEniEntity>();
          for (Object document : resultList) {
            result.add((DocumentEniEntity) document);
          }
        }

      } else {
        entity.getDocumento().setCsv(csv);
        entity.getDocumento().setIdEni(idEni);
        result = documentEniRepository.findListBy(entity);
      }

    } catch (Exception e) {
      LOG.error("Error al buscar el documento. ", e);
      throw new ServiceException("Error al buscar el documento. ", e);
    }

    return result;
  }


}
