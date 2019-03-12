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
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniOrganoEntity;

/**
 * Specification para consultas complejas de TemplateApplication.
 * 
 * @author carlos.munoz1
 * 
 */
public class DocumentoEniOrganoSpecification {

  /**
   * Método para las consultas según criterio dinámico.
   * 
   * @param prueba
   * @return
   */
  public Specification<DocumentEniOrganoEntity> dynamicCriteria(
      final DocumentEniOrganoEntity documentoEniOrganoEntity) {

    return new Specification<DocumentEniOrganoEntity>() {

      public Predicate toPredicate(Root<DocumentEniOrganoEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        // documento
        if (documentoEniOrganoEntity.getDocumentoEni() != null
            && documentoEniOrganoEntity.getDocumentoEni().getId() != null) {
          Join<DocumentEniOrganoEntity, DocumentEniEntity> unitJoin = root.join("documentoEni");
          predicates.add(
              cb.equal(unitJoin.get("id"), documentoEniOrganoEntity.getDocumentoEni().getId()));

        }

        query.where(predicates.toArray(new Predicate[predicates.size()]));

        return null;
      }
    };
  }

}
