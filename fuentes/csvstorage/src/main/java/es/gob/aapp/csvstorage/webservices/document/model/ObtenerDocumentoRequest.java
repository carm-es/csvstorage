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

package es.gob.aapp.csvstorage.webservices.document.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.util.constants.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class ObtenerDocumentoRequest.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerDocumentoRequest",
    propOrder = {"dir3", "csv", "idEni", "recuperacionOriginal", "documentoEni"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class ObtenerDocumentoRequest implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** Unidad orgánica. */
  @XmlElement(required = true)
  protected String dir3;
  /**
   * csv del documento de firma.
   */
  @XmlElement(required = false)
  protected String csv;

  /** The id eni. */
  @XmlElement(required = false)
  protected String idEni;

  @XmlElement(required = false, name = "recuperacion_original")
  protected RecuperacionOriginal recuperacionOriginal;

  @XmlElement(required = false, name = "documento_eni")
  protected DocumentoEni documentoEni;

  /**
   * Gets the csv.
   *
   * @return the csv
   */
  public String getCsv() {
    return csv;
  }

  /**
   * Sets the csv.
   *
   * @param csv the csv to set
   */
  public void setCsv(String csv) {
    this.csv = csv;
  }

  /**
   * Gets the dir3.
   *
   * @return the dir3
   */
  public String getDir3() {
    return dir3;
  }

  /**
   * Sets the dir3.
   *
   * @param dir3 the dir3 to set
   */
  public void setDir3(String dir3) {
    this.dir3 = dir3;
  }

  /**
   * Gets the id eni.
   *
   * @return the id eni
   */
  public String getIdEni() {
    return idEni;
  }

  /**
   * Sets the id eni.
   *
   * @param idEni the new id eni
   */
  public void setIdEni(String idEni) {
    this.idEni = idEni;
  }

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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ObtenerDocumentoRequest [csv=" + csv + ", dir3=" + dir3 + ", idEni=" + idEni + "]";
  }

  /**
   * @return the recuperacionOriginal
   */
  public RecuperacionOriginal getRecuperacionOriginal() {
    return recuperacionOriginal;
  }

  /**
   * @param recuperacionOriginal the recuperacionOriginal to set
   */
  public void setRecuperacionOriginal(RecuperacionOriginal recuperacionOriginal) {
    this.recuperacionOriginal = recuperacionOriginal;
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

  public String parametrosAuditoria() {
    String audit = "dir3" + Constants.AUDIT_SEPARATOR_VALUE + this.dir3;
    audit += (this.csv != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "csv" + Constants.AUDIT_SEPARATOR_VALUE + this.csv
        : "";
    audit += (this.idEni != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "idEni" + Constants.AUDIT_SEPARATOR_VALUE + this.idEni
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

    return audit;
  }

}
