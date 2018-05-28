package cz.fi.muni.TACOS.rest.controllers;

import cz.fi.muni.TACOS.dto.AttributeCategoryCreateDTO;
import cz.fi.muni.TACOS.dto.AttributeCategoryDTO;
import cz.fi.muni.TACOS.enums.UserRole;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;
import cz.fi.muni.TACOS.facade.AttributeCategoryFacade;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Path(ApiUris.URI_ATTRIBUTE_CATEGORIES)
@Stateless
public class AttributeCategoryController {

    private static final Logger log = LoggerFactory.getLogger(AttributeCategoryController.class);

    @Inject
    private AttributeCategoryFacade attributeCategoryFacade;

    @GET
    @Secured(roles = {
            UserRole.SUPERADMIN,
            UserRole.PRACTITIONER,
            UserRole.SUBMITTER
    })
    @Produces(MediaType.APPLICATION_JSON)
    public List<AttributeCategoryDTO> getAllAttributeCategories() {
        log.debug("rest getAllAttributeCategories()");

        return attributeCategoryFacade.getAll();
    }

    @GET
    @Secured(roles = {
            UserRole.SUPERADMIN,
            UserRole.PRACTITIONER,
            UserRole.SUBMITTER
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public AttributeCategoryDTO findAttributeCategoryById(@PathParam("id") Long id) {
        log.debug("rest findAttributeCategoryById({})", id);

        AttributeCategoryDTO attributeCategoryDTO = attributeCategoryFacade.findById(id);

        if (attributeCategoryDTO == null) {
            throw new ResourceNotFoundException("Attribute category for given id does not exist. id: " + id);
        }

        return attributeCategoryDTO;
    }
    @POST
    @Secured(roles = {
            UserRole.SUPERADMIN,
            UserRole.PRACTITIONER
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Long createAttributeCategory(AttributeCategoryCreateDTO specification) {
        log.debug("rest createAttributeCategory({})", specification);

        validateAttributeCategory(specification);

        try {
            return attributeCategoryFacade.create(specification);
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
    public void deleteAttributeCategory(@PathParam("id") Long id) {
        log.debug("rest deleteAttributeCategory({})", id);

        AttributeCategoryDTO categoryDTO = attributeCategoryFacade.findById(id);

        if (categoryDTO == null) {
            throw new ResourceNotFoundException("Attribute category for given id does not exist. id: " + id);
        }

        attributeCategoryFacade.delete(id);
    }

    private void validateAttributeCategory(AttributeCategoryCreateDTO specification) {
        if (specification.getName() == null || specification.getName().isEmpty()) {
            throw new InvalidParameterException("Attribute category needs to have proper name set.");
        }
    }
}
