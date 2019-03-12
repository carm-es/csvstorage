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


package es.gob.aapp.csvstorage.webservices.document.model.reference;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para TipoIdentificacionesPorCertificado.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="TipoIdentificacionesPorCertificado">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CLAVE_PERM"/>
 *     &lt;enumeration value="PIN24"/>
 *     &lt;enumeration value="DNIE"/>
 *     &lt;enumeration value="PF_2CA"/>
 *     &lt;enumeration value="PJ_2CA"/>
 *     &lt;enumeration value="EMPLEADO_PUBLICO"/>
 *     &lt;enumeration value="ENTIDAD_NO_PERSONA_JURIDICA"/>
 *     &lt;enumeration value="EMPLEADO_PUBLICO_PSEUD"/>
 *     &lt;enumeration value="REPRESENTACION_PJ"/>
 *     &lt;enumeration value="REPRESENTACION_ENTIDAD_SIN_PF"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoIdentificacionesPorCertificado")
@XmlEnum
public enum TipoIdentificacionesPorCertificado {

  CLAVE_PERM("CLAVE_PERM"), @XmlEnumValue("PIN24")
  PIN_24("PIN24"), DNIE("DNIE"), @XmlEnumValue("PF_2CA")
  PF_2_CA("PF_2CA"), @XmlEnumValue("PJ_2CA")
  PJ_2_CA("PJ_2CA"), EMPLEADO_PUBLICO("EMPLEADO_PUBLICO"), ENTIDAD_NO_PERSONA_JURIDICA(
      "ENTIDAD_NO_PERSONA_JURIDICA"), EMPLEADO_PUBLICO_PSEUD(
          "EMPLEADO_PUBLICO_PSEUD"), REPRESENTACION_PJ(
              "REPRESENTACION_PJ"), REPRESENTACION_ENTIDAD_SIN_PF("REPRESENTACION_ENTIDAD_SIN_PF");
  private final String value;

  TipoIdentificacionesPorCertificado(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static TipoIdentificacionesPorCertificado fromValue(String v) {
    for (TipoIdentificacionesPorCertificado c : TipoIdentificacionesPorCertificado.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
