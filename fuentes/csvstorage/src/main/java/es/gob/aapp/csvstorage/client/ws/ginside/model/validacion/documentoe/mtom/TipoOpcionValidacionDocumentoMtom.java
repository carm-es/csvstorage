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


package es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.documentoe.mtom;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para TipoOpcionValidacionDocumentoMtom.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="TipoOpcionValidacionDocumentoMtom">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TOVD01"/>
 *     &lt;enumeration value="TOVD02"/>
 *     &lt;enumeration value="TOVD03"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoOpcionValidacionDocumentoMtom")
@XmlEnum
public enum TipoOpcionValidacionDocumentoMtom {

  @XmlEnumValue("TOVD01")
  TOVD_01("TOVD01"), @XmlEnumValue("TOVD02")
  TOVD_02("TOVD02"), @XmlEnumValue("TOVD03")
  TOVD_03("TOVD03");
  private final String value;

  TipoOpcionValidacionDocumentoMtom(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static TipoOpcionValidacionDocumentoMtom fromValue(String v) {
    for (TipoOpcionValidacionDocumentoMtom c : TipoOpcionValidacionDocumentoMtom.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
