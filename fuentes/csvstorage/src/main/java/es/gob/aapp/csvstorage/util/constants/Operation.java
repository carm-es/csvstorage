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

public enum Operation {

  GUARDAR("guardarDocumento"), GUARDAR_ENI("guardarDocumentoENI"), MODIFICAR(
      "modificarDocumento"), MODIFICAR_ENI("modificarDocumentoENI"), ELIMINAR(
          "eliminarDocumento"), CONSULTAR("consultarDocumento"), OBTENER(
              "obtenerDocumento"), OBTENER_ENI("obtenerDocumentoENI"), OBTENER_TAMANIO(
                  "obtenerTamanioDocumento"), OTORGAR_CONSULTA(
                      "otorgarPermisoConsulta"), REVOCAR_CONSULTA(
                          "revocarPermisoConsulta"), CONVERTIR_ENI(
                              "convertirDocumentoAEni"), QUERY_DOCUMENT(
                                  "QueryDocument"), QUERY_DOCUMENT_SECURITY(
                                      "QueryDocumentSecurity"), CONSULTAR_PERMISOS_DOCUMENTO(
                                          "consultarPermisosDocumento"), CONSULTAR_APLICACIONES(
                                              "consultarAplicaciones");

  private String typeOperation;

  private Operation(String typeOperation) {
    this.typeOperation = typeOperation;
  }

  public String value() {
    return this.typeOperation;
  }
}
