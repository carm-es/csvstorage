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

package es.gob.aapp.csvstorage.model.object.document;

import java.util.List;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniOrganoEntity;



/**
 * Clase para mostrar mensajes al usuario en pantalla.
 * 
 */
public class DocumentEniObject extends DocumentObject {

  private String extension = null;
  private DocumentEniEntity documentEniEntity = null;
  private List<DocumentEniOrganoEntity> organosList = null;
  private Object objetoTipoDocumento = null;
  private boolean firmado = false;
  private byte[] firmas;
  private String calledFrom;

  /**
   * Constructor por defecto.
   */
  public DocumentEniObject() {
    super();
  }

  /**
   * @return the documentEniEntity
   */
  public DocumentEniEntity getDocumentEniEntity() {
    return documentEniEntity;
  }

  /**
   * @param documentEniEntity the documentEniEntity to set
   */
  public void setDocumentEniEntity(DocumentEniEntity documentEniEntity) {
    this.documentEniEntity = documentEniEntity;
  }

  /**
   * @return the organosList
   */
  public List<DocumentEniOrganoEntity> getOrganosList() {
    return organosList;
  }

  /**
   * @param organosList the organosList to set
   */
  public void setOrganosList(List<DocumentEniOrganoEntity> organosList) {
    this.organosList = organosList;
  }

  /**
   * @return the tipoDocumento
   */
  public Object getObjetoTipoDocumento() {
    return objetoTipoDocumento;
  }

  /**
   * @param tipoDocumento the tipoDocumento to set
   */
  public void setObjetoTipoDocumento(Object objetoTipoDocumento) {
    this.objetoTipoDocumento = objetoTipoDocumento;
  }

  /**
   * @return the firmado
   */
  public boolean isFirmado() {
    return firmado;
  }

  /**
   * @param firmado the firmado to set
   */
  public void setFirmado(boolean firmado) {
    this.firmado = firmado;
  }

  /**
   * @return the extension
   */
  public String getExtension() {
    return extension;
  }

  /**
   * @param extension the extension to set
   */
  public void setExtension(String extension) {
    this.extension = extension;
  }

  /**
   * @return the firmas
   */
  public byte[] getFirmas() {
    return firmas;
  }

  /**
   * @param firmas the firmas to set
   */
  public void setFirmas(byte[] firmas) {
    this.firmas = firmas;
  }

  @Override
  public String getCalledFrom() {
    return calledFrom;
  }

  @Override
  public void setCalledFrom(String calledFrom) {
    this.calledFrom = calledFrom;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "DocumentEniObject [extension=" + extension + ", documentEniEntity=" + documentEniEntity
        + ", organosList=" + organosList + ", tipoDocumento=" + objetoTipoDocumento + ", firmado="
        + firmado + "]";
  }



}
