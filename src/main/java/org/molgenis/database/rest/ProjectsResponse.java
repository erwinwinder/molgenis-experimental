package org.molgenis.database.rest;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.molgenis.database.domain.Project;

public class ProjectsResponse {
	private List<ProjectsResponse.ProjectResponse> projects;

	public ProjectsResponse(List<Project> projectEntities) {
		projects = projectEntities.stream().map(ProjectsResponse.ProjectResponse::new).collect(toList());
	}

	public List<ProjectsResponse.ProjectResponse> getProjects() {
		return projects;
	}

	protected static class ProjectResponse {
		private Project project;

		public ProjectResponse(Project project) {
			this.project = project;
		}

		public String getName() {
			return project.getName();
		}

		public String getDescription() {
			return project.getDescription();
		}
	}
}
