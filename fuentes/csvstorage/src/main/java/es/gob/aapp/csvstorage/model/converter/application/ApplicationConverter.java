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

package es.gob.aapp.csvstorage.model.converter.application;

import java.util.ArrayList;
import java.util.List;
import es.gob.aapp.csvstorage.model.converter.unit.UnitConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.model.converter.users.UsersConverter;
import es.gob.aapp.csvstorage.model.object.application.ApplicationObject;
import es.gob.aapp.csvstorage.webservices.administration.model.Aplicacion;

/**
 * Clase converter para aplicaciones consumidoras.
 * 
 * @author carlos.munoz1
 * 
 */
public abstract class ApplicationConverter {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(ApplicationConverter.class);

  /**
   * Convertidor de entities a objetos del model.
   * 
   * @param entity ApplicationEntity
   * @return ApplicationObject
   */
  public static ApplicationObject applicationEntityToModel(ApplicationEntity entity) {

    ApplicationObject model = null;

    if (entity != null) {
      model = new ApplicationObject();
      model.setId(entity.getId());
      model.setIdAplicacionPublico(entity.getIdAplicacionPublico());
      model.setIdAplicacion(entity.getIdAplicacion());
      model.setDescripcion(entity.getDescripcion());
      model.setPassword(entity.getPassword());
      model.setAdmin(entity.getIsAdmin());
      model.setNombreResponsable(entity.getNombreResponsable());
      model.setEmailResponsable(entity.getEmailResponsable());
      model.setTelefonoResponsable(entity.getTelefonoResponsable());
      model.setValidarDocumentoENI(entity.getValidarDocumentoENI());
      model.setActivo(entity.getActivo());
      model.setSerialNumberCertificado(entity.getSerialNumberCertificado());
      model.setCreatedAt(entity.getCreatedAt());
      if (entity.getCreatedBy() != null) {
        model.setCreatedBy(UsersConverter.userEntityToModel(entity.getCreatedBy()));
      }
      model.setModifiedAt(entity.getModifiedAt());
      if (entity.getModifiedBy() != null) {
        model.setModifiedBy(UsersConverter.userEntityToModel(entity.getModifiedBy()));
      }
      model.setLecturaCmis(entity.getLecturaCmis());
      model.setEscrituraCmis(entity.getEscrituraCmis());
      model.setPermisoLectura(entity.getPermisoLectura());
      model.setDocumentosPdfYEni(entity.getDocumentosPdfYEni());
      model.setUnidad(UnitConverter.unitEntityToModel(entity.getUnidad()));

    }

    return model;
  }

  /**
   * Convertidor de lista de entidades a lista de modelo. Método sobrecargado.
   * 
   * @param entities List<ApplicationEntity>
   * @return List<UnitObject>
   */
  public static List<ApplicationObject> applicationEntityToModel(List<ApplicationEntity> entities) {
    LOG.debug("[INI] ApplicationEntityToModel --> convertidor de listas");
    List<ApplicationObject> listaReturn = new ArrayList<ApplicationObject>();
    for (ApplicationEntity ApplicationEntity : entities) {
      listaReturn.add(applicationEntityToModel(ApplicationEntity));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] ApplicationEntityToModel --> convertidor de listas");
    return listaReturn;
  }

  /**
   * Convertidor de objetos modelo a entidades.
   * 
   * @param model UnitObject
   * @return entidad ApplicationEntity
   */
  public static ApplicationEntity applicationModelToEntity(ApplicationObject model) {

    ApplicationEntity entity = null;

    if (model != null) {
      entity = new ApplicationEntity();
      entity.setId(model.getId());
      entity.setIdAplicacionPublico(model.getIdAplicacionPublico());
      entity.setIdAplicacion(model.getIdAplicacion());
      entity.setDescripcion(model.getDescripcion());
      entity.setPassword(model.getPassword());
      entity.setIsAdmin(model.isAdmin());
      entity.setNombreResponsable(model.getNombreResponsable());
      entity.setEmailResponsable(model.getEmailResponsable());
      entity.setTelefonoResponsable(model.getTelefonoResponsable());
      entity.setValidarDocumentoENI(model.getValidarDocumentoENI());
      entity.setActivo(model.getActivo());
      entity.setSerialNumberCertificado(model.getSerialNumberCertificado());
      entity.setCreatedAt(model.getCreatedAt());
      if (model.getCreatedBy() != null) {
        entity.setCreatedBy(UsersConverter.userModelToEntity(model.getCreatedBy()));
      }
      entity.setModifiedAt(model.getModifiedAt());
      if (model.getModifiedBy() != null) {
        entity.setModifiedBy(UsersConverter.userModelToEntity(model.getModifiedBy()));
      }
      entity.setLecturaCmis(model.getLecturaCmis());
      entity.setEscrituraCmis(model.getEscrituraCmis());
      entity.setPermisoLectura(model.getPermisoLectura());
      entity.setDocumentosPdfYEni(model.getDocumentosPdfYEni());

    }

    return entity;
  }

  /**
   * Convertidor de lista de objetos a lista de entities. Método sobrecargado.
   * 
   * @param listaObjetos List<<ApplicationEntity>
   * @return List<UnitObject>
   */
  public static List<ApplicationEntity> applicationModelToEntity(
      List<ApplicationObject> listaObjetos) {
    LOG.debug("[INI] applicationModelToEntity --> convertidor de listas");
    List<ApplicationEntity> listaReturn = new ArrayList<ApplicationEntity>();
    for (ApplicationObject appAux : listaObjetos) {
      listaReturn.add(applicationModelToEntity(appAux));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] applicationModelToEntity --> convertidor de listas");
    return listaReturn;
  }

