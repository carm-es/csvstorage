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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerDocumentoEniRequest", propOrder = {"dir3", "idENI", "csv"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class ObtenerDocumentoEniRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Código de la unidad orgánica.
   */
  @XmlElement(required = true)
  protected String dir3;

  /**
   * Id ENI.
   */
  @XmlElement(required = true)
  protected String idENI;

  /**
   * csv del documento de firma.
   */
  @XmlElement(required = false)
  protected String csv;



  /**
   * @return the csv
   */
  public String getCsv() {
    return csv;
  }

  /**
   * @param csv the csv to set
   */
  public void setCsv(String csv) {
    this.csv = csv;
  }

  /**
   * @return the idENI
   */
  public String getIdENI() {
    return idENI;
  }

  /**
   * @param idENI the idENI to set
   */
  public void setIdENI(String idENI) {
    this.idENI = idENI;
  }

  /**
   * @return the dir3
   */
  public String getDir3() {
    return dir3;
  }

  /**
   * @param dir3 the dir3 to set
   */
  public void setDir3(String dir3) {
    this.dir3 = dir3;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ObtenerDocumentoEniRequest [idENI=" + idENI + ", csv=" + csv + ", dir3=" + dir3 + "]";
  }

  public String parametrosAuditoria() {
    String audit = "dir3" + Constants.AUDIT_SEPARATOR_VALUE + this.dir3;
    audit += (this.csv != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "csv" + Constants.AUDIT_SEPARATOR_VALUE + this.csv
        : "";
    audit += (this.idENI != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "idENI" + Constants.AUDIT_SEPARATOR_VALUE + this.idENI
        : "";

    return audit;
  }
}
