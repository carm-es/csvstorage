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

package es.gob.aapp.csvstorage.model.converter.exception;

/**
 * Clase de Excepciones para clientes de web services.
 * 
 */
public class ConverterException extends Exception {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor mensaje.
   * 
   * @param mensaje mensaje
   */
  public ConverterException(String mensaje) {
    super(mensaje);
  }

  /**
   * Constructor mensaje y excepci�n.
   * 
   * @param mensaje mensaje
   * @param e excepci�n
   */
  public ConverterException(String mensaje, Throwable e) {
    super(mensaje, e);
  }

}
