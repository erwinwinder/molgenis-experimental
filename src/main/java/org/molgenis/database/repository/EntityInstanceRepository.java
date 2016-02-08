package org.molgenis.database.repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.eclipse.persistence.dynamic.DynamicClassLoader;
import org.eclipse.persistence.dynamic.DynamicEntity;
import org.eclipse.persistence.dynamic.DynamicType;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.jpa.dynamic.JPADynamicHelper;
import org.eclipse.persistence.jpa.dynamic.JPADynamicTypeBuilder;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.sessions.Session;
import org.molgenis.database.domain.Attribute;
import org.molgenis.database.domain.EntityInstance;
import org.molgenis.database.domain.MolgenisEntity;

import com.google.inject.Provider;

public class EntityInstanceRepository {
	private Provider<EntityManager> entityManagerProvider;

	@Inject
	public EntityInstanceRepository(Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
	}

	public void createRepository(MolgenisEntity entity) {
		createRepository(entity.getId());
	}

	public void createRepository(String name) {
		EntityManager em = em();
		Session session = JpaHelper.getEntityManager(em).getServerSession();
		DynamicClassLoader dcl = DynamicClassLoader.lookup(session);

		Class<?> entityClass = dcl.createDynamicClass(name);
		JPADynamicTypeBuilder entityType = new JPADynamicTypeBuilder(entityClass, null, name);
		entityType.setPrimaryKeyFields("id");
		entityType.addDirectMapping("id", String.class, "id");

		DirectToFieldMapping payloadMapping = entityType.addDirectMapping("payload", Map.class, "payload");
		DatabaseField field = new DatabaseField("payload");
		field.setNullable(false);
		field.setColumnDefinition("jsonb");
		payloadMapping.setField(field);
		payloadMapping.setConverter(new PayloadConverter());

		getJpaDynamicHelper().addTypes(true, true, entityType.getType());
	}

	public void removeRepository(MolgenisEntity entity) {
		removeRepository(entity.getId());
	}

	public void removeRepository(String name) {
		getJpaDynamicHelper().removeType(name);
	}

	public void add(EntityInstance entityInstance) {
		JPADynamicHelper jpaDynamicHelper = getJpaDynamicHelper();
		DynamicEntity dynamicEntity = jpaDynamicHelper.newDynamicEntity(entityInstance.getEntity().getId());
		dynamicEntity.set("id", entityInstance.getId());
		dynamicEntity.set("payload", toPayload(entityInstance));

		em().merge(dynamicEntity);
	}

	public Optional<EntityInstance> findOne(MolgenisEntity entity, String id) {
		DynamicType type = getJpaDynamicHelper().getType(entity.getId());
		DynamicEntity dynamicEntity = em().find(type.getJavaClass(), id);
		if (dynamicEntity == null) {
			return Optional.empty();
		}

		Map<String, Object> values = dynamicEntity.get("payload");// TODO
		EntityInstance entityInstance = EntityInstance.builder().entity(entity).id(id).values(values).build();

		return Optional.of(entityInstance);
	}

	private Map<String, Object> toPayload(EntityInstance entityInstance) {
		Map<String, Object> payload = new LinkedHashMap<>(entityInstance.getValues().size());
		MolgenisEntity entity = entityInstance.getEntity();

		entityInstance.getValues().forEach((attrName, value) -> {
			Attribute attr = entity.getAttribute(attrName);
			payload.put(attrName, toPayloadValue(attr, value));
		});

		return payload;
	}

	private Object toPayloadValue(Attribute attribute, Object value) {
		if (value == null) {
			return null;
		}

		// TODO

		return value.toString();
	}

	private JPADynamicHelper getJpaDynamicHelper() {
		return new JPADynamicHelper(em());
	}

	private EntityManager em() {
		return entityManagerProvider.get();
	}
}
