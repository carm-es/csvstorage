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
import java.text.SimpleDateFormat;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.StringUtils;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.TipoDocumento;
import es.gob.aapp.csvstorage.client.ws.eni.firma.TipoFirmasElectronicas;
import es.gob.aapp.csvstorage.util.constants.Constants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "guardarDocumentoEniRequest", propOrder = {"dir3", "csv", "documento"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class GuardarDocumentoEniRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Código DIR3.
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
  protected TipoDocumento documento;



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
  public TipoDocumento getDocumento() {
    return documento;
  }

  /**
   * @param documento the documento to set
   */
  public void setDocumento(TipoDocumento documento) {
    this.documento = documento;
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
    return "GuardarDocumentoEniRequest [dir3=" + dir3 + ", csv=" + csv + ", documento=" + documento
        + "]";
  }

  public String parametrosAuditoria() {
    String csvAudit = (this.csv != null)
        ? Constants.AUDIT_SEPARATOR_ROW + "csv" + Constants.AUDIT_SEPARATOR_VALUE + this.csv
        : "";

    StringBuilder audit = new StringBuilder();
    audit.append("dir3").append(Constants.AUDIT_SEPARATOR_VALUE).append(this.dir3);
    audit.append(csvAudit);
    audit.append(parametrosAuditoriaMetadatos());
    audit.append(parametrosAuditoriaContenido());
    audit.append(parametrosAuditoriaFirmas());

    return audit.toString();
  }

  private String parametrosAuditoriaMetadatos() {
    String audit = "";
    if (this.documento != null && this.documento.getMetadatos() != null) {
      audit += (this.documento.getMetadatos().getIdentificador() != null)
          ? Constants.AUDIT_SEPARATOR_ROW + "Identificador" + Constants.AUDIT_SEPARATOR_VALUE
              + this.documento.getMetadatos().getIdentificador()
          : "";

      audit += (this.documento.getMetadatos().getVersionNTI() != null)
          ? Constants.AUDIT_SEPARATOR_ROW + "VersionNTI" + Constants.AUDIT_SEPARATOR_VALUE
              + this.documento.getMetadatos().getVersionNTI()
          : "";

      if (this.documento.getMetadatos().getOrgano() != null) {
        StringBuilder auditB = new StringBuilder().append("");
        for (String org : this.documento.getMetadatos().getOrgano()) {
          auditB.append(Constants.AUDIT_SEPARATOR_ROW).append("Organo")
              .append(Constants.AUDIT_SEPARATOR_VALUE).append(org);
        }
        audit += auditB.toString();
      }

      audit += (this.documento.getMetadatos().getFechaCaptura() != null)
          ? Constants.AUDIT_SEPARATOR_ROW + "FechaCaptura" + Constants.AUDIT_SEPARATOR_VALUE
              + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                  this.documento.getMetadatos().getFechaCaptura().toGregorianCalendar().getTime())
          : "";

      audit += (this.documento.getMetadatos().isOrigenCiudadanoAdministracion())
          ? Constants.AUDIT_SEPARATOR_ROW + "OrigenCiudadanoAdministracion"
              + Constants.AUDIT_SEPARATOR_VALUE + "true"
          : Constants.AUDIT_SEPARATOR_ROW + "OrigenCiudadanoAdministracion"
              + Constants.AUDIT_SEPARATOR_VALUE + "false";

      audit += (this.documento.getMetadatos().getEstadoElaboracion() != null && this.documento
          .getMetadatos().getEstadoElaboracion().getValorEstadoElaboracion() != null)
              ? Constants.AUDIT_SEPARATOR_ROW + "ValorEstadoElaboracion"
                  + Constants.AUDIT_SEPARATOR_VALUE
                  + this.documento.getMetadatos().getEstadoElaboracion().getValorEstadoElaboracion()
                      .value()
              : "";

      audit += (this.documento.getMetadatos().getEstadoElaboracion() != null
          && StringUtils.isNotEmpty(this.documento.getMetadatos().getEstadoElaboracion()
              .getIdentificadorDocumentoOrigen()))
                  ? Constants.AUDIT_SEPARATOR_ROW + "IdentificadorDocumentoOrigen"
                      + Constants.AUDIT_SEPARATOR_VALUE
                      + this.documento.getMetadatos().getEstadoElaboracion()
                          .getIdentificadorDocumentoOrigen()
                  : "";

      audit += (this.documento.getMetadatos().getTipoDocumental() != null)
          ? Constants.AUDIT_SEPARATOR_ROW + "TipoDocumental" + Constants.AUDIT_SEPARATOR_VALUE
              + this.documento.getMetadatos().getTipoDocumental().value()
          : "";
    }

    return audit;
  }

  private String parametrosAuditoriaContenido() {
    String audit = "";

    if (this.documento != null && this.documento.getContenido() != null) {
      audit += (this.documento.getContenido().getNombreFormato() != null)
          ? Constants.AUDIT_SEPARATOR_ROW + "NombreFormato" + Constants.AUDIT_SEPARATOR_VALUE
              + this.documento.getContenido().getNombreFormato()
          : "";
      audit += (this.documento.getContenido().getReferenciaFichero() != null)
          ? Constants.AUDIT_SEPARATOR_ROW + "referenciaFichero" + Constants.AUDIT_SEPARATOR_VALUE
              + this.documento.getContenido().getReferenciaFichero()
          : "";
    }
    return audit;
  }

  private String parametrosAuditoriaFirmas() {
    StringBuilder audit = new StringBuilder().append("");

    if (this.documento != null && this.documento.getFirmas() != null
        && this.documento.getFirmas().getFirma() != null) {
      for (TipoFirmasElectronicas firm : this.documento.getFirmas().getFirma()) {
        audit.append(Constants.AUDIT_SEPARATOR_ROW).append("TipoFirma")
            .append(Constants.AUDIT_SEPARATOR_VALUE).append(firm.getTipoFirma().value());
      }
    }

    return audit.toString();
  }

}
