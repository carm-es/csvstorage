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
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.util.constants.Constants;


/**
 * The Class GuardarDocumento.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    propOrder = {"dir3", "csv", "idEni", "tipoPermiso", "restricciones", "tipoIds", "nifs",
        "aplicaciones", "hash", "algoritmo"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class DocumentoRequest implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Cadena de 3 digitos con el ámbito de la aplicación.
   */
  @XmlElement(required = true)
  protected String dir3;
  /**
   * csv del documento de firma.
   */
  @XmlElement(required = false)
  protected String csv;

  /** El id eni. */
  @XmlElement(required = false)
  protected String idEni;

  /** El tipo de permiso */
  @XmlElement(required = false)
  protected DocumentoRequest.TipoPermiso tipoPermiso;

  /** El tipo de permiso */
  @XmlElement(required = false)
  protected ListaRestriccion restricciones;

  /** Lista de ids. */
  @XmlElement(required = false)
  protected ListaTipoIds tipoIds;

  /** Lista de nifs. */
  @XmlElement(required = false)
  protected ListaNifs nifs;

  /** Lista de aplicaciones. */
  @XmlElement(required = false)
  protected ListaAplicaciones aplicaciones;

  /** hash del documento */
  @XmlElement(required = false)
  protected String hash;

  /** algoritmo de cálculo del hash */
  @XmlElement(required = false)
  protected String algoritmo;

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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "DocumentoRequest [dir3=" + dir3 + ", csv=" + csv + ", idEni=" + idEni + ", nifs=" + nifs
        + "]";
  }

  public ListaTipoIds getTipoIds() {
    return tipoIds;
  }

  public void setTipoIds(ListaTipoIds tipoIds) {
    this.tipoIds = tipoIds;
  }

  public ListaAplicaciones getAplicaciones() {
    return aplicaciones;
  }

  public void setAplicaciones(ListaAplicaciones aplicaciones) {
    this.aplicaciones = aplicaciones;
  }

  /**
   * @return the nif
   */
  public ListaNifs getNifs() {
    return nifs;
  }

  /**
   * @param nifs the nifs to set
   */
  public void setNifs(ListaNifs nifs) {
    this.nifs = nifs;
  }

  /**
   * @return the tipoPermiso
   */
  public DocumentoRequest.TipoPermiso getTipoPermiso() {
    return tipoPermiso;
  }

  /**
   * @param tipoPermiso the tipoPermiso to set
   */
  public void setTipoPermiso(DocumentoRequest.TipoPermiso tipoPermiso) {
    this.tipoPermiso = tipoPermiso;
  }


  public ListaRestriccion getRestricciones() {
    return restricciones;
  }

  public void setRestricciones(ListaRestriccion restricciones) {
    this.restricciones = restricciones;
  }

  /**
   * <p>
   * Clase Java para tipoPermiso.
   * 
   * <p>
   * El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
   * <p>
   * 
   * <pre>
   * &lt;simpleType name="tipoPermiso">
   *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
   *     &lt;enumeration value="PUBLICO"/>
   *     &lt;enumeration value="PRIVADO"/>
   *     &lt;enumeration value="RESTRINGIDO"/>
   *   &lt;/restriction>
   * &lt;/simpleType>
   * </pre>
   * 
   */

  @XmlType(name = "tipoPermiso")
  @XmlEnum
  public enum TipoPermiso {

    @XmlEnumValue("PUBLICO")
    PUBLICO("PUBLICO"), @XmlEnumValue("PRIVADO")
    PRIVADO("PRIVADO"), @XmlEnumValue("RESTRINGIDO")
    RESTRINGIDO("RESTRINGIDO");
    private final String value;

    TipoPermiso(String v) {
      value = v;
    }

    public String value() {
      return value;
    }

    public static TipoPermiso fromValue(String v) {
      for (TipoPermiso c : TipoPermiso.values()) {
        if (c.value.equals(v)) {
          return c;
        }
      }
      throw new IllegalArgumentException(v);
    }

  }

  /**
   * @return the hash
   */
  public String getHash() {
    return hash;
  }

  /**
   * @param hash the hash to set
   */
  public void setHash(String hash) {
    this.hash = hash;
  }

  /**
   * @return the algotirmo
   */
  public String getAlgotirmo() {
    return algoritmo;
  }

  /**
   * @param algotirmo the algotirmo to set
   */
  public void setAlgotirmo(String algotirmo) {
    this.algoritmo = algotirmo;
  }

  public String parametrosAuditoria() {
    String audit = "dir3" + Constants.AUDIT_SEPARATOR_VALUE + this.dir3;
    audit += (this.csv != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "csv" + Constants.AUDIT_SEPARATOR_VALUE + this.csv
        : "";
    audit += (this.idEni != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "idEni" + Constants.AUDIT_SEPARATOR_VALUE + this.idEni
        : "";
    audit += (this.tipoPermiso != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "tipoPermiso" + Constants.AUDIT_SEPARATOR_VALUE
            + this.tipoPermiso.value
        : "";

    audit += (this.restricciones != null)
        ? Constants.AUDIT_SEPARATOR_ROW + this.restricciones.parametrosAuditoria()
        : "";
    audit +=
        (this.tipoIds != null) ? Constants.AUDIT_SEPARATOR_ROW + this.tipoIds.parametrosAuditoria()
            : "";
    audit +=
        (this.nifs != null) ? Constants.AUDIT_SEPARATOR_ROW + this.nifs.parametrosAuditoria() : "";
    audit += (this.aplicaciones != null)
        ? Constants.AUDIT_SEPARATOR_ROW + this.aplicaciones.parametrosAuditoria()
        : "";

    audit += (this.hash != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "hash" + Constants.AUDIT_SEPARATOR_VALUE + this.csv
        : "";
    audit += (this.algoritmo != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "algoritmo" + Constants.AUDIT_SEPARATOR_VALUE
            + this.algoritmo
        : "";

    return audit;
  }
}
