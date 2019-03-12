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

package es.gob.aapp.csvstorage.dao.entity.cmis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

// TODO: Auto-generated Javadoc
/**
 * Objeto entidad para los objetos CMIS.
 * 
 */
@Entity
@Table(name = "CSVST_OBJETO_CMIS")
public class ObjectCmisEntity {

  /** Clave primaria del objeto CMIS. */
  @Id
  @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "generator", sequenceName = "CSVST_OBJETO_CMIS_SEQ", allocationSize = 1)
  private Long id;

  /** Cadena con el uuid del objeto. */
  @NotEmpty
  @Column(nullable = true, name = "UUID")
  private String uuid;

  /** Ruta o nombre del fichero correspondiente */
  @Column(nullable = false, name = "FICHERO")
  private String fichero;

  /** Cadena con el tipo CMIS del objeto. */
  @Column(nullable = true, name = "TIPO")
  private String tipoCMIS;

  /** Cadena con el tipo CMIS del objeto. */
  @Column(nullable = true, name = "TIPO_PADRE")
  private String tipoPadreCMIS;

  public ObjectCmisEntity() {
    super();
  }

  public ObjectCmisEntity(Long id, String uuid, String fichero, String tipoCMIS) {
    super();
    this.id = id;
    this.uuid = uuid;
    this.fichero = fichero;
    this.tipoCMIS = tipoCMIS;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
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

  /**
   * @return the fichero
   */
  public String getFichero() {
    return fichero;
  }

  /**
   * @param fichero the fichero to set
   */
  public void setFichero(String fichero) {
    this.fichero = fichero;
  }

  /**
   * @return the tipoCMIS
   */
  public String getTipoCMIS() {
    return tipoCMIS;
  }

  /**
   * @param tipoCMIS the tipoCMIS to set
   */
  public void setTipoCMIS(String tipoCMIS) {
    this.tipoCMIS = tipoCMIS;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ObjectCmisEntity [id=" + id + ", uuid=" + uuid + ", fichero=" + fichero + ", tipoCMIS="
        + tipoCMIS + "]";
  }

  /**
   * @return the tipoPadreCMIS
   */
  public String getTipoPadreCMIS() {
    return tipoPadreCMIS;
  }

  /**
   * @param tipoPadreCMIS the tipoPadreCMIS to set
   */
  public void setTipoPadreCMIS(String tipoPadreCMIS) {
    this.tipoPadreCMIS = tipoPadreCMIS;
  }

}
