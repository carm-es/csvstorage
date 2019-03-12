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

package es.gob.aapp.csvstorage.dao.entity.reference;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_REFERENCIAS_APP_ST")
public class VReferenciasAppEntity {

  /** Clave primaria de la vista. */
  @Id
  @Column(columnDefinition = "BINARY(16)", name = "ID")
  private UUID id;

  @Column(name = "ID_DOCUMENTO")
  private Long idDocumento;

  @Column(name = "TIPO")
  private String tipo;

  @Column(name = "CSV")
  private String csv;

  @Column(name = "ID_ENI")
  private String idEni;

  @Column(name = "ID_UNIDAD_DOCUMENTO")
  private Long idUnidadDocumento;

  @Column(name = "DIR3_DOCUMENTO")
  private String dir3Documento;

  @Column(name = "DIR3_DOCUMENTO_NOMBRE")
  private String dir3DocumentoNombre;

  @Column(name = "ID_APLICACION")
  private String idAplicacion;

  @Column(name = "ID_APLICACION_PUBLICO")
  private String idAplicacionPublico;

  @Column(name = "ID_UNIDAD_APLICACION")
  private Long idUnidadAplicacion;

  @Column(name = "DIR3_APLICACION")
  private String dir3Aplicacion;

  @Column(name = "DIR3_APLICACION_NOMBRE")
  private String dir3AplicacionNombre;

  public VReferenciasAppEntity() {

  }

  public VReferenciasAppEntity(String dir3Documento, String csv, String idEni) {
    this.dir3Documento = dir3Documento;
    this.csv = csv;
    this.idEni = idEni;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Long getIdDocumento() {
    return idDocumento;
  }

  public void setIdDocumento(Long idDocumento) {
    this.idDocumento = idDocumento;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getCsv() {
    return csv;
  }

  public void setCsv(String csv) {
    this.csv = csv;
  }

  public String getIdEni() {
    return idEni;
  }

  public void setIdEni(String idEni) {
    this.idEni = idEni;
  }

  public String getDir3Documento() {
    return dir3Documento;
  }

  public void setDir3Documento(String dir3Documento) {
    this.dir3Documento = dir3Documento;
  }

  public String getDir3DocumentoNombre() {
    return dir3DocumentoNombre;
  }

  public void setDir3DocumentoNombre(String dir3DocumentoNombre) {
    this.dir3DocumentoNombre = dir3DocumentoNombre;
  }

  public String getIdAplicacion() {
    return idAplicacion;
  }

  public void setIdAplicacion(String idAplicacion) {
    this.idAplicacion = idAplicacion;
  }

  public String getIdAplicacionPublico() {
    return idAplicacionPublico;
  }

  public void setIdAplicacionPublico(String idAplicacionPublico) {
    this.idAplicacionPublico = idAplicacionPublico;
  }

  public String getDir3Aplicacion() {
    return dir3Aplicacion;
  }

  public void setDir3Aplicacion(String dir3Aplicacion) {
    this.dir3Aplicacion = dir3Aplicacion;
  }

  public String getDir3AplicacionNombre() {
    return dir3AplicacionNombre;
  }

  public void setDir3AplicacionNombre(String dir3AplicacionNombre) {
    this.dir3AplicacionNombre = dir3AplicacionNombre;
  }

  public Long getIdUnidadDocumento() {
    return idUnidadDocumento;
  }

  public void setIdUnidadDocumento(Long idUnidadDocumento) {
    this.idUnidadDocumento = idUnidadDocumento;
  }

  public Long getIdUnidadAplicacion() {
    return idUnidadAplicacion;
  }

  public void setIdUnidadAplicacion(Long idUnidadAplicacion) {
    this.idUnidadAplicacion = idUnidadAplicacion;
  }

}
