package org.molgenis.database.domain;

import static java.util.function.Function.identity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.collect.ImmutableList;

@Entity
public class MolgenisEntity extends AbstractJpaEntity {

	@NotNull
	@Size(max = 255)
	@Column(nullable = false, unique = true)
	private String name;

	@Lob
	private String description;

	@OrderBy
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "entity", fetch = FetchType.EAGER)
	private List<Attribute> attributes = new ArrayList<>();

	@OneToOne
	private Attribute labelAttribute;

	private transient Map<String, Attribute> attributeMap;

	protected MolgenisEntity() {
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<Attribute> getAttributes() {
		return ImmutableList.copyOf(attributes);
	}

	public Attribute getAttribute(String name) {
		if (attributeMap == null) {
			attributeMap = attributes.stream().collect(Collectors.toMap(Attribute::getName, identity()));
		}

		return attributeMap.get(name);
	}

	public Attribute getLabelAttribute() {
		return labelAttribute;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private MolgenisEntity entity = new MolgenisEntity();

		private Builder() {
		}

		public Builder name(String name) {
			entity.name = name;
			return this;
		}

		public Builder description(String description) {
			entity.description = description;
			return this;
		}

		public Builder attribute(Attribute attribute) {
			return attribute(attribute, false);
		}

		public Builder attribute(Attribute attribute, boolean labelAttribute) {
			entity.attributes.add(attribute);
			if (labelAttribute) {
				entity.labelAttribute = attribute;
			}
			attribute.setEntity(entity);
			return this;
		}

		public MolgenisEntity build() {
			return entity;
		}
	}
}
