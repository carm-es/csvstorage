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
public enum IdentificationType {

  CLAVE_PERM(1, "CLAVE_PERM"), PIN24(2, "PIN24"), DNIE(3, "DNIE"), PF_2CA(4, "PF_2CA"), PJ_2CA(5,
      "PJ_2CA"), COMPONENTESSL(6, "COMPONENTESSL"), SEDE_ELECTRONICA(7,
          "SEDE_ELECTRONICA"), SELLO_ORGANO(8, "SELLO_ORGANO"), EMPLEADO_PUBLICO(9,
              "EMPLEADO_PUBLICO"), ENTIDAD_NO_PERSONA_JURIDICA(10,
                  "ENTIDAD_NO_PERSONA_JURIDICA"), EMPLEADO_PUBLICO_PSEUD(11,
                      "EMPLEADO_PUBLICO_PSEUD"), CUALIFICADO_SELLO_ENTIDAD(12,
                          "CUALIFICADO_SELLO_ENTIDAD"), CUALIFICADO_AUTENTICACION(13,
                              "CUALIFICADO_AUTENTICACION"), CUALIFICADO_SELLO_TIEMPO(14,
                                  "CUALIFICADO_SELLO_TIEMPO"), REPRESENTACION_PJ(15,
                                      "REPRESENTACION_PJ"), REPRESENTACION_ENTIDAD_SIN_PF(16,
                                          "REPRESENTACION_ENTIDAD_SIN_PF");

  private final int code;
  private final String description;

  private IdentificationType(int code, String description) {
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
   * Obtiene el error a partir de un código.
   * 
   * @param codigo String
   * @return objeto Errors
   */
  public static IdentificationType getType(String codigo) {
    for (IdentificationType c : IdentificationType.values()) {
      if (String.valueOf(c.getCode()).equals(codigo)) {
        return c;
      }
    }
    throw new IllegalArgumentException("No hay tipo definido para  el código " + codigo);
  }

  /**
   * Obtiene el error a partir de una descripción.
   * 
   * @param description String
   * @return objeto Errors
   */
  public static IdentificationType getTypeByDescription(String description) {
    for (IdentificationType c : IdentificationType.values()) {
      if (String.valueOf(c.getDescription()).equals(description)) {
        return c;
      }
    }
    throw new IllegalArgumentException("No hay tipo definido para la descripción " + description);
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
