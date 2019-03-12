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

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniOrganoEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.document.DocumentEniOrganoManagerService;

/**
 * Implementación de los servicios manager para aplicaciones consumidoras de los servicios web.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("documentEniOrganoManagerService")
public class DocumentEniOrganoManagerServiceImpl implements DocumentEniOrganoManagerService {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(DocumentEniOrganoManagerServiceImpl.class);

  @Autowired
  EntityManager entityManager;

  private BaseRepository<DocumentEniOrganoEntity, Long> documentEniOrganoRepository;

  @PostConstruct
  private void configure() {
    documentEniOrganoRepository = new BaseRepositoryImpl<DocumentEniOrganoEntity, Long>(
        DocumentEniOrganoEntity.class, entityManager);
  }

  @Override
  public List<DocumentEniOrganoEntity> findByDocument(Long idDocument) throws ServiceException {

    LOG.debug("Entramos en findByDocument con idDocument = " + idDocument);

    List<DocumentEniOrganoEntity> result = null;

    try {
      DocumentEniOrganoEntity entity = new DocumentEniOrganoEntity();
      DocumentEniEntity documentEntity = new DocumentEniEntity();
      documentEntity.setId(idDocument);
      entity.setDocumentoEni(documentEntity);
      result = documentEniOrganoRepository.findListBy(entity);
    } catch (Exception e) {
      LOG.error("Error al buscar el informe. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    }

    return result;
  }

  @Override
  @Transactional
  public DocumentEniOrganoEntity create(DocumentEniOrganoEntity entity) throws ServiceException {

    LOG.debug("Entramos en create. ");

    return (DocumentEniOrganoEntity) documentEniOrganoRepository.save(entity);
  }

  @Override
  @Transactional
  public List<DocumentEniOrganoEntity> createOrganos(List<DocumentEniOrganoEntity> entityList)
      throws ServiceException {

    LOG.debug("Entramos en create. ");

    return (List<DocumentEniOrganoEntity>) documentEniOrganoRepository.save(entityList);
  }

  @Override
  @Transactional
  public void delete(DocumentEniOrganoEntity entity) throws ServiceException {
    LOG.debug("Entramos en delete. ");

    documentEniOrganoRepository.delete(entity.getId());
  }

  @Override
  @Transactional
  public void deleteOrganos(List<DocumentEniOrganoEntity> entityList) throws ServiceException {
    LOG.debug("Entramos en delete. ");

    documentEniOrganoRepository.delete(entityList);
  }


}
