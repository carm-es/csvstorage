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
import org.springframework.data.jpa.domain.Specification;
import es.gob.aapp.csvstorage.dao.entity.application.ApplicationEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentAplicacionEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;

/**
 * Specification para consultas complejas de TemplateApplication.
 * 
 * @author carlos.munoz1
 * 
 */
public class DocumentoAplicacionSpecification {

  /**
   * Método para las consultas según criterio dinámico.
   * 
   * @param documentoAplicacionEntity
   * @return
   */
  public Specification<DocumentAplicacionEntity> dynamicCriteria(
      final DocumentAplicacionEntity documentoAplicacionEntity) {

    return new Specification<DocumentAplicacionEntity>() {

      public Predicate toPredicate(Root<DocumentAplicacionEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        // documento
        if (documentoAplicacionEntity.getDocumento() != null
            && documentoAplicacionEntity.getDocumento().getId() != null) {
          Join<DocumentAplicacionEntity, DocumentEntity> unitJoin = root.join("documento");
          predicates
              .add(cb.equal(unitJoin.get("id"), documentoAplicacionEntity.getDocumento().getId()));
        }

        // aplicacion
        if (documentoAplicacionEntity.getAplicacion() != null
            && documentoAplicacionEntity.getAplicacion().getId() != null) {
          Join<DocumentAplicacionEntity, ApplicationEntity> unitJoin = root.join("aplicacion");
          predicates
              .add(cb.equal(unitJoin.get("id"), documentoAplicacionEntity.getAplicacion().getId()));
        }

        query.where(predicates.toArray(new Predicate[predicates.size()]));

        return null;
      }
    };
  }

}
