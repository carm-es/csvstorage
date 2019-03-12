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

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "CSVST_AUDITORIA_CONSULTAS")
public class QueryAuditEntity {

  /** Clave primaria del documento. */
  @Id
  @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "generator", sequenceName = "CSVST_AUDIT_CONSULTAS_SEQ",
      allocationSize = 1)
  private Long id;

  /** Cadena con la fecha de creación. */
  @Column(name = "FECHA")
  private Date fecha;

  // /** Cadena con el código idEni. */
  // @Column(nullable = true, name = "NIF")
  // private String nif;

  // /** The unidad organica. */
  // //@NotEmpty
  // @Column(nullable = true, name = "CODIGO_UNIDAD")
  // private String dir3;

  // /** Cadena con el código csv. */
  // @Column(nullable = true, name = "CSV")
  // private String csv;

  // /** Cadena con el código idEni. */
  // @Column(nullable = true, name = "ID_ENI")
  // private String idEni;

  // @Column(nullable = true, name = "TIPO_IDENTIFICACION")
  // private Integer tipoIdentificacion;

  @Column(nullable = true, name = "ID_DOCUMENTO")
  private Long idDocumento;

  @Column(nullable = true, name = "ID_APLICACION")
  private Long idAplicacion;

  @Column(nullable = true, name = "ID_APLICACION_TXT")
  private String idAplicacionTxt;

  @Column(nullable = true, name = "OPERACION")
  private String operacion;


  /**
   * Instantiates a new document entity.
   */
  public QueryAuditEntity() {
    super();
  }

  // public QueryAuditEntity(Long id, Date fecha, String nif, String dir3, String csv, String idEni)
  // {
  // super();
  // this.id = id;
  // this.fecha = fecha;
  // this.nif = nif;
  // this.dir3 = dir3;
  // this.csv = csv;
  // this.idEni = idEni;
  // }

  public QueryAuditEntity(Long id, Date fecha, Long idDocumento, Long idAplicacion,
      String idAplicacionTxt, String operacion) {
    super();
    this.id = id;
    this.fecha = fecha;

    this.idDocumento = idDocumento;
    this.idAplicacion = idAplicacion;
    this.idAplicacionTxt = idAplicacionTxt;
    this.operacion = operacion;
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
   * @return the fecha
   */
  public Date getFecha() {
    return fecha;
  }


  /**
   * @param fecha the fecha to set
   */
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }


  // /**
  // * @return the nif
  // */
  // public String getNif() {
  // return nif;
  // }
  //
  //
  // /**
  // * @param nif the nif to set
  // */
  // public void setNif(String nif) {
  // this.nif = nif;
  // }
  //
  //
  // /**
  // * @return the dir3
  // */
  // public String getDir3() {
  // return dir3;
  // }
  //
  //
  // /**
  // * @param dir3 the dir3 to set
  // */
  // public void setDir3(String dir3) {
  // this.dir3 = dir3;
  // }
  //
  //
  // /**
  // * @return the csv
  // */
  // public String getCsv() {
  // return csv;
  // }
  //
  //
  // /**
  // * @param csv the csv to set
  // */
  // public void setCsv(String csv) {
  // this.csv = csv;
  // }
  //
  //
  // /**
  // * @return the idEni
  // */
  // public String getIdEni() {
  // return idEni;
  // }
  //
  //
  // /**
  // * @param idEni the idEni to set
  // */
  // public void setIdEni(String idEni) {
  // this.idEni = idEni;
  // }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "QueryAuditEntity [id=" + id + ", fecha=" + fecha +
    // ", nif=" + nif + ", dir3=" + dir3 + ", csv=" + csv + ", idEni=" + idEni +
        ", idDocumento=" + idDocumento + ", idAplicacion=" + idAplicacion + ", idAplicacionTxt="
        + idAplicacionTxt + ", operacion = " + operacion + "]";
  }

  // /**
  // * @return the tipoIdentificacion
  // */
  // public Integer getTipoIdentificacion() {
  // return tipoIdentificacion;
  // }
  //
  // /**
  // * @param tipoIdentificacion the tipoIdentificacion to set
  // */
  // public void setTipoIdentificacion(Integer tipoIdentificacion) {
  // this.tipoIdentificacion = tipoIdentificacion;
  // }

  public Long getIdAplicacion() {
    return idAplicacion;
  }

  public void setIdAplicacion(Long idAplicacion) {
    this.idAplicacion = idAplicacion;
  }

  public String getIdAplicacionTxt() {
    return idAplicacionTxt;
  }

  public void setIdAplicacionTxt(String idAplicacionTxt) {
    this.idAplicacionTxt = idAplicacionTxt;
  }

  public String getOperacion() {
    return operacion;
  }

  public void setOperacion(String operacion) {
    this.operacion = operacion;
  }

  public Long getIdDocumento() {
    return idDocumento;
  }

  public void setIdDocumento(Long idDocumento) {
    this.idDocumento = idDocumento;
  }



}
