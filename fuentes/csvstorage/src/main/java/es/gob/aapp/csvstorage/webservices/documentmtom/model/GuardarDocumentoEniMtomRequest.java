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

package es.gob.aapp.csvstorage.webservices.documentmtom.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.documentoe.mtom.TipoDocumentoMtom;
import es.gob.aapp.csvstorage.client.ws.ginside.model.metadatosadicionales.TipoMetadatosAdicionales;
import es.gob.aapp.csvstorage.util.constants.Constants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "guardarDocumentoEniMtomRequest",
    propOrder = {"dir3", "csv", "documento", "metadatosAdicionales"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:documentmtom:model:v1.0")
public class GuardarDocumentoEniMtomRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Cadena de 3 digitos con el �mbito de la aplicaci�n.
   */
  @XmlElement(required = true)
  protected String dir3;
  /**
   * csv del documento de firma.
   */
  @XmlElement(required = false)
  protected String csv;

  /**
   * Contenido del documento de firma.
   */
  @XmlElement(required = true)
  protected TipoDocumentoMtom documento;

  /**
   * Metadatos adicionales
   */
  @XmlElement(required = false)
  protected TipoMetadatosAdicionales metadatosAdicionales;

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
   * @return the documento
   */
  public TipoDocumentoMtom getDocumento() {
    return documento;
  }

  /**
   * @param documento the documento to set
   */
  public void setDocumento(TipoDocumentoMtom documento) {
    this.documento = documento;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "GuardarDocumentoEniMtomRequest [dir3=" + dir3 + ", csv=" + csv + ", documento="
        + documento + "]";
  }

  /**
   * @return the metadatosAdicionales
   */
  public TipoMetadatosAdicionales getMetadatosAdicionales() {
    return metadatosAdicionales;
  }

  /**
   * @param metadatosAdicionales the metadatosAdicionales to set
   */
  public void setMetadatosAdicionales(TipoMetadatosAdicionales metadatosAdicionales) {
    this.metadatosAdicionales = metadatosAdicionales;
  }

  public String parametrosAuditoria() {
    String audit = "dir3" + Constants.AUDIT_SEPARATOR_VALUE + this.dir3;
    audit += (this.csv != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "csv" + Constants.AUDIT_SEPARATOR_VALUE + this.csv
        : "";
    audit += (this.documento != null && this.documento.getMetadatos() != null
        && this.documento.getMetadatos().getIdentificador() != null)
            ? Constants.AUDIT_SEPARATOR_ROW + "Identificador" + Constants.AUDIT_SEPARATOR_VALUE
                + this.documento.getMetadatos().getIdentificador()
            : "";

    return audit;
  }
}
