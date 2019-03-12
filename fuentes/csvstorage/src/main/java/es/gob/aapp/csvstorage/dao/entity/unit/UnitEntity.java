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

package es.gob.aapp.csvstorage.dao.entity.unit;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.NotEmpty;
import es.gob.aapp.csvstorage.util.constants.Constants;

// TODO: Auto-generated Javadoc
/**
 * Objeto entidad para aplicaciones consumidoras de los web services.
 * 
 * @author carlos.munoz1
 * 
 */
@Entity
@Table(name = "CSVST_UNIDAD_ORGANICA")
public class UnitEntity implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  /** Clave primaria de la aplicación. */
  @Id
  @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "generator", sequenceName = "CSVST_UNIDAD_SEQ", allocationSize = 1)
  private Long id;

  /** The unidad organica. */
  @NotEmpty
  /** Cadena con el identificador de la aplicación. */
  @Column(nullable = false, name = "UNIDAD_ORGANICA")
  private String unidadOrganica;

  /** Cadena con una breve descripción de la aplicación. */
  @Column(nullable = true, name = "CODIGO_SIA")
  private String codigoSia;

  /** The ambito. Cadena para establecer el ambito a una unidad orgánica */
  @Column(nullable = true, name = "AMBITO")
  private String ambito;

  @Column(nullable = false, name = "NOMBRE_UNIDAD_ORGANICA")
  private String nombre;

  @Column(nullable = false, name = "ESTADO")
  private String estado;

  @Column(nullable = false, name = "NIVEL_ADMINISTRACION")
  private Integer nivelAdministracion;

  @Column(nullable = false, name = "NIVEL_JERARQUICO")
  private Integer nivelJerarquico;

  @Column(nullable = false, name = "CODIGO_UNIDAD_SUPERIOR")
  private String codigoUnidadSuperior;

  @Column(nullable = false, name = "NOMBRE_UNIDAD_SUPERIOR")
  private String nombreUnidadSuperior;

  @Column(nullable = false, name = "CODIGO_UNIDAD_RAIZ")
  private String codigoUnidadRaiz;

  @Column(nullable = false, name = "NOMBRE_UNIDAD_RAIZ")
  private String nombreUnidadRaiz;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = true, name = "FECHA_ALTA_OFICIAL", length = 19)
  private Date fechaAlta;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = true, name = "FECHA_BAJA_OFICIAL", length = 19)
  private Date fechaBaja;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = true, name = "FECHA_EXTINCION", length = 19)
  private Date fechaExtincion;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = true, name = "FECHA_ANULACION", length = 19)
  private Date fechaAnulacion;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, name = "FECHA_CREACION", length = 19)
  private Date fechaCreacion;

  /**
   * Constructor por defecto.
   */
  public UnitEntity() {
    super();
  }

  /**
   * Constructor con parámetros.
   *
   * @param id the id
   * @param unidadOrganica the unidad organica
   * @param codigoSia the codigo sia
   */
  public UnitEntity(Long id, String unidadOrganica, String codigoSia, String ambito) {
    super();
    this.id = id;
    this.unidadOrganica = unidadOrganica;
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
   * Gets the unidad organica.
   *
   * @return the dir3
   */
  public String getUnidadOrganica() {
    return unidadOrganica;
  }

  /**
   * Sets the unidad organica.
   *
   * @param unidadOrganica the new unidad organica
   */
  public void setUnidadOrganica(String unidadOrganica) {
    this.unidadOrganica = unidadOrganica;
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
    StringBuffer tmpBuff = new StringBuffer("UnitEntity [id=");
    tmpBuff.append(id);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("unidadOrganica=");
    tmpBuff.append(unidadOrganica);
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("codigoSia=");
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
