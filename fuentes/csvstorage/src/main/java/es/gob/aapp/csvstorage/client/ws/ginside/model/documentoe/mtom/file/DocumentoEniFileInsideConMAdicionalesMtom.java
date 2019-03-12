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


package es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.mtom.file;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.documentoe.mtom.TipoDocumentoMtom;
import es.gob.aapp.csvstorage.client.ws.ginside.model.metadatosadicionales.TipoMetadatosAdicionales;


/**
 * <p>
 * Clase Java para DocumentoEniFileInsideConMAdicionalesMtom complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DocumentoEniFileInsideConMAdicionalesMtom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/mtom}documentoMtom"/>
 *         &lt;element name="metadatosAdicionales" type="{https://ssweb.seap.minhap.es/Inside/XSD/v1.0/metadatosAdicionales}TipoMetadatosAdicionales" minOccurs="0"/>
 *         &lt;element name="documentoEniBytes" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoEniFileInsideConMAdicionalesMtom",
    propOrder = {"documentoMtom", "metadatosAdicionales", "documentoEniBytes"})
public class DocumentoEniFileInsideConMAdicionalesMtom {

  @XmlElement(namespace = "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/mtom",
      required = true)
  protected TipoDocumentoMtom documentoMtom;
  protected TipoMetadatosAdicionales metadatosAdicionales;
  @XmlElement(required = true)
  @XmlMimeType("application/octet-stream")
  protected DataHandler documentoEniBytes;

  /**
   * Obtiene el valor de la propiedad documentoMtom.
   * 
   * @return possible object is {@link TipoDocumentoMtom }
   * 
   */
  public TipoDocumentoMtom getDocumentoMtom() {
    return documentoMtom;
  }

  /**
   * Define el valor de la propiedad documentoMtom.
   * 
   * @param value allowed object is {@link TipoDocumentoMtom }
   * 
   */
  public void setDocumentoMtom(TipoDocumentoMtom value) {
    this.documentoMtom = value;
  }

  /**
   * Obtiene el valor de la propiedad metadatosAdicionales.
   * 
   * @return possible object is {@link TipoMetadatosAdicionales }
   * 
   */
  public TipoMetadatosAdicionales getMetadatosAdicionales() {
    return metadatosAdicionales;
  }

  /**
   * Define el valor de la propiedad metadatosAdicionales.
   * 
   * @param value allowed object is {@link TipoMetadatosAdicionales }
   * 
   */
  public void setMetadatosAdicionales(TipoMetadatosAdicionales value) {
    this.metadatosAdicionales = value;
  }

  /**
   * Obtiene el valor de la propiedad documentoEniBytes.
   * 
   * @return possible object is {@link DataHandler }
   * 
   */
  public DataHandler getDocumentoEniBytes() {
    return documentoEniBytes;
  }

  /**
   * Define el valor de la propiedad documentoEniBytes.
   * 
   * @param value allowed object is {@link DataHandler }
   * 
   */
  public void setDocumentoEniBytes(DataHandler value) {
    this.documentoEniBytes = value;
  }

}
