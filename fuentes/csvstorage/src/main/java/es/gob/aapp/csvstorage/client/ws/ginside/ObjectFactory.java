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


package es.gob.aapp.csvstorage.client.ws.ginside;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the es.gob.aapp.csvstorage.client.ws.ginside package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

  private final static QName _ValidarExpedienteEniFileResponse_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "validarExpedienteEniFileResponse");
  private final static QName _VisualizarDocumentoEni_QNAME = new QName(
      "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles", "visualizarDocumentoEni");
  private final static QName _ErrorInside_QNAME = new QName(
      "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebService/types/error", "errorInside");
  private final static QName _ConvertirExpedienteAEniConMAdicionalesAutocontenido_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "convertirExpedienteAEniConMAdicionalesAutocontenido");
  private final static QName _ConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "convertirExpedienteAEniConMAdicionalesAutocontenidoResponse");
  private final static QName _ConvertirExpedienteAEniResponse_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "convertirExpedienteAEniResponse");
  private final static QName _ConvertirExpedienteAEniAutocontenidoResponse_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "convertirExpedienteAEniAutocontenidoResponse");
  private final static QName _ConvertirExpedienteAEniConMAdicionales_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "convertirExpedienteAEniConMAdicionales");
  private final static QName _ConvertirDocumentoAEni_QNAME = new QName(
      "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles", "convertirDocumentoAEni");
  private final static QName _VisualizarDocumentoEniResponse_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "visualizarDocumentoEniResponse");
  private final static QName _ConvertirDocumentoAEniConMAdicionalesResponse_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "convertirDocumentoAEniConMAdicionalesResponse");
  private final static QName _ConvertirExpedienteAEniConMAdicionalesResponse_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "convertirExpedienteAEniConMAdicionalesResponse");
  private final static QName _ConvertirDocumentoAEniResponse_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "convertirDocumentoAEniResponse");
  private final static QName _ValidarDocumentoEniFile_QNAME = new QName(
      "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles", "validarDocumentoEniFile");
  private final static QName _Credential_QNAME = new QName(
      "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebService/types/credential", "credential");
  private final static QName _ValidarExpedienteEniFile_QNAME = new QName(
      "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles", "validarExpedienteEniFile");
  private final static QName _ConvertirExpedienteAEniAutocontenido_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "convertirExpedienteAEniAutocontenido");
  private final static QName _ValidarDocumentoEniFileResponse_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "validarDocumentoEniFileResponse");
  private final static QName _ConvertirExpedienteAEni_QNAME = new QName(
      "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles", "convertirExpedienteAEni");
  private final static QName _ConvertirDocumentoAEniConMAdicionales_QNAME =
      new QName("https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
          "convertirDocumentoAEniConMAdicionales");

  /**
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes
   * for package: es.gob.aapp.csvstorage.client.ws.ginside
   * 
   */
  public ObjectFactory() {}

  /**
   * Create an instance of {@link ConvertirExpedienteAEniConMAdicionales }
   * 
   */
  public ConvertirExpedienteAEniConMAdicionales createConvertirExpedienteAEniConMAdicionales() {
    return new ConvertirExpedienteAEniConMAdicionales();
  }

  /**
   * Create an instance of {@link ConvertirDocumentoAEniResponse }
   * 
   */
  public ConvertirDocumentoAEniResponse createConvertirDocumentoAEniResponse() {
    return new ConvertirDocumentoAEniResponse();
  }

  /**
   * Create an instance of {@link ConvertirExpedienteAEniConMAdicionalesResponse }
   * 
   */
  public ConvertirExpedienteAEniConMAdicionalesResponse createConvertirExpedienteAEniConMAdicionalesResponse() {
    return new ConvertirExpedienteAEniConMAdicionalesResponse();
  }

  /**
   * Create an instance of {@link ConvertirExpedienteAEniAutocontenido }
   * 
   */
  public ConvertirExpedienteAEniAutocontenido createConvertirExpedienteAEniAutocontenido() {
    return new ConvertirExpedienteAEniAutocontenido();
  }

  /**
   * Create an instance of {@link ValidarDocumentoEniFileResponse }
   * 
   */
  public ValidarDocumentoEniFileResponse createValidarDocumentoEniFileResponse() {
    return new ValidarDocumentoEniFileResponse();
  }

  /**
   * Create an instance of {@link ConvertirExpedienteAEniConMAdicionalesAutocontenido }
   * 
   */
  public ConvertirExpedienteAEniConMAdicionalesAutocontenido createConvertirExpedienteAEniConMAdicionalesAutocontenido() {
    return new ConvertirExpedienteAEniConMAdicionalesAutocontenido();
  }

  /**
   * Create an instance of {@link ConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse }
   * 
   */
  public ConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse createConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse() {
    return new ConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse();
  }

  /**
   * Create an instance of {@link ConvertirExpedienteAEniResponse }
   * 
   */
  public ConvertirExpedienteAEniResponse createConvertirExpedienteAEniResponse() {
    return new ConvertirExpedienteAEniResponse();
  }

  /**
   * Create an instance of {@link ValidarExpedienteEniFile }
   * 
   */
  public ValidarExpedienteEniFile createValidarExpedienteEniFile() {
    return new ValidarExpedienteEniFile();
  }

  /**
   * Create an instance of {@link ConvertirExpedienteAEniAutocontenidoResponse }
   * 
   */
  public ConvertirExpedienteAEniAutocontenidoResponse createConvertirExpedienteAEniAutocontenidoResponse() {
    return new ConvertirExpedienteAEniAutocontenidoResponse();
  }

  /**
   * Create an instance of {@link VisualizarDocumentoEniResponse }
   * 
   */
  public VisualizarDocumentoEniResponse createVisualizarDocumentoEniResponse() {
    return new VisualizarDocumentoEniResponse();
  }

  /**
   * Create an instance of {@link ConvertirDocumentoAEniConMAdicionales }
   * 
   */
  public ConvertirDocumentoAEniConMAdicionales createConvertirDocumentoAEniConMAdicionales() {
    return new ConvertirDocumentoAEniConMAdicionales();
  }

  /**
   * Create an instance of {@link ConvertirDocumentoAEniConMAdicionalesResponse }
   * 
   */
  public ConvertirDocumentoAEniConMAdicionalesResponse createConvertirDocumentoAEniConMAdicionalesResponse() {
    return new ConvertirDocumentoAEniConMAdicionalesResponse();
  }

  /**
   * Create an instance of {@link ValidarDocumentoEniFile }
   * 
   */
  public ValidarDocumentoEniFile createValidarDocumentoEniFile() {
    return new ValidarDocumentoEniFile();
  }

  /**
   * Create an instance of {@link ValidarExpedienteEniFileResponse }
   * 
   */
  public ValidarExpedienteEniFileResponse createValidarExpedienteEniFileResponse() {
    return new ValidarExpedienteEniFileResponse();
  }

  /**
   * Create an instance of {@link ConvertirDocumentoAEni }
   * 
   */
  public ConvertirDocumentoAEni createConvertirDocumentoAEni() {
    return new ConvertirDocumentoAEni();
  }

  /**
   * Create an instance of {@link ConvertirExpedienteAEni }
   * 
   */
  public ConvertirExpedienteAEni createConvertirExpedienteAEni() {
    return new ConvertirExpedienteAEni();
  }

  /**
   * Create an instance of {@link VisualizarDocumentoEni }
   * 
   */
  public VisualizarDocumentoEni createVisualizarDocumentoEni() {
    return new VisualizarDocumentoEni();
  }

  /**
   * Create an instance of {@link WSCredentialInside }
   * 
   */
  public WSCredentialInside createWSCredentialInside() {
    return new WSCredentialInside();
  }

  /**
   * Create an instance of {@link WSErrorInside }
   * 
   */
  public WSErrorInside createWSErrorInside() {
    return new WSErrorInside();
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ValidarExpedienteEniFileResponse
   * }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "validarExpedienteEniFileResponse")
  public JAXBElement<ValidarExpedienteEniFileResponse> createValidarExpedienteEniFileResponse(
      ValidarExpedienteEniFileResponse value) {
    return new JAXBElement<ValidarExpedienteEniFileResponse>(
        _ValidarExpedienteEniFileResponse_QNAME, ValidarExpedienteEniFileResponse.class, null,
        value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link VisualizarDocumentoEni }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "visualizarDocumentoEni")
  public JAXBElement<VisualizarDocumentoEni> createVisualizarDocumentoEni(
      VisualizarDocumentoEni value) {
    return new JAXBElement<VisualizarDocumentoEni>(_VisualizarDocumentoEni_QNAME,
        VisualizarDocumentoEni.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link WSErrorInside }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebService/types/error",
      name = "errorInside")
  public JAXBElement<WSErrorInside> createErrorInside(WSErrorInside value) {
    return new JAXBElement<WSErrorInside>(_ErrorInside_QNAME, WSErrorInside.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement
   * }{@code <}{@link ConvertirExpedienteAEniConMAdicionalesAutocontenido }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirExpedienteAEniConMAdicionalesAutocontenido")
  public JAXBElement<ConvertirExpedienteAEniConMAdicionalesAutocontenido> createConvertirExpedienteAEniConMAdicionalesAutocontenido(
      ConvertirExpedienteAEniConMAdicionalesAutocontenido value) {
    return new JAXBElement<ConvertirExpedienteAEniConMAdicionalesAutocontenido>(
        _ConvertirExpedienteAEniConMAdicionalesAutocontenido_QNAME,
        ConvertirExpedienteAEniConMAdicionalesAutocontenido.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement
   * }{@code <}{@link ConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirExpedienteAEniConMAdicionalesAutocontenidoResponse")
  public JAXBElement<ConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse> createConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse(
      ConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse value) {
    return new JAXBElement<ConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse>(
        _ConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse_QNAME,
        ConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ConvertirExpedienteAEniResponse
   * }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirExpedienteAEniResponse")
  public JAXBElement<ConvertirExpedienteAEniResponse> createConvertirExpedienteAEniResponse(
      ConvertirExpedienteAEniResponse value) {
    return new JAXBElement<ConvertirExpedienteAEniResponse>(_ConvertirExpedienteAEniResponse_QNAME,
        ConvertirExpedienteAEniResponse.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement
   * }{@code <}{@link ConvertirExpedienteAEniAutocontenidoResponse }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirExpedienteAEniAutocontenidoResponse")
  public JAXBElement<ConvertirExpedienteAEniAutocontenidoResponse> createConvertirExpedienteAEniAutocontenidoResponse(
      ConvertirExpedienteAEniAutocontenidoResponse value) {
    return new JAXBElement<ConvertirExpedienteAEniAutocontenidoResponse>(
        _ConvertirExpedienteAEniAutocontenidoResponse_QNAME,
        ConvertirExpedienteAEniAutocontenidoResponse.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement
   * }{@code <}{@link ConvertirExpedienteAEniConMAdicionales }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirExpedienteAEniConMAdicionales")
  public JAXBElement<ConvertirExpedienteAEniConMAdicionales> createConvertirExpedienteAEniConMAdicionales(
      ConvertirExpedienteAEniConMAdicionales value) {
    return new JAXBElement<ConvertirExpedienteAEniConMAdicionales>(
        _ConvertirExpedienteAEniConMAdicionales_QNAME, ConvertirExpedienteAEniConMAdicionales.class,
        null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ConvertirDocumentoAEni }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirDocumentoAEni")
  public JAXBElement<ConvertirDocumentoAEni> createConvertirDocumentoAEni(
      ConvertirDocumentoAEni value) {
    return new JAXBElement<ConvertirDocumentoAEni>(_ConvertirDocumentoAEni_QNAME,
        ConvertirDocumentoAEni.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link VisualizarDocumentoEniResponse
   * }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "visualizarDocumentoEniResponse")
  public JAXBElement<VisualizarDocumentoEniResponse> createVisualizarDocumentoEniResponse(
      VisualizarDocumentoEniResponse value) {
    return new JAXBElement<VisualizarDocumentoEniResponse>(_VisualizarDocumentoEniResponse_QNAME,
        VisualizarDocumentoEniResponse.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement
   * }{@code <}{@link ConvertirDocumentoAEniConMAdicionalesResponse }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirDocumentoAEniConMAdicionalesResponse")
  public JAXBElement<ConvertirDocumentoAEniConMAdicionalesResponse> createConvertirDocumentoAEniConMAdicionalesResponse(
      ConvertirDocumentoAEniConMAdicionalesResponse value) {
    return new JAXBElement<ConvertirDocumentoAEniConMAdicionalesResponse>(
        _ConvertirDocumentoAEniConMAdicionalesResponse_QNAME,
        ConvertirDocumentoAEniConMAdicionalesResponse.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement
   * }{@code <}{@link ConvertirExpedienteAEniConMAdicionalesResponse }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirExpedienteAEniConMAdicionalesResponse")
  public JAXBElement<ConvertirExpedienteAEniConMAdicionalesResponse> createConvertirExpedienteAEniConMAdicionalesResponse(
      ConvertirExpedienteAEniConMAdicionalesResponse value) {
    return new JAXBElement<ConvertirExpedienteAEniConMAdicionalesResponse>(
        _ConvertirExpedienteAEniConMAdicionalesResponse_QNAME,
        ConvertirExpedienteAEniConMAdicionalesResponse.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ConvertirDocumentoAEniResponse
   * }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirDocumentoAEniResponse")
  public JAXBElement<ConvertirDocumentoAEniResponse> createConvertirDocumentoAEniResponse(
      ConvertirDocumentoAEniResponse value) {
    return new JAXBElement<ConvertirDocumentoAEniResponse>(_ConvertirDocumentoAEniResponse_QNAME,
        ConvertirDocumentoAEniResponse.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ValidarDocumentoEniFile }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "validarDocumentoEniFile")
  public JAXBElement<ValidarDocumentoEniFile> createValidarDocumentoEniFile(
      ValidarDocumentoEniFile value) {
    return new JAXBElement<ValidarDocumentoEniFile>(_ValidarDocumentoEniFile_QNAME,
        ValidarDocumentoEniFile.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link WSCredentialInside }{@code >}}
   * 
   */
  @XmlElementDecl(
      namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebService/types/credential",
      name = "credential")
  public JAXBElement<WSCredentialInside> createCredential(WSCredentialInside value) {
    return new JAXBElement<WSCredentialInside>(_Credential_QNAME, WSCredentialInside.class, null,
        value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ValidarExpedienteEniFile }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "validarExpedienteEniFile")
  public JAXBElement<ValidarExpedienteEniFile> createValidarExpedienteEniFile(
      ValidarExpedienteEniFile value) {
    return new JAXBElement<ValidarExpedienteEniFile>(_ValidarExpedienteEniFile_QNAME,
        ValidarExpedienteEniFile.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ConvertirExpedienteAEniAutocontenido
   * }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirExpedienteAEniAutocontenido")
  public JAXBElement<ConvertirExpedienteAEniAutocontenido> createConvertirExpedienteAEniAutocontenido(
      ConvertirExpedienteAEniAutocontenido value) {
    return new JAXBElement<ConvertirExpedienteAEniAutocontenido>(
        _ConvertirExpedienteAEniAutocontenido_QNAME, ConvertirExpedienteAEniAutocontenido.class,
        null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ValidarDocumentoEniFileResponse
   * }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "validarDocumentoEniFileResponse")
  public JAXBElement<ValidarDocumentoEniFileResponse> createValidarDocumentoEniFileResponse(
      ValidarDocumentoEniFileResponse value) {
    return new JAXBElement<ValidarDocumentoEniFileResponse>(_ValidarDocumentoEniFileResponse_QNAME,
        ValidarDocumentoEniFileResponse.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ConvertirExpedienteAEni }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirExpedienteAEni")
  public JAXBElement<ConvertirExpedienteAEni> createConvertirExpedienteAEni(
      ConvertirExpedienteAEni value) {
    return new JAXBElement<ConvertirExpedienteAEni>(_ConvertirExpedienteAEni_QNAME,
        ConvertirExpedienteAEni.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ConvertirDocumentoAEniConMAdicionales
   * }{@code >}}
   * 
   */
  @XmlElementDecl(namespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      name = "convertirDocumentoAEniConMAdicionales")
  public JAXBElement<ConvertirDocumentoAEniConMAdicionales> createConvertirDocumentoAEniConMAdicionales(
      ConvertirDocumentoAEniConMAdicionales value) {
    return new JAXBElement<ConvertirDocumentoAEniConMAdicionales>(
        _ConvertirDocumentoAEniConMAdicionales_QNAME, ConvertirDocumentoAEniConMAdicionales.class,
        null, value);
  }

}