  /**
   * Convertidor de entities a objetos del ws.
   * 
   * @param entity ApplicationEntity
   * @return Aplicacion
   */
  public static Aplicacion applicationEntityToWS(ApplicationEntity entity) {

    Aplicacion aplicacion = null;

    if (entity != null) {
      aplicacion = new Aplicacion();
      aplicacion.setIdAplicacionPublico(entity.getIdAplicacionPublico());
      aplicacion.setIdAplicacion(entity.getIdAplicacion());
      aplicacion.setDescripcion(entity.getDescripcion());
      // aplicacion.setPassword(entity.getPassword());
      if (entity.getUnidad() != null) {
        aplicacion.setUnidad(entity.getUnidad().getUnidadOrganica());
      }
      aplicacion.setResponsable(entity.getNombreResponsable());
      aplicacion.setEmail(entity.getEmailResponsable());
      aplicacion.setTelefono(entity.getTelefonoResponsable());
      aplicacion.setValidarDocumentoENI(entity.getValidarDocumentoENI());
      aplicacion.setActivo(entity.getActivo());
      aplicacion.setSerialNumberCertificado(entity.getSerialNumberCertificado());
    }

    return aplicacion;
  }

  /**
   * Convertidor de lista de entidades a lista de modelo. Método sobrecargado.
   * 
   * @param entities List<ApplicationEntity>
   * @return List<Aplicacion>
   */
  public static List<Aplicacion> applicationEntityToWS(List<ApplicationEntity> entities) {
    LOG.debug("[INI] ApplicationEntityToWS --> convertidor de listas");
    List<Aplicacion> listaReturn = new ArrayList<Aplicacion>();
    for (ApplicationEntity ApplicationEntity : entities) {
      listaReturn.add(applicationEntityToWS(ApplicationEntity));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] ApplicationEntityToWS --> convertidor de listas");
    return listaReturn;
  }

  /**
   * Convertidor de objetos modelo a entidades.
   * 
   * @param aplicacion Aplicacion
   * @return entidad ApplicationEntity
   */
  public static ApplicationEntity aplicacionWSToEntity(Aplicacion aplicacion) {

    ApplicationEntity entity = null;

    if (aplicacion != null) {
      entity = new ApplicationEntity();
      entity.setIdAplicacionPublico(aplicacion.getIdAplicacionPublico());
      entity.setIdAplicacion(aplicacion.getIdAplicacion());
      entity.setDescripcion(aplicacion.getDescripcion());
      entity.setPassword(aplicacion.getPassword());
      entity.setNombreResponsable(aplicacion.getResponsable());
      entity.setEmailResponsable(aplicacion.getEmail());
      entity.setTelefonoResponsable(aplicacion.getTelefono());
      entity.setValidarDocumentoENI(aplicacion.getValidarDocumentoENI());
      entity.setActivo(aplicacion.getActivo());
      entity.setSerialNumberCertificado(aplicacion.getSerialNumberCertificado());
      entity.setActivo(true);
      entity.setIsAdmin(false);
      entity.setLecturaCmis(false);
      entity.setEscrituraCmis(false);
    }

    return entity;
  }

  /**
   * Convertidor de lista de objetos a lista de entities. Método sobrecargado.
   * 
   * @param listaObjetos List<<ApplicationEntity>
   * @return List<Aplicacion>
   */
  public static List<ApplicationEntity> aplicacionWSToEntity(List<Aplicacion> listaObjetos) {
    LOG.debug("[INI] aplicacionWSToEntity --> convertidor de listas");
    List<ApplicationEntity> listaReturn = new ArrayList<ApplicationEntity>();
    for (Aplicacion appAux : listaObjetos) {
      listaReturn.add(aplicacionWSToEntity(appAux));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] aplicacionWSToEntity --> convertidor de listas");
    return listaReturn;
  }

  public static es.gob.aapp.csvstorage.webservices.document.model.Aplicacion applicationEntityToWSIdPublic(
      ApplicationEntity entity) {

    es.gob.aapp.csvstorage.webservices.document.model.Aplicacion aplicacion = null;

    if (entity != null && entity.getIdAplicacionPublico() != null) {
      aplicacion = new es.gob.aapp.csvstorage.webservices.document.model.Aplicacion();
      aplicacion.setIdAplicacionPublico(entity.getIdAplicacionPublico());
      aplicacion.setDescripcion(entity.getDescripcion());
    }

    return aplicacion;
  }

  /**
   * Convertidor de lista de entidades a lista de modelo. Método sobrecargado.
   * 
   * @param entities List<ApplicationEntity>
   * @return List<Aplicacion>
   */
  public static List<es.gob.aapp.csvstorage.webservices.document.model.Aplicacion> applicationEntityToWSIdPublic(
      List<ApplicationEntity> entities) {
    LOG.debug("[INI] applicationEntityToWSIdPublic --> convertidor de listas");
    List<es.gob.aapp.csvstorage.webservices.document.model.Aplicacion> listaReturn =
        new ArrayList<es.gob.aapp.csvstorage.webservices.document.model.Aplicacion>();
    for (ApplicationEntity ApplicationEntity : entities) {
      listaReturn.add(applicationEntityToWSIdPublic(ApplicationEntity));
    }
    LOG.debug("Total convertido: " + listaReturn.size());
    LOG.debug("[FIN] applicationEntityToWSIdPublic --> convertidor de listas");
    return listaReturn;
  }

}
