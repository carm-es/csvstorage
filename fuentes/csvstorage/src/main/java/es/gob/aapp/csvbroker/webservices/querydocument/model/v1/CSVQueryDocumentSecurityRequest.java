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
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.util.constants.Constants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSVQueryDocumentSecurityRequest", propOrder = {"nif", "tipoIdentificacion", "ip"},
    namespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0")
public class CSVQueryDocumentSecurityRequest extends CSVQueryDocumentRequest {

  @XmlElement(required = false, name = "nif")
  private String nif;

  @XmlElement(required = false, name = "tipoIdentificacion")
  private CSVQueryDocumentSecurityRequest.TipoIdentificacion tipoIdentificacion;

  @XmlElement(required = false, name = "ip")
  private String ip;


  /**
   * @return the nif
   */
  public String getNif() {
    return nif;
  }

  /**
   * @param nif the nif to set
   */
  public void setNif(String nif) {
    this.nif = nif;
  }

  /**
   * @return the tipoPermiso
   */
  public CSVQueryDocumentSecurityRequest.TipoIdentificacion getTipoIdentificacion() {
    return tipoIdentificacion;
  }

  /**
   * @param tipoIdentificacion the tipoPermiso to set
   */
  public void setTipoIdentificacion(
      CSVQueryDocumentSecurityRequest.TipoIdentificacion tipoIdentificacion) {
    this.tipoIdentificacion = tipoIdentificacion;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * <p>
   * Clase Java para tipoIdentificacion.
   * 
   * <p>
   * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
   * <p>
   * 
   * <pre>
   * &lt;simpleType name="tipoIdentificacion">
   *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
   *     &lt;enumeration value="CLAVE_PERM"/>
   *     &lt;enumeration value="PIN24"/>
   *     &lt;enumeration value="DNIE"/>
   *     &lt;enumeration value="PF_2CA"/>
   *     &lt;enumeration value="PJ_2CA"/>
   *     &lt;enumeration value="COMPONENTESSL"/>
   *     &lt;enumeration value="SEDE_ELECTRONICA"/>	 
   *     &lt;enumeration value="SELLO_ORGANO"/>	 
   *     &lt;enumeration value="EMPLEADO_PUBLICO"/>	 
   *     &lt;enumeration value="ENTIDAD_NO_PERSONA_JURIDICA"/>	 
   *     &lt;enumeration value="EMPLEADO_PUBLICO_PSEUD"/>	 
   *     &lt;enumeration value="CUALIFICADO_SELLO_ENTIDAD"/>	 	      
   *     &lt;enumeration value="CUALIFICADO_AUTENTICACION"/>
   *     &lt;enumeration value="CUALIFICADO_SELLO_TIEMPO"/>
   *     &lt;enumeration value="REPRESENTACION_PJ"/>
   *     &lt;enumeration value="REPRESENTACION_ENTIDAD_SIN_PF"/>
   *   &lt;/restriction>
   * &lt;/simpleType>
   * </pre>
   * 
   */

  @XmlType(name = "tipoIdentificacion")
  @XmlEnum
  public enum TipoIdentificacion {

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
    REPRESENTACION_ENTIDAD_SIN_PF("REPRESENTACION_ENTIDAD_SIN_PF");
    private final String value;

    TipoIdentificacion(String v) {
      value = v;
    }

    public String value() {
      return value;
    }

    public static TipoIdentificacion fromValue(String v) {
      for (TipoIdentificacion c : TipoIdentificacion.values()) {
        if (c.value.equals(v)) {
          return c;
        }
      }
      throw new IllegalArgumentException(v);
    }

  }

  @Override
  public String parametrosAuditoria() {
    String audit = super.parametrosAuditoria();
    audit += (this.nif != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "nif" + Constants.AUDIT_SEPARATOR_VALUE + this.nif
        : "";
    audit +=
        (this.tipoIdentificacion != null)
            ? Constants.AUDIT_SEPARATOR_ROW + "tipoIdentificacion" + Constants.AUDIT_SEPARATOR_VALUE
                + this.tipoIdentificacion.value()
            : "";
    audit += (this.ip != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "ip" + Constants.AUDIT_SEPARATOR_VALUE + this.ip
        : "";

    return audit;
  }
}
