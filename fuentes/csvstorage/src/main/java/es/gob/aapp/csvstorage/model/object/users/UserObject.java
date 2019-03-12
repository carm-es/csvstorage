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

package es.gob.aapp.csvstorage.model.object.users;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Objeto entidad para usuarios.
 *
 * @author mmaldonado
 */

public class UserObject {

  /** The id. */
  private Long id;

  /** The usuario. */
  @NotBlank
  private String usuario;

  /** The password. */
  private String password;

  /** The is admin. */
  private Boolean isAdmin;

  /**
   * Instantiates a new user entity.
   */
  public UserObject() {
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
    StringBuffer tmpBuff = new StringBuffer("UserEntity[id=");
    tmpBuff.append(getId());
    tmpBuff.append("usuario=");
    tmpBuff.append(getUsuario());
    tmpBuff.append("isAdmin=");
    tmpBuff.append(getIsAdmin());
    tmpBuff.append("]");
    return tmpBuff.toString();
  }

}
