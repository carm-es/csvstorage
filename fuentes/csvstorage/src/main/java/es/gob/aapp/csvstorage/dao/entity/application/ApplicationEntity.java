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

package es.gob.aapp.csvstorage.dao.entity.application;

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
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import es.gob.aapp.csvstorage.dao.entity.users.UserEntity;
import es.gob.aapp.csvstorage.util.constants.Constants;

/**
 * Objeto entidad para aplicaciones consumidoras de los web services.
 * 
 * @author carlos.munoz1
 * 
 */
@Entity
@Table(name = "CSVST_APLICACIONES")
public class ApplicationEntity implements Serializable {

  /** Clave primaria de la aplicación. */
  @Id
  @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "generator", sequenceName = "CSVST_APLICACIONES_SEQ",
      allocationSize = 1)
  private Long id;

  /**
   * Estable el identificador público de la aplicación. Utilizado para consultar documentos de otras
   * aplicaciones.
   */
  @Column(nullable = true, name = "ID_APLICACION_PUBLICO")
  private String idAplicacionPublico;

  @NotEmpty
  /** Cadena con el identificador de la aplicación. */
  @Column(nullable = false, name = "ID_APLICACION")
  private String idAplicacion;

  @NotEmpty
  /** Cadena con una breve descripci�n de la aplicación. */
  @Column(nullable = false, name = "DESCRIPCION")
  private String descripcion;

  @NotEmpty
  /** Cadena con el password de la aplicación. */
  @Column(nullable = false, name = "PASSWORD_HASH")
  private String password;

  /** The is admin. */
  @Column(nullable = false, name = "ADMINISTRADOR")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean isAdmin;

  @NotEmpty
  /** Cadena con el nombre del responsable de la aplicación. */
  @Column(nullable = false, name = "NOMBRE_RESPONSABLE")
  private String nombreResponsable;

  @NotEmpty
  /** Cadena con el email del responsable de la aplicación. */
  @Column(nullable = false, name = "EMAIL_RESPONSABLE")
  private String emailResponsable;

  /** Cadena con el telefono del responsable de la aplicación. */
  @Column(nullable = false, name = "TELEFONO_RESPONSABLE")
  private String telefonoResponsable;

  /** Indica si al guardar un documento ENI hay que validar previamente que sea correcto.. */
  @Column(nullable = false, name = "VALIDAR_DOC_ENI")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean validarDocumentoENI;

  /** Indica si al guardar un documento ENI hay que validar previamente que sea correcto.. */
  @Column(nullable = false, name = "ACTIVO")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean activo;

  /**
   * Contiene el numero de serie del certificado para el caso en el que la aplicación acceda por
   * certificado
   */
  @Column(nullable = true, name = "SERIAL_NUMBER_CERTIFICADO")
  private String serialNumberCertificado;


  /** Cadena con el usuarion de creación. */
  @ManyToOne
  @JoinColumn(name = "CREATED_BY")
  private UserEntity createdBy;


  /** Cadena con la fecha de creación. */
  @NotNull(groups = ApplicationEntity.class, message = "You must set created date")
  @Column(name = "CREATED_AT", insertable = false, updatable = false)
  private Date createdAt;

  /** Cadena con el usuarion de modificación. */
  @ManyToOne
  @JoinColumn(name = "MODIFIED_BY")
  private UserEntity modifiedBy;


  /** Cadena con la fecha de modificación. */
  @Column(name = "MODIFIED_AT", insertable = false, updatable = false)
  private Date modifiedAt;

  /** Objeto con los datos del organismo. */
  @ManyToOne
  @JoinColumn(name = "ID_UNIDAD")
  private UnitEntity unidad;


  /** Indica si al tiene permiso de lectura de los servicios CMIS */
  @Column(nullable = false, name = "LECTURA_CMIS")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean lecturaCmis;

  /** Indica si al tiene permiso de lectura de los servicios CMIS */
  @Column(nullable = false, name = "ESCRITURA_CMIS")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean escrituraCmis;

  @Column(nullable = false, name = "PERMISO_LECTURA")
  private Integer permisoLectura;

  @Column(nullable = false, name = "DOCUMENTOS_PDF_Y_ENI")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean documentosPdfYEni;


  /**
   * Constructor por defecto.
   */
  public ApplicationEntity() {
    super();
  }

  /**
   * Constructor con parámetros.
   * 
   * @param id
   * @param idAplicacion
   * @param descripcion
   * @param password
   */
  public ApplicationEntity(Long id, String idAplicacion, String descripcion, String password) {
    super();
    this.id = id;
    this.idAplicacion = idAplicacion;
    this.descripcion = descripcion;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIdAplicacion() {
    return idAplicacion;
  }

  public void setIdAplicacion(String idAplicacion) {
    this.idAplicacion = idAplicacion;
  }

  public String getIdAplicacionPublico() {
    return idAplicacionPublico;
  }

  public void setIdAplicacionPublico(String idAplicacionPublico) {
    this.idAplicacionPublico = idAplicacionPublico;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public String getNombreResponsable() {
    return nombreResponsable;
  }

  public void setNombreResponsable(String nombreResponsable) {
    this.nombreResponsable = nombreResponsable;
  }

  public String getEmailResponsable() {
    return emailResponsable;
  }

  public void setEmailResponsable(String emailResponsable) {
    this.emailResponsable = emailResponsable;
  }

  public String getTelefonoResponsable() {
    return telefonoResponsable;
  }

  public void setTelefonoResponsable(String telefonoResponsable) {
    this.telefonoResponsable = telefonoResponsable;
  }

  public Boolean getValidarDocumentoENI() {
    return validarDocumentoENI;
  }

  public void setValidarDocumentoENI(Boolean validarDocumentoENI) {
    this.validarDocumentoENI = validarDocumentoENI;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public UnitEntity getUnidad() {
    return unidad;
  }

  public void setUnidad(UnitEntity unidad) {
    this.unidad = unidad;
  }

  /**
   * @return the serialNumberCertificado
   */
  public String getSerialNumberCertificado() {
    return serialNumberCertificado;
  }

  /**
   * @param serialNumberCertificado the serialNumberCertificado to set
   */
  public void setSerialNumberCertificado(String serialNumberCertificado) {
    this.serialNumberCertificado = serialNumberCertificado;
  }

  @Override
  public String toString() {
    StringBuffer tmpBuff = new StringBuffer("ApplicationEntity [id=");
    tmpBuff.append(id);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("idAplicacion=");
    tmpBuff.append(idAplicacion);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("idAplicacionPublico=");
    tmpBuff.append(idAplicacionPublico);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("descripcion=");
    tmpBuff.append(descripcion);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("password=");
    tmpBuff.append(password);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("isAdmin=");
    tmpBuff.append(isAdmin);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("nombreResponsable=");
    tmpBuff.append(nombreResponsable);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("emailResponsable=");
    tmpBuff.append(emailResponsable);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("telefonoResponsable=");
    tmpBuff.append(telefonoResponsable);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("unidad Dir3=");
    tmpBuff.append(unidad);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("validarDocumentoENI=");
    tmpBuff.append(validarDocumentoENI);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("activo=");
    tmpBuff.append(activo);
    tmpBuff.append("]");
    return tmpBuff.toString();
  }

  /**
   * @return the createdBy
   */
  public UserEntity getCreatedBy() {
    return createdBy;
  }

  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(UserEntity createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @return the createdAt
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * @param createdAt the createdAt to set
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * @return the modifiedBy
   */
  public UserEntity getModifiedBy() {
    return modifiedBy;
  }

  /**
   * @param modifiedBy the modifiedBy to set
   */
  public void setModifiedBy(UserEntity modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  /**
   * @return the modifiedAt
   */
  public Date getModifiedAt() {
    return modifiedAt;
  }

  /**
   * @param modifiedAt the modifiedAt to set
   */
  public void setModifiedAt(Date modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  /**
   * @return the lecturaCmis
   */
  public Boolean getLecturaCmis() {
    return lecturaCmis;
  }

  /**
   * @param lecturaCmis the lecturaCmis to set
   */
  public void setLecturaCmis(Boolean lecturaCmis) {
    this.lecturaCmis = lecturaCmis;
  }

  /**
   * @return the escrituraCmis
   */
  public Boolean getEscrituraCmis() {
    return escrituraCmis;
  }

  /**
   * @param escrituraCmis the escrituraCmis to set
   */
  public void setEscrituraCmis(Boolean escrituraCmis) {
    this.escrituraCmis = escrituraCmis;
  }

  /**
   * @return the permisoLectura
   */
  public Integer getPermisoLectura() {
    return permisoLectura;
  }

  /**
   * @param permisoLectura the permisoLectura to set
   */
  public void setPermisoLectura(Integer permisoLectura) {
    this.permisoLectura = permisoLectura;
  }

  /**
   * @return the documentosPdfYEni
   */
  public Boolean getDocumentosPdfYEni() {
    return documentosPdfYEni;
  }

  /**
   * @param documentosPdfYEni the documentosPdfYEni to set
   */
  public void setDocumentosPdfYEni(Boolean documentosPdfYEni) {
    this.documentosPdfYEni = documentosPdfYEni;
  }


}
