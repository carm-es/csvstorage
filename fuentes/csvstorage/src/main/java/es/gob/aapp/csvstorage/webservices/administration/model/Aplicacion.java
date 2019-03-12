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

package es.gob.aapp.csvstorage.webservices.administration.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class Aplicacion.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aplicacion",
    propOrder = {"idAplicacionPublico", "idAplicacion", "password", "unidad", "descripcion",
        "responsable", "email", "telefono", "validarDocumentoENI", "activo",
        "serialNumberCertificado"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:administration:model:v1.0")
public class Aplicacion implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * IdAplicacionPublico.
   */
  @XmlElement(name = "idAplicacionPublico", required = true)
  protected String idAplicacionPublico;

  /**
   * IdAplicacion.
   */
  @XmlElement(name = "idAplicacion", required = true)
  protected String idAplicacion;

  /**
   * Password.
   */
  @XmlElement(name = "password", required = true)
  protected String password;

  /**
   * Unidad
   */
  @XmlElement(name = "unidad", required = true)
  protected String unidad;

  /** Descripción. */
  @XmlElement(name = "descripcion", required = true)
  protected String descripcion;

  /** Nombre del responsable de la aplicación */
  @XmlElement(name = "responsable", required = true)
  protected String responsable;

  /** Email del responsable de la aplicación */
  @XmlElement(name = "email", required = true)
  protected String email;

  /** Cadena con el telefono del responsable de la aplicación. */
  @XmlElement(name = "telefono", required = false)
  protected String telefono;

  /** Indica si al guardar un documento ENI hay que validar previamente que sea correcto. */
  @XmlElement(name = "validarDocumentoENI", required = false)
  protected Boolean validarDocumentoENI;

  /** Indica si al guardar un documento ENI hay que validar previamente que sea correcto. */
  @XmlElement(name = "activo", required = false)
  protected Boolean activo;

  /** Numero de serie del certificado */
  @XmlElement(required = true)
  protected String serialNumberCertificado;


  public String getIdAplicacionPublico() {
    return idAplicacionPublico;
  }

  public void setIdAplicacionPublico(String idAplicacionPublico) {
    this.idAplicacionPublico = idAplicacionPublico;
  }

  /**
   * Gets the id aplicacion.
   *
   * @return the id aplicacion
   */
  public String getIdAplicacion() {
    return idAplicacion;
  }



  /**
   * Sets the id aplicacion.
   *
   * @param idAplicacion the new id aplicacion
   */
  public void setIdAplicacion(String idAplicacion) {
    this.idAplicacion = idAplicacion;
  }



  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }



  /**
   * Sets the password.
   *
   * @param password the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }


  public String getUnidad() {
    return unidad;
  }

  public void setUnidad(String unidad) {
    this.unidad = unidad;
  }

  /**
   * Gets the descripcion.
   *
   * @return the descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }



  /**
   * Sets the descripcion.
   *
   * @param descripcion the new descripcion
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }


  /**
   * @return the responsable
   */
  public String getResponsable() {
    return responsable;
  }



  /**
   * @param responsable the responsable to set
   */
  public void setResponsable(String responsable) {
    this.responsable = responsable;
  }



  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }



  /**
   * @param email the emailResponsable to set
   */
  public void setEmail(String email) {
    this.email = email;
  }



  /**
   * @return the telefono
   */
  public String getTelefono() {
    return telefono;
  }



  /**
   * @param telefono the telefono to set
   */
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }



  /**
   * @return the validarDocumentoENI
   */
  public Boolean getValidarDocumentoENI() {
    return validarDocumentoENI;
  }



  /**
   * @param validarDocumentoENI the validarDocumentoENI to set
   */
  public void setValidarDocumentoENI(Boolean validarDocumentoENI) {
    this.validarDocumentoENI = validarDocumentoENI;
  }



  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "AltaAplicacionRequest [idAplicacion=" + idAplicacion + ", password=" + password
        + ", descripcion=" + descripcion + ", nombreResponsable=" + responsable + ", email=" + email
        + ", telefonoResponsable=" + telefono + ", validarDocumentoENI=" + validarDocumentoENI
        + "]";
  }



  /**
   * @return the activo
   */
  public Boolean getActivo() {
    return activo;
  }



  /**
   * @param activo the activo to set
   */
  public void setActivo(Boolean activo) {
    this.activo = activo;
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



}
