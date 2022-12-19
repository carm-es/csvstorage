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

package es.gob.aapp.csvstorage.services.business.unit.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.gob.aapp.csvstorage.consumer.dir3.Dir3Consumer;
import es.gob.aapp.csvstorage.consumer.dir3.converter.OrganicUnitConverter;
import es.gob.aapp.csvstorage.consumer.dir3.model.Unidades;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.model.converter.unit.UnitConverter;
import es.gob.aapp.csvstorage.model.object.unit.UnitObject;
import es.gob.aapp.csvstorage.services.business.unit.UnitBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.unit.UnitManagerService;



/**
 * Implementación de los servicios business para unidades orgánicas.
 * 
 * @author carlos.munoz1
 * 
 */
@Service("unitBusinessService")
public class UnitBusinessServiceImpl implements UnitBusinessService {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(UnitBusinessServiceImpl.class);

  /**
   * Inyección de los servicios de negocio de archivo.
   */
  @Autowired
  private UnitManagerService unitManagerService;

  /** Inyección del consumidor del ws de Dir3. */
  @Autowired
  private Dir3Consumer dir3consumer;

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.business.OrganicUnitBusinessService# saveList
   * (es.gob.aapp.csvstorage.consumer.dir3.model.Unidades)
   */
  public List<UnitObject> saveList(Unidades unidades) throws ServiceException {
    LOG.debug("[INI] Entramos en servicio saveList() para guardar lista de organismos.");

    List<UnitObject> listaRetorno = new ArrayList<UnitObject>();
    try {

      if (unidades != null && CollectionUtils.isNotEmpty(unidades.getUnidad())) {
        List<UnitObject> listaUnidades;

        listaUnidades = OrganicUnitConverter.organicUnitToObject(unidades);
        LOG.info("Llamamos al servicio manager para guardar unidades... ");

        for (UnitObject unit : listaUnidades) {
          try {
            listaRetorno.add(UnitConverter.unitEntityToModel(
                unitManagerService.saveUnit(UnitConverter.unitModelToEntity(unit))));
          } catch (ServiceException ex) {
            listaRetorno.add(UnitConverter.unitEntityToModel(
                unitManagerService.updateUnit(UnitConverter.unitModelToEntity(unit))));
          }
        }
        LOG.info("Se han insertado todas las unidades... ");

      }

    } catch (ConsumerWSException e) {
      LOG.error("Error en consumer. ", e);
      throw new ServiceException("Error en saveList() al guardar lista de unidades. ", e);
    }

    LOG.debug("[FIN] Salimos del servicio saveList() para guardar lista de organismos.");

    return listaRetorno;
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.business.OrganicUnitBusinessService#
   * saveOrganicUnitFromDir3()
   */
  @Transactional(rollbackFor = ServiceException.class)
  public int saveOrganicUnitFromDir3() throws ServiceException {

    LOG.info("[INI] Entramos en saveOrganicUnitFromDir3(). ");

    int totalGuardado = 0;

    try {
      totalGuardado = saveList(dir3consumer.exportOrganicUnitsToXML()).size();
    } catch (ConsumerWSException e) {
      LOG.error("Error al guardar unidades Dir3. ", e);
      throw new ServiceException(e.getLocalizedMessage(), e);
    }

    LOG.info("[FIN] Salimos de saveOrganicUnitFromDir3(). Total guardado: " + totalGuardado);

    return totalGuardado;
  }

  @Transactional(rollbackFor = ServiceException.class)
  public int saveOrganicUnitFromDir3(String date) throws ServiceException {

    LOG.info("[INI] Entramos en saveOrganicUnitFromDir3() con fecha. ");

    int totalGuardado = 0;

    try {
      totalGuardado = saveList(dir3consumer.exportOrganicUnitsToXML(date)).size();
    } catch (ConsumerWSException e) {
      LOG.error("Error al guardar unidades Dir3. ", e);
      throw new ServiceException(e.getLocalizedMessage(), e);
    }

    LOG.info(
        "[FIN] Salimos de saveOrganicUnitFromDir3() con fecha. Total guardado: " + totalGuardado);

    return totalGuardado;
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.business.OrganicUnitBusinessService#
   * saveOrganicUnitFromDir3()
   */
  @Transactional(rollbackFor = ServiceException.class)
  public int saveAllOrganicUnitFromDir3() throws ServiceException {

    LOG.info("[INI] Entramos en saveAllOrganicUnitFromDir3(). ");

    int totalGuardado = 0;

    try {
      totalGuardado = saveList(dir3consumer.exportAllOrganicUnitsToXML()).size();
    } catch (ConsumerWSException e) {
      LOG.error("Error al guardar unidades Dir3. ", e);
      throw new ServiceException(e.getLocalizedMessage(), e);
    }

    LOG.info("[FIN] Salimos de saveAllOrganicUnitFromDir3(). Total guardado: " + totalGuardado);

    return totalGuardado;
  }


  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.business.OrganicUnitBusinessService# saveList
   * (es.gob.aapp.csvstorage.consumer.dir3.model.Unidades)
   */
  @Transactional(rollbackFor = ServiceException.class)
  public void saveUnit(String unidad) throws ServiceException {
    LOG.debug("[INI] Entramos en servicio saveUnit() para guardar un organismo.");
    UnitObject model = new UnitObject();
    model.setUnidadOrganica(unidad);

    try {

      SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
      Date fechaInicio = format.parse("01/01/1900");
      model.setFechaCreacion(fechaInicio);

      unitManagerService.saveUnit(UnitConverter.unitModelToEntity(model));

    } catch (ParseException e) {
      LOG.error("Formato de fecha erroneo");
    } catch (Exception e) {
      LOG.error("Error en consumer. ", e);
      throw new ServiceException("Error en saveList() al guardar lista de unidades. ", e);
    }

    LOG.debug("[FIN] Salimos del servicio saveUnit() para guardar un organismo.");

  }


  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.services.business.units.OrganicUnitBusinessService
   * #finByCodigo(java.lang.String)
   */
  public UnitObject findByCodigo(String codigo) throws ServiceException {
    LOG.debug("[INI] findByCodigo. Codigo = " + codigo);
    UnitObject unitReturn = null;
    if (!StringUtils.isEmpty(codigo)) {
      unitReturn = UnitConverter.unitEntityToModel(unitManagerService.findByDir3(codigo));
    }
    LOG.debug("[FIN] findByCodigo. Return: " + unitReturn);
    return unitReturn;
  }

  @Override
  public List<UnitObject> getAllOrganicUnits() throws ServiceException {
    LOG.debug("[INI] getAllOrganicUnits");
    return UnitConverter.unitEntityToModel(unitManagerService.getAllUnits());
  }

  @Override
  public Page getAllOrganicUnitsPageable(Pageable pageable) throws ServiceException {
    LOG.debug("[INI] getAllOrganicUnitsPageable");
    return unitManagerService.getAllUnitsPageable(pageable);
  }

  @Override
  public Page getAllOrganicUnitsPageableByCodigoOrNombre(Pageable pageable, String filtro)
      throws ServiceException {
    LOG.debug("[INI] getAllOrganicUnitsPageableByCodigoOrNombre");
    return unitManagerService.getAllUnitsPageableByCodigoOrNombre(pageable, filtro, filtro);
  }

  @Override
  @Transactional(rollbackFor = ServiceException.class)
  public void deleteUnit(String codigoDir3) throws ServiceException {
    UnitObject dir3 = findByCodigo(codigoDir3);
    if (dir3 == null) {
      throw new ServiceException("No existe el DIR3:" + codigoDir3);
    }
    unitManagerService.deleteById(dir3.getId());
  }

  @Override
  public List<UnitObject> findLikeNombre(String nombre) {
    LOG.debug("findLikeNombre: " + nombre);

    List<UnitObject> listaRetorno = new ArrayList<UnitObject>();

    if (!StringUtils.isEmpty(nombre)) {
      listaRetorno = UnitConverter.unitEntityToModel(unitManagerService.findLikeNombre(nombre));
    }

    return listaRetorno;
  }

}
