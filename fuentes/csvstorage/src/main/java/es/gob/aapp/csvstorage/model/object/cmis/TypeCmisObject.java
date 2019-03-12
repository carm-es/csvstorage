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

package es.gob.aapp.csvstorage.model.object.cmis;

// TODO: Auto-generated Javadoc
/**
 * Objeto para los tipos de objetos CMIS.
 * 
 */
public class TypeCmisObject {

  private Long id;

  private String type;

  private String baseType;

  private String version;

  private String name;

  private String description;

  public TypeCmisObject() {
    super();
  }


  public TypeCmisObject(Long id, String type, String baseType, String version, String name,
      String description) {
    super();
    this.id = id;
    this.type = type;
    this.baseType = baseType;
    this.version = version;
    this.name = name;
    this.description = description;
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
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return the baseType
   */
  public String getBaseType() {
    return baseType;
  }

  /**
   * @param baseType the baseType to set
   */
  public void setBaseType(String baseType) {
    this.baseType = baseType;
  }

  /**
   * @return the version
   */
  public String getVersion() {
    return version;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(String version) {
    this.version = version;
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


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "TypeCmisEntity [id=" + id + ", type=" + type + ", baseType=" + baseType + ", version="
        + version + ", nombre=" + name + ", description=" + description + "]";
  }


}
