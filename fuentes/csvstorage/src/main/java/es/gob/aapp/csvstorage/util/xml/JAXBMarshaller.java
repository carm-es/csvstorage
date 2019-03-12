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

package es.gob.aapp.csvstorage.util.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathExpressionException;
import es.gob.aapp.csvstorage.webservices.document.model.reference.TipoReferenciaDocumento;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.TipoDocumento;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.TipoDocumentoInsideConMAdicionales;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;

/**
 * Clase �til de transformaciones XML y objetos java.
 * 
 */
public class JAXBMarshaller {

  /**
   * M�todo para convertir un objeto Java al string XML equivalente.
   * 
   * @param object Objeto java a convertir.
   * @param clase Class java para saber el tipo.
   * @return String con el XML equivalente.
   * @throws JAXBException
   */
  public static <T> String marshallRootElement(T object, Class<T> clase) throws JAXBException {

    StringWriter sw = null;

    JAXBContext jc = JAXBContext.newInstance(clase);
    Marshaller marshaller = jc.createMarshaller();
    sw = new StringWriter();
    marshaller.marshal(object, sw);

    return sw.toString();

  }// fin marshallRootElement

  public static String marshallData(TipoDocumento doc, String nameSapce, String localPart,
      String prefix) throws ConsumerWSException {
    StringWriter sw;
    try {
      JAXBContext context = JAXBContext.newInstance(doc.getClass());
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
      marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper());
      sw = new StringWriter();

      JAXBElement<TipoDocumento> jx = new JAXBElement<TipoDocumento>(
          new QName(nameSapce, localPart, prefix), TipoDocumento.class, doc);
      marshaller.marshal(jx, sw);

    } catch (PropertyException e) {
      throw new ConsumerWSException(e.getMessage());
    } catch (JAXBException e) {
      throw new ConsumerWSException(e.getMessage());
    }

