package org.molgenis.database.repository;

import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.eclipse.persistence.dynamic.DynamicClassLoader;
import org.eclipse.persistence.dynamic.DynamicEntity;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.jpa.dynamic.JPADynamicHelper;
import org.eclipse.persistence.jpa.dynamic.JPADynamicTypeBuilder;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.sessions.Session;
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
		JPADynamicHelper jpaDynamicHelper = new JPADynamicHelper(em);

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

		jpaDynamicHelper.addTypes(true, true, entityType.getType());
	}

	public void add(EntityInstance entityInstance) {
		JPADynamicHelper jpaDynamicHelper = new JPADynamicHelper(em());
		DynamicEntity dynamicEntity = jpaDynamicHelper.newDynamicEntity(entityInstance.getEntity().getId());
		dynamicEntity.set("id", entityInstance.getId());
		dynamicEntity.set("payload", entityInstance.getValues());

		em().merge(dynamicEntity);
	}

	protected EntityManager em() {
		return entityManagerProvider.get();
	}
}
