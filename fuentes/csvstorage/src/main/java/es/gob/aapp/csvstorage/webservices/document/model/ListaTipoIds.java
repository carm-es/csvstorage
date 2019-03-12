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
@XmlType(name = "restringidoPorIdentificacion", propOrder = {"identificacion"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class ListaTipoIds implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Lista de cadenas con los códigos de los organismos.
   */
  @XmlElement(required = true, name = "identificacion")
  private List<ListaTipoIds.TipoId> identificacion = new ArrayList<ListaTipoIds.TipoId>();

  /**
   * Constructor por defecto.
   */
  public ListaTipoIds() {
    super();
  }

  /**
   * Constructor con parámetros.
   *
   * @param identificacion
   */
  public ListaTipoIds(List<ListaTipoIds.TipoId> identificacion) {
    super();
    this.identificacion = identificacion;
  }

  public List<ListaTipoIds.TipoId> getId() {
    return identificacion;
  }

  public void setId(List<ListaTipoIds.TipoId> identificacion) {
    this.identificacion = identificacion;
  }

  @Override
  public String toString() {
    return "IdList [id=" + identificacion + "]";
  }


  @XmlType(name = "tipoId")
  @XmlEnum
  public enum TipoId {

    @XmlEnumValue("CLAVE_PERM")
    CLAVE_PERM("CLAVE_PERM"), @XmlEnumValue("PIN24")
    PIN24("PIN24"), @XmlEnumValue("DNIE")
    DNIE("DNIE"), @XmlEnumValue("PF_2CA")
    PF_2CA("PF_2CA"), @XmlEnumValue("PJ_2CA")
    PJ_2CA("PJ_2CA"), @XmlEnumValue("COMPONENTESSL")
    COMPONENTESSL("COMPONENTESSL"), @XmlEnumValue("SEDE_ELECTRONICA")
    SEDE_ELECTRONICA("SEDE_ELECTRONICA"), @XmlEnumValue("SELLO_ORGANO")
    SELLO_ORGANO("SELLO_ORGANO"), @XmlEnumValue("EMPLEADO_PUBLICO")
    EMPLEADO_PUBLICO("EMPLEADO_PUBLICO"), @XmlEnumValue("ENTIDAD_NO_PERSONA_JURIDICA")
    ENTIDAD_NO_PERSONA_JURIDICA(
        "ENTIDAD_NO_PERSONA_JURIDICA"), @XmlEnumValue("EMPLEADO_PUBLICO_PSEUD")
    EMPLEADO_PUBLICO_PSEUD("EMPLEADO_PUBLICO_PSEUD"), @XmlEnumValue("CUALIFICADO_SELLO_ENTIDAD")
    CUALIFICADO_SELLO_ENTIDAD(
        "CUALIFICADO_SELLO_ENTIDAD"), @XmlEnumValue("CUALIFICADO_AUTENTICACION")
    CUALIFICADO_AUTENTICACION(
        "CUALIFICADO_AUTENTICACION"), @XmlEnumValue("CUALIFICADO_SELLO_TIEMPO")
    CUALIFICADO_SELLO_TIEMPO("CUALIFICADO_SELLO_TIEMPO"), @XmlEnumValue("REPRESENTACION_PJ")
    REPRESENTACION_PJ("REPRESENTACION_PJ"), @XmlEnumValue("REPRESENTACION_ENTIDAD_SIN_PF")
    REPRESENTACION_ENTIDAD_SIN_PF("REPRESENTACION_ENTIDAD_SIN_PF"),;
    private final String value;

    TipoId(String v) {
      value = v;
    }

    public String value() {
      return value;
    }

    public static TipoId fromValue(String v) {
      for (TipoId c : TipoId.values()) {
        if (c.value.equals(v)) {
          return c;
        }
      }
      throw new IllegalArgumentException(v);
    }

  }

  public String parametrosAuditoria() {
    StringBuilder audit = new StringBuilder().append("");
    final String LABEL_TIPOID = "tipoId" + Constants.AUDIT_SEPARATOR_VALUE;
    if (identificacion != null) {
      boolean primero = true;
      for (ListaTipoIds.TipoId ident : identificacion) {
        if (ident != null) {
          if (primero) {
            primero = false;
            audit.append(LABEL_TIPOID).append(ident.value);
          } else {
            audit.append(Constants.AUDIT_SEPARATOR_ROW).append(LABEL_TIPOID).append(ident.value);
          }
        }
      }
    }

    return audit.toString();
  }
}
