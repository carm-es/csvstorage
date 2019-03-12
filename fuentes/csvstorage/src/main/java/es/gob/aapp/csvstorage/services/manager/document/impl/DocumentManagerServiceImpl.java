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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentAplicacionEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniOrganoEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentIdsEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentNifEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentRestriccionEntity;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.model.object.document.DocumentEniObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentInfo;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.exception.ValidationException;
import es.gob.aapp.csvstorage.services.manager.document.DocumentEniManagerService;
import es.gob.aapp.csvstorage.services.manager.document.DocumentEniOrganoManagerService;
import es.gob.aapp.csvstorage.services.manager.document.DocumentManagerService;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.file.FileTransactionListener;
import es.gob.aapp.csvstorage.util.file.FileUtil;
import es.gob.aapp.csvstorage.util.xml.JAXBMarshaller;

/**
 * Implementación de los servicios manager para aplicaciones consumidoras de los servicios web.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("documentManagerService")
@PropertySource("classpath:query.properties")
public class DocumentManagerServiceImpl implements DocumentManagerService {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(DocumentManagerServiceImpl.class);
  public static final String ERROR_AL_BUSCAR_EL_DOCUMENTO = "Error al buscar el documento. ";

  @Autowired
  EntityManager entityManager;

  private BaseRepository<DocumentEntity, Long> documentRepository;

  private BaseRepository<DocumentNifEntity, Long> documentNifRepository;

  private BaseRepository<DocumentRestriccionEntity, Long> documentRestriccionRepository;

  private BaseRepository<DocumentIdsEntity, Long> documentRestriccionIdRepository;

  private BaseRepository<DocumentAplicacionEntity, Long> documentAplicacionRepository;

  @Autowired
  private DocumentEniManagerService documentEniManagerService;

  @Autowired
  private DocumentEniOrganoManagerService documentEniOrganoManagerService;

  @Value("${Document.findDocumentByCvsOrIdEni}")
  private String queryFindDocumentByCvsOrIdEni;

  public static final String FILE_SEPARATOR = "file.separator";
  private static String separator = System.getProperty(FILE_SEPARATOR);

  @PostConstruct
  private void configure() {
    documentRepository = new BaseRepositoryImpl<>(DocumentEntity.class, entityManager);
    documentNifRepository = new BaseRepositoryImpl<>(DocumentNifEntity.class, entityManager);
    documentRestriccionRepository =
        new BaseRepositoryImpl<>(DocumentRestriccionEntity.class, entityManager);
    documentRestriccionIdRepository =
        new BaseRepositoryImpl<>(DocumentIdsEntity.class, entityManager);
    documentAplicacionRepository =
        new BaseRepositoryImpl<>(DocumentAplicacionEntity.class, entityManager);
  }

  @Override
  public List<DocumentEntity> findByDocument(String csv, String dir3, String idEni)
      throws ServiceException {

    LOG.debug(
        "Entramos en findByDocument con csv = " + csv + "; dir3=" + dir3 + "; idEni=" + idEni);

    List<DocumentEntity> result;

    try {
      DocumentEntity entity = new DocumentEntity();
      entity.setCsv(csv);
      entity.setIdEni(idEni);

      if (dir3 != null) {
        entity.setUnidadOrganica(new UnitEntity());
        entity.getUnidadOrganica().setUnidadOrganica(dir3);
      }
      result = documentRepository.findListBy(entity);
    } catch (Exception e) {
      throw new ServiceException(ERROR_AL_BUSCAR_EL_DOCUMENTO, e);
    }
    LOG.info("Salimos en findByDocument con resultado = " + result);
    return result;
  }

  @Override
  public List<DocumentEntity> existDocument(String dir3, String csv, String idEni)
      throws ServiceException {

    LOG.debug("Entramos en findByOne con dir3= " + dir3 + ", csv= " + csv + ", idEni=" + idEni);
    List<DocumentEntity> result = new ArrayList<>();

    try {
      DocumentEntity entity = new DocumentEntity();

      if (dir3 != null) {
        entity.setUnidadOrganica(new UnitEntity());
        entity.getUnidadOrganica().setUnidadOrganica(dir3);
      }

      // Si se han informado el csv y el idEni, se busca que no exista un documento con ese csv o
      // ese idEni
      if (StringUtils.isNotEmpty(csv) && StringUtils.isNotEmpty(idEni)) {

        Map<String, Object> data = new HashMap<>();

        data.put("dir3", dir3);
        data.put("csv", csv);
        data.put("idEni", idEni);

        List<Object> resultList = documentRepository.findListByQuery(queryFindDocumentByCvsOrIdEni,
            data, Integer.MAX_VALUE);

        if (!resultList.isEmpty()) {
          result = new ArrayList<>();
          for (Object document : resultList) {
            result.add((DocumentEntity) document);
          }
        }

      } else {
        entity.setCsv(csv);
        entity.setIdEni(idEni);
        result = documentRepository.findListBy(entity);
      }

    } catch (Exception e) {
      LOG.error(ERROR_AL_BUSCAR_EL_DOCUMENTO, e);
      throw new ServiceException(ERROR_AL_BUSCAR_EL_DOCUMENTO, e);
    }
    LOG.debug("result.size()=" + result.size());
    return result;
  }

  @Override
  public List<DocumentEntity> findAll(String csv, String dir3, String idENI)
      throws ServiceException {

    LOG.debug("Entramos en findAll con csv = " + csv + " Dir3= " + dir3);

    List<DocumentEntity> result = null;

    try {

      DocumentEntity documentEntity = new DocumentEntity();
      documentEntity.setCsv(csv);
      if (dir3 != null) {
        documentEntity.setUnidadOrganica(new UnitEntity());
        documentEntity.getUnidadOrganica().setUnidadOrganica(dir3);
      }

      if (idENI == null || idENI.isEmpty()) {
        LOG.info("Se procede a buscar el idENI " + idENI + " - " + documentEntity);
        result = documentRepository.findListBy(documentEntity);
      } else {

        List<DocumentEniEntity> eniEntityList =
            documentEniManagerService.findByAll(idENI, csv, dir3);

        if (eniEntityList != null && !eniEntityList.isEmpty()) {
          result = new ArrayList<>();
          for (DocumentEniEntity documentEniEntity : eniEntityList) {
            result.add(documentEniEntity.getDocumento());
          }

        } else {
          result = this.findByDocument(csv, dir3, idENI);
        }

      }
    } catch (Exception e) {
      LOG.error(ERROR_AL_BUSCAR_EL_DOCUMENTO, e);
      throw new ServiceException(ERROR_AL_BUSCAR_EL_DOCUMENTO, e);
    }

    return result;
  }

  @Override
  public List<DocumentEntity> findByCreatedAt(Date createdAt) throws ServiceException {

    LOG.debug("Entramos en findByCreatedAt con createdAt = " + createdAt.toString());

    List<DocumentEntity> result = null;

    try {

      DocumentEntity documentEntity = new DocumentEntity();
      documentEntity.setCreatedAt(createdAt);

      result = documentRepository.findListBy(documentEntity);

    } catch (Exception e) {
      LOG.error(ERROR_AL_BUSCAR_EL_DOCUMENTO, e);
      throw new ServiceException(ERROR_AL_BUSCAR_EL_DOCUMENTO, e);
    }

    return result;
  }

  @Override
  @Transactional
  public DocumentEntity create(DocumentEntity entity) throws ServiceException {

    LOG.debug("Entramos en create. ");
    return documentRepository.save(entity);
  }

  @Override
  @Transactional(
      rollbackFor = {IOException.class, ServiceException.class, ValidationException.class})
  public DocumentEntity create(DocumentEntity entity, DocumentObject documentObject,
      boolean modificar) throws ServiceException, JAXBException, ValidationException {

    LOG.debug("Entramos en create [,,,]. ");

    DocumentInfo documentInfo = new DocumentInfo();
    documentInfo.setCsv(documentObject.getCsv());
    documentInfo.setIdEni(documentObject.getIdEni());
    String name = documentObject.getUuid();

    int action = FileTransactionListener.ACTION_SAVE;
    if (modificar) {
      action = FileTransactionListener.ACTION_UPDATE;
    }

    if (entity.getContenidoPorRef() == null) {
      entity.setContenidoPorRef(false);
    }

    // Iniciamos la transacionalidad de ficheros
    transactionFile(entity.getRutaFichero(), entity.getUuid(), action, entity.getContenidoPorRef());

    // Para fichero de gran tamaño en el contenido del XML (DocumentInfo) guardamos la referencia
    if (documentObject.getContenidoPorRef() != null) {
      entity.setContenidoPorRef(true);

      name = entity.getUuid() + Constants.BIG_FILE_NAME;

      FileUtil.renameFile(documentObject.getContenidoPorRef(), name);

      String refFile = Constants.REF_FILE + documentObject.getPathFile() + separator + name;
      documentInfo.setContenido(refFile.getBytes());

    } else {
      documentInfo.setContenido(documentObject.getContenido());
    }
    documentInfo.setTipoMime(documentObject.getMimeType());



    String contentStr = JAXBMarshaller.marshallRootElement(documentInfo, DocumentInfo.class);

    // Guardamos el DocumentEntity en bbdd
    documentRepository.save(entity);

    // Guadamos los NIFs con permisos sobre los documentos si existen
    guardarNifs(documentObject.getNifs(), entity, modificar);

    // Guardamos las restricciones si existen
    guardarRestricciones(documentObject.getRestricciones(), entity, modificar);

    // Guardamos las restricciones de ID si existen
    guardarRestriccionesId(documentObject.getTipoIds(), entity, modificar);

    // Guardamos las aplicaciones de consulta si existen
    guardarAplicacionesConsulta(documentObject.getAplicaciones(), entity, modificar);

    // Guardamos el documento en la NAS
    try {
      FileUtil.saveFile(entity.getRutaFichero(), entity.getUuid(), contentStr.getBytes());
    } catch (IOException e) {
      LOG.error("Se ha producido un error al guardar el fichero " + name + " en la NAS ", e);
      throw new ServiceException(e.getMessage());
    }

    LOG.info("Salimos de guardar el fichero en la NAS ");
    if (StringUtils.isNotEmpty(documentObject.getCodigoHash())
        && StringUtils.isNotEmpty(documentObject.getAlgoritmoHash())) {

      String hash;
      if (documentObject.getContenidoPorRef() != null) {
        hash = FileUtil.hashCodeStream(entity.getRutaFichero() + separator + name,
            documentObject.getAlgoritmoHash());
      } else {
        hash = FileUtil.hashCodeStream(documentObject.getContenido(),
            documentObject.getAlgoritmoHash());
      }

      if (StringUtils.isEmpty(hash) || !documentObject.getCodigoHash().equals(hash)) {
        LOG.error("El hash del documento no coincide con el enviado: " + hash);
        throw new ValidationException("El hash del documento no coincide con el enviado");
      }
    }

    LOG.info("Actualizamos el tamano del documento ");
    updateDocumentSize(entity, entity.getRutaFichero(), name);

    LOG.debug("Salimos en create [,,,]. ");

    return entity;
  }

  @Override
  @Transactional(rollbackFor = {ServiceException.class, ValidationException.class})
  public DocumentEntity createEni(DocumentEntity entity, DocumentEniObject documentEniObject,
      boolean modificar) throws ServiceException, JAXBException, ValidationException {

    DocumentInfo documentInfo = new DocumentInfo();
    documentInfo.setCsv(documentEniObject.getCsv());
    documentInfo.setIdEni(documentEniObject.getIdEni());
    documentInfo.setContenido(documentEniObject.getContenido());
    documentInfo.setTipoMime(documentEniObject.getMimeType());
    String name = entity.getUuid();

    if (entity.getContenidoPorRef() == null) {
      entity.setContenidoPorRef(false);
    }

    int action = FileTransactionListener.ACTION_SAVE;
    if (modificar) {
      action = FileTransactionListener.ACTION_UPDATE;
    }

    transactionFile(entity.getRutaFichero(), entity.getUuid(), action, false);

    // Para fichero de gran tamaño en el contenido del XML (DocumentInfo) guardamos la referencia
    if (documentEniObject.getContenidoPorRef() != null) {
      entity.setContenidoPorRef(true);

      name = entity.getUuid() + Constants.BIG_FILE_NAME;

      FileUtil.renameFile(documentEniObject.getContenidoPorRef(), name);

      String refFile = Constants.REF_FILE + entity.getRutaFichero() + separator + name;
      documentInfo.setContenido(refFile.getBytes());

    } else {
      documentInfo.setContenido(documentEniObject.getContenido());
    }
    documentInfo.setTipoMime(documentEniObject.getMimeType());


    String fileContent = JAXBMarshaller.marshallRootElement(documentInfo, DocumentInfo.class);

    // Guardamos el DocumentEntity en bbdd
    documentRepository.save(entity);

    // Guadamos los NIFs con permisos sobre los documentos si existen
    guardarNifs(documentEniObject.getNifs(), entity, modificar);

    // Guardamos las restricciones si existen
    guardarRestricciones(documentEniObject.getRestricciones(), entity, modificar);

    // Guardamos las restricciones de ID si existen
    guardarRestriccionesId(documentEniObject.getTipoIds(), entity, modificar);

    // Guardamos las aplicaciones de consulta si existen
    guardarAplicacionesConsulta(documentEniObject.getAplicaciones(), entity, modificar);

    DocumentEniEntity documentEniEntity = documentEniObject.getDocumentEniEntity();
    documentEniEntity.setDocumento(entity);

    // Guardamos el DocumentEniEntity en bbdd
    documentEniManagerService.create(documentEniEntity);

    if (modificar) {
      compararOrganos(documentEniObject, documentEniEntity);
    }

    if (documentEniObject.getOrganosList() != null) {
      // Guardamos la lista de DocumentEniOrganoEntity en bbdd
      documentEniOrganoManagerService.createOrganos(documentEniObject.getOrganosList());
    }

    // Guardamos en la NAS
    try {
      FileUtil.saveFile(entity.getRutaFichero(), entity.getUuid(), fileContent.getBytes());
    } catch (IOException e) {
      LOG.error("Se ha producido un error al guardar el fichero " + name + " en la NAS ", e);
      throw new ServiceException(e.getMessage());
    }

    if (StringUtils.isNotEmpty(documentEniObject.getCodigoHash())
        && StringUtils.isNotEmpty(documentEniObject.getAlgoritmoHash())) {

      String hash;
      if (documentEniObject.getContenidoPorRef() != null) {
        hash = FileUtil.hashCodeStream(entity.getRutaFichero() + separator + name,
            documentEniObject.getAlgoritmoHash());
      } else {
        hash = FileUtil.hashCodeStream(documentEniObject.getContenido(),
            documentEniObject.getAlgoritmoHash());
      }

      if (StringUtils.isEmpty(hash) || !documentEniObject.getCodigoHash().equals(hash)) {
        LOG.error("El hash del documento no coincide con el enviado: " + hash);
        throw new ValidationException("El hash del documento no coincide con el enviado");
      }
    }

    updateDocumentSize(entity, entity.getRutaFichero(), name);
    return entity;
  }


  private void compararOrganos(DocumentEniObject documentEniObject, DocumentEniEntity documentEnt)
      throws ServiceException {
    List<DocumentEniOrganoEntity> listEliminarOrganos = new ArrayList<>();

    // Comparamos los organos
    Set<DocumentEniOrganoEntity> listOrganosBBDD = documentEnt.getDocumentoOrganos();
    List<DocumentEniOrganoEntity> listOrganosPeticion = documentEniObject.getOrganosList();

    Iterator<DocumentEniOrganoEntity> itListBBDD = listOrganosBBDD.iterator();
    while (itListBBDD.hasNext()) {
      DocumentEniOrganoEntity organoBBDD = itListBBDD.next();
      boolean encontrado = false;
      Iterator<DocumentEniOrganoEntity> itListPeticion = listOrganosPeticion.iterator();
      DocumentEniOrganoEntity organoPeticion;
      while (itListPeticion.hasNext() && !encontrado) {
        organoPeticion = itListPeticion.next();
        if (organoBBDD.getOrgano().equals(organoPeticion.getOrgano())) {
          // ya existe, por lo tanto no hay que volver a insertarlo
          listOrganosPeticion.remove(organoPeticion);
          encontrado = true;
        }
      }

      if (!encontrado) {
        // Si el organo de bbdd no se encuentra dentro de los de la peticion se elimina
        listEliminarOrganos.add(organoBBDD);
      }
    }
    // eliminamos los organos que ya no pertenecen al documento
    if (!listEliminarOrganos.isEmpty()) {
      // Guardamos la lista de DocumentEniOrganoEntity
      documentEniOrganoManagerService.deleteOrganos(listEliminarOrganos);
    }

  }


  @Override
  @Transactional
  public void delete(DocumentEntity entity) throws ServiceException {
    LOG.debug("Entramos en delete. ");

    documentRepository.delete(entity.getId());
  }

  @Override
  @Transactional(rollbackFor = IOException.class)
  public void deleteEni(List<DocumentEntity> entityList) throws ServiceException, IOException {

    for (DocumentEntity entity : entityList) {

      // Se procede a buscar en la tabla CSVST_DOCUMENTO_ENI
      List<DocumentEniEntity> documentEniEntityList =
          documentEniManagerService.findByDocument(entity.getId(), null, null);

      transactionFile(entity.getRutaFichero(), entity.getUuid(),
          FileTransactionListener.ACTION_DELETE, entity.getContenidoPorRef());

      String uuidDocumento = null;
      if (documentEniEntityList != null && !documentEniEntityList.isEmpty()) {
        LOG.debug("Eliminamos el documento.");
        DocumentEniEntity docEnt = documentEniEntityList.get(0);

        // se procede a buscar en la tabla CSVST_DOCUMENTO_ENI_ORGANO
        List<DocumentEniOrganoEntity> organoList =
            documentEniOrganoManagerService.findByDocument(docEnt.getId());
        if (organoList != null) {
          for (DocumentEniOrganoEntity organo : organoList) {
            documentEniOrganoManagerService.delete(organo);
          }
        }

        // ------------------------------------------
        // Dicha funcionalidad solo ocurre cuando INSIDE guarda documentos grandes.
        // 1. A partir del documento eni entity, obtenemos la referencia del otro fichero
        // 2. Una vez obtenido dicha referencia, obtenemos el uuid
        // 3. A partir de uuid obtenemos su entity se procede a borrar
        String referenciaDocumento = documentEniEntityList.get(0).getReferencia();

        // compruebo que no este vacio y que se comprueba que sea una url
        if (!StringUtils.isEmpty(referenciaDocumento) && referenciaDocumento.lastIndexOf("/") > 0) {

          String[] referenciArray = referenciaDocumento.split("/");

          if (referenciArray.length > 0) {
            uuidDocumento = referenciArray[referenciArray.length - 1];
          }
        }
        // ------------------------------------------

        // Elimino en la tabla CSVST_DOCUMENTO_ENI
        documentEniManagerService.delete(docEnt);

      }

      LOG.info("BUSQUEDA DE NIF: " + entity.toString());
      // Realizamos la busqueda de NIF
      List<DocumentNifEntity> nifBbddList = findNifsByDocument(entity, null);
      if (nifBbddList != null) {
        documentNifRepository.delete(nifBbddList);
      }

      // Realizamos la busqueda de Restricciones
      List<DocumentRestriccionEntity> listRestri = findRestriccionesByDocument(entity);
      if (listRestri != null) {
        documentRestriccionRepository.delete(listRestri);
      }

      // Realizamos la busqueda de Restricciones Id
      List<DocumentIdsEntity> listRestriId = findRestriccionesIDByDocument(entity);
      if (listRestriId != null) {
        documentRestriccionIdRepository.delete(listRestriId);
      }

      // Si un documento tiene asociadas aplicaciones para consultarlo, no se elimina el documento
      // Se procede a eliminar en la tabla CSVST_DOCUMENTO
      delete(entity);


      // Si uuidDocumento obtenido por motivos de INSIDE no esta vacio y se procede a eliminarse
      if (StringUtils.isNotEmpty(uuidDocumento)) {

        // elimino en bbdd
        DocumentEntity documentoContenido = this.findByUuid(uuidDocumento);

        if (documentoContenido != null) {
          this.delete(documentoContenido);

          // elimino el fichero fisico
          deleteFile(documentoContenido);

        }

      }

      // elimino el fichero fisico
      deleteFile(entity);

    }

  }


  /**
   * Metodo que se encarga de eliminar el fichero del disco
   * 
   * @param entity
   * @throws IOException
   */
  private void deleteFile(DocumentEntity entity) throws IOException {

    LOG.info("Se procede a eliminar el fichero en disco: " + entity.toString());

    // Se procede a eliminar el fichero fisico
    if (entity.getContenidoPorRef()) {
      FileUtil.deleteFile(entity.getRutaFichero() + System.getProperty(FILE_SEPARATOR)
          + entity.getUuid() + Constants.BIG_FILE_NAME);
    }

    FileUtil.deleteFile(
        entity.getRutaFichero() + System.getProperty(FILE_SEPARATOR) + entity.getUuid());

    LOG.info("Fichero eliminado.");
  }


  private void guardarRestricciones(List<Integer> restricciones, DocumentEntity document,
      boolean modificar) throws ServiceException {

    LOG.info("Entramos en guardarRestricciones. " + restricciones);

    // En caso de ser una modificacion eliminamos las restricciones
    if (modificar) {
      List<DocumentRestriccionEntity> restriccionBbddList = findRestriccionesByDocument(document);
      if (restriccionBbddList != null) {
        documentRestriccionRepository.delete(restriccionBbddList);
      }
    }

    List<DocumentRestriccionEntity> retriccionList = new ArrayList<>();
    if (restricciones != null) {
      for (Integer restriccion : restricciones) {
        DocumentRestriccionEntity documentRestriccionEntity = new DocumentRestriccionEntity();
        documentRestriccionEntity.setDocumento(document);
        documentRestriccionEntity.setRestriccion(restriccion);

        retriccionList.add(documentRestriccionEntity);
      }
    }

    LOG.info("Lista de Restricciones: " + retriccionList);

    LOG.info("procedemos a guardar las restricciones");
    documentRestriccionRepository.save(retriccionList);


    LOG.info("Salimos en guardarRestricciones.");
  }


  private void guardarRestriccionesId(List<Integer> ids, DocumentEntity document, boolean modificar)
      throws ServiceException {

    LOG.info("Entramos en guardarRestriccionesId.");

    // En caso de ser una modificacion eliminamos las restricciones de ID
    if (modificar) {
      List<DocumentIdsEntity> restriccionIdBbddList = findRestriccionesIDByDocument(document);
      if (restriccionIdBbddList != null) {
        documentRestriccionIdRepository.delete(restriccionIdBbddList);
      }
    }

    List<DocumentIdsEntity> retriccionIdList = new ArrayList<>();
    if (ids != null) {
      for (Integer id : ids) {

        if (id != null) {

          DocumentIdsEntity documentidsEntity = new DocumentIdsEntity();
          documentidsEntity.setDocumento(document);
          documentidsEntity.setTipoId(id);

          retriccionIdList.add(documentidsEntity);
        }
      }
    }

    LOG.info("Lista de Restricciones ID: " + retriccionIdList);

    LOG.info("procedemos a guardar las restricciones Id");
    documentRestriccionIdRepository.save(retriccionIdList);


    LOG.info("Salimos en guardarRestricciones Id.");
  }

  private void guardarNifs(List<String> nifs, DocumentEntity document, boolean modificar)
      throws ServiceException {

    // En caso de ser una modificacion eliminamos los NIFs
    if (modificar) {
      List<DocumentNifEntity> nifBbddList = findNifsByDocument(document, null);
      if (nifBbddList != null) {
        documentNifRepository.delete(nifBbddList);
      }
    }

    List<DocumentNifEntity> nifList = new ArrayList<>();
    if (nifs != null) {
      for (String nif : nifs) {
        DocumentNifEntity documentNifEntity = new DocumentNifEntity();
        documentNifEntity.setDocumento(document);
        documentNifEntity.setNif(nif);
        nifList.add(documentNifEntity);
      }
    }
    documentNifRepository.save(nifList);

  }

  @Override
  public List<DocumentNifEntity> findNifsByDocument(DocumentEntity document, String nif)
      throws ServiceException {

    LOG.debug("Entramos en findByDocument con csv = " + document.getId());

    List<DocumentNifEntity> result = null;

    try {
      DocumentNifEntity entity = new DocumentNifEntity();
      entity.setDocumento(document);
      if (StringUtils.isNotEmpty(nif)) {
        entity.setNif(nif);
      }
      result = documentNifRepository.findListBy(entity);
    } catch (Exception e) {
      LOG.error("Error al buscar los NIF del documento: ", e);
      throw new ServiceException("Error al buscar los NIF del documento. ", e);
    }

    return result;
  }


  @Override
  public List<DocumentNifEntity> findNifsByDocument(DocumentEntity document)
      throws ServiceException {

    LOG.debug("Entramos en findByDocument con csv = " + document.getId());

    List<DocumentNifEntity> result = null;

    try {
      DocumentNifEntity entity = new DocumentNifEntity();
      entity.setDocumento(document);
      result = documentNifRepository.findListBy(entity);
    } catch (Exception e) {
      LOG.error("Error al buscar los NIF del documento. ", e);
      throw new ServiceException("Error al buscar los NIF del documento. ", e);
    }

    return result;
  }

  @Override
  public List<DocumentRestriccionEntity> findRestriccionesByDocument(DocumentEntity document)
      throws ServiceException {

    LOG.debug("Entramos en findRestriccionesByDocument con csv = " + document.getId());

    List<DocumentRestriccionEntity> result = null;

    try {
      DocumentRestriccionEntity entity = new DocumentRestriccionEntity();
      entity.setDocumento(document);

      result = documentRestriccionRepository.findListBy(entity);
    } catch (Exception e) {
      LOG.error("Error al buscar las Restricciones del documento. ", e);
      throw new ServiceException("Error al buscar las Restricciones del documento. ", e);
    }

    LOG.debug("Salimos de findRestriccionesByDocument con csv = " + document.getId());
    return result;
  }


  @Override
  public List<DocumentIdsEntity> findRestriccionesIDByDocument(DocumentEntity document)
      throws ServiceException {

    LOG.info("Entramos en findRestriccionesIDByDocument con id = " + document.getId());

    List<DocumentIdsEntity> result = null;
    try {
      DocumentIdsEntity entity = new DocumentIdsEntity();
      entity.setDocumento(document);

      if (entity != null) {
        LOG.info("Entramos en findRestriccionesIDByDocument " + entity);
        result = documentRestriccionIdRepository.findListBy(entity);
      }

    } catch (Exception e) {
      LOG.error("Error al buscar las Restricciones de ID del documento. ", e);
      throw new ServiceException("Error al buscar las Restricciones de ID del documento. ", e);
    }

    LOG.debug("Salimos de findRestriccionesIdByDocument con csv= " + document.getId());
    return result;
  }


  @Override
  public List<DocumentIdsEntity> findRestriccionesIDByDocument(DocumentEntity document,
      Integer tipoId) throws ServiceException {

    LOG.info("Entramos en findRestriccionesIDByDocument con csv = " + document.getId());

    List<DocumentIdsEntity> result = null;

    try {
      DocumentIdsEntity entity = new DocumentIdsEntity();

      entity.setDocumento(document);
      entity.setTipoId(tipoId);
      LOG.info("Entramos en findRestriccionesIDByDocument " + entity);
      result = documentRestriccionIdRepository.findListBy(entity);
    } catch (Exception e) {
      LOG.error("Error al buscar las Restricciones de ID del documento. ", e);
      throw new ServiceException("Error al buscar las Restricciones de ID del documento. ", e);
    }

    LOG.debug("Salimos de findRestriccionesIdByDocument con csv = " + document.getId());
    return result;
  }

  @Override
  public DocumentEntity findByUuid(String uuid) throws ServiceException {
    LOG.debug("Entramos en findByUuid con uuid = " + uuid);

    DocumentEntity result = null;

    try {

      DocumentEntity documentEntity = new DocumentEntity();
      documentEntity.setUuid(uuid);

      List<DocumentEntity> list = documentRepository.findListBy(documentEntity);

      if (list != null && !list.isEmpty()) {
        result = list.get(0);
      }

    } catch (Exception e) {
      LOG.error("Error al buscar el documento por uuid. ", e);
      throw new ServiceException(ERROR_AL_BUSCAR_EL_DOCUMENTO, e);
    }

    return result;
  }

  @Override
  @Transactional
  public void updateFileSize() throws ServiceException {
    LOG.debug("Entramos en updateFileSize. ");
    // Obtenemos todos los documentos que no tengan definido ningún tamaño, es decir, cuyo campo
    // TamanioFichero sea -1
    DocumentEntity entity = new DocumentEntity();
    entity.setTamanioFichero((long) -1);

    try {

      List<DocumentEntity> resultList = documentRepository.findListBy(entity);
      if (!resultList.isEmpty()) {
        for (DocumentEntity document : resultList) {
          updateDocumentSize(document, document.getRutaFichero(), document.getUuid());

        }
      }

    } catch (Exception e) {
      LOG.error("Se ha producido un error al obtener los ficheros sin tamaño definido. ", e);
      throw new ServiceException("Se ha producido un error al obtener los ficheros sin tamaño.", e);
    }
    LOG.debug("Salimos de updateFileSize. ");
  }

  private void updateDocumentSize(DocumentEntity document, String pathName, String uuid) {
    // Obtenemos el tamaño del fichero
    String path;
    String name;
    if (uuid != null) {
      path = pathName;
      name = uuid;
    } else if (pathName.lastIndexOf('/') > 0) {
      path = pathName.substring(0, pathName.lastIndexOf('/'));
      name = pathName.substring(pathName.lastIndexOf('/') + 1);
    } else {
      path = pathName.substring(0, pathName.lastIndexOf('\\'));
      name = pathName.substring(pathName.lastIndexOf('\\') + 1);
    }

    Long fileSize = FileUtil.getFileSize(path, name);

    if (fileSize != -1) {
      // Establecemos el tamaño del documento en Kb
      if (fileSize > 0) {
        fileSize = fileSize / 1024;
      }
      document.setTamanioFichero(fileSize);
      documentRepository.save(document);
    }

    LOG.debug("Se ha actualizado el tamaño del documento " + document.getUuid());

  }

  private void transactionFile(String pathFile, String fileName, int action, boolean contentByRef)
      throws ServiceException {

    FileTransactionListener cleanupTransactionListener =
        new FileTransactionListener(pathFile, fileName, action, contentByRef);

    TransactionSynchronizationManager.registerSynchronization(cleanupTransactionListener);
  }

  /**
   * @return the documentNifRepository
   */
  public BaseRepository<DocumentNifEntity, Long> getDocumentNifRepository() {
    return documentNifRepository;
  }

  /**
   * @param documentNifRepository the documentNifRepository to set
   */
  public void setDocumentNifRepository(
      BaseRepository<DocumentNifEntity, Long> documentNifRepository) {
    this.documentNifRepository = documentNifRepository;
  }

  @Override
  public List<DocumentAplicacionEntity> findDocumentAplicacionByDocument(DocumentEntity document)
      throws ServiceException {

    LOG.info("Entramos en findAplicacionesByDocument con idDocumento = " + document.getId());

    List<DocumentAplicacionEntity> result = null;

    try {
      DocumentAplicacionEntity entity = new DocumentAplicacionEntity();

      entity.setDocumento(document);
      result = documentAplicacionRepository.findListBy(entity);
    } catch (Exception e) {
      LOG.error("Error al buscar las Aplicaciones de consulta del documento.", e);
      throw new ServiceException("Error al buscar las Aplicaciones de consulta del documento: ", e);
    }

    LOG.debug("Salimos de findAplicacionesByDocument con idDocumento = " + document.getId());
    return result;
  }

  @Override
  public List<DocumentAplicacionEntity> findDocumentAplicacionByDocumentAndApplication(
      DocumentEntity document, ApplicationEntity application) throws ServiceException {

    LOG.info("Entramos en findDocumentAplicacionByDocumentAndApplication con idDocumento = "
        + document.getId() + " y Aplicacion = " + application);

    List<DocumentAplicacionEntity> result = null;

    try {
      DocumentAplicacionEntity entity = new DocumentAplicacionEntity();

      entity.setDocumento(document);
      entity.setAplicacion(application);
      result = documentAplicacionRepository.findListBy(entity);
    } catch (Exception e) {
      LOG.error("Error al buscar las Aplicaciones de consulta del documento. ", e);
      throw new ServiceException("Error al buscar las Aplicaciones de consulta del documento. ", e);
    }

    LOG.debug("Salimos de findAplicacionesByDocument con idDocumento = " + document.getId());
    return result;
  }

  @Override
  public List<DocumentAplicacionEntity> findDocumentAplicacionByDocument(DocumentEntity document,
      Long idAplicacion) throws ServiceException {

    LOG.info("Entramos en findAplicacionesByDocument con idDocumento = " + document.getId());

    List<DocumentAplicacionEntity> result = null;

    try {
      DocumentAplicacionEntity entity = new DocumentAplicacionEntity();

      entity.setDocumento(document);
      ApplicationEntity aplicacion = new ApplicationEntity();
      aplicacion.setId(idAplicacion);
      entity.setAplicacion(aplicacion);
      result = documentAplicacionRepository.findListBy(entity);
    } catch (Exception e) {
      LOG.error("Error al buscar las Aplicaciones de consulta del documento. ", e);
      throw new ServiceException("Error al buscar las Aplicaciones de consulta del documento. ", e);
    }

    LOG.debug("Salimos de findRestriccionesIdByDocument con csv = " + document.getId());
    return result;
  }

  public void guardarAplicacionesConsulta(List<ApplicationEntity> aplicaciones,
      DocumentEntity document, boolean modificar) throws ServiceException {

    LOG.info("Entramos en guardarAplicacionesConsulta.");

    // En caso de ser una modificacion eliminamos las aplicaciones anteriores
    if (modificar) {
      List<DocumentAplicacionEntity> documentAplicacionList =
          findDocumentAplicacionByDocument(document);
      if (documentAplicacionList != null) {
        LOG.info("Se elimina las restricciones... " + documentAplicacionList);
        documentAplicacionRepository.delete(documentAplicacionList);
      }
    }

    List<DocumentAplicacionEntity> documentAplicacionList = new ArrayList<>();
    if (aplicaciones != null) {
      for (ApplicationEntity aplicacion : aplicaciones) {
        DocumentAplicacionEntity documentAplicacionEntity = new DocumentAplicacionEntity();
        documentAplicacionEntity.setDocumento(document);
        documentAplicacionEntity.setAplicacion(aplicacion);

        documentAplicacionList.add(documentAplicacionEntity);
      }
    }

    LOG.info("Lista de Aplicaciones a guardar: " + documentAplicacionList);

    LOG.info("Se guardan las aplicaciones.");
    documentAplicacionRepository.save(documentAplicacionList);


    LOG.info("Salimos en guardarAplicacionesConsulta.");
  }

  public void revocarPermisoConsultaAplicacion(
      List<DocumentAplicacionEntity> documentAplicacionList) throws ServiceException {
    documentAplicacionRepository.delete(documentAplicacionList);
  }
}
