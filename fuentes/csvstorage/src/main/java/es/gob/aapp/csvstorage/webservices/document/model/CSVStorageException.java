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

package es.gob.aapp.csvstorage.webservices.document.model;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "es.gob.aapp.csvstorage.webservices.document.model.ExceptionInfo",
    name = "errorInfo",
    targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class CSVStorageException extends Exception {

  private static final long serialVersionUID = 1L;

  private ExceptionInfo exceptionInfo;

  /**
   * Sole constructor.
   */
  public CSVStorageException() {
    super();
  }

  /**
   * Constructor with parameters.
   * 
   * @param message Exception message.
   */
  public CSVStorageException(String message) {
    super(message);
  }

  /**
   * Constructor with parameters.
   * 
   * @param message Exception message.
   * @param cause Exception cause.
   */
  public CSVStorageException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor with parameters.
   * 
   * @param message Exception message.
   * @param exceptionInfo Exception info.
   */
  public CSVStorageException(String message, ExceptionInfo exceptionInfo) {
    super(message);
    this.exceptionInfo = exceptionInfo;
  }

  /**
   * Constructor with parameters.
   * 
   * @param message Exception message.
   * @param cause Exception cause.
   * @param exceptionInfo Exception info.
   */
  public CSVStorageException(String message, ExceptionInfo exceptionInfo, Throwable cause) {
    super(message, cause);
    this.exceptionInfo = exceptionInfo;
  }

  /**
   * Return ws fault information.
   * 
   * @return Fault information.
   */
  public ExceptionInfo getFaultInfo() {
    return this.exceptionInfo;
  }

  public ExceptionInfo getExceptionInfo() {
    return this.exceptionInfo;
  }

}
