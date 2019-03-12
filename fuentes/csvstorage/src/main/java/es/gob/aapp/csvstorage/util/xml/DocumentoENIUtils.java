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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import es.gob.aapp.csvstorage.client.ws.eni.credential.CanonicalizationMethodType;
import es.gob.aapp.csvstorage.client.ws.eni.credential.DigestMethodType;
import es.gob.aapp.csvstorage.client.ws.eni.credential.KeyInfoType;
import es.gob.aapp.csvstorage.client.ws.eni.credential.ObjectType;
import es.gob.aapp.csvstorage.client.ws.eni.credential.ReferenceType;
import es.gob.aapp.csvstorage.client.ws.eni.credential.SignatureMethodType;
import es.gob.aapp.csvstorage.client.ws.eni.credential.SignatureType;
import es.gob.aapp.csvstorage.client.ws.eni.credential.SignatureValueType;
import es.gob.aapp.csvstorage.client.ws.eni.credential.SignedInfoType;
import es.gob.aapp.csvstorage.client.ws.eni.credential.TransformType;
import es.gob.aapp.csvstorage.client.ws.eni.credential.TransformsType;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.TipoDocumento;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.contenido.TipoContenido;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.metadatos.EnumeracionEstadoElaboracion;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.metadatos.TipoDocumental;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.metadatos.TipoEstadoElaboracion;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.metadatos.TipoMetadatos;
import es.gob.aapp.csvstorage.client.ws.eni.firma.Firmas;
import es.gob.aapp.csvstorage.client.ws.eni.firma.TipoFirma;
import es.gob.aapp.csvstorage.client.ws.eni.firma.TipoFirmasElectronicas;
import es.gob.aapp.csvstorage.client.ws.eni.firma.TipoFirmasElectronicas.ContenidoFirma;
import es.gob.aapp.csvstorage.model.object.document.DocumentInfo;

public class DocumentoENIUtils {
  private static final Logger LOG = Logger.getLogger(DocumentoENIUtils.class);

  private static final String NODE_CONTENT = "content";
  private static final String ATTR_ID = "Id";
  private static final String ATTR_REF = "ref";
  private static final String ATTR_URI = "URI";
  private static final String ATTR_TYPE = "Type";
  private static final String ATTR_ALGORITHM = "Algorithm";
  private static final String ATTR_MIME_TYPE = "MimeType";
  private static final String ATTR_ENCODING = "Encoding";

  private DocumentoENIUtils() {
    throw new IllegalStateException("Clase de utilidad, no instanciable");
  }

  /**
   * Se obtiene un byte[] del DocumentoENI sin contenido pasándole la ruta donde se encuentra
   * guardado el archivo
   * 
   * @param filePath
   * @return
   */
  public static byte[] documentoEniContentRemover(String filePath) {
    byte[] result = null;

    try {
      Integer[] posiciones = obtainDocENIcontentPositions(filePath);
      result = removeContent(filePath, posiciones);
    } catch (IOException | XMLStreamException e) {
      LOG.error("Error al quitar el contenido del documento: " + e.getMessage());
    }

    return result;
  }

