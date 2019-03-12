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


package es.gob.aapp.csvbroker.webservices.querydocument.model.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.util.constants.Constants;


/**
 * <p>
 * Clase Java para CSVQueryDocumentRequest complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CSVQueryDocumentRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="csv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dir3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSVQueryDocumentRequest",
    propOrder = {"csv", "dir3", "recuperacionOriginal", "documentoEni", "idEni"})
public class CSVQueryDocumentRequest {

  @XmlElement(required = false)
  protected String csv;
  @XmlElement(required = false)
  protected String dir3;
  @XmlElement(required = false, name = "recuperacion_original")
  private RecuperacionOriginal recuperacionOriginal;
  @XmlElement(required = false, name = "documento_eni")
  private DocumentoEni documentoEni;

  /** The id eni. */
  @XmlElement(required = false)
  protected String idEni;

  /**
   * Obtiene el valor de la propiedad csv.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getCsv() {
    return csv;
  }

  /**
   * Define el valor de la propiedad csv.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setCsv(String value) {
    this.csv = value;
  }

  /**
   * Obtiene el valor de la propiedad dir3.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getDir3() {
    return dir3;
  }

  /**
   * Define el valor de la propiedad dir3.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setDir3(String value) {
    this.dir3 = value;
  }

  /**
   * @return the recuperacionOriginal
   */
  public CSVQueryDocumentRequest.RecuperacionOriginal getRecuperacionOriginal() {
    return recuperacionOriginal;
  }

  /**
   * @param recuperacionOriginal the recuperacionOriginal to set
   */
  public void setRecuperacionOriginal(
      CSVQueryDocumentRequest.RecuperacionOriginal recuperacionOriginal) {
    this.recuperacionOriginal = recuperacionOriginal;
  }

  /**
   * <p>
   * Clase Java para recuperacionOriginal.
   * 
   * <p>
   * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
   * <p>
   * 
   * <pre>
   * &lt;simpleType name="recuperacionOriginal">
   *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
   *     &lt;enumeration value="S"/>
   *     &lt;enumeration value="N"/>
   *   &lt;/restriction>
   * &lt;/simpleType>
   * </pre>
   * 
   */
  @XmlType(name = "recuperacionOriginal")
  @XmlEnum
  public enum RecuperacionOriginal {

    S, N;

    public String value() {
      return name();
    }

    public static RecuperacionOriginal fromValue(String v) {
      return valueOf(v);
    }

  }

  @XmlType(name = "documentoEni")
  @XmlEnum
  public enum DocumentoEni {

    S, N;

    public String value() {
      return name();
    }

    public static DocumentoEni fromValue(String v) {
      return valueOf(v);
    }

  }

  /**
   * @return the documentoEni
   */
  public DocumentoEni getDocumentoEni() {
    return documentoEni;
  }

  /**
   * @param documentoEni the documentoEni to set
   */
  public void setDocumentoEni(DocumentoEni documentoEni) {
    this.documentoEni = documentoEni;
  }

  public String getIdEni() {
    return idEni;
  }

  public void setIdEni(String idEni) {
    this.idEni = idEni;
  }

  public String parametrosAuditoria() {
    String audit = "";
    audit += (this.dir3 != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "dir3" + Constants.AUDIT_SEPARATOR_VALUE + this.dir3
        : "";
    audit += (this.csv != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "csv" + Constants.AUDIT_SEPARATOR_VALUE + this.csv
        : "";
    audit +=
        (this.recuperacionOriginal != null)
            ? Constants.AUDIT_SEPARATOR_ROW + "recuperacion_original"
                + Constants.AUDIT_SEPARATOR_VALUE + this.recuperacionOriginal.value()
            : "";
    audit += (this.documentoEni != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "documento_eni" + Constants.AUDIT_SEPARATOR_VALUE
            + this.documentoEni.value()
        : "";
    audit += (this.idEni != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "id_eni" + Constants.AUDIT_SEPARATOR_VALUE + this.idEni
        : "";
    return audit;
  }
}
