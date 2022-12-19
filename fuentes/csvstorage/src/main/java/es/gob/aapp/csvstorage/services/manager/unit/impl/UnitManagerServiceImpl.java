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

package es.gob.aapp.csvstorage.services.manager.unit.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.UnitRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.unit.UnitManagerService;

/**
 * Implementación de los servicios manager para aplicaciones consumidoras de los servicios web.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("unitManagerService")
@PropertySource("classpath:query.properties")
public class UnitManagerServiceImpl implements UnitManagerService {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(UnitEntity.class);

  @Autowired
  EntityManager entityManager;

  private BaseRepository<UnitEntity, Long> unitRepository;

  /**
   * Consulta a base de datos para obtener las unidades por nombre.
   */
  @Value("${Unit.findLikeNombre}")
  private String queryFindLikeNombre;


  @Autowired
  private UnitRepository unitRepositoryPageable;

  @PostConstruct
  private void configure() {
    unitRepository = new BaseRepositoryImpl<UnitEntity, Long>(UnitEntity.class, entityManager);
  }

  @Override
  public List<UnitEntity> findAll() throws ServiceException {
    LOG.debug("Entramos en findAll ");

    List<UnitEntity> result = null;

    try {

      UnitEntity unitEntity = new UnitEntity();

      result = (List<UnitEntity>) unitRepository.findListBy(unitEntity);

    } catch (NoSuchMethodException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    } catch (SecurityException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (IllegalAccessException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (InvocationTargetException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    }
    return result;
  }

  @Override
  public UnitEntity findByDir3(String dir3) throws ServiceException {
    LOG.debug("Entramos en findAll ");

    UnitEntity result = null;

    try {

      UnitEntity unitEntity = new UnitEntity();
      unitEntity.setUnidadOrganica(dir3);

      result = (UnitEntity) unitRepository.findOneBy(unitEntity);

    } catch (NoSuchMethodException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    } catch (SecurityException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (IllegalAccessException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (InvocationTargetException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    }
    return result;
  }

  @Override
  public List<UnitEntity> findUnitsByDir3(String dir3) throws ServiceException {
    LOG.debug("Entramos en findAll. dir3=" + dir3);

    try {

      UnitEntity unitEntity = new UnitEntity();
      unitEntity.setUnidadOrganica(dir3);

      return unitRepository.findListBy(unitEntity);

    } catch (NoSuchMethodException | SecurityException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      throw new ServiceException("Error al buscar las unidades orgánicas: " + e.getMessage(), e);
    }
  }

  @Override
  public UnitEntity findById(Long id) throws ServiceException {
    LOG.debug("Entramos en findById ");

    UnitEntity result = null;

    try {

      UnitEntity unitEntity = new UnitEntity();
      unitEntity.setId(id);

      result = (UnitEntity) unitRepository.findOneBy(unitEntity);

    } catch (NoSuchMethodException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    } catch (SecurityException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (IllegalAccessException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (InvocationTargetException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    }
    return result;
  }

  @Override
  public List<UnitEntity> findUnitsByDir3andAmbito(String dir3, String ambito)
      throws ServiceException {
    LOG.debug("Entramos en findAll ");

    List<UnitEntity> result = null;

    try {

      UnitEntity unitEntity = new UnitEntity();
      unitEntity.setUnidadOrganica(dir3);
      unitEntity.setAmbito(ambito);

      result = (List<UnitEntity>) unitRepository.findListBy(unitEntity);

    } catch (NoSuchMethodException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar el informe. ", e);
    } catch (SecurityException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (IllegalAccessException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (IllegalArgumentException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    } catch (InvocationTargetException e) {
      LOG.error("Error al buscar las unidades orgánicas. ", e);
      throw new ServiceException("Error al buscar las unidades orgánicas. ", e);
    }
    return result;
  }

  @Override
  public UnitEntity saveUnit(UnitEntity entity) throws ServiceException {
    LOG.debug("Entramos en saveUnit con entity. " + entity.getUnidadOrganica());

    try {

      // control unidad existente
      List<UnitEntity> unitAuxEntityList = findUnitsByDir3(entity.getUnidadOrganica());
      if (unitAuxEntityList != null && unitAuxEntityList.size() > 0) {
        throw new ServiceException(
            "No se ha podido crear la unidad ya existe: " + entity.getUnidadOrganica());
      }
      return this.unitRepository.save(entity);

    } catch (Exception e) {
      LOG.error("La unidad " + entity.getUnidadOrganica() + " ya existe. ");
      throw new ServiceException("Error al guardar la unidad en base de datos. ", e);
    }
  }

  @Override
  public UnitEntity updateUnit(UnitEntity entity) throws ServiceException {
    LOG.debug("Entramos en updateUnit con entity. " + entity.getUnidadOrganica());

    try {

      // control unidad existente
      List<UnitEntity> unitAuxEntityList = findUnitsByDir3(entity.getUnidadOrganica());

      if (unitAuxEntityList == null || unitAuxEntityList.size() == 0) {
        throw new ServiceException("Unidad no existe");
      } else {
        UnitEntity unidadGuardada = unitAuxEntityList.get(0);
        entity.setId(unidadGuardada.getId());
        return this.unitRepository.save(entity);
      }

    } catch (Exception e) {
      LOG.error("Error al hacer el save con la entidad. ", e);
      throw new ServiceException("Error al guardar la unidad en base de datos. ", e);
    }
  }

  @Override
  public List<UnitEntity> getAllUnits() {
    return (List<UnitEntity>) unitRepositoryPageable.findAll();
  }

  @Override
  public Page getAllUnitsPageable(Pageable pageable) {

    return unitRepositoryPageable.findAll(pageable);
  }

  @Override
  public Page getAllUnitsPageableByCodigoOrNombre(Pageable pageable, String codigo, String nombre) {

    return unitRepositoryPageable
        .findByUnidadOrganicaIgnoreCaseContainingOrNombreIgnoreCaseContaining(pageable, codigo,
            nombre);
  }

  @Override
  public void deleteById(Long id) {
    unitRepository.delete(id);
  }

  @Override
  public List<UnitEntity> findLikeNombre(String nombre) {

    List<UnitEntity> listaReturn = new ArrayList<>();
    Map<String, Object> data = new HashMap<>();

    data.put("nombre", "%" + nombre.toUpperCase() + "%");

    for (Object object : unitRepository.findListByQuery(queryFindLikeNombre, data, 10)) {
      listaReturn.add((UnitEntity) object);
    }

    return listaReturn;
  }

}
