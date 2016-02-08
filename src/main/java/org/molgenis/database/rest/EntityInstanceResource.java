package org.molgenis.database.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.molgenis.database.rest.EntityInstanceResource.PATH;
import static org.molgenis.database.rest.MolgenisEntityResource.ENTITY_PATH_TEMPLATE;
import static org.molgenis.database.rest.MolgenisEntityResource.PATH_PART_ENTITIES;

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
import org.molgenis.database.UnknownMolgenisEntityException;
import org.molgenis.database.domain.EntityInstance;

@Path(PATH)
@Produces(APPLICATION_JSON)
public class EntityInstanceResource {
	public static final String PATH = "/" + PATH_PART_ENTITIES + "/" + ENTITY_PATH_TEMPLATE + "/instances";
	public static final String INSTANCE_PATH_TEMPLATE = "{entityInstanceId}";
	private DataService dataService;

	@Context
	UriInfo uri;

	@Inject
	public EntityInstanceResource(DataService dataService) {
		this.dataService = dataService;
	}

	@GET
	@Path(INSTANCE_PATH_TEMPLATE)
	public void findInstance(@PathParam("entityName") String entityName,
			@PathParam("entityInstanceId") String entityInstanceId) {

		try {
			Optional<EntityInstance> entityInstance = dataService.findEntityInstance(entityName, entityInstanceId);
			if (!entityInstance.isPresent()) {
				throw new NotFoundException("No instance of entity '" + entityName + "' with id '" + entityInstanceId
						+ "' found.");
			}

		} catch (UnknownMolgenisEntityException e) {
			throw new NotFoundException(e.getMessage());
		}

		System.out.println(entityName);
	}
}
