package org.molgenis.database;

import java.util.Optional;

import javax.inject.Inject;

import org.molgenis.database.domain.EntityInstance;
import org.molgenis.database.domain.MolgenisEntity;
import org.molgenis.database.repository.EntityInstanceRepository;
import org.molgenis.database.repository.EntityRepository;

import com.google.inject.persist.Transactional;

public class DataService {
	private EntityRepository entityRepository;
	private EntityInstanceRepository entityInstanceRepository;

	@Inject
	public DataService(EntityRepository entityRepository, EntityInstanceRepository entityInstanceRepository) {
		this.entityRepository = entityRepository;
		this.entityInstanceRepository = entityInstanceRepository;
	}

	@Transactional
	public void addEntity(MolgenisEntity entity) throws EntityAlreadyExistsException {
		if (entityRepository.findByName(entity.getName()).isPresent()) {
			throw new EntityAlreadyExistsException("Entity with name '" + entity.getName() + "' already exists");
		}

		entityRepository.insert(entity);
		entityInstanceRepository.createRepository(entity);
	}

	@Transactional
	public Optional<MolgenisEntity> findEntityByName(String name) {
		return entityRepository.findByName(name);
	}

	@Transactional
	public Optional<EntityInstance> findEntityInstance(String molgenisEntityName, String id)
			throws UnknownMolgenisEntityException {

		Optional<MolgenisEntity> entity = findEntityByName(molgenisEntityName);
		if (!entity.isPresent()) {
			throw new UnknownMolgenisEntityException("Unknown entity '" + molgenisEntityName + "'");
		}

		return entityInstanceRepository.findOne(entity.get(), id);
	}

	@Transactional
	public Optional<EntityInstance> findEntityInstance(MolgenisEntity entity, String id) {
		return entityInstanceRepository.findOne(entity, id);
	}

	@Transactional
	public void addEntityInstance(EntityInstance entityInstance) {
		entityInstanceRepository.add(entityInstance);
	}
}
