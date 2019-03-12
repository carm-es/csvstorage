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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * Objeto entidad para los objetos CMIS.
 * 
 */
@Entity
@Table(name = "CSVST_PROP_ADIC_OBJETO_CMIS")
public class PropAdditObjectCmisEntity {

  /** Clave primaria del objeto CMIS. */
  @Id
  @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "generator", sequenceName = "CSVST_PROP_OBJ_CMIS_SEQ",
      allocationSize = 1)
  private Long id;

  /** Cadena con el uuid del objeto. */
  @Column(nullable = true, name = "UUID")
  private String uuid;


  /** Cadena con el uuid del objeto. */
  @ManyToOne
  @JoinColumn(name = "ID_PROPIEDAD")
  private PropertiesTypeCmisEntity property;

  /** Cadena con el tipo CMIS del objeto. */
  @Column(nullable = true, name = "VALOR")
  private String value;



  public PropAdditObjectCmisEntity() {
    super();
  }

  public PropAdditObjectCmisEntity(Long id, String uuid, PropertiesTypeCmisEntity property,
      String value) {
    super();
    this.id = id;
    this.uuid = uuid;
    this.property = property;
    this.value = value;
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
   * @return the document
   */
  public String getUuid() {
    return uuid;
  }

  /**
   * @param document the document to set
   */
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  /**
   * @return the property
   */
  public PropertiesTypeCmisEntity getProperty() {
    return property;
  }

  /**
   * @param property the property to set
   */
  public void setProperty(PropertiesTypeCmisEntity property) {
    this.property = property;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * @param value the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "PropAdditDocumentCmisEntity [id=" + id + ", uuid=" + uuid + ", property=" + property
        + ", value=" + value + "]";
  }



}
