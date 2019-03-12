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

package es.gob.aapp.csvstorage.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity para tabla de prueba.
 * 
 * @author carlos.munoz1
 *
 */
@Entity
@Table(name = "CSV_PRUEBA")
public class PruebaEntity {

  @Id
  @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "generator", sequenceName = "CSV_PRUEBA_SEQ", allocationSize = 1)
  @Column(name = "ID", nullable = false)
  private int id;

  @Column(name = "ORGANISMO", nullable = false)
  private String organismo;

  @Column(name = "ENDPOINT", nullable = true)
  private String endpoint;

  public PruebaEntity() {
    super();
  }

  public PruebaEntity(int id, String organismo, String endpoint) {
    super();
    this.id = id;
    this.organismo = organismo;
    this.endpoint = endpoint;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOrganismo() {
    return organismo;
  }

  public void setOrganismo(String organismo) {
    this.organismo = organismo;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  @Override
  public String toString() {
    return "PruebaEntity [id=" + id + ", organismo=" + organismo + ", endpoint=" + endpoint + "]";
  }


}
