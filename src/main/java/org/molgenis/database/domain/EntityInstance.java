package org.molgenis.database.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.ImmutableMap;

public class EntityInstance {
	private MolgenisEntity entity;
	private Map<String, Object> values = new LinkedHashMap<>();
	private String id;

	private EntityInstance() {
	}

	public static EntityInstance.Builder builder() {
		return new EntityInstance.Builder();
	}

	public String getId() {
		return id;
	}

	public MolgenisEntity getEntity() {
		return entity;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String attributeName) {
		return (T) values.get(attributeName);
	}

	public Map<String, Object> getValues() {
		return ImmutableMap.copyOf(values);
	}

	@Override
	public String toString() {
		return "EntityInstance [entity=" + entity + ", values=" + values + ", id=" + id + "]";
	}

	public static class Builder {
		private EntityInstance entityInstance = new EntityInstance();

		public Builder id(String id) {
			entityInstance.id = id;
			return this;
		}

		public Builder id() {
			entityInstance.id = UUID.randomUUID().toString().replace("-", "");
			return this;
		}

		public Builder entity(MolgenisEntity entity) {
			entityInstance.entity = entity;
			return this;
		}

		public Builder values(Map<String, Object> values) {
			entityInstance.values = new LinkedHashMap<>(values);
			return this;
		}

		public Builder value(String attributeName, Object value) {
			entityInstance.values.put(attributeName, value);
			return this;
		}

		public EntityInstance build() {
			return entityInstance;
		}
	}
}
