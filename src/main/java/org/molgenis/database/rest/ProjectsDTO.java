package org.molgenis.database.rest;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.molgenis.database.domain.Project;

public class ProjectsDTO {
	private List<ProjectsDTO.ProjectResponse> projects;

	public ProjectsDTO(List<Project> projectEntities) {
		projects = projectEntities.stream().map(ProjectsDTO.ProjectResponse::new).collect(toList());
	}

	public List<ProjectsDTO.ProjectResponse> getProjects() {
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
