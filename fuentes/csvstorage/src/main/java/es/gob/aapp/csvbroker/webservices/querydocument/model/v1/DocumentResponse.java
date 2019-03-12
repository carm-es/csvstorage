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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para documentResponse complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="documentResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentResponse", propOrder = {"content", "name", "mime"})
public class DocumentResponse {

  @XmlElement(required = true)
  protected byte[] content;
  @XmlElement(required = true)
  protected String name;
  @XmlElement(required = true)
  protected String mime;

  /**
   * Obtiene el valor de la propiedad content.
   * 
   * @return possible object is byte[]
   */
  public byte[] getContent() {
    return content;
  }

  /**
   * Define el valor de la propiedad content.
   * 
   * @param value allowed object is byte[]
   */
  public void setContent(byte[] value) {
    this.content = value;
  }

  /**
   * Obtiene el valor de la propiedad name.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getName() {
    return name;
  }

  /**
   * Define el valor de la propiedad name.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setName(String value) {
    this.name = value;
  }

  /**
   * Obtiene el valor de la propiedad mime.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getMime() {
    return mime;
  }

  /**
   * Define el valor de la propiedad mime.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setMime(String value) {
    this.mime = value;
  }

}
