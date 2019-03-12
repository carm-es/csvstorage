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

package es.gob.aapp.csvstorage.model.object.truststore;

import java.io.InputStream;
import java.util.Date;

/**
 * Objeto entidad para almacen.
 *
 * @author mmaldonado
 */

public class TruststoreObject {

  /** The id. */
  private Long id;

  /** The usuario. */


  /** The password. */
  private String passwordKS;

  private String alias;

  private InputStream certificado;

  private String asunto;

  private String emisor;

  private String serialNumbre;

  private Date validoDesde;

  private Date validoHasta;


  public TruststoreObject() {
    super();
  }

  public TruststoreObject(Long id, InputStream certificado, String alias, String passwordKS) {
    super();
    this.id = id;
    this.certificado = certificado;
    this.alias = alias;
    this.passwordKS = passwordKS;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public InputStream getCertificado() {
    return certificado;
  }

  public void setCertificado(InputStream certificado) {
    this.certificado = certificado;
  }

  /**
   * @return the password
   */
  public String getPasswordKS() {
    return passwordKS;
  }

  /**
   * @param password the password to set
   */
  public void setPasswordKS(String passwordKS) {
    this.passwordKS = passwordKS;
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "AlmacenObject [id=" + id + "passwordKS=" + passwordKS + "certificado=" + certificado
        + "alias=" + alias + "]";
  }

  /**
   * @return the asunto
   */
  public String getAsunto() {
    return asunto;
  }

  /**
   * @param asunto the asunto to set
   */
  public void setAsunto(String asunto) {
    this.asunto = asunto;
  }

  /**
   * @return the issuer
   */
  public String getEmisor() {
    return emisor;
  }

  /**
   * @param issuer the issuer to set
   */
  public void setEmisor(String emisor) {
    this.emisor = emisor;
  }

  /**
   * @return the serialNumbre
   */
  public String getSerialNumbre() {
    return serialNumbre;
  }

  /**
   * @param serialNumbre the serialNumbre to set
   */
  public void setSerialNumbre(String serialNumbre) {
    this.serialNumbre = serialNumbre;
  }

  /**
   * @return the fechaDesde
   */
  public Date getValidoDesde() {
    return validoDesde;
  }

  /**
   * @param validoDesde the validoDesde to set
   */
  public void setValidoDesde(Date validoDesde) {
    this.validoDesde = validoDesde;
  }

  /**
   * @return the fechaHasta
   */
  public Date getValidoHasta() {
    return validoHasta;
  }

  /**
   * @param validoHasta the validoHasta to set
   */
  public void setValidoHasta(Date validoHasta) {
    this.validoHasta = validoHasta;
  }



}
