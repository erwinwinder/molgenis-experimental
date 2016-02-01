package org.molgenis.database.repository;

import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.molgenis.database.domain.Project;

import com.google.inject.Provider;

public class ProjectRepository extends AbstractJpaRepository<Project> {

	@Inject
	public ProjectRepository(Provider<EntityManager> entityManagerProvider) {
		super(Project.class, entityManagerProvider);
	}

	public Optional<Project> findByName(String name) {
		CriteriaBuilder cb = cb();
		CriteriaQuery<Project> cq = cb.createQuery(Project.class);
		cq.where(cb.equal(cq.from(Project.class).get("name"), name));

		return findFirst(em().createQuery(cq).getResultList());
	}

}
