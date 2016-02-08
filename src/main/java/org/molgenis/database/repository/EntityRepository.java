package org.molgenis.database.repository;

import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.molgenis.database.domain.MolgenisEntity;

import com.google.inject.Provider;

public class EntityRepository extends AbstractJpaRepository<MolgenisEntity> {

	@Inject
	public EntityRepository(Provider<EntityManager> entityManagerProvider) {
		super(MolgenisEntity.class, entityManagerProvider);
	}

	public Optional<MolgenisEntity> findByName(String name) {
		return findByField("name", name);
	}

}
