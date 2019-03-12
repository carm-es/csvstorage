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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import es.gob.aapp.csvstorage.dao.entity.cmis.PropertiesTypeCmisEntity;
import es.gob.aapp.csvstorage.dao.entity.cmis.TypeCmisEntity;

public class PropertiesTypeCmisSpecification {

  /**
   * Méodo para las consultas según criterio dinámico.
   * 
   * @param propTypeCmis
   * @return
   */
  public Specification<PropertiesTypeCmisEntity> dynamicCriteria(
      final PropertiesTypeCmisEntity propTypeCmis) {
    return new Specification<PropertiesTypeCmisEntity>() {
      public Predicate toPredicate(Root<PropertiesTypeCmisEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        // idProperty
        if (!StringUtils.isBlank(propTypeCmis.getIdProperty())) {
          predicates.add(cb.equal(root.get("idProperty"), propTypeCmis.getIdProperty()));
        }

        // type
        if (propTypeCmis.getType() != null) {
          Join<PropertiesTypeCmisEntity, TypeCmisEntity> docJoin = root.join("type");
          if (propTypeCmis.getType().getId() != null) {
            predicates.add(cb.equal(docJoin.get("id"), propTypeCmis.getType().getId()));
          }
          if (propTypeCmis.getType().getType() != null) {
            predicates.add(cb.equal(docJoin.get("type"), propTypeCmis.getType().getType()));
          }
        }

        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return null;
      }
    };
  }

}
