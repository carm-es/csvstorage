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

package es.gob.aapp.csvstorage.webservices.administration.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.services.manager.unit.UnitManagerService;
import es.gob.aapp.csvstorage.util.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.users.UserEntity;
import es.gob.aapp.csvstorage.model.converter.application.ApplicationConverter;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.application.ApplicationManagerService;
import es.gob.aapp.csvstorage.services.manager.users.UserManagerService;
import es.gob.aapp.csvstorage.util.constants.ApplicationPermission;
import es.gob.aapp.csvstorage.webservices.administration.AdminService;
import es.gob.aapp.csvstorage.webservices.administration.model.Aplicacion;
import es.gob.aapp.csvstorage.webservices.administration.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.administration.model.InfAdicional;
import es.gob.aapp.csvstorage.webservices.administration.model.WSCredential;

@WebService(serviceName = "AdminService", portName = "AdminServicePort",
    targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:administration:v1.0",
    endpointInterface = "es.gob.aapp.csvstorage.webservices.administration.AdminService")
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.BARE, use = Use.LITERAL)

public class AdminServiceImpl implements AdminService {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(AdminServiceImpl.class);

  private static final Locale locale = LocaleContextHolder.getLocale();

  /** Inyección de los servicios business de las aplicaciones */
  @Autowired
  private ApplicationManagerService applicationManagerService;

  /**
   * Inyección de los servicios de negocio de usuarios.
   */
  @Autowired
  private UserManagerService userManagerService;

  @Autowired
  private UnitManagerService unitManagerService;

  @Autowired
  private MessageSource messageSource;

  @Override
  @Transactional(rollbackFor = ServiceException.class)
  public Aplicacion altaAplicacion(WSCredential info, Aplicacion aplicacion)
      throws CSVStorageException {
    LOG.info("entra en altaAplicacion");
    ApplicationEntity applicationEntity = null;
    try {
      // Validamos si la aplicación con la que se llama al servicio tiene permisos de administrador
      if (isAdmin()) {
        if (aplicacion != null) {
          applicationEntity = ApplicationConverter.aplicacionWSToEntity(aplicacion);

          if (aplicacion.getUnidad() != null && !aplicacion.getUnidad().isEmpty()) {
            UnitEntity dir3 = unitManagerService.findByDir3(aplicacion.getUnidad());
            applicationEntity.setUnidad(dir3);
          } else {
            throw new CSVStorageException(
                messageSource.getMessage("NotBlank.aplicacion.dir3", null, locale));
          }


          if (aplicacion.getDescripcion() == null) {
            applicationEntity.setDescripcion(aplicacion.getIdAplicacion());
          }

          // Buscamos si la aplicación ya existe
          ApplicationEntity aplicacionEnBBDD =
              applicationManagerService.findByIdAplicacion(aplicacion.getIdAplicacion());
          UserEntity userEntity = userManagerService.findByUsuario(info.getIdaplicacion());



          if (aplicacion.getIdAplicacion().equals(aplicacion.getIdAplicacionPublico())) {
            throw new CSVStorageException(
                messageSource.getMessage("NotEquals.aplicacion.id", null, locale));
          }

          // Si no existe se crea y si existe se actualiza
          if (aplicacionEnBBDD != null) {
            applicationEntity.setId(aplicacionEnBBDD.getId());
            validateSerialNumber(applicationEntity);
            applicationEntity.setCreatedAt(aplicacionEnBBDD.getCreatedAt());
            applicationEntity.setCreatedBy(aplicacionEnBBDD.getCreatedBy());
            applicationEntity.setModifiedAt(Calendar.getInstance().getTime());
            applicationEntity.setModifiedBy(userEntity);
            applicationEntity.setPermisoLectura(aplicacionEnBBDD.getPermisoLectura());

            applicationManagerService.update(applicationEntity, true);
          } else {
            validateSerialNumber(applicationEntity);
            applicationEntity.setCreatedAt(Calendar.getInstance().getTime());
            applicationEntity.setCreatedBy(userEntity);
            applicationEntity
                .setPermisoLectura(ApplicationPermission.PERMISSION_CREATED_DOCUMENTS.getCode());
            applicationEntity.setDocumentosPdfYEni(true);

            applicationManagerService.create(applicationEntity);
          }

          LOG.debug("Aplicacion guardada con éxito. ");
        }
      } else {
        throw new CSVStorageException("No tienes permiso para realizar esta operación");
      }
    } catch (ServiceException e) {
      throw new CSVStorageException(
          "Se ha producido un error al dar de alta la aplicación: " + aplicacion.getIdAplicacion());
    }

    LOG.info("sale de altaAplicacion");
    return ApplicationConverter.applicationEntityToWS(applicationEntity);
  }

