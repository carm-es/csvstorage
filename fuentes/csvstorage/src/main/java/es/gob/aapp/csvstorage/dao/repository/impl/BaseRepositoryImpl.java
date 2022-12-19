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

package es.gob.aapp.csvstorage.dao.repository.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import reactor.util.Assert;

/**
 * Implementación del repositorio base común.
 * 
 * @author carlos.munoz1
 * 
 * @param <T>
 * @param <ID>
 */
@SuppressWarnings("unchecked")
@Transactional
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements BaseRepository<T, ID> {// NOSONAR

  public static final String DYNAMIC_CRITERIA = "dynamicCriteria";
  EntityManager entityManager;

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(BaseRepositoryImpl.class);

  private static final Map<String, String> specificationMap;
  static {
    specificationMap = new HashMap<>();
    specificationMap.put("ApplicationEntity",
        "es.gob.aapp.csvstorage.dao.specification.ApplicationSpecification");
    specificationMap.put("DocumentEntity",
        "es.gob.aapp.csvstorage.dao.specification.DocumentoSpecification");
    specificationMap.put("DocumentEniEntity",
        "es.gob.aapp.csvstorage.dao.specification.DocumentoEniSpecification");
    specificationMap.put("DocumentEniOrganoEntity",
        "es.gob.aapp.csvstorage.dao.specification.DocumentoEniOrganoSpecification");
    specificationMap.put("DocumentNifEntity",
        "es.gob.aapp.csvstorage.dao.specification.DocumentoNifSpecification");
    specificationMap.put("DocumentRestriccionEntity",
        "es.gob.aapp.csvstorage.dao.specification.DocumentoRestriccionSpecification");
    specificationMap.put("DocumentIdsEntity",
        "es.gob.aapp.csvstorage.dao.specification.DocumentoTipoIdSpecification");
    specificationMap.put("DocumentAplicacionEntity",
        "es.gob.aapp.csvstorage.dao.specification.DocumentoAplicacionSpecification");
    specificationMap.put("ObjectCmisEntity",
        "es.gob.aapp.csvstorage.dao.specification.ObjectCmisSpecification");
    specificationMap.put("PropAdditObjectCmisEntity",
        "es.gob.aapp.csvstorage.dao.specification.PropAdditObjectCmisSpecification");
    specificationMap.put("PropertiesTypeCmisEntity",
        "es.gob.aapp.csvstorage.dao.specification.PropertiesTypeCmisSpecification");
    specificationMap.put("TypeCmisEntity",
        "es.gob.aapp.csvstorage.dao.specification.TypeCmisSpecification");
    specificationMap.put("UnitEntity",
        "es.gob.aapp.csvstorage.dao.specification.UnitSpecification");
    specificationMap.put("UserEntity",
        "es.gob.aapp.csvstorage.dao.specification.UserSpecification");
    specificationMap.put("VReferenciasAppEntity",
        "es.gob.aapp.csvstorage.dao.specification.VReferenciasAppSpecification");

  }

  public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
    super(domainClass, entityManager);
    this.entityManager = entityManager;
  }

  public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
      EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  public Object findOneBy(T data)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    // buscamos la especificacion concreta para el tipo dado
    Object specification = getSpecification(data);
    Assert.notNull(specification, "Specification is mandatory: " + specification);
    Method metodoR = specification.getClass().getMethod(DYNAMIC_CRITERIA, data.getClass());
    Specification<T> spec = (Specification<T>) metodoR.invoke(specification, data);
    return super.findOne(spec);
  }

  public List<T> findListBy(T data)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    // buscamos la especificacion concreta para el tipo dado
    Object specification = getSpecification(data);
    Assert.notNull(specification, "Specification is mandatory: " + specification);
    Method metodoR = specification.getClass().getMethod(DYNAMIC_CRITERIA, data.getClass());
    Specification<T> spec = (Specification<T>) metodoR.invoke(specification, data);
    return super.findAll(spec);
  }

  public List<T> findListByList(List<T> list)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    List<T> listReturn = new ArrayList<>();
    for (T t : list) {
      listReturn.addAll(findListBy(t));
    }
    return listReturn;
  }

  public List<Object> findListByQuery(String queryString, Map<String, Object> data,
      int maxResults) {

    LOG.debug("Buscando: queryString=" + queryString + "; filterData=" + data + "; maxResults"
        + maxResults);

    Query query = entityManager.createQuery(queryString);
    Set<Parameter<?>> parameters = query.getParameters();
    if (parameters != null) {
      for (Parameter<?> parameter : parameters) {
        Object value = data.get(parameter.getName());
        query.setParameter(parameter.getName(), value);
      }
    }
    query.setMaxResults(maxResults);
    return query.getResultList();
  }

  private Object getSpecification(T data) {
    Object retorno = null;
    try {
      if (specificationMap.containsKey(data.getClass().getSimpleName())) {
        String specification = specificationMap.get(data.getClass().getSimpleName());
        Class<?> clazz = Class.forName(specification);
        retorno = clazz.newInstance();
      }

    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
      LOG.error("Se ha producido un error al obtener la especificación", e);
    }

    return retorno;
  }

  public Object findFirstResult(T data, Sort sort)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

    Object specification = getSpecification(data);
    Assert.notNull(specification, "Specification is mandatory: " + specification);
    Method metodoR = specification.getClass().getMethod(DYNAMIC_CRITERIA, data.getClass());
    Specification<T> spec = (Specification<T>) metodoR.invoke(specification, data);

    TypedQuery<T> findAllBooks = getQuery(spec, sort);
    findAllBooks.setFirstResult(0);
    findAllBooks.setMaxResults(1);

    return findAllBooks.getSingleResult();

  }
}
