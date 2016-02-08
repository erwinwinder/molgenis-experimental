package org.molgenis.database.repository;

import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.molgenis.database.domain.Project;

import com.google.inject.Provider;

public class ProjectRepository extends AbstractJpaRepository<Project> {

	@Inject
	public ProjectRepository(Provider<EntityManager> entityManagerProvider) {
		super(Project.class, entityManagerProvider);
	}

	public Optional<Project> findByName(String name) {
		return findByField("name", name);
	}

}
