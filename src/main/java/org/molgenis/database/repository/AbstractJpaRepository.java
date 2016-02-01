package org.molgenis.database.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.molgenis.database.domain.AbstractJpaEntity;

import com.google.inject.Provider;

public abstract class AbstractJpaRepository<E extends AbstractJpaEntity> {

	private Provider<EntityManager> entityManagerProvider;
	private Class<E> entityClass;

	protected AbstractJpaRepository(Class<E> entityClass, Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
		this.entityClass = entityClass;
	}

	public E insert(E jpaEntity) {
		return em().merge(jpaEntity);
	}

	public Optional<E> findById(String id) {
		E entity = em().find(entityClass, id);
		return entity != null ? Optional.of(entity) : Optional.empty();
	}

	public List<E> findAll() {
		CriteriaQuery<E> cq = cb().createQuery(entityClass);
		cq.from(entityClass);

		return em().createQuery(cq).getResultList();
	}

	protected Optional<E> findFirst(List<E> entities) {
		return entities.isEmpty() ? Optional.empty() : Optional.of(entities.get(0));
	}

	protected EntityManager em() {
		return entityManagerProvider.get();
	}

	protected CriteriaBuilder cb() {
		return em().getCriteriaBuilder();
	}
}
