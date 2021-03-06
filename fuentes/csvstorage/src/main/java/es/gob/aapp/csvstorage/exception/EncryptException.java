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

package es.gob.aapp.csvstorage.exception;

/**
 * Excepciones de la capa de servicios de la aplicaci�n.
 */
public class EncryptException extends Exception {

  private static final long serialVersionUID = 1L;

  private String aditionalInfo;

  /**
   * Constructor mensaje
   * 
   * @param mensaje mensaje
   */
  public EncryptException(String codigo) {
    super(codigo);
  }

  /**
   * Constructor c�digo e informaci�n adicional.
   * 
   * @param codigo
   * @param aditionalInfo
   */
  public EncryptException(String codigo, String aditionalInfo) {
    super(codigo);
    this.aditionalInfo = aditionalInfo;
  }

  /**
   * Constructor mensaje y excepci�n.
   * 
   * @param mensaje
   * @param exception
   */
  public EncryptException(String mensaje, Throwable exception) {
    super(mensaje, exception);
  }

  public String getAditionalInfo() {
    return aditionalInfo;
  }

  public void setAditionalInfo(String aditionalInfo) {
    this.aditionalInfo = aditionalInfo;
  }

}
