package org.molgenis.database.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Project extends AbstractJpaEntity {

	@NotNull
	@Size(max = 255)
	@Column(nullable = false, unique = true)
	private String name;

	@Lob
	private String description;

	public static Project.Builder builder() {
		return new Project.Builder();
	}

	protected Project() {
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public static class Builder {
		private Project project = new Project();

		private Builder() {
		}

		public Builder name(String name) {
			project.name = name;
			return this;
		}

		public Builder description(String description) {
			project.description = description;
			return this;
		}

		public Project build() {
			return project;
		}
	}
}
