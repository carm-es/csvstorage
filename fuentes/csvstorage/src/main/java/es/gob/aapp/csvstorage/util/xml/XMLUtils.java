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
import java.io.StringWriter;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.xerces.dom.DeferredElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLUtils {

  private static String nodoARemplazar = null;

  public static Node getNode(byte[] xml, String tag)
      throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document dom = db.parse(new ByteArrayInputStream(xml));
    Node nodo = null;

    // No se ha pasado ningun tag que tenga match desde
    // 'documentoAdicionalWsToEni(byte[] adicional)'.
    // El * es comodin e indicamos que coja el primer nodo y recoge el
    // nombre y se lo asigna al tag.
    // asi nos aseguramos que hay match.
    if ("*".equals(tag)) {
      tag = dom.getDocumentElement().getNodeName();
      nodoARemplazar = tag;

    }

    nodo = (dom.getElementsByTagName(tag).item(0));
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

  public static String signatureString(String expression, byte[] data)
      throws ParserConfigurationException, SAXException, IOException, XPathExpressionException,
      TransformerException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new ByteArrayInputStream(data));
    XPathFactory xpf = XPathFactory.newInstance();
    XPath xpath = xpf.newXPath();

    XPathExpression exprFirst = xpath.compile(expression);
    DeferredElementImpl nodeParent =
        (DeferredElementImpl) exprFirst.evaluate(doc, XPathConstants.NODE);
    return nodeToString(nodeParent);
  }


  public static String metadataValue(String expression, byte[] data)
      throws ParserConfigurationException, SAXException, IOException, XPathExpressionException,
      TransformerException {
    String nodeValue = null;
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new ByteArrayInputStream(data));
    XPathFactory xpf = XPathFactory.newInstance();
    XPath xpath = xpf.newXPath();

    XPathExpression exprFirst = xpath.compile(expression);
    DeferredElementImpl nodeParent =
        (DeferredElementImpl) exprFirst.evaluate(doc, XPathConstants.NODE);

    if (nodeParent != null) {
      nodeValue = nodeParent.getFirstChild().getNodeValue();
    }

    return nodeValue;
  }

}
