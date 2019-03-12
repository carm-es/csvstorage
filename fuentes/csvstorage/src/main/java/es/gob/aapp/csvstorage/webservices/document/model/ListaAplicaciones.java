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
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.util.constants.Constants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "restringidoAplicaciones", propOrder = {"idAplicacion"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0")
public class ListaAplicaciones implements Serializable {
  private static final long serialVersionUID = 5475083481836773707L;
  /**
   * Lista de cadenas con los códigos de los organismos.
   */
  @XmlElement(required = true, name = "idAplicacion")
  private List<String> idAplicacion = new ArrayList<String>();

  /**
   * Constructor por defecto.
   */
  public ListaAplicaciones() {
    super();
  }

  /**
   * Constructor con parámetros.
   * 
   * @param idAplicacion
   */
  public ListaAplicaciones(List<String> idAplicacion) {
    super();
    this.idAplicacion = idAplicacion;
  }

  public List<String> getAplicacion() {
    return idAplicacion;
  }

  public void setAplicacion(List<String> aplicacion) {
    this.idAplicacion = aplicacion;
  }

  @Override
  public String toString() {
    return "AplicacionList [idAplicacion=" + idAplicacion + "]";
  }

  public String parametrosAuditoria() {
    StringBuilder audit = new StringBuilder().append("");
    final String LABEL_APLICACION = "idAplicacion" + Constants.AUDIT_SEPARATOR_VALUE;
    if (idAplicacion != null) {
      boolean primero = true;
      for (String apli : idAplicacion) {
        if (primero) {
          primero = false;
          audit.append(LABEL_APLICACION).append(apli);
        } else {
          audit.append(Constants.AUDIT_SEPARATOR_ROW).append(LABEL_APLICACION).append(apli);
        }
      }
    }

    return audit.toString();
  }
}
