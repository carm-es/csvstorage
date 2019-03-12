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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.util.constants.Constants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "restringidoNif", propOrder = {"nif"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class ListaNifs implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Lista de cadenas con los códigos de los organismos.
   */
  @XmlElement(required = true, name = "nif")
  private List<String> nif = new ArrayList<String>();

  /**
   * Constructor por defecto.
   */
  public ListaNifs() {
    super();
  }

  /**
   * Constructor con parámetros.
   * 
   * @param nif
   */
  public ListaNifs(List<String> nif) {
    super();
    this.nif = nif;
  }

  public List<String> getNif() {
    return nif;
  }

  public void setNif(List<String> nif) {
    this.nif = nif;
  }

  @Override
  public String toString() {
    return "NifList [nif=" + nif + "]";
  }

  public String parametrosAuditoria() {
    StringBuilder audit = new StringBuilder().append("");
    final String LABEL_NIF = "nif" + Constants.AUDIT_SEPARATOR_VALUE;
    if (nif != null) {
      boolean primero = true;
      for (String ni : nif) {
        if (primero) {
          primero = false;
          audit.append(LABEL_NIF).append(ni);
        } else {
          audit.append(Constants.AUDIT_SEPARATOR_ROW).append(LABEL_NIF).append(ni);
        }
      }
    }

    return audit.toString();
  }
}
