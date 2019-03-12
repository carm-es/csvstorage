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

//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de
// enlace (JAXB) XML v2.2.8-b130911.1802
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el
// esquema de origen.
// Generado el: 2018.09.19 a las 09:30:02 AM CEST
//


package es.gob.aapp.csvstorage.webservices.document.model.reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para TipoMetadato complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TipoMetadato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Nombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Valor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoMetadato", propOrder = {"nombre", "valor"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class TipoMetadato {

  @XmlElement(name = "Nombre", required = true)
  protected String nombre;
  @XmlElement(name = "Valor", required = true)
  protected String valor;

  /**
   * Obtiene el valor de la propiedad nombre.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Define el valor de la propiedad nombre.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setNombre(String value) {
    this.nombre = value;
  }

  /**
   * Obtiene el valor de la propiedad valor.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getValor() {
    return valor;
  }

  /**
   * Define el valor de la propiedad valor.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setValor(String value) {
    this.valor = value;
  }

}
