package cz.fi.muni.TACOS.rest.controllers;

import cz.fi.muni.TACOS.dto.ProductCategoryCreateDTO;
import cz.fi.muni.TACOS.dto.ProductCategoryDTO;
import cz.fi.muni.TACOS.enums.UserRole;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;
import cz.fi.muni.TACOS.facade.ProductCategoryFacade;
import cz.fi.muni.TACOS.facade.ProductFacade;
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
@Path(ApiUris.URI_PRODUCT_CATEGORIES)
@Stateless
public class ProductCategoryController {

	private static final Logger log = LoggerFactory.getLogger(AttributeCategoryController.class);

	@Inject
	private ProductCategoryFacade productCategoryFacade;

	@Inject
	private ProductFacade productFacade;

	@GET
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
			UserRole.SUBMITTER
	})
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductCategoryDTO> getAllProductCategories() {
		log.debug("rest getAllProductCategories()");

		return productCategoryFacade.getAll();
	}

	@GET
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
			UserRole.SUBMITTER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/filter/leaf")
	public List<ProductCategoryDTO> getLeafProductCategories() {
		log.debug("rest getLeafProductCategories()");

		return productCategoryFacade.getLeafCategories();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public ProductCategoryDTO findProductCategoryById(@PathParam("id") Long id) {
		log.debug("rest findProductCategoryById({})", id);

		ProductCategoryDTO productCategoryDTO = productCategoryFacade.findById(id);

		if (productCategoryDTO == null) {
			throw new ResourceNotFoundException("Product category for given id does not exist. id: " + id);
		}

		return productCategoryDTO;
	}

	@POST
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Long createProductCategory(ProductCategoryCreateDTO specification) {
		log.debug("rest createProductCategory({})", specification);

		validateProductCategory(specification);

		try {
			return productCategoryFacade.create(specification);
		} catch (InvalidRelationEntityIdException e) {
			throw new InvalidParameterException(e);
		}
	}

	@DELETE
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public void deleteProductCategory(@PathParam("id") Long id) {
		log.debug("rest deleteProductCategory({})", id);

		ProductCategoryDTO productCategoryDTO = productCategoryFacade.findById(id);

		if (productCategoryDTO == null) {
			throw new ResourceNotFoundException("Product category for given id does not exist. id: " + id);
		}

		productCategoryFacade.delete(id);
	}

	@PUT
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{categoryId}/addSubCategory/{subCategoryId}")
	public void addSubCategory(@PathParam("categoryId") Long categoryId, @QueryParam("subCategoryId") Long subCategoryId) {
		log.debug("Rest addSubCategory (category: {}, subCategory: {})", categoryId, subCategoryId);

		if (productCategoryFacade.findById(categoryId) == null) {
			throw new ResourceNotFoundException("Category with id "+ categoryId +" not found!");
		}
		if (productCategoryFacade.findById(subCategoryId) == null) {
			throw new ResourceNotFoundException("SubCategory with id "+ subCategoryId +" not found!");
		}

		productCategoryFacade.addSubCategory(categoryId, subCategoryId);
	}

	@PUT
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{categoryId}/removeSubCategory/{subCategoryId}")
	public void removeSubCategory(@PathParam("categoryId") Long categoryId, @QueryParam("subCategoryId") Long subCategoryId) {
		log.debug("Rest removeSubCategory (category: {}, subCategory: {})", categoryId, subCategoryId);

		if (productCategoryFacade.findById(categoryId) == null) {
			throw new ResourceNotFoundException("Category with id "+ categoryId +" not found!");
		}
		if (productCategoryFacade.findById(subCategoryId) == null) {
			throw new ResourceNotFoundException("SubCategory with id "+ subCategoryId +" not found!");
		}

		productCategoryFacade.removeSubCategory(categoryId, subCategoryId);
	}

	@PUT
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{categoryId}/addProduct/{productId}")
	public void addProduct(@PathParam("categoryId") Long categoryId, @QueryParam("productId") Long productId) {
		log.debug("Rest addProduct (category: {}, product: {})", categoryId, productId);

		if (productCategoryFacade.findById(categoryId) == null) {
			throw new ResourceNotFoundException("Category with id "+ categoryId +" not found!");
		}
		if (productFacade.findById(productId) == null) {
			throw new ResourceNotFoundException("Product with id "+ productId +" not found!");
		}

		productCategoryFacade.addProductToCategory(categoryId, productId);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Path("/{categoryId}/removeProduct/{productId}")
	public void removeProduct(@PathParam("categoryId") Long categoryId, @QueryParam("productId") Long productId) {
		log.debug("Rest removeProduct (category: {}, product: {})", categoryId, productId);

		if (productCategoryFacade.findById(categoryId) == null) {
			throw new ResourceNotFoundException("Category with id "+ categoryId +" not found!");
		}
		if (productFacade.findById(productId) == null) {
			throw new ResourceNotFoundException("Product with id "+ productId +" not found!");
		}

		productCategoryFacade.removeProductFromCategory(categoryId, productId);
	}

	private void validateProductCategory(ProductCategoryCreateDTO specification) {
		if (specification.getName() == null || specification.getName().isEmpty()) {
			throw new InvalidParameterException("Product category needs to have proper name set.");
		}
	}
}
