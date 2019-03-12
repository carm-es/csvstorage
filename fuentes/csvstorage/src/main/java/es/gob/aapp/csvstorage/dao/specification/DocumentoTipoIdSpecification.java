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

import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentIdsEntity;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Specification para consultas complejas de TemplateApplication.
 * 
 * @author carlos.munoz1
 * 
 */
public class DocumentoTipoIdSpecification {

  /**
   * Método para las consultas según criterio dinámico.
   * 
   * @param documentIdsEntity
   * @return
   */
  public Specification<DocumentIdsEntity> dynamicCriteria(
      final DocumentIdsEntity documentIdsEntity) {

    return new Specification<DocumentIdsEntity>() {

      public Predicate toPredicate(Root<DocumentIdsEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        // documento
        if (documentIdsEntity.getDocumento() != null
            && documentIdsEntity.getDocumento().getId() != null) {
          Join<DocumentIdsEntity, DocumentEntity> unitJoin = root.join("documento");
          predicates.add(cb.equal(unitJoin.get("id"), documentIdsEntity.getDocumento().getId()));

        }

        // restriccion ID
        if (documentIdsEntity.getTipoId() != null) {
          predicates.add(cb.equal(root.get("tipoId"), documentIdsEntity.getTipoId()));
        }

        query.where(predicates.toArray(new Predicate[predicates.size()]));

        return null;
      }
    };
  }

}
