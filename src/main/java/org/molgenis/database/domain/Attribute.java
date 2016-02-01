package org.molgenis.database.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name", "entity_id" }))
public class Attribute extends AbstractJpaEntity {

	@NotNull
	@Size(max = 255)
	@Column(nullable = false)
	private String name;

	@NotNull
	@OneToOne(optional = false)
	private MolgenisEntity entity;

	@Lob
	private String description;

	@NotNull
	@Column(nullable = false)
	private DataType dataType = DataType.STRING;

	private boolean nillable = false;
	private boolean uniqueValues = false;

	@OrderColumn
	private int attributeOrder = 0;

	public static Builder builder() {
		return new Attribute.Builder();
	}

	protected Attribute() {
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
		return uniqueValues;
	}

	public int getOrder() {
		return attributeOrder;
	}

	public MolgenisEntity getEntity() {
		return entity;
	}

	public static class Builder {
		Attribute attribute = new Attribute();

		private Builder() {
		}

		public Builder name(String name) {
			attribute.name = name;
			return this;
		}

		public Builder entity(MolgenisEntity entity) {
			attribute.entity = entity;
			return this;
		}

		public Builder description(String description) {
			attribute.description = description;
			return this;
		}

		public Builder dataType(DataType dataType) {
			attribute.dataType = dataType;
			return this;
		}

		public Builder nillable(boolean nillable) {
			attribute.nillable = nillable;
			return this;
		}

		public Builder unique(boolean unique) {
			attribute.uniqueValues = unique;
			return this;
		}

		public Builder order(int order) {
			attribute.attributeOrder = order;
			return this;
		}

		public Attribute build() {
			return attribute;
		}
	}

}
