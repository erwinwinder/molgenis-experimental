package org.molgenis.database.rest;

import java.util.Collections;
import java.util.Map;

import org.molgenis.database.domain.Project;

public class ProjectDTO {
	private String name;
	private String description;
	private Map<String, String> links;

	public ProjectDTO(Project project, String selfUrl) {
		this.name = project.getName();
		this.description = project.getDescription();
		links = Collections.singletonMap("self", selfUrl);
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

}
