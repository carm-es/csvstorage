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

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para ContenidoInfo complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ContenidoInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contenido" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="tipoMIME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContenidoInfoBigData", propOrder = {"files", "tipoMIME"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class ContenidoInfoBigData {

  @XmlElement(required = true)
  protected List<String> files;
  @XmlElement(required = true)
  protected String tipoMIME;


  /**
   * Obtiene el valor de la propiedad tipoMIME.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getTipoMIME() {
    return tipoMIME;
  }

  /**
   * Define el valor de la propiedad tipoMIME.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setTipoMIME(String value) {
    this.tipoMIME = value;
  }

  /**
   * @return the files
   */
  public List<String> getFiles() {
    return files;
  }

  /**
   * @param files the files to set
   */
  public void setFiles(List<String> files) {
    this.files = files;
  }

}
