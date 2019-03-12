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
 * Enum de los posibles permisos de los usuarios sobre los documentos almacenados en la NAS
 * 
 * @author carlos.munoz1
 * 
 */
public enum DocumentPermission {

  PUBLICO(1, "PUBLICO"), PRIVADO(2, "PRIVADO"), RESTRINGIDO(3, "RESTRINGIDO");

  private final int code;
  private final String description;

  private DocumentPermission(int code, String description) {
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
   * Obtiene el permiso a partir de un código.
   * 
   * @param codigo String
   * @return objeto DocumentPermission
   */
  public static DocumentPermission getPermission(int codigo) {
    for (DocumentPermission c : DocumentPermission.values()) {
      if (c.getCode() == codigo) {
        return c;
      }
    }
    throw new IllegalArgumentException("No hay permiso definido para  el código " + codigo);
  }

  /**
   * Sobrescribimos toString() para mostrar el nombre del error, código y descripción.
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