  /**
   * Devuelve un array de Integer de tamanio 2:
   * <p>
   * - El primer entero corresponde con la posicion de &lt;ValorBinario>
   * <p>
   * - El sengundo entero corresponde con la posicion de &lt;/ValorBinario>.
   * <p>
   * De esta manera se delimita donde se encuentra el contenido pesado del Documento ENI
   * 
   * @param filePath
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   * @throws XMLStreamException
   */
  private static Integer[] obtainDocENIcontentPositions(String filePath)
      throws IOException, XMLStreamException {
    Integer[] posiciones = new Integer[2];
    final String ELEMENT_CONTENT = "ValorBinario";
    final XMLInputFactory factory = XMLInputFactory.newInstance();

    try (final InputStream stream = new FileInputStream(new File(filePath))) {
      final XMLEventReader reader = factory.createXMLEventReader(stream);

      boolean continuar = true;
      while (continuar && reader.hasNext()) {
        final XMLEvent event = reader.nextEvent();
        if (event.isStartElement()
            && event.asStartElement().getName().getLocalPart().equals(ELEMENT_CONTENT)) {
          int prefLen = event.asStartElement().getName().getPrefix().length();
          if (prefLen > 0) {
            prefLen++;
          }
          posiciones[0] =
              event.getLocation().getCharacterOffset() + prefLen + ELEMENT_CONTENT.length() + 3;
        } else if (event.isEndElement()
            && event.asEndElement().getName().getLocalPart().equals(ELEMENT_CONTENT)) {
          int prefLen = event.asEndElement().getName().getPrefix().length();
          if (prefLen > 0) {
            prefLen++;
          }
          posiciones[1] = event.getLocation().getCharacterOffset();// -
                                                                   // event.asEndElement().getName().getLocalPart().length()
                                                                   // - prefLen - 3;
          continuar = false;
        }
      }
      reader.close();
    }

    return posiciones;
  }

  private static byte[] removeContent(String filePath, Integer[] posiciones) throws IOException {
    byte[] result = null;

    if (posiciones[0] != null) {
      try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
          RandomAccessFile raf = new RandomAccessFile(filePath, "r");) {
        raf.seek(0);

        int c;
        while ((c = raf.read()) != -1 && (raf.getFilePointer() < posiciones[0])) {
          baos.write(c);
        }

        raf.seek(posiciones[1]);
        while ((c = raf.read()) != -1) {
          baos.write(c);
        }
        result = baos.toByteArray();
      }

    } else {
      result = IOUtils.toByteArray(new FileInputStream(new File(filePath)));
    }
    // FileUtils.writeByteArrayToFile(new File("C:\\aapp\\docEni_vaciado.xml"), result);

