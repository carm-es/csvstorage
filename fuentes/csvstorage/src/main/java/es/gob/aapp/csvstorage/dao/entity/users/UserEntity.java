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

package es.gob.aapp.csvstorage.dao.entity.users;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 * Objeto entidad para usuarios.
 *
 * @author mmaldonado
 */
@Entity
@Table(name = "CSVST_USUARIOS")
public class UserEntity implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The id. */
  @Id
  @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "generator", sequenceName = "CSVST_USER_SEQ", allocationSize = 1)
  private Long id;


  /** The usuario. */
  @Column(nullable = false, name = "USUARIO")
  private String usuario;

  /** The password. */
  @Column(nullable = false, name = "PASSWORD")
  private String password;

  /** The is admin. */
  @Column(nullable = false, name = "ADMINISTRADOR")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean isAdmin;

  /**
   * Instantiates a new user entity.
   */
  public UserEntity() {
    super();
  }


  /**
   * Gets the id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(Long id) {
    this.id = id;
  }



  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password.
   *
   * @param password the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }



  /**
   * Gets the checks if is admin.
   *
   * @return the isAdmin
   */
  public Boolean getIsAdmin() {
    return isAdmin;
  }

  /**
   * Sets the checks if is admin.
   *
   * @param isAdmin the isAdmin to set
   */
  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  /**
   * Gets the usuario.
   *
   * @return the usuario
   */
  public String getUsuario() {
    return usuario;
  }


  /**
   * Sets the usuario.
   *
   * @param usuario the new usuario
   */
  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "UserEntity [id=" + id + ", usuario=" + usuario + ", password=" + password + ", isAdmin="
        + isAdmin + "]";
  }



}
