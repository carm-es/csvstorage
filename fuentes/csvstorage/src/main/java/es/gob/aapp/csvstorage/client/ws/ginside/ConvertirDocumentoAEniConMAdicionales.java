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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.conversion.mtom.TipoDocumentoConversionInsideMtom;
import es.gob.aapp.csvstorage.client.ws.ginside.model.metadatosadicionales.TipoMetadatosAdicionales;


/**
 * <p>
 * Clase Java para convertirDocumentoAEniConMAdicionales complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="convertirDocumentoAEniConMAdicionales">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documento" type="{https://ssweb.seap.minhap.es/Inside/XSD/v1.0/documento-e/conversion/mtom}TipoDocumentoConversionInsideMtom"/>
 *         &lt;element name="metadatosAdicionales" type="{https://ssweb.seap.minhap.es/Inside/XSD/v1.0/metadatosAdicionales}TipoMetadatosAdicionales" minOccurs="0"/>
 *         &lt;element name="contenido" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="firmar" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "convertirDocumentoAEniConMAdicionales",
    propOrder = {"documento", "metadatosAdicionales", "contenido", "firmar"})
public class ConvertirDocumentoAEniConMAdicionales {

  @XmlElement(required = true)
  protected TipoDocumentoConversionInsideMtom documento;
  protected TipoMetadatosAdicionales metadatosAdicionales;
  protected byte[] contenido;
  protected Boolean firmar;

  /**
   * Obtiene el valor de la propiedad documento.
   * 
   * @return possible object is {@link TipoDocumentoConversionInsideMtom }
   * 
   */
  public TipoDocumentoConversionInsideMtom getDocumento() {
    return documento;
  }

  /**
   * Define el valor de la propiedad documento.
   * 
   * @param value allowed object is {@link TipoDocumentoConversionInsideMtom }
   * 
   */
  public void setDocumento(TipoDocumentoConversionInsideMtom value) {
    this.documento = value;
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
   * Obtiene el valor de la propiedad contenido.
   * 
   * @return possible object is byte[]
   */
  public byte[] getContenido() {
    return contenido;
  }

  /**
   * Define el valor de la propiedad contenido.
   * 
   * @param value allowed object is byte[]
   */
  public void setContenido(byte[] value) {
    this.contenido = value;
  }

  /**
   * Obtiene el valor de la propiedad firmar.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isFirmar() {
    return firmar;
  }

  /**
   * Define el valor de la propiedad firmar.
   * 
   * @param value allowed object is {@link Boolean }
   * 
   */
  public void setFirmar(Boolean value) {
    this.firmar = value;
  }

}
