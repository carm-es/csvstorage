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
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.util.Util;

public class DocumentoSpecification {

  /**
   * Méodo para las consultas según criterio dinámico.
   * 
   * @param prueba
   * @return
   */
  public Specification<DocumentEntity> dynamicCriteria(final DocumentEntity document) {
    return new Specification<DocumentEntity>() {
      public Predicate toPredicate(Root<DocumentEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        // unidadOrganica
        if (document != null && document.getUnidadOrganica() != null) {
          Join<DocumentEntity, UnitEntity> unitJoin = root.join("unidadOrganica");
          predicates.add(cb.equal(unitJoin.get("unidadOrganica"),
              document.getUnidadOrganica().getUnidadOrganica()));

        }

        // csv
        if (!StringUtils.isBlank(document.getCsv())) {

          String csv = document.getCsv();
          String csvConAmbito = Util.comprobarCSVConAmbito(document.getCsv());
          String csvSinGuiones = Util.formatearCsvSinGuiones(document.getCsv());

          Predicate p = cb.or(cb.equal(root.get("csv"), csv),
              cb.equal(root.get("csv"), csvConAmbito), cb.equal(root.get("csv"), csvSinGuiones));
          predicates.add(p);
        }

        // idEni
        if (!StringUtils.isBlank(document.getIdEni())) {
          predicates.add(cb.equal(root.get("idEni"), document.getIdEni()));
        }

        // fecha de creación
        if (document.getCreatedAt() != null) {
          predicates
              .add(cb.lessThanOrEqualTo(root.<Date>get("createdAt"), document.getCreatedAt()));
        }

        // uuid
        if (!StringUtils.isBlank(document.getUuid())) {
          predicates.add(cb.equal(root.get("uuid"), document.getUuid()));
        }

        // Tamaño del fichero
        if (document.getTamanioFichero() != null) {
          predicates.add(cb.equal(root.get("tamanioFichero"), document.getTamanioFichero()));
        }

        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return null;
      }
    };
  }

}
