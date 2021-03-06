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

package es.gob.aapp.csvstorage.webservices.administration.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.util.constants.Errors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exceptionInfo", propOrder = {"code", "description"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:administration:model:v1.0")
public class ExceptionInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * C�digo del error.
   */
  @XmlElement(required = true, name = "code")
  protected String code;

  /**
   * Descripci�n del error.
   */
  @XmlElement(required = true, name = "description")
  protected String description;

  /**
   * Constructor por defecto.
   */
  public ExceptionInfo() {
    super();
  }

  /**
   * Constructor a partir del enum Errors.
   * 
   * @param code
   * @param description
   */
  public ExceptionInfo(Errors error) {
    super();
    this.code = String.valueOf(error.getCode());
    this.description = error.getDescription();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
