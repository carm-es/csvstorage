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

import es.gob.aapp.csvstorage.dao.entity.document.DocumentRestriccionEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Enum de los posibles permisos de los usuarios sobre las restricciones
 * 
 * @author oscar.jimenez1
 * 
 */
public enum DocumentRestriccion {

  RESTRINGIDO_ID(1, "RESTRINGIDO_ID"), RESTRINGIDO_NIF(2, "RESTRINGIDO_NIF"), RESTRINGIDO_PUB(3,
      "RESTRINGIDO_PUB"), RESTRINGIDO_APP(4, "RESTRINGIDO_APP");

  private final int code;
  private final String description;

  private DocumentRestriccion(int code, String description) {
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
  public static DocumentRestriccion getRestriccion(int codigo) {
    for (DocumentRestriccion c : DocumentRestriccion.values()) {
      if (c.getCode() == codigo) {
        return c;
      }
    }
    throw new IllegalArgumentException("No hay permiso definido para  el código " + codigo);
  }

  /**
   * Obtiene los codigos a partir de una lista de DocumentRestriccionEntity
   * 
   * @param documentRestriccionEntity
   *
   * @return permisos List<Integer>
   */
  public static List<Integer> getFullCode(
      List<DocumentRestriccionEntity> documentRestriccionEntity) {

    List<Integer> permisos = new ArrayList<>();
    if (documentRestriccionEntity != null) {
      for (DocumentRestriccionEntity permisoEntity : documentRestriccionEntity) {
        permisos.add(permisoEntity.getRestriccion());
      }
    }

    return permisos;

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