  @Override
  @Transactional(rollbackFor = ServiceException.class)
  public Aplicacion bajaAplicacion(WSCredential info, String idAplicacion)
      throws CSVStorageException {
    LOG.info("entra en bajaAplicacion");

    ApplicationEntity aplicacionEnBBDD = null;
    try {
      // Validamos si la aplicación con la que se llama al servicio tiene permisos de administrador
      if (isAdmin()) {
        if (idAplicacion != null) {


          // Buscamos si la aplicación ya existe
          aplicacionEnBBDD = applicationManagerService.findByIdAplicacion(idAplicacion);

          // Si no existe se crea y si existe se actualiza
          if (aplicacionEnBBDD != null) {
            aplicacionEnBBDD.setActivo(false);
            applicationManagerService.update(aplicacionEnBBDD, true);

          }

          LOG.debug("Aplicacion se ha eliminado correctamente. ");
        }
      } else {
        throw new CSVStorageException("No tiene permiso para realizar esta operación.");
      }
    } catch (ServiceException e) {
      throw new CSVStorageException(
          "Se ha producido un error al dar de alta la aplicación " + idAplicacion);
    }

    LOG.info("sale de bajaAplicacion");
    return ApplicationConverter.applicationEntityToWS(aplicacionEnBBDD);

  }


  @Override
  public List<Aplicacion> listaAplicaciones(WSCredential info) throws CSVStorageException {
    List<Aplicacion> listAplicaciones = null;

    if (isAdmin()) {

      listAplicaciones =
          ApplicationConverter.applicationEntityToWS(applicationManagerService.findAll());


    } else {
      throw new CSVStorageException("No tiene permiso para realizar esta operación.");
    }

    return listAplicaciones;
  }


  private boolean isAdmin() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    boolean isAdmin = false;
    if (auth.getAuthorities() != null) {
      for (GrantedAuthority authority : auth.getAuthorities()) {
        if ("ADMIN".equals(authority.getAuthority())) {
          isAdmin = true;
        }
      }
    }
    return isAdmin;
  }



  @Override
  public List<InfAdicional> infAdicionalAltaApp(WSCredential info) throws CSVStorageException {
    List<InfAdicional> listaInfAdicional = null;
    if (isAdmin()) {
      InfAdicional infAdicional = new InfAdicional();
      infAdicional.setKey("validarDocumentoENI");
      infAdicional.setValue("true");

      listaInfAdicional = new ArrayList<InfAdicional>();
      listaInfAdicional.add(infAdicional);

    } else {
      throw new CSVStorageException("No tiene permiso para realizar esta operación");
    }

    return listaInfAdicional;
  }

  private void validateSerialNumber(ApplicationEntity application) throws CSVStorageException {
    String message = null;
    try {
      // Se valida que no haya otra aplicación con el mismo número de serie del certificado
      if (StringUtils.isNotEmpty(application.getSerialNumberCertificado())) {
        List<ApplicationEntity> applicationList =
            applicationManagerService.findBySerialNumber(application.getSerialNumberCertificado());

        if (applicationList != null && applicationList.size() > 0) {

          if (application.getId() == null) {
            LOG.error("Error, número de serie repetido. ");
            message = "El número de serie del certificado ya está asociado a otra aplicación.";
          } else {
            for (ApplicationEntity appBBDD : applicationList) {
              if (!appBBDD.getId().equals(application.getId())) {
                LOG.error("Error, numero de serie repetido. ");
                message = "El número de serie del certificado ya está asociado a otra aplicación.";
              }
            }
          }

        }
      }
    } catch (ServiceException e) {
      LOG.error("Error, número de serie repetido. ");
      message = "Se ha producido un error al validar el certificado.";
    }

    if (message != null) {
      throw new CSVStorageException(message);
    }
  }

  @Override
  @Transactional(rollbackFor = ServiceException.class)
  public Aplicacion eliminaAplicacion(WSCredential info, String idAplicacion)
      throws CSVStorageException {
    LOG.info("entra en eliminaAplicacion");

    ApplicationEntity aplicacionEnBBDD = null;
    try {
      // Validamos si la aplicación con la que se llama al servicio tiene permisos de administrador
      if (isAdmin()) {
        if (idAplicacion != null) {


          // Si existe algun documento creado por esta aplicación no se podrá eliminar
          aplicacionEnBBDD = applicationManagerService.findByIdAplicacion(idAplicacion);

          // Si no existe se crea y si existe se actualiza
          if (aplicacionEnBBDD != null) {
            aplicacionEnBBDD.setActivo(false);
            applicationManagerService.update(aplicacionEnBBDD, true);

          }

          LOG.debug("Aplicacion se ha eliminado correctamente. ");
        }
      } else {
        throw new CSVStorageException("No tiene permiso para realizar esta operación");
      }
    } catch (ServiceException e) {
      throw new CSVStorageException(
          "Se ha producido un error al dar de alta la aplicación " + idAplicacion);
    }

    LOG.info("sale de eliminaAplicacion");
    return ApplicationConverter.applicationEntityToWS(aplicacionEnBBDD);

  }

}
