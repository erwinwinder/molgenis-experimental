package org.molgenis.database.rest;

import static org.molgenis.database.rest.ProjectResource.PATH_PART_PROJECTS;
import static org.molgenis.database.rest.RestUtils.CONTENT_TYPE_API_V1;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.molgenis.database.DataService;
import org.molgenis.database.ProjectService;
import org.molgenis.database.domain.MolgenisEntity;
import org.molgenis.database.domain.Project;

@Path("/" + PATH_PART_PROJECTS)
@Produces(CONTENT_TYPE_API_V1)
public class ProjectResource {
	public static final String PATH_PART_PROJECTS = "projects";
	private ProjectService projectService;
	private DataService dataService;

	@Context
	UriInfo uri;

	@Inject
	public ProjectResource(ProjectService projectService, DataService dataService) {
		this.projectService = projectService;
		this.dataService = dataService;
	}

	@GET
	public ProjectsResponse findAll() {
		// Project p =
		// Project.builder().name("testje").description("A test project").build();
		// this.projectService.add(p);

		// entityInstanceRepository.createRepository("testje4");

		MolgenisEntity entity = new MolgenisEntity("theentity");
		dataService.addRepository(entity);

		// EntityInstance instance =
		// EntityInstance.builder().entity(entity).id().value("test",
		// 1).build();
		// entityInstanceRepository.add(instance);

		return new ProjectsResponse(projectService.findAll());
	}

	@GET
	@Path("{projectName}")
	public ProjectResponse getProject(@PathParam("projectName") String projectName) {
		Optional<Project> project = projectService.findByName(projectName);

		if (!project.isPresent()) {
			throw new NotFoundException("Project '" + projectName + " is not found");
		}

		String self = uri.getBaseUriBuilder().path(PATH_PART_PROJECTS).path(project.get().getName()).toString();

		return new ProjectResponse(project.get(), self);
	}
}
