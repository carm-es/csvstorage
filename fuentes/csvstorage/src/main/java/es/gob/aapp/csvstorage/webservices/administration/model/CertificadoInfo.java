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
@XmlType(name = "certificadoInfo", propOrder = {"certificado", "alias"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:administration:model:v1.0")
public class CertificadoInfo implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * IdAplicacion.
   */
  @XmlElement(name = "certificado", required = true)
  protected byte[] certificado;

  /**
   * Password.
   */
  @XmlElement(name = "alias", required = true)
  protected String alias;

  /**
   * @return the certificado
   */
  public byte[] getCertificado() {
    return certificado;
  }

  /**
   * @param certificado the certificado to set
   */
  public void setCertificado(byte[] certificado) {
    this.certificado = certificado;
  }

  /**
   * @return the alias
   */
  public String getAlias() {
    return alias;
  }

  /**
   * @param alias the alias to set
   */
  public void setAlias(String alias) {
    this.alias = alias;
  }



}
