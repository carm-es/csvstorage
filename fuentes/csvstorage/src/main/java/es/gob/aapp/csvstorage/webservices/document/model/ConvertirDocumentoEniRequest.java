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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.metadatos.TipoDocumental;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.metadatos.TipoEstadoElaboracion;
import es.gob.aapp.csvstorage.util.constants.Constants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "convertirDocumentoEniRequest", propOrder = {"dir3", "csv", "metadatosEni"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class ConvertirDocumentoEniRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Código DIR3.
   */
  @XmlElement(required = true)
  protected String dir3;
  /**
   * csv del documento de firma.
   */
  @XmlElement(required = true)
  protected String csv;

  /**
   * Metadatos obligatorios del documento.
   */
  @XmlElement(required = true)
  protected MetadatosEni metadatosEni;


  /**
   * @return the csv
   */
  public String getCsv() {
    return csv;
  }

  /**
   * @param csv the csv to set
   */
  public void setCsv(String csv) {
    this.csv = csv;
  }

  /**
   * 
   * /**
   * 
   * @return the dir3
   */
  public String getDir3() {
    return dir3;
  }

  /**
   * @param dir3 the dir3 to set
   */
  public void setDir3(String dir3) {
    this.dir3 = dir3;
  }



  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "GuardarDocumentoEniRequest [dir3=" + dir3 + ", csv=" + csv + ", metadatosEni="
        + metadatosEni + "]";
  }

  /**
   * @return the metadatosEni
   */
  public MetadatosEni getMetadatosEni() {
    return metadatosEni;
  }

  /**
   * @param metadatosEni the metadatosEni to set
   */
  public void setMetadatosEni(MetadatosEni metadatosEni) {
    this.metadatosEni = metadatosEni;
  }

  public String parametrosAuditoria() {
    String audit = "dir3" + Constants.AUDIT_SEPARATOR_VALUE + this.dir3;
    audit += Constants.AUDIT_SEPARATOR_ROW + "csv" + Constants.AUDIT_SEPARATOR_VALUE + this.csv;
    audit += Constants.AUDIT_SEPARATOR_ROW + this.metadatosEni.parametrosAuditoria();

    return audit;
  }

  /**
   * <p>
   * Clase Java para anonymous complex type.
   * 
   * <p>
   * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
   * 
   * <pre>
   * &lt;complexType>
   *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *       &lt;sequence>
   *         &lt;element name="VersionNTI" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *         &lt;element name="Identificador" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *         &lt;element name="Organo" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
   *         &lt;element name="FechaCaptura" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
   *         &lt;element name="OrigenCiudadanoAdministracion" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
   *         &lt;element name="EstadoElaboracion" type="{http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/metadatos}TipoEstadoElaboracion"/>
   *         &lt;element name="TipoDocumental" type="{http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/metadatos}tipoDocumental"/>
   *       &lt;/sequence>
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   * 
   * 
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = {"versionNTI", "identificador", "organo", "fechaCaptura",
      "origenCiudadanoAdministracion", "estadoElaboracion", "tipoDocumental"})
  public static class MetadatosEni {

    @XmlElement(name = "VersionNTI", required = true)
    protected String versionNTI;
    @XmlElement(name = "Identificador", required = true)
    protected String identificador;
    @XmlElement(name = "Organo", required = true)
    protected List<String> organo;
    @XmlElement(name = "FechaCaptura", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaCaptura;
    @XmlElement(name = "OrigenCiudadanoAdministracion")
    protected boolean origenCiudadanoAdministracion;
    @XmlElement(name = "EstadoElaboracion", required = true)
    protected TipoEstadoElaboracion estadoElaboracion;
    @XmlElement(name = "TipoDocumental", required = true)
    @XmlSchemaType(name = "string")
    protected TipoDocumental tipoDocumental;

    /**
     * Obtiene el valor de la propiedad versionNTI.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getVersionNTI() {
      return versionNTI;
    }

    /**
     * Define el valor de la propiedad versionNTI.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setVersionNTI(String value) {
      this.versionNTI = value;
    }

    /**
     * Obtiene el valor de la propiedad identificador.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIdentificador() {
      return identificador;
    }

    /**
     * Define el valor de la propiedad identificador.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setIdentificador(String value) {
      this.identificador = value;
    }

    /**
     * Gets the value of the organo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the organo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getOrgano().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     * 
     * 
     */
    public List<String> getOrgano() {
      if (organo == null) {
        organo = new ArrayList<String>();
      }
      return this.organo;
    }

    /**
     * Obtiene el valor de la propiedad fechaCaptura.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getFechaCaptura() {
      return fechaCaptura;
    }

    /**
     * Define el valor de la propiedad fechaCaptura.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setFechaCaptura(XMLGregorianCalendar value) {
      this.fechaCaptura = value;
    }

    /**
     * Obtiene el valor de la propiedad origenCiudadanoAdministracion.
     * 
     */
    public boolean isOrigenCiudadanoAdministracion() {
      return origenCiudadanoAdministracion;
    }

    /**
     * Define el valor de la propiedad origenCiudadanoAdministracion.
     * 
     */
    public void setOrigenCiudadanoAdministracion(boolean value) {
      this.origenCiudadanoAdministracion = value;
    }

    /**
     * Obtiene el valor de la propiedad estadoElaboracion.
     * 
     * @return possible object is {@link TipoEstadoElaboracion }
     * 
     */
    public TipoEstadoElaboracion getEstadoElaboracion() {
      return estadoElaboracion;
    }

    /**
     * Define el valor de la propiedad estadoElaboracion.
     * 
     * @param value allowed object is {@link TipoEstadoElaboracion }
     * 
     */
    public void setEstadoElaboracion(TipoEstadoElaboracion value) {
      this.estadoElaboracion = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoDocumental.
     * 
     * @return possible object is {@link TipoDocumental }
     * 
     */
    public TipoDocumental getTipoDocumental() {
      return tipoDocumental;
    }

    /**
     * Define el valor de la propiedad tipoDocumental.
     * 
     * @param value allowed object is {@link TipoDocumental }
     * 
     */
    public void setTipoDocumental(TipoDocumental value) {
      this.tipoDocumental = value;
    }


    public String parametrosAuditoria() {
      String audit = "VersionNTI" + Constants.AUDIT_SEPARATOR_VALUE + this.versionNTI;
      audit += Constants.AUDIT_SEPARATOR_ROW + "Identificador" + Constants.AUDIT_SEPARATOR_VALUE
          + this.identificador;
      for (String org : organo) {
        audit += Constants.AUDIT_SEPARATOR_ROW + "Organo" + Constants.AUDIT_SEPARATOR_VALUE + org;
      }
      audit += Constants.AUDIT_SEPARATOR_ROW + "FechaCaptura" + Constants.AUDIT_SEPARATOR_VALUE
          + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
              .format(this.fechaCaptura.toGregorianCalendar().getTime());
      String boolStrOrigen = (this.origenCiudadanoAdministracion) ? "true" : "false";
      audit += Constants.AUDIT_SEPARATOR_ROW + "origenCiudadanoAdministracion"
          + Constants.AUDIT_SEPARATOR_VALUE + boolStrOrigen;
      audit += Constants.AUDIT_SEPARATOR_ROW + "EstadoElaboracion" + Constants.AUDIT_SEPARATOR_VALUE
          + this.estadoElaboracion.getValorEstadoElaboracion().value();
      audit += Constants.AUDIT_SEPARATOR_ROW + "TipoDocumental" + Constants.AUDIT_SEPARATOR_VALUE
          + this.tipoDocumental.value();

      return audit;
    }
  }
}
