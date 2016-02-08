package org.molgenis.database.rest;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.molgenis.database.domain.Attribute;
import org.molgenis.database.domain.DataType;
import org.molgenis.database.domain.MolgenisEntity;

public class MolgenisEntityDTO {
	private String name;
	private String description;
	private Map<String, String> links;
	private List<AttributeDTO> attributes;

	protected MolgenisEntityDTO() {
	}

	public MolgenisEntityDTO(MolgenisEntity entity, String selfUrl) {
		name = entity.getName();
		description = entity.getDescription();
		links = Collections.singletonMap("self", selfUrl);
		attributes = entity.getAttributes().stream().map(AttributeDTO::new).collect(toList());
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Map<String, String> getLinks() {
		return links;
	}

	public List<AttributeDTO> getAttributes() {
		return attributes;
	}

	public MolgenisEntity toMolgenisEntity() {
		MolgenisEntity.Builder builder = MolgenisEntity.builder().name(name).description(description);
		attributes.stream().map(AttributeDTO::toAttribute).forEach(builder::attribute);

		return builder.build();
	}

	@Override
	public String toString() {
		return "EntityResponse [name=" + name + ", description=" + description + ", links=" + links + ", attributes="
				+ attributes + "]";
	}

	public static class AttributeDTO {
		private String name;
		private String description;
		private DataType dataType;
		private boolean nillable;
		private boolean unique;

		protected AttributeDTO() {
		}

		public AttributeDTO(Attribute attribute) {
			name = attribute.getName();
			description = attribute.getDescription();
			dataType = attribute.getDataType();
			nillable = attribute.isNillable();
			unique = attribute.isUnique();
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public DataType getDataType() {
			return dataType;
		}

		public boolean isNillable() {
			return nillable;
		}

		public boolean isUnique() {
			return unique;
		}

		public Attribute toAttribute() {
			return Attribute.builder().name(name).description(description).dataType(dataType).nillable(nillable)
					.unique(unique).build();
		}

		@Override
		public String toString() {
			return "AttributeResponse [name=" + name + ", description=" + description + ", dataType=" + dataType
					+ ", nillable=" + nillable + ", unique=" + unique + "]";
		}

	}
}
