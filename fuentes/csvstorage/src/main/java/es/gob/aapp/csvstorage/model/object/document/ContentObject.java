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

import java.util.Arrays;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Clase para mostrar mensajes al usuario en pantalla.
 * 
 */
@XmlRootElement
public class ContentObject {

  private String csv = null;
  private String idEni = null;
  private byte[] contenido = null;
  private String mimeType = null;

  public ContentObject(String csv, String idEni, byte[] contenido, String mimeType) {
    super();
    this.csv = csv;
    this.idEni = idEni;
    this.contenido = contenido;
    this.mimeType = mimeType;
  }


  /**
   * Constructor por defecto.
   */
  public ContentObject() {
    super();
  }

  /**
   * @return the csv
   */
  public String getCsv() {
    return csv;
  }

  /**
   * @param csv the csv to set
   */
  public void setCsv(String csv) {
    this.csv = csv;
  }

  /**
   * @return the contenido
   */
  public byte[] getContenido() {
    return contenido;
  }

  /**
   * @param contenido the contenido to set
   */
  public void setContenido(byte[] contenido) {
    this.contenido = contenido;
  }

  /**
   * @return the mimeType
   */
  public String getMimeType() {
    return mimeType;
  }

  /**
   * @param mimeType the mimeType to set
   */
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ContentObject [csv=" + csv + ", idEni=" + idEni + ", contenido="
        + Arrays.toString(contenido) + ", mimeType=" + mimeType + "]";
  }


  /**
   * @return the idEni
   */
  public String getIdEni() {
    return idEni;
  }


  /**
   * @param idEni the idEni to set
   */
  public void setIdEni(String idEni) {
    this.idEni = idEni;
  }



}
