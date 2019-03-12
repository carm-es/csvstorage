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

package es.gob.aapp.csvstorage.model.object.application;

import java.util.Date;
import es.gob.aapp.csvstorage.model.object.unit.UnitObject;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import es.gob.aapp.csvstorage.model.object.users.UserObject;
import es.gob.aapp.csvstorage.util.constants.Constants;

/**
 * Objeto para los datos de aplicaciones consumidoras.
 * 
 * @author carlos.munoz1
 * 
 */
public class ApplicationObject {

  /** Clave primaria de la aplicación. Identificador secuencial. */
  private Long id;

  @NotBlank
  private String idAplicacionPublico;

  /** Cadena con el identificador de la aplicación. */
  @NotBlank
  private String idAplicacion;

  /** Cadena con una breve descripción de la aplicación. */
  @NotBlank
  private String descripcion;

  /** Cadena con el password de la aplicación. */
  private String password;

  /** Objeto con los datos del organismo. */
  private UnitObject unidad;

  /** The is admin. */
  private boolean admin;

  @NotEmpty
  /** Nombre del responsable de la aplicación */
  private String nombreResponsable;

  @NotEmpty
  /** Email del responsable de la aplicación */
  private String emailResponsable;

  /** Cadena con el telefono del responsable de la aplicación. */
  private String telefonoResponsable;

  /** Indica si al guardar un documento ENI hay que validar previamente que sea correcto. */
  private boolean validarDocumentoENI;

  /** Indica si el estado de la aplicación es activo */
  private boolean activo;

  /**
   * Contiene el numero de serie del certificado para el caso en el que la aplicación acceda por
   * certificado
   */
  private String serialNumberCertificado;

  /** Cadena con el usuarion de creación. */
  private UserObject createdBy;

  /** Cadena con la fecha de creación. */
  private Date createdAt;

  /** Cadena con el usuarion de modificación. */
  private UserObject modifiedBy;

  /** Cadena con la fecha de modificación. */
  private Date modifiedAt;


  /** Indica si al tiene permiso de lectura de los servicios CMIS */
  private boolean lecturaCmis;

  /** Indica si al tiene permiso de lectura de los servicios CMIS */
  private boolean escrituraCmis;

  /** Permiso de lectura sobre los documentos almacenados en la NAS */
  private Integer permisoLectura;

  /** Indica si la aplicación solo puede guardar documentos PDF y ENI */
  private boolean documentosPdfYEni;


  /**
   * Constructor por defecto.
   */
  public ApplicationObject() {
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
  public ApplicationObject(Long id, String idAplicacion, String descripcion, String password) {
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

  public String getIdAplicacionPublico() {
    return idAplicacionPublico;
  }

  public void setIdAplicacionPublico(String idAplicacionPublico) {
    this.idAplicacionPublico = idAplicacionPublico;
  }

  public String getIdAplicacion() {
    return idAplicacion;
  }

  public void setIdAplicacion(String idAplicacion) {
    this.idAplicacion = idAplicacion;
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

  public UnitObject getUnidad() {
    return unidad;
  }

  public void setUnidad(UnitObject unidad) {
    this.unidad = unidad;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
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

  public boolean getValidarDocumentoENI() {
    return validarDocumentoENI;
  }

  public void setValidarDocumentoENI(boolean validarDocumentoENI) {
    this.validarDocumentoENI = validarDocumentoENI;
  }

  public boolean getActivo() {
    return activo;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }

  @Override
  public String toString() {
    StringBuffer tmpBuff = new StringBuffer("ApplicationObject [id=");
    tmpBuff.append(id);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("idAplicacionPublico=");
    tmpBuff.append(idAplicacionPublico);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("idAplicacion=");
    tmpBuff.append(idAplicacion);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("descripcion=");
    tmpBuff.append(descripcion);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("password=");
    tmpBuff.append(password);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("isAdmin=");
    tmpBuff.append(admin);
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
    tmpBuff.append("unidad DIR3=");
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

  /**
   * @return the createdBy
   */
  public UserObject getCreatedBy() {
    return createdBy;
  }

  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(UserObject createdBy) {
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
  public UserObject getModifiedBy() {
    return modifiedBy;
  }

  /**
   * @param modifiedBy the modifiedBy to set
   */
  public void setModifiedBy(UserObject modifiedBy) {
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
  public boolean getLecturaCmis() {
    return lecturaCmis;
  }

  /**
   * @param lecturaCmis the lecturaCmis to set
   */
  public void setLecturaCmis(boolean lecturaCmis) {
    this.lecturaCmis = lecturaCmis;
  }

  /**
   * @return the escrituraCmis
   */
  public boolean getEscrituraCmis() {
    return escrituraCmis;
  }

  /**
   * @param escrituraCmis the escrituraCmis to set
   */
  public void setEscrituraCmis(boolean escrituraCmis) {
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
  public boolean getDocumentosPdfYEni() {
    return documentosPdfYEni;
  }

  /**
   * @param documentosPdfYEni the documentosPdfYEni to set
   */
  public void setDocumentosPdfYEni(boolean documentosPdfYEni) {
    this.documentosPdfYEni = documentosPdfYEni;
  }


}
