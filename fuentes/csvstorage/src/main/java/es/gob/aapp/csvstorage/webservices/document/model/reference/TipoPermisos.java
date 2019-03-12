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
 * Clase Java para TipoPermisos complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TipoPermisos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="Publico" type="{}TipoPublico"/>
 *         &lt;element name="Privado" type="{}TipoPrivado"/>
 *         &lt;element name="Restringido" type="{}TipoRestringido"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoPermisos", propOrder = {"publico", "privado", "restringido"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class TipoPermisos {

  @XmlElement(name = "Publico")
  @XmlSchemaType(name = "string")
  protected TipoPublico publico;
  @XmlElement(name = "Privado")
  @XmlSchemaType(name = "string")
  protected TipoPrivado privado;
  @XmlElement(name = "Restringido")
  protected TipoRestringido restringido;

  /**
   * Obtiene el valor de la propiedad publico.
   * 
   * @return possible object is {@link TipoPublico }
   * 
   */
  public TipoPublico getPublico() {
    return publico;
  }

  /**
   * Define el valor de la propiedad publico.
   * 
   * @param value allowed object is {@link TipoPublico }
   * 
   */
  public void setPublico(TipoPublico value) {
    this.publico = value;
  }

  /**
   * Obtiene el valor de la propiedad privado.
   * 
   * @return possible object is {@link TipoPrivado }
   * 
   */
  public TipoPrivado getPrivado() {
    return privado;
  }

  /**
   * Define el valor de la propiedad privado.
   * 
   * @param value allowed object is {@link TipoPrivado }
   * 
   */
  public void setPrivado(TipoPrivado value) {
    this.privado = value;
  }

  /**
   * Obtiene el valor de la propiedad restringido.
   * 
   * @return possible object is {@link TipoRestringido }
   * 
   */
  public TipoRestringido getRestringido() {
    return restringido;
  }

  /**
   * Define el valor de la propiedad restringido.
   * 
   * @param value allowed object is {@link TipoRestringido }
   * 
   */
  public void setRestringido(TipoRestringido value) {
    this.restringido = value;
  }

}
