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

package es.gob.aapp.csvstorage.dao.entity.audit;

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
 * Objeto entidad para los documentos.
 * 
 * @author carlos.munoz1
 * 
 */
@Entity
@Table(name = "CSVST_AUDITORIA_PARAM")
public class AuditParamEntity {

  /** Clave primaria del documento. */
  @Id
  @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "generator", sequenceName = "CSVST_AUDITORIA_PARAM_SEQ",
      allocationSize = 1)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ID_AUDITORIA")
  private QueryAuditEntity auditoria;

  @Column(name = "PARAMETRO")
  private String parametro;

  @Column(nullable = true, name = "VALOR")
  private String valor;


  /**
   * Instantiates a new document entity.
   */
  public AuditParamEntity() {
    super();
  }

  public AuditParamEntity(Long id, QueryAuditEntity auditoria, String parametro, String valor) {
    super();
    this.id = id;
    this.auditoria = auditoria;
    this.parametro = parametro;
    this.valor = valor;
  }

  public AuditParamEntity(QueryAuditEntity auditoria, String parametro, String valor) {
    super();
    this.auditoria = auditoria;
    this.parametro = parametro;
    this.valor = valor;
  }



  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public QueryAuditEntity getAuditoria() {
    return auditoria;
  }

  public void setAuditoria(QueryAuditEntity auditoria) {
    this.auditoria = auditoria;
  }

  public String getParametro() {
    return parametro;
  }

  public void setParametro(String parametro) {
    this.parametro = parametro;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }

  @Override
  public String toString() {
    return "QueryAuditEntity [id=" + id + ", auditoria=" + auditoria + ", parametro=" + parametro
        + ", valor=" + valor + "]";
  }



}
