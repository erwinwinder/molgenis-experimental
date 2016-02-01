package org.molgenis.database.rest;

import java.util.Collections;
import java.util.Map;

import org.molgenis.database.domain.Project;

public class ProjectResponse {
	private Project project;
	private Map<String, String> links;

	public ProjectResponse(Project project, String selfUrl) {
		this.project = project;
		links = Collections.singletonMap("self", selfUrl);
	}

	public String getName() {
		return project.getName();
	}

	public String getDescription() {
		return project.getDescription();
	}

	public Map<String, String> getLinks() {
		return links;
	}

}
