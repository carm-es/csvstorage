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


package es.gob.aapp.csvstorage.client.ws.ginside;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.0.1 2017-01-25T16:10:19.274+01:00 Generated source
 * version: 3.0.1
 */

@WebFault(name = "errorInside",
    targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebService/types/error")
public class InsideWSException extends Exception {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 4099462759936685899L;
  private es.gob.aapp.csvstorage.client.ws.ginside.WSErrorInside errorInside;

  public InsideWSException() {
    super();
  }

  public InsideWSException(String message) {
    super(message);
  }

  public InsideWSException(String message, Throwable cause) {
    super(message, cause);
  }

  public InsideWSException(String message,
      es.gob.aapp.csvstorage.client.ws.ginside.WSErrorInside errorInside) {
    super(message);
    this.errorInside = errorInside;
  }

  public InsideWSException(String message,
      es.gob.aapp.csvstorage.client.ws.ginside.WSErrorInside errorInside, Throwable cause) {
    super(message, cause);
    this.errorInside = errorInside;
  }

  public es.gob.aapp.csvstorage.client.ws.ginside.WSErrorInside getFaultInfo() {
    return this.errorInside;
  }
}
