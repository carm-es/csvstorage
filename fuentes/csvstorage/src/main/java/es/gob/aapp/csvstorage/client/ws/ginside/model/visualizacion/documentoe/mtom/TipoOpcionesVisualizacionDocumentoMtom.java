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


package es.gob.aapp.csvstorage.client.ws.ginside.model.visualizacion.documentoe.mtom;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para TipoOpcionesVisualizacionDocumentoMtom complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TipoOpcionesVisualizacionDocumentoMtom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EstamparImagen" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="EstamparNombreOrganismo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="FilasNombreOrganismo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Fila" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoOpcionesVisualizacionDocumentoMtom",
    propOrder = {"estamparImagen", "estamparNombreOrganismo", "filasNombreOrganismo"})
public class TipoOpcionesVisualizacionDocumentoMtom {

  @XmlElement(name = "EstamparImagen")
  protected boolean estamparImagen;
  @XmlElement(name = "EstamparNombreOrganismo")
  protected boolean estamparNombreOrganismo;
  @XmlElement(name = "FilasNombreOrganismo")
  protected TipoOpcionesVisualizacionDocumentoMtom.FilasNombreOrganismo filasNombreOrganismo;

  /**
   * Obtiene el valor de la propiedad estamparImagen.
   * 
   */
  public boolean isEstamparImagen() {
    return estamparImagen;
  }

  /**
   * Define el valor de la propiedad estamparImagen.
   * 
   */
  public void setEstamparImagen(boolean value) {
    this.estamparImagen = value;
  }

  /**
   * Obtiene el valor de la propiedad estamparNombreOrganismo.
   * 
   */
  public boolean isEstamparNombreOrganismo() {
    return estamparNombreOrganismo;
  }

  /**
   * Define el valor de la propiedad estamparNombreOrganismo.
   * 
   */
  public void setEstamparNombreOrganismo(boolean value) {
    this.estamparNombreOrganismo = value;
  }

  /**
   * Obtiene el valor de la propiedad filasNombreOrganismo.
   * 
   * @return possible object is {@link TipoOpcionesVisualizacionDocumentoMtom.FilasNombreOrganismo }
   * 
   */
  public TipoOpcionesVisualizacionDocumentoMtom.FilasNombreOrganismo getFilasNombreOrganismo() {
    return filasNombreOrganismo;
  }

  /**
   * Define el valor de la propiedad filasNombreOrganismo.
   * 
   * @param value allowed object is
   *        {@link TipoOpcionesVisualizacionDocumentoMtom.FilasNombreOrganismo }
   * 
   */
  public void setFilasNombreOrganismo(
      TipoOpcionesVisualizacionDocumentoMtom.FilasNombreOrganismo value) {
    this.filasNombreOrganismo = value;
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
   *         &lt;element name="Fila" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
   *       &lt;/sequence>
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   * 
   * 
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = {"fila"})
  public static class FilasNombreOrganismo {

    @XmlElement(name = "Fila", required = true)
    protected List<String> fila;

    /**
     * Gets the value of the fila property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the fila property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getFila().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     * 
     * 
     */
    public List<String> getFila() {
      if (fila == null) {
        fila = new ArrayList<String>();
      }
      return this.fila;
    }

  }

}
