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
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.util.constants.Constants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "restricciones", propOrder = {"restriccion"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class ListaRestriccion implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Lista de cadenas con los códigos de los organismos.
   */
  @XmlElement(required = false, name = "restriccion")
  private List<ListaRestriccion.Restriccion> restriccion =
      new ArrayList<ListaRestriccion.Restriccion>();

  /**
   * Constructor por defecto.
   */
  public ListaRestriccion() {
    super();
  }

  /**
   * Constructor con parámetros.
   *
   * @param restriccion
   */
  public ListaRestriccion(List<ListaRestriccion.Restriccion> restriccion) {
    super();
    this.restriccion = restriccion;
  }

  public List<ListaRestriccion.Restriccion> getRestriccion() {
    return restriccion;
  }

  public void setRestriccion(List<ListaRestriccion.Restriccion> restriccion) {
    this.restriccion = restriccion;
  }

  @Override
  public String toString() {
    return "RestriccionList [restriccion=" + restriccion + "]";
  }

  /**
   * <p>
   * Clase Java para restriccion.
   * 
   * <p>
   * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
   * <p>
   * 
   * <pre>
   * &lt;simpleType name="restriccion">
   *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
   *     &lt;enumeration value="RESTRINGIDO_ID"/>
   *     &lt;enumeration value="RESTRINGIDO_NIF"/>
   *     &lt;enumeration value="RESTRINGIDO_PUB"/>
   *     &lt;enumeration value="RESTRINGIDO_APP"/>
   *   &lt;/restriction>
   * &lt;/simpleType>
   * </pre>
   * 
   */

  @XmlType(name = "restriccion")
  @XmlEnum
  public enum Restriccion {
    @XmlEnumValue("RESTRINGIDO_ID")
    RESTRINGIDO_ID("RESTRINGIDO_ID"),

    @XmlEnumValue("RESTRINGIDO_NIF")
    RESTRINGIDO_NIF("RESTRINGIDO_NIF"),

    @XmlEnumValue("RESTRINGIDO_PUB")
    RESTRINGIDO_PUB("RESTRINGIDO_PUB"),

    @XmlEnumValue("RESTRINGIDO_APP")
    RESTRINGIDO_APP("RESTRINGIDO_APP");
    private final String value;

    Restriccion(String v) {
      value = v;
    }

    public String value() {
      return value;
    }

    public static Restriccion fromValue(String v) {
      for (Restriccion c : Restriccion.values()) {
        if (c.value.equals(v)) {
          return c;
        }
      }
      throw new IllegalArgumentException(v);
    }

  }

  public String parametrosAuditoria() {
    StringBuilder audit = new StringBuilder().append("");
    final String LABEL_RESTRICCION = "restriccion" + Constants.AUDIT_SEPARATOR_VALUE;
    if (restriccion != null) {
      boolean primero = true;
      for (ListaRestriccion.Restriccion rest : restriccion) {
        if (rest != null) {
          if (primero) {
            primero = false;
            audit.append(LABEL_RESTRICCION).append(rest.value);
          } else {
            audit.append(Constants.AUDIT_SEPARATOR_ROW).append(LABEL_RESTRICCION)
                .append(rest.value);
          }
        }
      }
    }

    return audit.toString();
  }
}
