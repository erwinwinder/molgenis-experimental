package org.molgenis.database;

import javax.inject.Inject;

import org.molgenis.database.domain.MolgenisEntity;
import org.molgenis.database.repository.EntityInstanceRepository;
import org.molgenis.database.repository.EntityRepository;

public class DataService {
	private EntityRepository entityRepository;
	private EntityInstanceRepository entityInstanceRepository;

	@Inject
	public DataService(EntityRepository entityRepository, EntityInstanceRepository entityInstanceRepository) {
		this.entityRepository = entityRepository;
		this.entityInstanceRepository = entityInstanceRepository;
	}

	public void addRepository(MolgenisEntity entity) {
		entityRepository.insert(entity);
		entityInstanceRepository.createRepository(entity);
	}
}
