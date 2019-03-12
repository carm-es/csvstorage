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

package es.gob.aapp.csvstorage.services.business.validation;

import javax.activation.DataHandler;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.webservices.document.model.Response;



/**
 * Servicios business de validación.
 * 
 * @author carlos.munoz1
 * 
 */
public interface ValidationBusinessService {

  /**
   * Valida un documento ENI
   * 
   * @param contenido
   * @return
   */
  String validarDocumentoEni(DataHandler contenido);


  /**
   * Validar unidad orgánica
   * 
   * @param dir3
   * @return lista de unidades
   * @throws ConsumerWSException
   */
  UnitEntity findUnidadOrganicaByDir3(String dir3) throws ConsumerWSException;

  /**
   * Realiza la validación de los metadatos de los documentos ENI
   * 
   * @param documentEniEntity
   * @param dir3
   * @param response
   * @return
   * @throws ServiceException
   */
  boolean validarMetadatosEni(DocumentEniEntity documentEniEntity, String dir3, Response response)
      throws ServiceException;

  /**
   * Valida que se hayan informado los datos obligatorios
   * 
   * @param response
   * @param documentObject
   * @return
   * @throws ConsumerWSException
   */
  boolean validarDatosObligatorios(Response response, DocumentObject documentObject)
      throws ConsumerWSException;

  /**
   * Realiza las validaciones para permitir guardar el documento
   * 
   * @param response
   * @param aplicacion
   * @param documentObject
   * @return
   * @throws ConsumerWSException
   * @throws ServiceException
   */
  boolean validacionGuardado(Response response, ApplicationEntity aplicacion,
      DocumentObject documentObject) throws ConsumerWSException, ServiceException;

  /**
   * Realiza las validaciones para permitir guardar el documento
   * 
   * @param response
   * @param aplicacion
   * @param documentObject
   * @return
   * @throws ConsumerWSException
   * @throws ServiceException
   */
  DocumentEntity validacionModificado(Response response, ApplicationEntity aplicacion,
      DocumentObject documentObject) throws ConsumerWSException, ServiceException;

  /**
   * Realiza las validaciones para permitir guardar los permisos
   * 
   * @param response
   * @param aplicacion
   * @param documentObject
   * @return
   * @throws ConsumerWSException
   * @throws ServiceException
   */
  DocumentEntity validacionOtorgarPermiso(Response response, ApplicationEntity aplicacion,
      DocumentObject documentObject) throws ConsumerWSException, ServiceException;

}
