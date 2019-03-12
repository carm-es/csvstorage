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
 * Objeto entidad para los objetos CMIS.
 * 
 */

public class PropAdditObjectCmisObject {

  private Long id;

  private String uuid;

  private PropertiesTypeCmisObject property;

  private String value;

  public PropAdditObjectCmisObject() {
    super();
  }

  public PropAdditObjectCmisObject(Long id, String uuid, PropertiesTypeCmisObject property,
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
  public PropertiesTypeCmisObject getProperty() {
    return property;
  }

  /**
   * @param property the property to set
   */
  public void setProperty(PropertiesTypeCmisObject property) {
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
