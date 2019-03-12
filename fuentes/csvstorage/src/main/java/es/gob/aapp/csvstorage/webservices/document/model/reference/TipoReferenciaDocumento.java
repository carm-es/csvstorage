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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para TipoReferenciaDocumento complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TipoReferenciaDocumento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Identificador" type="{}TipoIdentificador"/>
 *         &lt;element name="Permiso" type="{}TipoPermisos"/>
 *         &lt;element name="eEMGDE.Firma.TipoFirma.FormatoFirma" type="{}FormatoFirma" minOccurs="0"/>
 *         &lt;element name="Hash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Direccion" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="URLVisible" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="Emisor" type="{}TipoEmisor" minOccurs="0"/>
 *         &lt;element name="Receptor" type="{}TipoReceptor" minOccurs="0"/>
 *         &lt;element name="Metadatos" type="{}TipoMetadatos" minOccurs="0"/>
 *         &lt;element name="eEMGDE.Trazabilidad" type="{}TipoTrazabilidad" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoReferenciaDocumento",
    propOrder = {"identificador", "permiso", "eemgdeFirmaTipoFirmaFormatoFirma", "hash",
        "direccion", "urlVisible", "emisor", "receptor", "metadatos", "eemgdeTrazabilidad"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class TipoReferenciaDocumento {

  @XmlElement(name = "Identificador", required = true)
  protected TipoIdentificador identificador;
  @XmlElement(name = "Permiso", required = true)
  protected TipoPermisos permiso;
  @XmlElement(name = "eEMGDE.Firma.TipoFirma.FormatoFirma")
  @XmlSchemaType(name = "string")
  protected FormatoFirma eemgdeFirmaTipoFirmaFormatoFirma;
  @XmlElement(name = "Hash")
  protected String hash;
  @XmlElement(name = "Direccion", required = true)
  @XmlSchemaType(name = "anyURI")
  protected String direccion;
  @XmlElement(name = "URLVisible")
  @XmlSchemaType(name = "anyURI")
  protected String urlVisible;
  @XmlElement(name = "Emisor")
  protected TipoEmisor emisor;
  @XmlElement(name = "Receptor")
  protected TipoReceptor receptor;
  @XmlElement(name = "Metadatos")
  protected TipoMetadatos metadatos;
  @XmlElement(name = "eEMGDE.Trazabilidad")
  protected TipoTrazabilidad eemgdeTrazabilidad;

  /**
   * Obtiene el valor de la propiedad identificador.
   * 
   * @return possible object is {@link TipoIdentificador }
   * 
   */
  public TipoIdentificador getIdentificador() {
    return identificador;
  }

  /**
   * Define el valor de la propiedad identificador.
   * 
   * @param value allowed object is {@link TipoIdentificador }
   * 
   */
  public void setIdentificador(TipoIdentificador value) {
    this.identificador = value;
  }

  /**
   * Obtiene el valor de la propiedad permiso.
   * 
   * @return possible object is {@link TipoPermisos }
   * 
   */
  public TipoPermisos getPermiso() {
    return permiso;
  }

  /**
   * Define el valor de la propiedad permiso.
   * 
   * @param value allowed object is {@link TipoPermisos }
   * 
   */
  public void setPermiso(TipoPermisos value) {
    this.permiso = value;
  }

  /**
   * Obtiene el valor de la propiedad eemgdeFirmaTipoFirmaFormatoFirma.
   * 
   * @return possible object is {@link FormatoFirma }
   * 
   */
  public FormatoFirma getEEMGDEFirmaTipoFirmaFormatoFirma() {
    return eemgdeFirmaTipoFirmaFormatoFirma;
  }

  /**
   * Define el valor de la propiedad eemgdeFirmaTipoFirmaFormatoFirma.
   * 
   * @param value allowed object is {@link FormatoFirma }
   * 
   */
  public void setEEMGDEFirmaTipoFirmaFormatoFirma(FormatoFirma value) {
    this.eemgdeFirmaTipoFirmaFormatoFirma = value;
  }

  /**
   * Obtiene el valor de la propiedad hash.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getHash() {
    return hash;
  }

  /**
   * Define el valor de la propiedad hash.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setHash(String value) {
    this.hash = value;
  }

  /**
   * Obtiene el valor de la propiedad direccion.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getDireccion() {
    return direccion;
  }

  /**
   * Define el valor de la propiedad direccion.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setDireccion(String value) {
    this.direccion = value;
  }

  /**
   * Obtiene el valor de la propiedad urlVisible.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getURLVisible() {
    return urlVisible;
  }

  /**
   * Define el valor de la propiedad urlVisible.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setURLVisible(String value) {
    this.urlVisible = value;
  }

  /**
   * Obtiene el valor de la propiedad emisor.
   * 
   * @return possible object is {@link TipoEmisor }
   * 
   */
  public TipoEmisor getEmisor() {
    return emisor;
  }

  /**
   * Define el valor de la propiedad emisor.
   * 
   * @param value allowed object is {@link TipoEmisor }
   * 
   */
  public void setEmisor(TipoEmisor value) {
    this.emisor = value;
  }

  /**
   * Obtiene el valor de la propiedad receptor.
   * 
   * @return possible object is {@link TipoReceptor }
   * 
   */
  public TipoReceptor getReceptor() {
    return receptor;
  }

  /**
   * Define el valor de la propiedad receptor.
   * 
   * @param value allowed object is {@link TipoReceptor }
   * 
   */
  public void setReceptor(TipoReceptor value) {
    this.receptor = value;
  }

  /**
   * Obtiene el valor de la propiedad metadatos.
   * 
   * @return possible object is {@link TipoMetadatos }
   * 
   */
  public TipoMetadatos getMetadatos() {
    return metadatos;
  }

  /**
   * Define el valor de la propiedad metadatos.
   * 
   * @param value allowed object is {@link TipoMetadatos }
   * 
   */
  public void setMetadatos(TipoMetadatos value) {
    this.metadatos = value;
  }

  /**
   * Obtiene el valor de la propiedad eemgdeTrazabilidad.
   * 
   * @return possible object is {@link TipoTrazabilidad }
   * 
   */
  public TipoTrazabilidad getEEMGDETrazabilidad() {
    return eemgdeTrazabilidad;
  }

  /**
   * Define el valor de la propiedad eemgdeTrazabilidad.
   * 
   * @param value allowed object is {@link TipoTrazabilidad }
   * 
   */
  public void setEEMGDETrazabilidad(TipoTrazabilidad value) {
    this.eemgdeTrazabilidad = value;
  }

}
