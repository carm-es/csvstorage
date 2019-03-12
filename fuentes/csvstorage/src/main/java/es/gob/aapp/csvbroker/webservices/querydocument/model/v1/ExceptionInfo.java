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

package es.gob.aapp.csvbroker.webservices.querydocument.model.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExceptionInfo",
    namespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0",
    propOrder = {"code", "description"})
public class ExceptionInfo {

  @XmlElement(namespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0")
  protected int code;
  @XmlElement(namespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0",
      required = true)
  protected String description;

  /**
   * Constructor por defecto.
   */
  public ExceptionInfo() {
    super();
  }



  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
