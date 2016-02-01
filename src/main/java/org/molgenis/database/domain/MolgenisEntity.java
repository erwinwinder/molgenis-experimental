package org.molgenis.database.domain;

import java.util.List;

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
	@Column(nullable = false)
	private String name;

	@Lob
	private String description;

	@OrderBy
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "entity", fetch = FetchType.EAGER)
	private List<Attribute> attributes;

	@OneToOne
	private Attribute labelAttribute;

	protected MolgenisEntity() {
	}

	public MolgenisEntity(String name) {
		this.name = name;
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

	public Attribute getLabelAttribute() {
		return labelAttribute;
	}

}
