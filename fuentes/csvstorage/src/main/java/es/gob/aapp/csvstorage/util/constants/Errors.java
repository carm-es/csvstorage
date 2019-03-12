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

package es.gob.aapp.csvstorage.util.constants;

/**
 * Enum de los posibles errores con c�digo y descripci�n.
 * 
 * @author carlos.munoz1
 * 
 */
public enum Errors {

  VALIDATION_OK(0, "La operaci\u00F3n se ha realizado con \u00E9xito 2."), WAIT_RESPONSE(1,
      "El documento no puede recuperarse. Puede consultarse pasado un tiempo."), CSV_NOT_FOUND(2,
          "CSV no encontrado."), UNIT_LIST_RESPONSE(3,
              "Se devuelve una lista de organismos que pueden contener el documento."), USER_NOT_FOUND(
                  4, "El usuario o password introducido no es v\u00E1lido."),

  INTERNAL_SERVICE_ERROR(500, "Ha ocurrido un error interno en la aplicaci\u00F3n. ");

  private final int code;
  private final String description;

  private Errors(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getCode() {
    return code;
  }

  /**
   * Obtiene el error a partir de un c�digo.
   * 
   * @param codigo String
   * @return objeto Errors
   */
  public static Errors getError(String codigo) {
    for (Errors c : Errors.values()) {
      if (String.valueOf(c.getCode()).equals(codigo)) {
        return c;
      }
    }
    throw new IllegalArgumentException("No hay error definido para  el c�digo " + codigo);
  }

  /**
   * Sobrescribimos toString() para mostrar el nombre del error, c�digo y descripci�n.
   */
  @Override
  public String toString() {
    StringBuffer tmpBuff = new StringBuffer();
    tmpBuff.append(this.name());
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("code=");
    tmpBuff.append(this.getCode());
    tmpBuff.append(Constants.SEPARADOR_DATOS);
    tmpBuff.append("description=");
    tmpBuff.append(this.getDescription());
    return tmpBuff.toString();
  }

}
