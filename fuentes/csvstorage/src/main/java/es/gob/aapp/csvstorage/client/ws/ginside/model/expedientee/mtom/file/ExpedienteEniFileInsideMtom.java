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


package es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.mtom.file;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.client.ws.eni.expedientee.mtom.TipoExpedienteMtom;


/**
 * <p>
 * Clase Java para ExpedienteEniFileInsideMtom complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ExpedienteEniFileInsideMtom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://administracionelectronica.gob.es/ENI/XSD/v1.0/expediente-e/mtom}expedienteMtom"/>
 *         &lt;element name="expedienteEniBytes" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExpedienteEniFileInsideMtom", propOrder = {"expedienteMtom", "expedienteEniBytes"})
public class ExpedienteEniFileInsideMtom {

  @XmlElement(namespace = "http://administracionelectronica.gob.es/ENI/XSD/v1.0/expediente-e/mtom",
      required = true)
  protected TipoExpedienteMtom expedienteMtom;
  @XmlElement(required = true)
  @XmlMimeType("application/octet-stream")
  protected DataHandler expedienteEniBytes;

  /**
   * Obtiene el valor de la propiedad expedienteMtom.
   * 
   * @return possible object is {@link TipoExpedienteMtom }
   * 
   */
  public TipoExpedienteMtom getExpedienteMtom() {
    return expedienteMtom;
  }

  /**
   * Define el valor de la propiedad expedienteMtom.
   * 
   * @param value allowed object is {@link TipoExpedienteMtom }
   * 
   */
  public void setExpedienteMtom(TipoExpedienteMtom value) {
    this.expedienteMtom = value;
  }

  /**
   * Obtiene el valor de la propiedad expedienteEniBytes.
   * 
   * @return possible object is {@link DataHandler }
   * 
   */
  public DataHandler getExpedienteEniBytes() {
    return expedienteEniBytes;
  }

  /**
   * Define el valor de la propiedad expedienteEniBytes.
   * 
   * @param value allowed object is {@link DataHandler }
   * 
   */
  public void setExpedienteEniBytes(DataHandler value) {
    this.expedienteEniBytes = value;
  }

}
