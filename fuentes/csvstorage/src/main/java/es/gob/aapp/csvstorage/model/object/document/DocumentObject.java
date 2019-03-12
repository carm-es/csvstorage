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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.TipoDocumento;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.model.object.cmis.PropAdditObjectCmisObject;
import es.gob.aapp.csvstorage.util.constants.DocumentPermission;
import es.gob.aapp.csvstorage.util.constants.IdentificationType;

/**
 * Clase para mostrar mensajes al usuario en pantalla.
 * 
 */
public class DocumentObject {

  private String dir3 = null;
  private String csv = null;
  private String idEni = null;
  private byte[] contenido = null;
  private String mimeType = null;
  private String uuid = null;
  private String name = null;
  private String pathFile = null;
  private String createdBy = null;
  private Date createdAt = null;
  private String modifiedBy = null;
  private Date modifiedAt = null;
  private String contenidoPorRef = null;
  private List<PropAdditObjectCmisObject> additionalProperties;
  private List<String> nifs = null;
  private DocumentPermission tipoPermiso;
  private List<Integer> restricciones = null;
  private List<Integer> tipoIds = null;
  private List<ApplicationEntity> aplicaciones = null;
  private IdentificationType tipoIdentificacion;
  private String recuperacionOriginal;
  private TipoDocumento tipoDocumento;
  private Boolean isEni = false;
  private Long tamanio = null;
  private String codigoHash = null;
  private String algoritmoHash = null;
  private String documentoEni;
  private String calledFrom;
  private String parametrosAuditoria;

  public DocumentObject(String dir3, String csv, String idEni, List<String> nif, byte[] contenido,
      String mimeType) {
    super();
    this.dir3 = dir3;
    this.csv = csv;
    this.idEni = idEni;
    this.nifs = nif;
    this.contenido = contenido;
    this.mimeType = mimeType;
  }

  public DocumentObject() {
    super();
  }

  public String getDir3() {
    return dir3;
  }

  public void setDir3(String dir3) {
    this.dir3 = dir3;
  }

  public String getCsv() {
    return csv;
  }

  public void setCsv(String csv) {
    this.csv = csv;
  }

  public String getIdEni() {
    return idEni;
  }

  public void setIdEni(String idEni) {
    this.idEni = idEni;
  }

  public byte[] getContenido() {
    return contenido;
  }

  public void setContenido(byte[] contenido) {
    this.contenido = contenido;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPathFile() {
    return pathFile;
  }

  public void setPathFile(String pathFile) {
    this.pathFile = pathFile;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public Date getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(Date modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public List<PropAdditObjectCmisObject> getAdditionalProperties() {
    return additionalProperties;
  }

  public void setAdditionalProperties(List<PropAdditObjectCmisObject> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  public String getContenidoPorRef() {
    return contenidoPorRef;
  }

  public void setContenidoPorRef(String contenidoPorRef) {
    this.contenidoPorRef = contenidoPorRef;
  }

  public List<String> getNifs() {
    if (nifs == null) {
      nifs = new ArrayList<>();
    }
    return nifs;
  }

  public void setNifs(List<String> nifs) {
    this.nifs = nifs;
  }

  public DocumentPermission getTipoPermiso() {
    return tipoPermiso;
  }

  public List<Integer> getRestricciones() {
    if (restricciones == null) {
      restricciones = new ArrayList<>();
    }
    return restricciones;
  }

  public void setRestricciones(List<Integer> restricciones) {
    this.restricciones = restricciones;
  }

  public List<Integer> getTipoIds() {
    if (tipoIds == null) {
      tipoIds = new ArrayList<>();
    }
    return tipoIds;
  }

  public void setTipoIds(List<Integer> tipoIds) {
    this.tipoIds = tipoIds;
  }


  public void setTipoPermiso(DocumentPermission tipoPermiso) {
    this.tipoPermiso = tipoPermiso;
  }

  public IdentificationType getTipoIdentificacion() {
    return tipoIdentificacion;
  }

  public void setTipoIdentificacion(IdentificationType tipoIdentificacion) {
    this.tipoIdentificacion = tipoIdentificacion;
  }

  public String getRecuperacionOriginal() {
    return recuperacionOriginal;
  }


  public void setRecuperacionOriginal(String recuperacionOriginal) {
    this.recuperacionOriginal = recuperacionOriginal;
  }

  public TipoDocumento getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(TipoDocumento tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public Boolean getIsEni() {
    return isEni;
  }

  public void setIsEni(Boolean isEni) {
    this.isEni = isEni;
  }

  public Long getTamanio() {
    return tamanio;
  }

  public void setTamanio(Long tamanio) {
    this.tamanio = tamanio;
  }

  public String getCodigoHash() {
    return codigoHash;
  }

  public void setCodigoHash(String codigoHash) {
    this.codigoHash = codigoHash;
  }

  public String getDocumentoEni() {
    return documentoEni;
  }

  public void setDocumentoEni(String documentoEni) {
    this.documentoEni = documentoEni;
  }

  public String getAlgoritmoHash() {
    return algoritmoHash;
  }

  public void setAlgoritmoHash(String algoritmoHash) {
    this.algoritmoHash = algoritmoHash;
  }

  public List<ApplicationEntity> getAplicaciones() {
    return aplicaciones;
  }

  public void setAplicaciones(List<ApplicationEntity> aplicaciones) {
    this.aplicaciones = aplicaciones;
  }

  public String getCalledFrom() {
    return calledFrom;
  }

  public void setCalledFrom(String calledFrom) {
    this.calledFrom = calledFrom;
  }

  public String getParametrosAuditoria() {
    return parametrosAuditoria;
  }

  public void setParametrosAuditoria(String parametrosAuditoria) {
    this.parametrosAuditoria = parametrosAuditoria;
  }

  @Override
  public String toString() {
    return "DocumentObject{" + "dir3='" + dir3 + '\'' + ", csv='" + csv + '\'' + ", idEni='" + idEni
        + '\'' + ", mimeType='" + mimeType + '\'' + ", uuid='" + uuid + '\'' + ", name='" + name
        + '\'' + ", pathFile='" + pathFile + '\'' + ", createdBy='" + createdBy + '\''
        + ", createdAt=" + createdAt + ", modifiedBy='" + modifiedBy + '\'' + ", modifiedAt="
        + modifiedAt + ", contenidoPorRef='" + contenidoPorRef + '\'' + ", additionalProperties="
        + additionalProperties + ", nifs=" + nifs + ", tipoPermiso=" + tipoPermiso
        + ", restricciones=" + restricciones + ", tipoIds=" + tipoIds + ", tipoIdentificacion="
        + tipoIdentificacion + ", aplicaciones=" + aplicaciones + ", recuperacionOriginal='"
        + recuperacionOriginal + '\'' + ", tipoDocumento=" + tipoDocumento + ", isEni=" + isEni
        + ", tamanio=" + tamanio + ", codigoHash='" + codigoHash + '\'' + ", algoritmoHash='"
        + algoritmoHash + '\'' + ", documentoEni='" + documentoEni + '\'' + '}';
  }
}
