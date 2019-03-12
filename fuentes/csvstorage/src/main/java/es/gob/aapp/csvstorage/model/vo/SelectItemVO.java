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

package es.gob.aapp.csvstorage.model.vo;

/**
 * Clase utilizada para mostrar valores en los item de autocomplete.
 * 
 */
public class SelectItemVO {

  /**
   * Etiqueta con el valor a mostrar.
   */
  private String label;

  /**
   * Descripci�n opcional del label.
   */
  private String desc;

  /**
   * Constructor con par�metros.
   * 
   * @param label
   * @param desc
   */
  public SelectItemVO(String label, String desc) {
    super();
    this.label = label;
    this.desc = desc;
  }

  // getter y setter

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
