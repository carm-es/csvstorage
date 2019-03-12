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
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.util.constants.Constants;


/**
 * The Class GuardarDocumento.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"dir3", "csv", "idEni", "aplicaciones"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class DocumentoPermisoRequest implements Serializable {

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


  /** Lista de aplicaciones. */
  @XmlElement(required = true)
  protected ListaAplicaciones aplicaciones;


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


  public ListaAplicaciones getAplicaciones() {
    return aplicaciones;
  }

  public void setAplicaciones(ListaAplicaciones aplicaciones) {
    this.aplicaciones = aplicaciones;
  }

  public String parametrosAuditoria() {
    String audit = "dir3" + Constants.AUDIT_SEPARATOR_VALUE + this.dir3;
    audit += (this.csv != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "csv" + Constants.AUDIT_SEPARATOR_VALUE + this.csv
        : "";
    audit += (this.idEni != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "idEni" + Constants.AUDIT_SEPARATOR_VALUE + this.idEni
        : "";
    audit += (this.aplicaciones != null)
        ? Constants.AUDIT_SEPARATOR_ROW + this.aplicaciones.parametrosAuditoria()
        : "";

    return audit;
  }
}
