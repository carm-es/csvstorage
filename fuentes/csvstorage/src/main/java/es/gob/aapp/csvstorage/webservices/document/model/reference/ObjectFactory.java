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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the es.gob.aapp.csvstorage.webservices.document.model.reference package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

  private final static QName _ReferenciaDocumento_QNAME = new QName("", "ReferenciaDocumento");
  private final static QName _TipoIdentificadorEEMGDEFirmaFormatoFirmaValorCSV_QNAME =
      new QName("", "eEMGDE.Firma.FormatoFirma.ValorCSV");
  private final static QName _TipoIdentificadorEEMGDEIdentificadorSecuenciaIdentificador_QNAME =
      new QName("", "eEMGDE.Identificador.SecuenciaIdentificador");

  /**
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes
   * for package: es.gob.aapp.csvstorage.webservices.document.model.reference
   * 
   */
  public ObjectFactory() {}

  /**
   * Create an instance of {@link TipoReferenciaDocumento }
   * 
   */
  public TipoReferenciaDocumento createTipoReferenciaDocumento() {
    return new TipoReferenciaDocumento();
  }

  /**
   * Create an instance of {@link TipoPermisos }
   * 
   */
  public TipoPermisos createTipoPermisos() {
    return new TipoPermisos();
  }

  /**
   * Create an instance of {@link TipoTrazabilidad }
   * 
   */
  public TipoTrazabilidad createTipoTrazabilidad() {
    return new TipoTrazabilidad();
  }

  /**
   * Create an instance of {@link TipoNifs }
   * 
   */
  public TipoNifs createTipoNifs() {
    return new TipoNifs();
  }

  /**
   * Create an instance of {@link TipoIdentificaciones }
   * 
   */
  public TipoIdentificaciones createTipoIdentificaciones() {
    return new TipoIdentificaciones();
  }

  /**
   * Create an instance of {@link TipoIdentificador }
   * 
   */
  public TipoIdentificador createTipoIdentificador() {
    return new TipoIdentificador();
  }

  /**
   * Create an instance of {@link TipoAplicaciones }
   * 
   */
  public TipoAplicaciones createTipoAplicaciones() {
    return new TipoAplicaciones();
  }

  /**
   * Create an instance of {@link TipoMetadatos }
   * 
   */
  public TipoMetadatos createTipoMetadatos() {
    return new TipoMetadatos();
  }

  /**
   * Create an instance of {@link TipoEmisor }
   * 
   */
  public TipoEmisor createTipoEmisor() {
    return new TipoEmisor();
  }

  /**
   * Create an instance of {@link TipoMetadato }
   * 
   */
  public TipoMetadato createTipoMetadato() {
    return new TipoMetadato();
  }

  /**
   * Create an instance of {@link TipoRestringido }
   * 
   */
  public TipoRestringido createTipoRestringido() {
    return new TipoRestringido();
  }

  /**
   * Create an instance of {@link TipoReceptor }
   * 
   */
  public TipoReceptor createTipoReceptor() {
    return new TipoReceptor();
  }

  /**
   * Create an instance of {@link TipoTraza }
   * 
   */
  public TipoTraza createTipoTraza() {
    return new TipoTraza();
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link TipoReferenciaDocumento }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "", name = "ReferenciaDocumento")
  public JAXBElement<TipoReferenciaDocumento> createReferenciaDocumento(
      TipoReferenciaDocumento value) {
    return new JAXBElement<TipoReferenciaDocumento>(_ReferenciaDocumento_QNAME,
        TipoReferenciaDocumento.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "", name = "eEMGDE.Firma.FormatoFirma.ValorCSV",
      scope = TipoIdentificador.class)
  public JAXBElement<String> createTipoIdentificadorEEMGDEFirmaFormatoFirmaValorCSV(String value) {
    return new JAXBElement<String>(_TipoIdentificadorEEMGDEFirmaFormatoFirmaValorCSV_QNAME,
        String.class, TipoIdentificador.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "", name = "eEMGDE.Identificador.SecuenciaIdentificador",
      scope = TipoIdentificador.class)
  public JAXBElement<String> createTipoIdentificadorEEMGDEIdentificadorSecuenciaIdentificador(
      String value) {
    return new JAXBElement<String>(
        _TipoIdentificadorEEMGDEIdentificadorSecuenciaIdentificador_QNAME, String.class,
        TipoIdentificador.class, value);
  }

}
