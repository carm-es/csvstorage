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

package es.gob.aapp.csvstorage.dao.entity.document;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;

/**
 * Objeto entidad para los documentos.
 * 
 * @author carlos.munoz1
 * 
 */
@Entity
@Table(name = "CSVST_DOCUMENTO")
public class DocumentEntity implements Serializable {

  @Id
  @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "generator", sequenceName = "CSVST_DOCUMENTO_SEQ", allocationSize = 1)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ID_UNIDAD_ORGANICA")
  private UnitEntity unidadOrganica;

  @Column(nullable = true, name = "CSV")
  private String csv;

  @Column(nullable = true, name = "ID_ENI")
  private String idEni;

  @Column(nullable = true, name = "TIPO_MIME")
  private String tipoMINE;

  @NotEmpty
  /** Cadena con la ruta donde se ha almacenado el fichero. */
  @Column(nullable = false, name = "RUTA_FICHERO")
  private String rutaFichero;

  @Column(nullable = true, name = "UUID")
  private String uuid;

  @ManyToOne
  @JoinColumn(name = "CREATED_BY")
  private ApplicationEntity createdBy;

  @NotNull(groups = DocumentEntity.class, message = "You must set created date")
  @Column(name = "CREATED_AT", insertable = false, updatable = false)
  private Date createdAt;

  @ManyToOne
  @JoinColumn(name = "MODIFIED_BY")
  private ApplicationEntity modifiedBy;

  @Column(name = "MODIFIED_AT", insertable = false, updatable = false)
  private Date modifiedAt;

  @Column(nullable = true, name = "NOMBRE")
  private String name;

  /**
   * Tipo de permiso sobre el documento: 1-Público 2-Privado 3-Restringido con Identificación
   * 4-Restringido por NIF 5-Restringido a Empleados Públicos
   */
  @Column(nullable = false, name = "TIPO_PERMISO")
  private Integer tipoPermiso;

  @Column(nullable = true, name = "TAMANIO_FICHERO")
  private Long tamanioFichero;

  /** Contenido al documento por referencia */
  @Column(nullable = false, name = "CONTENIDO_POR_REF")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean contenidoPorRef;

  public enum TipoPermiso {
    MALE, FEMALE
  }

  public DocumentEntity() {
    // why??
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCsv() {
    return csv;
  }

  public void setCsv(String csv) {
    this.csv = csv;
  }

  public String getTipoMINE() {
    return tipoMINE;
  }

  public void setTipoMINE(String tipoMINE) {
    this.tipoMINE = tipoMINE;
  }

  public String getRutaFichero() {
    return rutaFichero;
  }

  public void setRutaFichero(String rutaFichero) {
    this.rutaFichero = rutaFichero;
  }

  public UnitEntity getUnidadOrganica() {
    return unidadOrganica;
  }

  public void setUnidadOrganica(UnitEntity unidadOrganica) {
    this.unidadOrganica = unidadOrganica;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getIdEni() {
    return idEni;
  }

  public void setIdEni(String idEni) {
    this.idEni = idEni;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public ApplicationEntity getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(ApplicationEntity createdBy) {
    this.createdBy = createdBy;
  }

  public ApplicationEntity getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(ApplicationEntity modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public Date getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(Date modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getTamanioFichero() {
    return tamanioFichero;
  }

  public void setTamanioFichero(Long tamanioFichero) {
    this.tamanioFichero = tamanioFichero;
  }

  public Boolean getContenidoPorRef() {
    return contenidoPorRef;
  }

  public void setContenidoPorRef(Boolean contenidoPorRef) {
    this.contenidoPorRef = contenidoPorRef;
  }

  public Integer getTipoPermiso() {
    return tipoPermiso;
  }

  public void setTipoPermiso(Integer tipoPermiso) {
    this.tipoPermiso = tipoPermiso;
  }

  @Override
  public String toString() {
    return "DocumentEntity{" + "id=" + id + ", unidadOrganica="
        + (unidadOrganica == null ? null : unidadOrganica.getUnidadOrganica()) + ", csv='" + csv
        + '\'' + ", idEni='" + idEni + '\'' + ", tipoMINE='" + tipoMINE + '\'' + ", rutaFichero='"
        + rutaFichero + '\'' + ", uuid='" + uuid + '\'' + ", createdBy="
        + (createdBy == null ? null : createdBy.getIdAplicacion()) + ", createdAt=" + (createdAt)
        + ", modifiedBy=" + (modifiedBy == null ? null : modifiedBy.getIdAplicacion())
        + ", modifiedAt=" + (modifiedAt) + ", name='" + name + '\'' + ", tipoPermiso=" + tipoPermiso
        + ", tamanioFichero=" + tamanioFichero + ", contenidoPorRef=" + contenidoPorRef + '}';
  }
}
