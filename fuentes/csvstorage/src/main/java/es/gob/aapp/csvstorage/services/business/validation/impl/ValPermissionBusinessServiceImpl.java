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

package es.gob.aapp.csvstorage.services.business.validation.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentAplicacionEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentIdsEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentNifEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentRestriccionEntity;
import es.gob.aapp.csvstorage.services.business.validation.ValPermissionBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.services.manager.document.DocumentManagerService;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.constants.DocumentPermission;
import es.gob.aapp.csvstorage.util.constants.DocumentRestriccion;
import es.gob.aapp.csvstorage.util.constants.IdentificationType;

/**
 * Implementación de los servicios de validación de los permissos.
 *
 * @author carlos.munoz1
 *
 */
@Service("valPermissionBusinessService")
public class ValPermissionBusinessServiceImpl implements ValPermissionBusinessService {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(ValPermissionBusinessServiceImpl.class);

  @Autowired
  private DocumentManagerService documentManagerService;

  @Override
  public boolean permisoLectura(ApplicationEntity applicacion, DocumentEntity documentEntity,
      IdentificationType identificationType, String nif, String calledFrom) {

    LOG.info("[INI] permisoLectura");
    boolean permission = false;
    boolean calledFromDocumentService = (!StringUtils.isEmpty(calledFrom)
        && calledFrom.equals(Constants.CALLED_FROM_DOCUMENT_SERVICE));

    // Si es el propietario siempre tiene acceso a sus documentos
    if (documentEntity.getCreatedBy() != null
        && applicacion.getId().equals(documentEntity.getCreatedBy().getId())) {
      LOG.info("El documento a consultar es propiedad de la Aplicación: "
          + applicacion.getIdAplicacion());
      return true;
    }

    // Compruebo que el documento no sea privado
    if (documentEntity.getTipoPermiso().equals(DocumentPermission.PRIVADO.getCode())) {
      LOG.info("El documento a consultar es PRIVADO.");
      return false;
    }

    // llega aquí si el documento es PUBLICO o RESTRINGIDO
    // y la petición no la realiza el propietario
    if (!calledFromDocumentService) {// Si la petición se realiza mediante QueryDocumentService
      if (documentEntity.getTipoPermiso().equals(DocumentPermission.PUBLICO.getCode())) {
        LOG.info("El documento a consultar es PUBLICO.");
        return true;
      } else {
        // comprobamos primero los permisos por usuario
        permission = permisoPorUsuario(documentEntity, identificationType, nif);

        // Si el usuario no tiene permisos comprobamos si lo tiene la aplicación
        if (!permission) {
          permission = permisoPorAplicacion(applicacion, documentEntity);
        }
      }
    }

    LOG.info("[FIN] permisoLectura");
    return permission;
  }

  @Override
  public boolean permisoModificar(ApplicationEntity applicacion, DocumentEntity documentEntity) {

    LOG.info("[INI] permisoModificar");

    // Si es el propietario siempre tiene acceso a sus documentos
    if (documentEntity.getCreatedBy() != null
        && applicacion.getId().equals(documentEntity.getCreatedBy().getId())) {
      return true;
    }

    return false;
  }

  @Override
  public boolean permisOtorgar(ApplicationEntity applicacion, DocumentEntity documentEntity) {


    boolean permiso = false;
    if (documentEntity.getTipoPermiso().equals(DocumentPermission.RESTRINGIDO.getCode())) {
      List<DocumentAplicacionEntity> documentAplicacionList = new ArrayList<>();
      try {
        documentAplicacionList = documentManagerService
            .findDocumentAplicacionByDocumentAndApplication(documentEntity, applicacion);
      } catch (ServiceException e) {
        LOG.error(
            "Se ha producido un error al buscar las restricciones por aplicacion del documento"
                + e.getMessage());
      }

      if (!documentAplicacionList.isEmpty()) {
        permiso = true;
      }

    }

    return permiso;
  }

  @Override
  public boolean permisoDelete(ApplicationEntity applicacion, DocumentEntity documentEntity) {

    LOG.info("[INI] permisoDelete");

    // Si es el propietario siempre tiene acceso a sus documentos
    if (documentEntity.getCreatedBy() != null
        && applicacion.getId().equals(documentEntity.getCreatedBy().getId())) {
      return true;
    }

    return false;
  }

  private boolean permisoPorAplicacion(ApplicationEntity applicacion,
      DocumentEntity documentEntity) {
    LOG.info("[INI] permisoPorAplicacion");
    boolean permission = false;

    if (documentEntity.getTipoPermiso().equals(DocumentPermission.PUBLICO.getCode())) {
      LOG.info("El documento a consultar es PUBLICO.");
      permission = true;
    } else if (documentEntity.getTipoPermiso().equals(DocumentPermission.RESTRINGIDO.getCode())) {
      LOG.info("El documento a consultar es de acceso RESTRINGIDO.");
      List<DocumentAplicacionEntity> documentAplicacionList = new ArrayList<>();
      try {
        documentAplicacionList = documentManagerService
            .findDocumentAplicacionByDocumentAndApplication(documentEntity, applicacion);
      } catch (ServiceException e) {
        LOG.error(e.getMessage());
      }
      if (!documentAplicacionList.isEmpty()) {
        permission = true;
      }
    }

    LOG.info("Tiene permisos por aplicación: " + permission);
    LOG.info("[FIN] permisoPorAplicacion");
    return permission;
  }

