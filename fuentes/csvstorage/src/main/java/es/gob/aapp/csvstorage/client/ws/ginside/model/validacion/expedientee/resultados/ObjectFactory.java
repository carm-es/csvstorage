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


package es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.expedientee.resultados;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.expedientee.resultados
 * package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

  private final static QName _TipoResultadoValidacionExpedienteInside_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/validacion/expediente-e/resultados",
          "TipoResultadoValidacionExpedienteInside");

  /**
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes
   * for package: es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.expedientee.resultados
   * 
   */
  public ObjectFactory() {}

  /**
   * Create an instance of {@link TipoResultadoValidacionExpedienteInside }
   * 
   */
  public TipoResultadoValidacionExpedienteInside createTipoResultadoValidacionExpedienteInside() {
    return new TipoResultadoValidacionExpedienteInside();
  }

  /**
   * Create an instance of {@link TipoResultadoValidacionDetalleExpedienteInside }
   * 
   */
  public TipoResultadoValidacionDetalleExpedienteInside createTipoResultadoValidacionDetalleExpedienteInside() {
    return new TipoResultadoValidacionDetalleExpedienteInside();
  }

  /**
   * Create an instance of {@link JAXBElement
   * }{@code <}{@link TipoResultadoValidacionExpedienteInside }{@code >}}
   * 
   */
  @XmlElementDecl(
      namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/validacion/expediente-e/resultados",
      name = "TipoResultadoValidacionExpedienteInside")
  public JAXBElement<TipoResultadoValidacionExpedienteInside> createTipoResultadoValidacionExpedienteInside(
      TipoResultadoValidacionExpedienteInside value) {
    return new JAXBElement<TipoResultadoValidacionExpedienteInside>(
        _TipoResultadoValidacionExpedienteInside_QNAME,
        TipoResultadoValidacionExpedienteInside.class, null, value);
  }

}
