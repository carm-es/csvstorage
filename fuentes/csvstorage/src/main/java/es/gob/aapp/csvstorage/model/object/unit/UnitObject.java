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

package es.gob.aapp.csvstorage.model.object.unit;

import java.util.Date;
import es.gob.aapp.csvstorage.util.constants.Constants;

/**
 * Objeto para los datos de unidades orgánicas.
 * 
 * @author carlos.munoz1
 * 
 */
public class UnitObject {

  /** Clave primaria de la unidad. Identificador secuencial. */
  private Long id;

  /** Cadena con el identificador de la aplicación. */
  private String unidadOrganica;

  /** Cadena con una breve descripción de la aplicación. */
  private String codigoSia;

  /** The ambito. */
  private String ambito;

  private String nombre;

  private String estado;

  private Integer nivelAdministracion;

  private Integer nivelJerarquico;

  private String codigoUnidadSuperior;

  private String nombreUnidadSuperior;

  private String codigoUnidadRaiz;

  private String nombreUnidadRaiz;

  private Date fechaAlta;

  private Date fechaBaja;

  private Date fechaExtincion;

  private Date fechaAnulacion;

  private Date fechaCreacion;

  /**
   * Constructor por defecto.
   */
  public UnitObject() {
    super();
  }

  /**
   * Constructor con parámetros.
   *
   * @param id the id
   * @param dir3 the dir3
   * @param codigoSia the codigo sia
   * @param ambito the ambito
   */
  public UnitObject(Long id, String dir3, String codigoSia, String ambito) {
    super();
    this.id = id;
    this.unidadOrganica = dir3;
    this.codigoSia = codigoSia;
    this.ambito = ambito;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the codigo.
   *
   * @return the codigo
   */
  public String getUnidadOrganica() {
    return unidadOrganica;
  }

  /**
   * Sets the codigo.
   *
   * @param codigo the new codigo
   */
  public void setUnidadOrganica(String unidadOrganica) {
    this.unidadOrganica = unidadOrganica;
  }

  /**
   * Gets the codigo sia.
   *
   * @return the codigo sia
   */
  public String getCodigoSia() {
    return codigoSia;
  }

  /**
   * Sets the codigo sia.
   *
   * @param codigoSia the new codigo sia
   */
  public void setCodigoSia(String codigoSia) {
    this.codigoSia = codigoSia;
  }

  /**
   * Gets the ambito.
   *
   * @return the ambito
   */
  public String getAmbito() {
    return ambito;
  }

  /**
   * Sets the ambito.
   *
   * @param ambito the new ambito
   */
  public void setAmbito(String ambito) {
    this.ambito = ambito;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuffer tmpBuff = new StringBuffer("UnitObject [id=");
    tmpBuff.append(id);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("dir3=");
    tmpBuff.append(unidadOrganica);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("desccodigoSiaripcion=");
    tmpBuff.append(codigoSia);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("ambito=");
    tmpBuff.append(ambito);
    tmpBuff.append("]");
    return tmpBuff.toString();
  }

  /**
   * @return the nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * @param nombre the nombre to set
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * @return the estado
   */
  public String getEstado() {
    return estado;
  }

  /**
   * @param estado the estado to set
   */
  public void setEstado(String estado) {
    this.estado = estado;
  }

  /**
   * @return the nivelAdministracion
   */
  public Integer getNivelAdministracion() {
    return nivelAdministracion;
  }

  /**
   * @param nivelAdministracion the nivelAdministracion to set
   */
  public void setNivelAdministracion(Integer nivelAdministracion) {
    this.nivelAdministracion = nivelAdministracion;
  }

  /**
   * @return the nivelJerarquico
   */
  public Integer getNivelJerarquico() {
    return nivelJerarquico;
  }

  /**
   * @param nivelJerarquico the nivelJerarquico to set
   */
  public void setNivelJerarquico(Integer nivelJerarquico) {
    this.nivelJerarquico = nivelJerarquico;
  }

  /**
   * @return the codigoUnidadSuperior
   */
  public String getCodigoUnidadSuperior() {
    return codigoUnidadSuperior;
  }

  /**
   * @param codigoUnidadSuperior the codigoUnidadSuperior to set
   */
  public void setCodigoUnidadSuperior(String codigoUnidadSuperior) {
    this.codigoUnidadSuperior = codigoUnidadSuperior;
  }

  /**
   * @return the nombreUnidadSuperior
   */
  public String getNombreUnidadSuperior() {
    return nombreUnidadSuperior;
  }

  /**
   * @param nombreUnidadSuperior the nombreUnidadSuperior to set
   */
  public void setNombreUnidadSuperior(String nombreUnidadSuperior) {
    this.nombreUnidadSuperior = nombreUnidadSuperior;
  }

  /**
   * @return the codigoUnidadRaiz
   */
  public String getCodigoUnidadRaiz() {
    return codigoUnidadRaiz;
  }

  /**
   * @param codigoUnidadRaiz the codigoUnidadRaiz to set
   */
  public void setCodigoUnidadRaiz(String codigoUnidadRaiz) {
    this.codigoUnidadRaiz = codigoUnidadRaiz;
  }

  /**
   * @return the nombreUnidadRaiz
   */
  public String getNombreUnidadRaiz() {
    return nombreUnidadRaiz;
  }

  /**
   * @param nombreUnidadRaiz the nombreUnidadRaiz to set
   */
  public void setNombreUnidadRaiz(String nombreUnidadRaiz) {
    this.nombreUnidadRaiz = nombreUnidadRaiz;
  }

  /**
   * @return the fechaAlta
   */
  public Date getFechaAlta() {
    return fechaAlta;
  }

  /**
   * @param fechaAlta the fechaAlta to set
   */
  public void setFechaAlta(Date fechaAlta) {
    this.fechaAlta = fechaAlta;
  }

  /**
   * @return the fechaBaja
   */
  public Date getFechaBaja() {
    return fechaBaja;
  }

  /**
   * @param fechaBaja the fechaBaja to set
   */
  public void setFechaBaja(Date fechaBaja) {
    this.fechaBaja = fechaBaja;
  }

  /**
   * @return the fechaExtincion
   */
  public Date getFechaExtincion() {
    return fechaExtincion;
  }

  /**
   * @param fechaExtincion the fechaExtincion to set
   */
  public void setFechaExtincion(Date fechaExtincion) {
    this.fechaExtincion = fechaExtincion;
  }

  /**
   * @return the fechaAnulacion
   */
  public Date getFechaAnulacion() {
    return fechaAnulacion;
  }

  /**
   * @param fechaAnulacion the fechaAnulacion to set
   */
  public void setFechaAnulacion(Date fechaAnulacion) {
    this.fechaAnulacion = fechaAnulacion;
  }

  /**
   * @return the fechaCreacion
   */
  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  /**
   * @param fechaCreacion the fechaCreacion to set
   */
  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

}
