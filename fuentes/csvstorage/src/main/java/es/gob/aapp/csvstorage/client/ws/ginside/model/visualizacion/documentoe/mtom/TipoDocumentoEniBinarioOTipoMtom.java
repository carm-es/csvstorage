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


package es.gob.aapp.csvstorage.client.ws.ginside.model.visualizacion.documentoe.mtom;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.documentoe.mtom.TipoDocumentoMtom;


/**
 * <p>
 * Clase Java para TipoDocumentoEniBinarioOTipoMtom complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TipoDocumentoEniBinarioOTipoMtom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="documentoEniBinario" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="documentoEniTipo" type="{http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/mtom}TipoDocumentoMtom"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoDocumentoEniBinarioOTipoMtom",
    propOrder = {"documentoEniBinario", "documentoEniTipo"})
public class TipoDocumentoEniBinarioOTipoMtom {

  @XmlMimeType("application/octet-stream")
  protected DataHandler documentoEniBinario;
  protected TipoDocumentoMtom documentoEniTipo;

  /**
   * Obtiene el valor de la propiedad documentoEniBinario.
   * 
   * @return possible object is {@link DataHandler }
   * 
   */
  public DataHandler getDocumentoEniBinario() {
    return documentoEniBinario;
  }

  /**
   * Define el valor de la propiedad documentoEniBinario.
   * 
   * @param value allowed object is {@link DataHandler }
   * 
   */
  public void setDocumentoEniBinario(DataHandler value) {
    this.documentoEniBinario = value;
  }

  /**
   * Obtiene el valor de la propiedad documentoEniTipo.
   * 
   * @return possible object is {@link TipoDocumentoMtom }
   * 
   */
  public TipoDocumentoMtom getDocumentoEniTipo() {
    return documentoEniTipo;
  }

  /**
   * Define el valor de la propiedad documentoEniTipo.
   * 
   * @param value allowed object is {@link TipoDocumentoMtom }
   * 
   */
  public void setDocumentoEniTipo(TipoDocumentoMtom value) {
    this.documentoEniTipo = value;
  }

}
