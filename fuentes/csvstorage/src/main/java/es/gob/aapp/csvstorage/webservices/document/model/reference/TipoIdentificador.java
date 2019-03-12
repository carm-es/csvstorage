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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para TipoIdentificador complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TipoIdentificador">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="2">
 *         &lt;element name="eEMGDE.Firma.FormatoFirma.ValorCSV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eEMGDE.Identificador.SecuenciaIdentificador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoIdentificador",
    propOrder = {"eemgdeFirmaFormatoFirmaValorCSVOrEEMGDEIdentificadorSecuenciaIdentificador"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class TipoIdentificador {

  @XmlElementRefs({
      @XmlElementRef(name = "eEMGDE.Firma.FormatoFirma.ValorCSV", type = JAXBElement.class,
          required = false),
      @XmlElementRef(name = "eEMGDE.Identificador.SecuenciaIdentificador", type = JAXBElement.class,
          required = false)})
  protected List<JAXBElement<String>> eemgdeFirmaFormatoFirmaValorCSVOrEEMGDEIdentificadorSecuenciaIdentificador;

  /**
   * Gets the value of the
   * eemgdeFirmaFormatoFirmaValorCSVOrEEMGDEIdentificadorSecuenciaIdentificador property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the
   * eemgdeFirmaFormatoFirmaValorCSVOrEEMGDEIdentificadorSecuenciaIdentificador property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getEEMGDEFirmaFormatoFirmaValorCSVOrEEMGDEIdentificadorSecuenciaIdentificador().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link JAXBElement
   * }{@code <}{@link String }{@code >} {@link JAXBElement }{@code <}{@link String }{@code >}
   * 
   * 
   */
  public List<JAXBElement<String>> getEEMGDEFirmaFormatoFirmaValorCSVOrEEMGDEIdentificadorSecuenciaIdentificador() {
    if (eemgdeFirmaFormatoFirmaValorCSVOrEEMGDEIdentificadorSecuenciaIdentificador == null) {
      eemgdeFirmaFormatoFirmaValorCSVOrEEMGDEIdentificadorSecuenciaIdentificador =
          new ArrayList<JAXBElement<String>>();
    }
    return this.eemgdeFirmaFormatoFirmaValorCSVOrEEMGDEIdentificadorSecuenciaIdentificador;
  }

}
