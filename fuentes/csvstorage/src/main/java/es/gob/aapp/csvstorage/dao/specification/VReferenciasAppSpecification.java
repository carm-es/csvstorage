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

package es.gob.aapp.csvstorage.dao.specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import es.gob.aapp.csvstorage.dao.entity.reference.VReferenciasAppEntity;
import es.gob.aapp.csvstorage.util.Util;

public class VReferenciasAppSpecification {

  /**
   * Méodo para las consultas según criterio dinámico.
   * 
   * @param prueba
   * @return
   */
  public Specification<VReferenciasAppEntity> dynamicCriteria(
      final VReferenciasAppEntity referencia) {
    return new Specification<VReferenciasAppEntity>() {
      public Predicate toPredicate(Root<VReferenciasAppEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        // getIdUnidadDocumento
        if (referencia != null && referencia.getDir3Documento() != null) {
          Predicate p = cb.equal(root.get("dir3Documento"), referencia.getDir3Documento());
          predicates.add(p);
        }

        // dir3_documento
        if (referencia != null && referencia.getIdUnidadDocumento() != null) {
          Predicate p = cb.equal(root.get("idUnidadDocumento"), referencia.getIdUnidadDocumento());
          predicates.add(p);
        }

        // csv
        if (!StringUtils.isBlank(referencia.getCsv())) {

          String csv = referencia.getCsv();
          String csvConAmbito = Util.comprobarCSVConAmbito(referencia.getCsv());
          String csvSinGuiones = Util.formatearCsvSinGuiones(referencia.getCsv());

          Predicate p = cb.or(cb.equal(root.get("csv"), csv),
              cb.equal(root.get("csv"), csvConAmbito), cb.equal(root.get("csv"), csvSinGuiones));
          predicates.add(p);
        }

        // idEni
        if (!StringUtils.isBlank(referencia.getIdEni())) {
          predicates.add(cb.equal(root.get("idEni"), referencia.getIdEni()));
        }

        // idDocumento
        if (referencia.getIdDocumento() != null) {
          predicates.add(cb.equal(root.get("idDocumento"), referencia.getIdDocumento()));
        }


        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return null;
      }
    };
  }

}
