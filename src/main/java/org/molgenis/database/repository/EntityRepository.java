package org.molgenis.database.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.molgenis.database.domain.MolgenisEntity;

import com.google.inject.Provider;

public class EntityRepository extends AbstractJpaRepository<MolgenisEntity> {

	@Inject
	public EntityRepository(Provider<EntityManager> entityManagerProvider) {
		super(MolgenisEntity.class, entityManagerProvider);
	}

}
