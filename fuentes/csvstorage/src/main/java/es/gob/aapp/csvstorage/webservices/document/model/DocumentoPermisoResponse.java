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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * The Class GuardarDocumento.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentoPermisoResponse",
    propOrder = {"valorCSV", "idEni", "tipoPermiso", "restricciones",
        "restringidoPorIdentificacion", "restringidoNif", "restringidoAplicaciones", "direccion",
        "hash", "algoritmo"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class DocumentoPermisoResponse extends Response {

  @XmlElement(required = false)
  protected String valorCSV;

  @XmlElement(required = false)
  protected String idEni;

  @XmlElement(required = false)
  protected DocumentoRequest.TipoPermiso tipoPermiso;

  /** El tipo de permiso */
  @XmlElement(required = false)
  protected ListaRestriccion restricciones;


  /** Lista de ids. */
  @XmlElement(required = false)
  protected ListaTipoIds restringidoPorIdentificacion;

  /** Lista de nifs. */
  @XmlElement(required = false)
  protected ListaNifs restringidoNif;

  /** Lista de aplicaciones. */
  @XmlElement(required = false)
  protected ListaAplicaciones restringidoAplicaciones;

  @XmlElement(required = false)
  protected String direccion;

  /** hash del documento */
  @XmlElement(required = false)
  protected String hash;

  /** algoritmo de cálculo del hash */
  @XmlElement(required = false)
  protected String algoritmo;


  public String toString() {
    return "Response: codigo:" + codigo + " descripcion: " + descripcion
        + "DocumentoRequest [Restricciones=" + restricciones + "tipoPermiso=" + tipoPermiso
        + ", tipoIds=" + restringidoPorIdentificacion + ", nifs=" + restringidoNif
        + ", aplicaciones=" + restringidoAplicaciones + "]";
  }

  public String getIdEni() {
    return idEni;
  }

  public void setIdEni(String idEni) {
    this.idEni = idEni;
  }

  public String getValorCSV() {
    return valorCSV;
  }

  public void setValorCSV(String valorCSV) {
    this.valorCSV = valorCSV;
  }

  /**
   * @return the hash
   * 
   */
  public String getHash() {
    return hash;
  }

  /**
   * @param hash the hash to set
   */
  public void setHash(String hash) {
    this.hash = hash;
  }

  /**
   * @return the algotirmo
   */
  public String getAlgotirmo() {
    return algoritmo;
  }

  /**
   * @param algotirmo the algotirmo to set
   */
  public void setAlgotirmo(String algotirmo) {
    this.algoritmo = algotirmo;
  }

  public DocumentoRequest.TipoPermiso getTipoPermiso() {
    return tipoPermiso;
  }

  public void setTipoPermiso(DocumentoRequest.TipoPermiso tipoPermiso) {
    this.tipoPermiso = tipoPermiso;
  }

  public ListaRestriccion getRestricciones() {
    return restricciones;
  }

  public void setRestricciones(ListaRestriccion restricciones) {
    this.restricciones = restricciones;
  }

  public ListaTipoIds getRestringidoPorIdentificacion() {
    return restringidoPorIdentificacion;
  }

  public void setRestringidoPorIdentificacion(ListaTipoIds tipoIds) {
    this.restringidoPorIdentificacion = tipoIds;
  }

  public ListaNifs getRestringidoNif() {
    return restringidoNif;
  }

  public void setRestringidoNif(ListaNifs restringidoNif) {
    this.restringidoNif = restringidoNif;
  }

  public ListaAplicaciones getAplicaciones() {
    return restringidoAplicaciones;
  }

  public void setAplicaciones(ListaAplicaciones aplicaciones) {
    this.restringidoAplicaciones = aplicaciones;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

}
