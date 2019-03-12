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

import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.util.constants.IdentificationType;



/**
 * Servicios business de validación.
 * 
 * @author carlos.munoz1
 * 
 */
public interface ValPermissionBusinessService {

  /**
   * Validar los permisos de lectura del documento
   * 
   * @param applicacion
   * @param documentEntity
   * @param identificationType
   * @param nif
   * @return error
   */
  boolean permisoLectura(ApplicationEntity applicacion, DocumentEntity documentEntity,
      IdentificationType identificationType, String nif, String calledFrom);

  boolean permisoModificar(ApplicationEntity applicacion, DocumentEntity documentEntity);

  boolean permisOtorgar(ApplicationEntity applicacion, DocumentEntity documentEntity);

  boolean permisoDelete(ApplicationEntity applicacion, DocumentEntity documentEntity);

}
