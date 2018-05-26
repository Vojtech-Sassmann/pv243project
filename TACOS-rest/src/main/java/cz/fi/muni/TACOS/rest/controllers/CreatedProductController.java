package cz.fi.muni.TACOS.rest.controllers;

import cz.fi.muni.TACOS.dto.CreatedProductCreateDTO;
import cz.fi.muni.TACOS.dto.CreatedProductDTO;
import cz.fi.muni.TACOS.dto.UserCreateDTO;
import cz.fi.muni.TACOS.dto.UserDTO;
import cz.fi.muni.TACOS.enums.UserRole;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;
import cz.fi.muni.TACOS.facade.AttributeFacade;
import cz.fi.muni.TACOS.facade.CreatedProductFacade;
import cz.fi.muni.TACOS.facade.UserFacade;
import cz.fi.muni.TACOS.rest.ApiUris;
import cz.fi.muni.TACOS.rest.exceptions.InvalidParameterException;
import cz.fi.muni.TACOS.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Peter Balcirak <peter.balcirak@gmail.com>
 */
@Path(ApiUris.URI_CREATED_PRODUCTS)
@Stateless
public class CreatedProductController {

	private static final Logger log = LoggerFactory.getLogger(AttributeCategoryController.class);

	@Inject
	private UserFacade userFacade;

	@Inject
	private CreatedProductFacade createdProductFacade;

	@Inject
	private AttributeFacade attributeFacade;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CreatedProductDTO> getAllCreatedProducts() {
		log.debug("rest getAllCreatedProducts()");

		return createdProductFacade.getAll();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public CreatedProductDTO findCreatedProductById(@PathParam("id") Long id) {
		log.debug("rest findCreatedProductById({})", id);

		CreatedProductDTO createdProductDTO = createdProductFacade.findById(id);

		if (createdProductDTO == null) {
			throw new ResourceNotFoundException("Created product for given id does not exist. id: " + id);
		}

		return createdProductDTO;
	}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Long createCreatedProduct(CreatedProductCreateDTO specification) {
		log.debug("rest createCreatedProduct({})", specification);

		validateCreatedProduct(specification);

		//TODO : REMOVE THIS WHEN SECURITY IS IMPLEMENTED
		UserDTO fakeUser = userFacade.findByEmail("v.sassmann@gmail.com");
		if (fakeUser == null) {
			UserCreateDTO fakeUserCreate = new UserCreateDTO();
			fakeUserCreate.setEmail("v.sassmann@gmail.com");
			fakeUserCreate.setName("fake");
			fakeUserCreate.setSurname("user");
			fakeUserCreate.setRole(UserRole.SUPERADMIN);
			fakeUserCreate.setPassword("fakeuser");

			userFacade.create(fakeUserCreate);
			fakeUser = userFacade.findByEmail("v.sassmann@gmail.com");
		}

		try {
			return createdProductFacade.create(specification, fakeUser.getId());
		} catch (InvalidRelationEntityIdException e) {
			throw new InvalidParameterException(e);
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public void deleteCreatedProduct(@PathParam("id") Long id) {
		log.debug("rest deleteCreatedProduct({})", id);

		CreatedProductDTO createdProductDTO = createdProductFacade.findById(id);

		if (createdProductDTO == null) {
			throw new ResourceNotFoundException("Created product for given id does not exist. id: " + id);
		}

		createdProductFacade.delete(id);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{createdProductId}/addAttribute/{attributeId}")
	public void addAttribute(@PathParam("createdProductId") Long createdProductId, @QueryParam("attributeId") Long attributeId) {
		log.debug("Rest addAttribute(created product id: {}, attribute id: {})", createdProductId, attributeId);

		if (createdProductFacade.findById(createdProductId) == null) {
			throw new ResourceNotFoundException("Created product with id "+ createdProductId +" not found!");
		}
		if (attributeFacade.findById(attributeId) == null) {
			throw new ResourceNotFoundException("Attribute with id "+ attributeId +" not found!");
		}

		createdProductFacade.addAttribute(createdProductId, attributeId);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{createdProductId}/removeAttribute/{attributeId}")
	public void removeAttribute(@PathParam("createdProductId") Long createdProductId, @QueryParam("attributeId") Long attributeId) {
		log.debug("Rest removeAttribute(created product id: {}, attribute id: {})", createdProductId, attributeId);

		if (createdProductFacade.findById(createdProductId) == null) {
			throw new ResourceNotFoundException("Created product with id "+ createdProductId +" not found!");
		}
		if (attributeFacade.findById(attributeId) == null) {
			throw new ResourceNotFoundException("Attribute with id "+ attributeId +" not found!");
		}

		createdProductFacade.removeAttribute(createdProductId, attributeId);
	}

	private void validateCreatedProduct(CreatedProductCreateDTO specification) {
		if (specification.getCount() == null || specification.getCount() < 0L) {
			throw new InvalidParameterException("Created product needs to have proper count set.");
		}
		if (specification.getDescription() == null) {
			throw new InvalidParameterException("Created product cannot has null description.");
		}

		if (specification.getProductId() == null) {
			throw new InvalidParameterException("Created product cannot has null product id.");
		}
	}

}
