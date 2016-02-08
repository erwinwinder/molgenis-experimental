package org.molgenis.database.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.molgenis.database.rest.ProjectResource.PATH_PART_PROJECTS;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.molgenis.database.ProjectService;
import org.molgenis.database.domain.Project;

@Path("/" + PATH_PART_PROJECTS)
@Produces(APPLICATION_JSON)
public class ProjectResource {
	public static final String PATH_PART_PROJECTS = "projects";
	private ProjectService projectService;

	@Context
	UriInfo uri;

	@Inject
	public ProjectResource(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GET
	public ProjectsDTO findAll() {
		return new ProjectsDTO(projectService.findAll());
	}

	@GET
	@Path("{projectName}")
	public ProjectDTO getProject(@PathParam("projectName") String projectName) {
		Optional<Project> project = projectService.findByName(projectName);

		if (!project.isPresent()) {
			throw new NotFoundException("Project '" + projectName + " is not found");
		}

		String self = uri.getBaseUriBuilder().path(PATH_PART_PROJECTS).path(project.get().getName()).toString();

		return new ProjectDTO(project.get(), self);
	}
}