  private boolean permisoPorUsuario(DocumentEntity documentEntity,
      IdentificationType identificationType, String nif) {
    LOG.info("[INI] permisoPorUsuario");

    // obtengo las restricciones que tiene dicho documento
    List<DocumentRestriccionEntity> listRestricciones = new ArrayList<>();
    try {
      listRestricciones = documentManagerService.findRestriccionesByDocument(documentEntity);
    } catch (ServiceException e) {
      LOG.error("Se ha producido un error al obtener las Restricciones del documento");
    }

    // obtengo todos los codigos de los permisos
    List<Integer> permisos = DocumentRestriccion.getFullCode(listRestricciones);

    boolean permisoRestringido = false;
    if (!permisos.isEmpty() && (permisos.contains(DocumentRestriccion.RESTRINGIDO_ID.getCode())
        || permisos.contains(DocumentRestriccion.RESTRINGIDO_NIF.getCode())
        || permisos.contains(DocumentRestriccion.RESTRINGIDO_PUB.getCode()))) {

      LOG.info("Se procede a validar los permisos por Usuario: TipoIdentificacion="
          + identificationType + "; nif=" + nif);

      // compruebo restricciones por Nif
      if (permisos.contains(DocumentRestriccion.RESTRINGIDO_NIF.getCode())) {
        LOG.info("El documento está restringido por NIF.");
        if (!StringUtils.isEmpty(nif)) {
          permisoRestringido = compruebaRestringidoNif(documentEntity, nif);
        } else {
          permisoRestringido = false;
          LOG.info("No se ha indicado ningun NIF para obtener el documento");
        }

      }

      if (!permisoRestringido && (permisos.contains(DocumentRestriccion.RESTRINGIDO_ID.getCode())
          || permisos.contains(DocumentRestriccion.RESTRINGIDO_PUB.getCode()))) {
        LOG.info("El documento está restringido por TipoIdentificacion y/o EmpleadoPublico.");
        if (identificationType == null) {
          permisoRestringido = false;
          LOG.warn("No se ha indicado ningun tipoIdentificacion para obtener el documento");
          return permisoRestringido;
        }
        // compruebo que el identificador esté en bbdd para dicho documento
        LOG.info("identificationType.getCode(): " + identificationType.getCode());
        if (permisos.contains(DocumentRestriccion.RESTRINGIDO_ID.getCode())) {
          LOG.info("El documento está restringido por TipoIdentificacion.");
          permisoRestringido = compruebaRestriccionId(documentEntity, identificationType);
        }

        if (!permisoRestringido
            && permisos.contains(DocumentRestriccion.RESTRINGIDO_PUB.getCode())) {
          LOG.info("El documento está restringido por EmpleadoPublico.");
          if (identificationType.getCode() != IdentificationType.EMPLEADO_PUBLICO.getCode()
              && identificationType.getCode() != IdentificationType.EMPLEADO_PUBLICO_PSEUD
                  .getCode()) {

            LOG.info("El usuario no es un empleado público");
          } else {
            permisoRestringido = true;
          }
        }
      }

    }

    LOG.info("Tiene permisos de usuario: " + permisoRestringido);

    LOG.info("[FIN] permisoPorUsuario");
    return permisoRestringido;
  }


  public boolean compruebaRestriccionId(DocumentEntity documentEntity,
      IdentificationType identificationType) {

    boolean restringidoPermitido = false;

    try {
      List<DocumentIdsEntity> listRestriccionesId = documentManagerService
          .findRestriccionesIDByDocument(documentEntity, identificationType.getCode());

      if (listRestriccionesId.isEmpty()) {
        LOG.error(
            "No contiene ningun ID asociado a tipoIdentificacion: " + identificationType.getCode());
      } else {
        restringidoPermitido = true;
      }

    } catch (ServiceException e) {
      LOG.error("Se ha producido un error al obtener las Restricciones de ID del documento");
    }

    return restringidoPermitido;
  }

  public boolean compruebaRestringidoNif(DocumentEntity documentEntity, String nif) {

    boolean restringidoPermitido = false;
    try {
      List<DocumentNifEntity> listNifs =
          documentManagerService.findNifsByDocument(documentEntity, nif);
      if (listNifs.isEmpty()) {
        LOG.warn("El usuario con NIF " + nif + " no tiene permiso para obtener el documento");
      } else {
        restringidoPermitido = true;
      }
    } catch (ServiceException e) {
      LOG.error("Se ha producido un error al obtener los NIFs del documento");
    }

    return restringidoPermitido;
  }

}