    return sw.toString();
  }

  public static String marshallDataDocument(Object doc, String nameSapce, String localPart,
      String prefix) throws JAXBException, XPathExpressionException, ParserConfigurationException,
      SAXException, IOException, TransformerException {
    JAXBContext context = JAXBContext.newInstance(doc.getClass());
    Marshaller marshaller = context.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);


    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
    StringWriter sw = new StringWriter();
    if (doc instanceof TipoDocumento) {

      if (((TipoDocumento) doc).getContenido() != null
          && ((TipoDocumento) doc).getContenido().getDatosXML() != null) {
        marshaller.setProperty(CharacterEscapeHandler.class.getName(),
            new CharacterEscapeHandler() {
              @Override
              public void escape(char[] ac, int i, int j, boolean flag, Writer writer)
                  throws IOException {
                writer.write(ac, i, j);
              }
            });
      }

      if (((TipoDocumento) doc).getContenido() != null
          && ((TipoDocumento) doc).getContenido().getDatosXML() != null) {
        Object data = ((TipoDocumento) doc).getContenido().getDatosXML();
        if (data instanceof String) {
          // Validamos que no tenga ya un CDATA
          if (!String.valueOf(data).startsWith("<![CDATA[")) {
            StringBuilder tmpBuild = new StringBuilder("<![CDATA[");
            tmpBuild.append(data);
            tmpBuild.append("]]>");
            ((TipoDocumento) doc).getContenido().setDatosXML(new String(tmpBuild.toString()));
          }
        }
      }
      JAXBElement<TipoDocumento> jx = new JAXBElement<TipoDocumento>(
          new QName(nameSapce, localPart, prefix), TipoDocumento.class, (TipoDocumento) doc);
      marshaller.marshal(jx, sw);
    } else if (doc instanceof TipoDocumentoInsideConMAdicionales) {

      if (((TipoDocumentoInsideConMAdicionales) doc).getDocumento().getContenido() != null
          && ((TipoDocumentoInsideConMAdicionales) doc).getDocumento().getContenido()
              .getDatosXML() != null) {
        marshaller.setProperty(CharacterEscapeHandler.class.getName(),
            new CharacterEscapeHandler() {
              @Override
              public void escape(char[] ac, int i, int j, boolean flag, Writer writer)
                  throws IOException {
                writer.write(ac, i, j);
              }
            });
      }

      if (((TipoDocumentoInsideConMAdicionales) doc).getDocumento().getContenido() != null
          && ((TipoDocumentoInsideConMAdicionales) doc).getDocumento().getContenido()
              .getDatosXML() != null) {
        Object data =
            ((TipoDocumentoInsideConMAdicionales) doc).getDocumento().getContenido().getDatosXML();
        if (data instanceof String) {
          StringBuilder tmpBuild = new StringBuilder("<![CDATA[");
          tmpBuild.append(data);
          tmpBuild.append("]]>");
          ((TipoDocumentoInsideConMAdicionales) doc).getDocumento().getContenido()
              .setDatosXML(new String(tmpBuild.toString()));
        }
      }
      JAXBElement<TipoDocumentoInsideConMAdicionales> jx =
          new JAXBElement<TipoDocumentoInsideConMAdicionales>(
              new QName(nameSapce, localPart, prefix), TipoDocumentoInsideConMAdicionales.class,
              (TipoDocumentoInsideConMAdicionales) doc);
      marshaller.marshal(jx, sw);

    } else if (doc instanceof TipoReferenciaDocumento) {

      JAXBElement<TipoReferenciaDocumento> jx =
          new JAXBElement<TipoReferenciaDocumento>(new QName(nameSapce, localPart, prefix),
              TipoReferenciaDocumento.class, (TipoReferenciaDocumento) doc);
      marshaller.marshal(jx, sw);

    }


    return sw.toString();
  }

  /**
   * M�todo para convertir un String XML a un objeto JAXB.
   * 
   * @param s Cadena con el XML a convertir.
   * @param clase Class java para saber el tipo.
   * @return Objeto java convertido.
   * @throws JAXBException
   */
  @SuppressWarnings("unchecked")
  public static <T> T unmarshallRootElement(String s, Class<T> clase) throws JAXBException {

    JAXBContext jc = JAXBContext.newInstance(clase);
    Unmarshaller unmarshaller = jc.createUnmarshaller();

    T objeto = (T) unmarshaller.unmarshal(new StreamSource(new StringReader(s)));

    if (objeto instanceof JAXBElement<?>) {
      JAXBElement<T> jaxbElement = (JAXBElement<T>) objeto;
      objeto = (T) jaxbElement.getValue();
    }

    return objeto;

  }// fin unmarshallRootElement

  public static TipoDocumento unmarshallDataDocument(byte[] data)
      throws JAXBException, ParserConfigurationException, SAXException, IOException,
      TransformerFactoryConfigurationError, TransformerException {
    TipoDocumento retorno = null;
    try {
      JAXBContext jc = JAXBContext.newInstance(TipoDocumento.class.getPackage().getName());

      Unmarshaller unmarshaller = jc.createUnmarshaller(); //
      JAXBElement<?> element =
          (JAXBElement<?>) unmarshaller.unmarshal(new ByteArrayInputStream(data));
      retorno = (TipoDocumento) element.getValue();
    } catch (JAXBException e) {
      Node nodoAdicionales = getNode(data, "ns7:metadatosAdicionales");
      if (nodoAdicionales != null) {
        byte[] nodoEniString = documentoAdicionalToEni(data);

        JAXBContext jc = JAXBContext.newInstance(TipoDocumento.class.getPackage().getName());

        Unmarshaller unmarshaller = jc.createUnmarshaller(); //
        JAXBElement<?> element =
            (JAXBElement<?>) unmarshaller.unmarshal(new ByteArrayInputStream(nodoEniString));
        retorno = (TipoDocumento) element.getValue();
      }
    }
    return retorno;
  }

  public static Node getNode(byte[] xml, String tag)
      throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document dom = db.parse(new ByteArrayInputStream(xml));
    Node nodo = ((Node) dom.getElementsByTagName(tag).item(0));
    return nodo;
  }

  public static String nodeToString(Node node)
      throws TransformerFactoryConfigurationError, TransformerException {
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    Source source = new DOMSource(node);
    StringWriter sw = new StringWriter();
    StreamResult result = new StreamResult(sw);
    transformer.transform(source, result);
    return sw.toString();
  }

  public static byte[] documentoAdicionalToEni(byte[] adicional)
      throws ParserConfigurationException, SAXException, IOException,
      TransformerFactoryConfigurationError, TransformerException {
    Node nodoEni = getNode(adicional, "ns5:documento");
    Element nodoEniElem = (Element) nodoEni;
    nodoEniElem.setAttribute("xmlns:ns2",
        "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/metadatos");
    nodoEniElem.setAttribute("xmlns:ns3",
        "http://administracionelectronica.gob.es/ENI/XSD/v1.0/firma");
    nodoEniElem.setAttribute("xmlns:ns4", "http://www.w3.org/2000/09/xmldsig#");
    nodoEniElem.setAttribute("xmlns:ns5",
        "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e");
    nodoEniElem.setAttribute("xmlns:ns6",
        "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/metadatosAdicionales");
    nodoEniElem.setAttribute("xmlns:ns7",
        "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/documento-e");
    nodoEniElem.setAttribute("xmlns:insidews",
        "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/documento-e");
    nodoEniElem.setAttribute("xmlns",
        "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/contenido");
    nodoEniElem.setAttribute("xmlns:enidoc",
        "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e");
    return nodeToString(nodoEni).getBytes();
  }

  /**
   * M�todo para convertir un fichero XML a un objeto JAXB.
   * 
   * @param rutaXML Cadena con la ruta y nombre del fichero.
   * @param clase Class java para saber el tipo.
   * @return Objeto java convertido.
   * @throws JAXBException
   */
  @SuppressWarnings("unchecked")
  public static <T> T unmarshallRootElementFich(String rutaXML, Class<T> clase)
      throws JAXBException {

    JAXBContext jc = JAXBContext.newInstance(clase);
    Unmarshaller unmarshaller = jc.createUnmarshaller();

    T objeto = (T) unmarshaller.unmarshal(new StreamSource(rutaXML));

    if (objeto instanceof JAXBElement<?>) {
      JAXBElement<T> jaxbElement = (JAXBElement<T>) objeto;
      objeto = (T) jaxbElement.getValue();
    }

    return objeto;

  }// fin unmarshallRootElementFich


}
