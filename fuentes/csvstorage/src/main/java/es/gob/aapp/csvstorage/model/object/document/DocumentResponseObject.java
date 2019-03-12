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

package es.gob.aapp.csvstorage.model.object.document;



/**
 * Clase para mostrar mensajes al usuario en pantalla.
 * 
 */
public class DocumentResponseObject extends ResponseObject {

  private Object contenido = null;
  private String uuid = null;

  public DocumentResponseObject() {
    super();

  }

  public DocumentResponseObject(String codigo, String descripcion, Object contenido) {
    super(codigo, descripcion);
    this.contenido = contenido;
  }

  /**
   * @return the contenido
   */
  public Object getContenido() {
    return contenido;
  }

  /**
   * @param contenido the contenido to set
   */
  public void setContenido(Object contenido) {
    this.contenido = contenido;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "DocumentResponseObject [codigo=" + getCodigo() + ", descripcion=" + getDescripcion()
        + ", contenido=" + contenido + "]";
  }

  /**
   * @return the uuid
   */
  public String getUuid() {
    return uuid;
  }

  /**
   * @param uuid the uuid to set
   */
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }



}
