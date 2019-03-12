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

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class Aplicacion.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aplicacion", propOrder = {"idAplicacionPublico", "descripcion"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class Aplicacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @XmlElement(name = "idAplicacionPublico", required = false)
  protected String idAplicacionPublico;

  @XmlElement(name = "descripcion", required = false)
  protected String descripcion;

  public String getIdAplicacionPublico() {
    return idAplicacionPublico;
  }

  public void setIdAplicacionPublico(String idAplicacionPublico) {
    this.idAplicacionPublico = idAplicacionPublico;
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



}
