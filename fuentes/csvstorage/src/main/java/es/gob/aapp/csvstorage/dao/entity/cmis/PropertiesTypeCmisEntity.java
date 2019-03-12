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
import org.hibernate.annotations.Type;

// TODO: Auto-generated Javadoc
/**
 * Objeto entidad para los objetos CMIS.
 * 
 */
@Entity
@Table(name = "CSVST_PROPIEDADES_TIPO_CMIS")
public class PropertiesTypeCmisEntity {

  /** Clave primaria del objeto CMIS. */
  @Id
  @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "generator", sequenceName = "CSVST_PROP_TIPO_CMIS_SEQ",
      allocationSize = 1)
  private Long id;

  /** Cadena con el uuid del objeto. */
  @ManyToOne
  @JoinColumn(name = "ID_TIPO")
  private TypeCmisEntity type;

  /** Ruta o nombre del fichero correspondiente */
  @Column(nullable = false, name = "ID_PROPIEDAD")
  private String idProperty;

  /** Cadena con el tipo CMIS del objeto. */
  @Column(nullable = true, name = "NOMBRE")
  private String name;

  /** Cadena con el tipo CMIS del objeto. */
  @Column(nullable = true, name = "DESCRIPCION")
  private String description;

  /** Cadena con el tipo CMIS del objeto. */
  @Column(nullable = true, name = "TIPO_DATO")
  private String datatype;

  /** Cadena con el tipo CMIS del objeto. */
  @Column(nullable = true, name = "CARDINALIDAD")
  private String cardinality;

  /** Cadena con el tipo CMIS del objeto. */
  @Column(nullable = true, name = "MODIFICABLE")
  private String updateability;

  /** The is admin. */
  @Column(nullable = false, name = "HEREDADO")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean inherited;

  /** The is admin. */
  @Column(nullable = false, name = "REQUERIDO")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean required;

  /** The is admin. */
  @Column(nullable = false, name = "CONSULTABLE")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean queryable;

  /** The is admin. */
  @Column(nullable = false, name = "ORDENABLE")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean orderable;



  public PropertiesTypeCmisEntity() {
    super();
    // TODO Auto-generated constructor stub
  }



  public PropertiesTypeCmisEntity(Long id, TypeCmisEntity type, String idProperty, String name,
      String description, String datatype, String cardinality, String updateability,
      Boolean inherited, Boolean required, Boolean queryable, Boolean orderable) {
    super();
    this.id = id;
    this.type = type;
    this.idProperty = idProperty;
    this.name = name;
    this.description = description;
    this.datatype = datatype;
    this.cardinality = cardinality;
    this.updateability = updateability;
    this.inherited = inherited;
    this.required = required;
    this.queryable = queryable;
    this.orderable = orderable;
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
   * @return the type
   */
  public TypeCmisEntity getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(TypeCmisEntity type) {
    this.type = type;
  }

  /**
   * @return the baseType
   */
  public String getIdProperty() {
    return idProperty;
  }

  /**
   * @param baseType the baseType to set
   */
  public void setIdProperty(String idProperty) {
    this.idProperty = idProperty;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the datatype
   */
  public String getDatatype() {
    return datatype;
  }

  /**
   * @param datatype the datatype to set
   */
  public void setDatatype(String datatype) {
    this.datatype = datatype;
  }

  /**
   * @return the cardinality
   */
  public String getCardinality() {
    return cardinality;
  }

  /**
   * @param cardinality the cardinality to set
   */
  public void setCardinality(String cardinality) {
    this.cardinality = cardinality;
  }

  /**
   * @return the updateability
   */
  public String getUpdateability() {
    return updateability;
  }

  /**
   * @param updateability the updateability to set
   */
  public void setUpdateability(String updateability) {
    this.updateability = updateability;
  }

  /**
   * @return the inherited
   */
  public Boolean getInherited() {
    return inherited;
  }

  /**
   * @param inherited the inherited to set
   */
  public void setInherited(Boolean inherited) {
    this.inherited = inherited;
  }

  /**
   * @return the required
   */
  public Boolean getRequired() {
    return required;
  }

  /**
   * @param required the required to set
   */
  public void setRequired(Boolean required) {
    this.required = required;
  }

  /**
   * @return the queryable
   */
  public Boolean getQueryable() {
    return queryable;
  }

  /**
   * @param queryable the queryable to set
   */
  public void setQueryable(Boolean queryable) {
    this.queryable = queryable;
  }

  /**
   * @return the orderable
   */
  public Boolean getOrderable() {
    return orderable;
  }

  /**
   * @param orderable the orderable to set
   */
  public void setOrderable(Boolean orderable) {
    this.orderable = orderable;
  }



  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "PropertyTypeCmisEntity [id=" + id + ", type=" + type + ", idProperty=" + idProperty
        + ", name=" + name + ", description=" + description + ", datatype=" + datatype
        + ", cardinality=" + cardinality + ", updateability=" + updateability + ", inherited="
        + inherited + ", required=" + required + ", queryable=" + queryable + ", orderable="
        + orderable + "]";
  }



}
