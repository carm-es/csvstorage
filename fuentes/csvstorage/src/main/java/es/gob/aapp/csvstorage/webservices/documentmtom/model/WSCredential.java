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

package es.gob.aapp.csvstorage.webservices.documentmtom.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSCredential", propOrder = {"idaplicacion", "password"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:documentmtom:model:v1.0")
public class WSCredential implements Serializable {

  private final static long serialVersionUID = -1L;
  @XmlElement(required = true)
  protected String idaplicacion;
  @XmlElement(required = true)
  protected String password;

  /**
   * Gets the value of the idaplicacion property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getIdaplicacion() {
    return idaplicacion;
  }

  /**
   * Sets the value of the idaplicacion property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setIdaplicacion(String value) {
    this.idaplicacion = value;
  }

  /**
   * Gets the value of the password property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the value of the password property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setPassword(String value) {
    this.password = value;
  }

}
