package org.molgenis.database.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.molgenis.database.rest.MolgenisEntityResource.PATH_PART_ENTITIES;

import java.net.URI;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.molgenis.database.DataService;
import org.molgenis.database.EntityAlreadyExistsException;
import org.molgenis.database.domain.MolgenisEntity;

@Path("/" + PATH_PART_ENTITIES)
@Produces(APPLICATION_JSON)
public class MolgenisEntityResource {
	public static final String PATH_PART_ENTITIES = "entities";
	public static final String ENTITY_PATH_TEMPLATE = "{entityName}";
	private DataService dataService;

	@Context
	UriInfo uri;

	@Inject
	public MolgenisEntityResource(DataService dataService) {
		this.dataService = dataService;
	}

	@GET
	@Path(ENTITY_PATH_TEMPLATE)
	public MolgenisEntityDTO getEntity(@PathParam("entityName") String entityName) {
		Optional<MolgenisEntity> entity = dataService.findEntityByName(entityName);

		if (!entity.isPresent()) {
			throw new NotFoundException("Entity '" + entityName + " is not found");
		}

		String self = uri.getBaseUriBuilder().path(PATH_PART_ENTITIES).path(ENTITY_PATH_TEMPLATE).build(entityName)
				.toString();

		return new MolgenisEntityDTO(entity.get(), self);
	}

	@POST
	@Consumes(APPLICATION_JSON)
	public Response addEntity(MolgenisEntityDTO entityDTO) {

		try {
			dataService.addEntity(entityDTO.toMolgenisEntity());
			URI location = uri.getBaseUriBuilder().path(PATH_PART_ENTITIES).path(ENTITY_PATH_TEMPLATE)
					.build(entityDTO.getName());

			return Response.created(location).build();
		} catch (EntityAlreadyExistsException e) {
			throw new ClientErrorException(e.getMessage(), Response.Status.CONFLICT);
		}
	}
}
