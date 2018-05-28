package cz.fi.muni.TACOS.rest.controllers;

import cz.fi.muni.TACOS.dto.ProductCreateDTO;
import cz.fi.muni.TACOS.dto.ProductDTO;
import cz.fi.muni.TACOS.enums.UserRole;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;
import cz.fi.muni.TACOS.facade.ProductFacade;
import cz.fi.muni.TACOS.facade.TemplateFacade;
import cz.fi.muni.TACOS.rest.ApiUris;
import cz.fi.muni.TACOS.rest.exceptions.InvalidParameterException;
import cz.fi.muni.TACOS.rest.exceptions.ResourceNotFoundException;
import cz.fi.muni.TACOS.rest.interceptors.Secured;
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
@Path(ApiUris.URI_PRODUCTS)
@Stateless
public class ProductController {


	private static final Logger log = LoggerFactory.getLogger(AttributeController.class);

	@Inject
	private ProductFacade productFacade;

	@Inject
	private TemplateFacade templateFacade;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
	})
	public Long createProduct(ProductCreateDTO specification) {
		log.debug("Rest createProduct({})", specification);

		validateProduct(specification);

		try {
			return productFacade.create(specification);
		} catch (InvalidRelationEntityIdException e) {
			throw new InvalidParameterException(e);
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
	})
	public void deleteProduct(@PathParam("id") Long id) {
		log.debug("Rest deleteProduct({})", id);

		ProductDTO productDTO = productFacade.findById(id);

		if (productDTO == null) {
			throw new ResourceNotFoundException("Product for given id does not exist. id: " + id);
		}

		productFacade.delete(id);
	}

	@GET
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
			UserRole.SUBMITTER
	})
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductDTO> getAllProducts() {
		log.debug("Rest getAllProducts()");

		return productFacade.getAll();
	}

	@GET
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
			UserRole.SUBMITTER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public ProductDTO findProductById(@PathParam("id") Long id) {
		log.debug("rest findProductById({})", id);

		ProductDTO productDTO = productFacade.findById(id);

		if (productDTO == null) {
			throw new ResourceNotFoundException("Product for given id does not exist.");
		}

		return productDTO;
	}

	@PUT
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{productId}/addTemplate/{templateId}")
	public void addTemplate(@PathParam("productId") Long productId, @QueryParam("templateId") Long templateId) {
		log.debug("Rest addTemplate(product: {productId}, template: {templateId})", productId, templateId);

		if (productFacade.findById(productId) == null) {
			throw new ResourceNotFoundException("Product with id "+ productId +" not found!");
		}
		if (templateFacade.findById(templateId) == null) {
			throw new ResourceNotFoundException("Template with id "+ templateId +" not found!");
		}

		productFacade.addTemplate(productId, templateId);
	}

	@PUT
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{productId}/removeTemplate/{templateId}")
	public void removeTemplate(@PathParam("productId") Long productId, @QueryParam("templateId") Long templateId) {
		log.debug("Rest removeTemplate(product: {productId}, template: {templateId})", productId, templateId);

		if (productFacade.findById(productId) == null) {
			throw new ResourceNotFoundException("Product with id "+ productId +" not found!");
		}
		if (templateFacade.findById(templateId) == null) {
			throw new ResourceNotFoundException("Template with id "+ templateId +" not found!");
		}

		productFacade.removeTemplate(productId, templateId);
	}

	private void validateProduct(ProductCreateDTO specification) {
		if (specification.getName() == null || specification.getName().isEmpty()) {
			throw new InvalidParameterException("Product name needs to be set.");
		}
		if (specification.getDescription() == null) {
			throw new InvalidParameterException("Product description cannot be null");
		}
	}
}