    return result;
  }

  /**
   * Se obtiene el DocumentoENI parseado en la clase TipoDocumento pasándole un InputStream del
   * contenido.
   * 
   * @param docEni
   * @return
   * @throws IOException
   * @throws XMLStreamException
   */
  public static TipoDocumento getTipoDocumentoByDocumentoEni(InputStream stream)
      throws IOException, XMLStreamException {
    TipoDocumento td = new TipoDocumento();
    final XMLInputFactory factory = XMLInputFactory.newInstance();
    final String NODE_DOCUMENTO = "documento";
    final String NODE_CONTENIDO = "contenido";
    final String NODE_METADATOS = "metadatos";
    final String NODE_FIRMAS = "firmas";

    try {
      final XMLEventReader reader = factory.createXMLEventReader(stream);
      while (reader.hasNext()) {
        final XMLEvent event = reader.nextEvent();
        if (event.isStartElement()
            && event.asStartElement().getName().getLocalPart().equals(NODE_DOCUMENTO)) {
          td.setId(event.asStartElement().getAttributeByName(new QName(ATTR_ID)) != null
              ? event.asStartElement().getAttributeByName(new QName(ATTR_ID)).getValue()
              : null);
        } else if (event.isStartElement()
            && event.asStartElement().getName().getLocalPart().equals(NODE_CONTENIDO)) {
          td.setContenido(parseDocEniContenido(reader, NODE_CONTENIDO,
              event.asStartElement().getAttributeByName(new QName(ATTR_ID)) != null
                  ? event.asStartElement().getAttributeByName(new QName(ATTR_ID)).getValue()
                  : null));
        } else if (event.isStartElement()
            && event.asStartElement().getName().getLocalPart().equals(NODE_METADATOS)) {
          td.setMetadatos(parseDocEniMetadatos(reader, NODE_METADATOS,
              event.asStartElement().getAttributeByName(new QName(ATTR_ID)) != null
                  ? event.asStartElement().getAttributeByName(new QName(ATTR_ID)).getValue()
                  : null));
        } else if (event.isStartElement()
            && event.asStartElement().getName().getLocalPart().equals(NODE_FIRMAS)) {
          td.setFirmas(parseDocEniFirmas(reader, NODE_FIRMAS));
        }
      }
    } finally {
      if (stream != null) {
        stream.close();
      }
    }

    return td;
  }

  private static TipoContenido parseDocEniContenido(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String id) throws XMLStreamException {
    TipoContenido contenido = new TipoContenido();
    contenido.setId(id);

    final String NODE_DATOS_XML = "DatosXML";
    final String NODE_VALOR_BINARIO = "ValorBinario";
    final String NODE_REFERENCIA_FICHERO = "referenciaFichero";
    final String NODE_NOMBRE_FORMATO = "NombreFormato";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_DATOS_XML:
            contenido.setDatosXML(reader.getElementText());
            break;
          case NODE_VALOR_BINARIO:
            contenido.setValorBinario(reader.getElementText().getBytes());
            break;
          case NODE_REFERENCIA_FICHERO:
            contenido.setReferenciaFichero(reader.getElementText());
            break;
          case NODE_NOMBRE_FORMATO:
            contenido.setNombreFormato(reader.getElementText());
            break;
          default:
            break;
        }
      }
    }

    return contenido;
  }

  private static TipoMetadatos parseDocEniMetadatos(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String id) throws XMLStreamException {
    TipoMetadatos metadatos = new TipoMetadatos();
    metadatos.setId(id);

    final String NODE_VERSION = "VersionNTI";
    final String NODE_IDENTIFICADOR = "Identificador";
    final String NODE_ORGANO = "Organo";
    final String NODE_FECHA = "FechaCaptura";
    final String NODE_ORIGEN = "OrigenCiudadanoAdministracion";
    final String NODE_ELABORACION = "EstadoElaboracion";
    final String NODE_TIPO_DOCUMENTAL = "TipoDocumental";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_VERSION:
            metadatos.setVersionNTI(reader.getElementText());
            break;
          case NODE_IDENTIFICADOR:
            metadatos.setIdentificador(reader.getElementText());
            break;
          case NODE_ORGANO:
            metadatos.getOrgano().add(reader.getElementText());
            break;
          case NODE_FECHA:
            String fecha = reader.getElementText().replace('T', ' ');
            fecha = fecha.substring(0, fecha.indexOf('.'));
            try {
              GregorianCalendar cal = new GregorianCalendar();
              cal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fecha));
              metadatos.setFechaCaptura(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            } catch (Exception e) {
              LOG.error("Error al convertir la fecha \"" + fecha + "\" a XMLGregorianCalendar: "
                  + e.getMessage());
            }
            break;
          case NODE_ORIGEN:
            metadatos.setOrigenCiudadanoAdministracion(Boolean.valueOf(reader.getElementText()));
            break;
          case NODE_ELABORACION:
            metadatos.setEstadoElaboracion(parseDocEniElaboracion(reader, NODE_ELABORACION));
            break;
          case NODE_TIPO_DOCUMENTAL:
            metadatos.setTipoDocumental(TipoDocumental.fromValue(reader.getElementText()));
            break;
          default:
            break;
        }
      }
    }

    return metadatos;
  }

  private static TipoEstadoElaboracion parseDocEniElaboracion(final XMLEventReader reader,
      final String PARENT_NODE_NAME) throws XMLStreamException {
    TipoEstadoElaboracion elaboracion = new TipoEstadoElaboracion();

    final String NODE_VALOR_ESTADO = "ValorEstadoElaboracion";
    final String NODE_ID_DOCUMENTO_ORIGEN = "IdentificadorDocumentoOrigen";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_VALOR_ESTADO:
            elaboracion.setValorEstadoElaboracion(
                EnumeracionEstadoElaboracion.fromValue(reader.getElementText()));
            break;
          case NODE_ID_DOCUMENTO_ORIGEN:
            elaboracion.setIdentificadorDocumentoOrigen(reader.getElementText());
            break;
          default:
            break;
        }
      }
    }

    return elaboracion;
  }

  private static Firmas parseDocEniFirmas(final XMLEventReader reader,
      final String PARENT_NODE_NAME) throws XMLStreamException {
    Firmas firmas = new Firmas();

    final String NODE_FIRMA = "firma";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_FIRMA:
            firmas.getFirma()
                .add(parseDocEniFirmaElectronica(reader, NODE_FIRMA,
                    event.asStartElement().getAttributeByName(new QName(ATTR_ID)) != null
                        ? event.asStartElement().getAttributeByName(new QName(ATTR_ID)).getValue()
                        : null,
                    event.asStartElement().getAttributeByName(new QName(ATTR_REF)) != null
                        ? event.asStartElement().getAttributeByName(new QName(ATTR_REF)).getValue()
                        : null));
            break;
          default:
            break;
        }
      }
    }

    return firmas;
  }

  private static TipoFirmasElectronicas parseDocEniFirmaElectronica(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String id, String ref) throws XMLStreamException {
    TipoFirmasElectronicas firma = new TipoFirmasElectronicas();
    firma.setId(id);
    firma.setRef(ref);

    final String NODE_TIPO_FIRMA = "TipoFirma";
    final String NODE_CONTENIDO_FIRMA = "ContenidoFirma";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_TIPO_FIRMA:
            firma.setTipoFirma(TipoFirma.fromValue(reader.getElementText()));
            break;
          case NODE_CONTENIDO_FIRMA:
            firma.setContenidoFirma(parseDocEniContenidoFirma(reader, NODE_CONTENIDO_FIRMA));
            break;
          default:
            break;
        }
      }
    }

    return firma;
  }

  private static ContenidoFirma parseDocEniContenidoFirma(final XMLEventReader reader,
      final String PARENT_NODE_NAME) throws XMLStreamException {
    ContenidoFirma contFirma = new ContenidoFirma();

    final String NODE_CSV = "CSV";
    final String NODE_FIRMA_CERTIFICADO = "FirmaConCertificado";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_CSV:
            contFirma.setCSV(parseDocEniFirmaCsv(reader, NODE_CSV));
            break;
          case NODE_FIRMA_CERTIFICADO:
            contFirma.setFirmaConCertificado(
                parseDocEniFirmaCertificado(reader, NODE_FIRMA_CERTIFICADO));
            break;
          default:
            break;
        }
      }
    }

    return contFirma;
  }

  private static TipoFirmasElectronicas.ContenidoFirma.CSV parseDocEniFirmaCsv(
      final XMLEventReader reader, final String PARENT_NODE_NAME) throws XMLStreamException {
    TipoFirmasElectronicas.ContenidoFirma.CSV firmaCsv =
        new TipoFirmasElectronicas.ContenidoFirma.CSV();

    final String NODE_VALOR_CSV = "ValorCSV";
    final String NODE_REGULACION_CSV = "RegulacionGeneracionCSV";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_VALOR_CSV:
            firmaCsv.setValorCSV(reader.getElementText());
            break;
          case NODE_REGULACION_CSV:
            firmaCsv.setRegulacionGeneracionCSV(reader.getElementText());
            break;
          default:
            break;
        }
      }
    }

    return firmaCsv;
  }

  private static TipoFirmasElectronicas.ContenidoFirma.FirmaConCertificado parseDocEniFirmaCertificado(
      final XMLEventReader reader, final String PARENT_NODE_NAME) throws XMLStreamException {
    TipoFirmasElectronicas.ContenidoFirma.FirmaConCertificado firmaCertificado =
        new TipoFirmasElectronicas.ContenidoFirma.FirmaConCertificado();

    final String NODE_FIRMA_BASE64 = "FirmaBase64";
    final String NODE_SIGNATURE = "Signature";
    final String NODE_REFERENCIA_FIRMA = "ReferenciaFirma";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_FIRMA_BASE64:
            firmaCertificado.setFirmaBase64(reader.getElementText().getBytes());
            break;
          case NODE_REFERENCIA_FIRMA:
            firmaCertificado.setReferenciaFirma(reader.getElementText());
            break;
          case NODE_SIGNATURE:
            firmaCertificado.setSignature(parseDocEniSignature(reader, NODE_SIGNATURE,
                event.asStartElement().getAttributeByName(new QName(ATTR_ID)) != null
                    ? event.asStartElement().getAttributeByName(new QName(ATTR_ID)).getValue()
                    : null));
            break;
          default:
            break;
        }
      }
    }

    return firmaCertificado;
  }

  private static SignatureType parseDocEniSignature(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String id) throws XMLStreamException {
    SignatureType signature = new SignatureType();
    signature.setId(id);

    final String NODE_SIGNED_INFO = "SignedInfo";
    final String NODE_SIGNATURE_VALUE = "SignatureValue";
    final String NODE_KEY_INFO = "KeyInfo";
    final String NODE_OBJECT = "Object";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_SIGNED_INFO:
            signature.setSignedInfo(parseDocEniSignedInfo(reader, NODE_SIGNED_INFO,
                event.asStartElement().getAttributeByName(new QName(ATTR_ID)) != null
                    ? event.asStartElement().getAttributeByName(new QName(ATTR_ID)).getValue()
                    : null));
            break;
          case NODE_SIGNATURE_VALUE:
            signature.setSignatureValue(parseDocEniSignatureValue(reader, NODE_SIGNATURE_VALUE,
                event.asStartElement().getAttributeByName(new QName(ATTR_ID)) != null
                    ? event.asStartElement().getAttributeByName(new QName(ATTR_ID)).getValue()
                    : null));
            break;
          case NODE_KEY_INFO:
            signature.setKeyInfo(parseDocEniKeyInfo(reader, NODE_KEY_INFO,
                event.asStartElement().getAttributeByName(new QName(ATTR_ID)) != null
                    ? event.asStartElement().getAttributeByName(new QName(ATTR_ID)).getValue()
                    : null));
            break;
          case NODE_OBJECT:
            signature.getObject()
                .add(parseDocEniFirmaObject(reader, NODE_OBJECT,
                    event.asStartElement().getAttributeByName(new QName(ATTR_ID)) != null
                        ? event.asStartElement().getAttributeByName(new QName(ATTR_ID)).getValue()
                        : null,
                    event.asStartElement().getAttributeByName(new QName(ATTR_MIME_TYPE)) != null
                        ? event
                            .asStartElement().getAttributeByName(new QName(ATTR_MIME_TYPE))
                            .getValue()
                        : null,
                    event.asStartElement().getAttributeByName(new QName(ATTR_ENCODING)) != null
                        ? event.asStartElement().getAttributeByName(new QName(ATTR_ENCODING))
                            .getValue()
                        : null));
            break;
          default:
            break;
        }
      }
    }

    return signature;
  }

  private static SignedInfoType parseDocEniSignedInfo(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String id) throws XMLStreamException {
    SignedInfoType info = new SignedInfoType();
    info.setId(id);

    final String NODE_CANONICALIZATION_METHOD = "CanonicalizationMethod";
    final String NODE_SIGNATURE_METHOD = "SignatureMethod";
    final String NODE_REFERENCE = "Reference";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_CANONICALIZATION_METHOD:
            info.setCanonicalizationMethod(parseDocEniCanonalicalizationMethod(reader,
                NODE_CANONICALIZATION_METHOD,
                event.asStartElement().getAttributeByName(new QName(ATTR_ALGORITHM)) != null ? event
                    .asStartElement().getAttributeByName(new QName(ATTR_ALGORITHM)).getValue()
                    : null));
            break;
          case NODE_SIGNATURE_METHOD:
            info.setSignatureMethod(parseDocEniSignatureMethod(reader, NODE_SIGNATURE_METHOD,
                event.asStartElement().getAttributeByName(new QName(ATTR_ALGORITHM)) != null ? event
                    .asStartElement().getAttributeByName(new QName(ATTR_ALGORITHM)).getValue()
                    : null));
            break;
          case NODE_REFERENCE:
            info.getReference()
                .add(parseDocEniSignatureReference(reader, NODE_REFERENCE,
                    event.asStartElement().getAttributeByName(new QName(ATTR_ID)) != null
                        ? event.asStartElement().getAttributeByName(new QName(ATTR_ID)).getValue()
                        : null,
                    event.asStartElement().getAttributeByName(new QName(ATTR_URI)) != null
                        ? event.asStartElement().getAttributeByName(new QName(ATTR_URI)).getValue()
                        : null,
                    event.asStartElement().getAttributeByName(new QName(ATTR_TYPE)) != null
                        ? event.asStartElement().getAttributeByName(new QName(ATTR_TYPE)).getValue()
                        : null));
            break;
          default:
            break;
        }
      }
    }

    return info;
  }

  private static CanonicalizationMethodType parseDocEniCanonalicalizationMethod(
      final XMLEventReader reader, final String PARENT_NODE_NAME, String algorithm)
      throws XMLStreamException {
    CanonicalizationMethodType canonicalMeth = new CanonicalizationMethodType();
    canonicalMeth.setAlgorithm(algorithm);

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_CONTENT:
            canonicalMeth.getContent().add(reader.getElementText());
            break;
          default:
            break;
        }
      }
    }

    return canonicalMeth;
  }

  private static SignatureMethodType parseDocEniSignatureMethod(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String algorithm) throws XMLStreamException {
    SignatureMethodType signatureMeth = new SignatureMethodType();
    signatureMeth.setAlgorithm(algorithm);

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_CONTENT:
            signatureMeth.getContent().add(reader.getElementText());
            break;
          default:
            break;
        }
      }
    }

    return signatureMeth;
  }

  private static ReferenceType parseDocEniSignatureReference(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String id, String uri, String type) throws XMLStreamException {
    ReferenceType reference = new ReferenceType();
    reference.setId(id);
    reference.setURI(uri);
    reference.setType(type);

    final String NODE_TRANFSORMS = "Transforms";
    final String NODE_DIGEST_METH = "DigestMethod";
    final String NODE_DIGEST_VALUE = "DigestValue";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_TRANFSORMS:
            reference.setTransforms(parseDocEniTransforms(reader, NODE_TRANFSORMS));
            break;
          case NODE_DIGEST_METH:
            reference.setDigestMethod(parseDocEniDigestMethod(reader, NODE_DIGEST_METH,
                event.asStartElement().getAttributeByName(new QName(ATTR_ALGORITHM)) != null ? event
                    .asStartElement().getAttributeByName(new QName(ATTR_ALGORITHM)).getValue()
                    : null));
            break;
          case NODE_DIGEST_VALUE:
            reference.setDigestValue(reader.getElementText().getBytes());
            break;
          default:
            break;
        }
      }
    }

    return reference;
  }

  private static TransformsType parseDocEniTransforms(final XMLEventReader reader,
      final String PARENT_NODE_NAME) throws XMLStreamException {
    TransformsType transforms = new TransformsType();

    final String NODE_TRANFSORMS = "Transform";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_TRANFSORMS:
            transforms.getTransform().add(parseDocEniTransform(reader, NODE_TRANFSORMS,
                event.asStartElement().getAttributeByName(new QName(ATTR_ALGORITHM)) != null ? event
                    .asStartElement().getAttributeByName(new QName(ATTR_ALGORITHM)).getValue()
                    : null));
            break;
          default:
            break;
        }
      }
    }

    return transforms;
  }

  private static TransformType parseDocEniTransform(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String algorithm) throws XMLStreamException {
    TransformType transform = new TransformType();
    transform.setAlgorithm(algorithm);

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_CONTENT:
            transform.getContent().add(reader.getElementText());
            break;
          default:
            break;
        }
      }
    }

    return transform;
  }

  private static DigestMethodType parseDocEniDigestMethod(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String algorithm) throws XMLStreamException {
    DigestMethodType digest = new DigestMethodType();
    digest.setAlgorithm(algorithm);

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_CONTENT:
            digest.getContent().add(reader.getElementText());
            break;
          default:
            break;
        }
      }
    }

    return digest;
  }

  private static SignatureValueType parseDocEniSignatureValue(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String id) throws XMLStreamException {
    SignatureValueType value = new SignatureValueType();
    value.setId(id);

    final String NODE_VALUE = "value";

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_VALUE:
            value.setValue(reader.getElementText().getBytes());
            break;
          default:
            break;
        }
      }
    }

    return value;
  }

  private static KeyInfoType parseDocEniKeyInfo(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String id) throws XMLStreamException {
    KeyInfoType info = new KeyInfoType();
    info.setId(id);

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_CONTENT:
            info.getContent().add(reader.getElementText());
            break;
          default:
            break;
        }
      }
    }

    return info;
  }

  private static ObjectType parseDocEniFirmaObject(final XMLEventReader reader,
      final String PARENT_NODE_NAME, String id, String mimeType, String encoding)
      throws XMLStreamException {
    ObjectType obj = new ObjectType();
    obj.setId(id);
    obj.setMimeType(mimeType);
    obj.setEncoding(encoding);

    boolean continuar = true;
    while (continuar && reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isEndElement()
          && event.asEndElement().getName().getLocalPart().equals(PARENT_NODE_NAME)) {
        continuar = false;
      }
      if (continuar && event.isStartElement()) {
        final StartElement element = event.asStartElement();
        final String elementName = element.getName().getLocalPart();
        switch (elementName) {
          case NODE_CONTENT:
            obj.getContent().add(reader.getElementText());
            break;
          default:
            break;
        }
      }
    }

    return obj;
  }

  /**
   * Se obtiene la clase DocumentInfo pasándole un String con el XML
   * 
   * @param xml
   * @return
   * @throws IOException
   * @throws XMLStreamException
   */
  public static DocumentInfo parseToDocumentInfo(String xml)
      throws IOException, XMLStreamException {
    DocumentInfo docInfo = new DocumentInfo();
    final XMLInputFactory factory = XMLInputFactory.newInstance();
    final String NODE_CSV = "csv";
    final String NODE_ID_ENI = "idEni";
    final String NODE_TIPO_MIME = "tipoMime";
    final String NODE_CONTENIDO = "contenido";

    try (final InputStream stream = new ByteArrayInputStream(xml.getBytes())) {
      final XMLEventReader reader = factory.createXMLEventReader(stream);
      while (reader.hasNext()) {
        final XMLEvent event = reader.nextEvent();
        if (event.isStartElement()
            && event.asStartElement().getName().getLocalPart().equals(NODE_CSV)) {
          docInfo.setCsv(reader.getElementText());
        } else if (event.isStartElement()
            && event.asStartElement().getName().getLocalPart().equals(NODE_ID_ENI)) {
          docInfo.setIdEni(reader.getElementText());
        } else if (event.isStartElement()
            && event.asStartElement().getName().getLocalPart().equals(NODE_TIPO_MIME)) {
          docInfo.setTipoMime(reader.getElementText());
        } else if (event.isStartElement()
            && event.asStartElement().getName().getLocalPart().equals(NODE_CONTENIDO)) {
          docInfo.setContenido(Base64.decodeBase64(reader.getElementText()));
        }
      }
    }
    return docInfo;
  }
}
