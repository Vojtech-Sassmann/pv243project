package cz.fi.muni.TACOS.rest.controllers;

import cz.fi.muni.TACOS.dto.AttributeCreateDTO;
import cz.fi.muni.TACOS.dto.AttributeDTO;
import cz.fi.muni.TACOS.enums.UserRole;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;
import cz.fi.muni.TACOS.facade.AttributeFacade;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Path(ApiUris.URI_ATTRIBUTES)
@Stateless
public class AttributeController {

    private static final Logger log = LoggerFactory.getLogger(AttributeController.class);

    @Inject
    private AttributeFacade attributeFacade;

    @POST
    @Secured(roles = {
            UserRole.SUPERADMIN,
            UserRole.PRACTITIONER
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Long createAttribute(AttributeCreateDTO specification) {
        log.debug("Rest createAttribute({})", specification);

        validateAttribute(specification);

        try {
            return attributeFacade.create(specification);
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
    public void deleteAttribute(@PathParam("id") Long id) {
        log.debug("Rest deleteAttribute({})", id);

        AttributeDTO attributeDTO = attributeFacade.findById(id);

        if (attributeDTO == null) {
            throw new ResourceNotFoundException("Attribute for given id does not exist. id: " + id);
        }

        attributeFacade.delete(id);
    }

    @GET
    @Secured(roles = {
            UserRole.SUPERADMIN,
            UserRole.PRACTITIONER,
            UserRole.SUBMITTER
    })
    @Produces(MediaType.APPLICATION_JSON)
    public List<AttributeDTO> getAllAttributes() {
        log.debug("Rest getAllAttributes()");

        return attributeFacade.getAll();
    }

    @GET
    @Secured(roles = {
            UserRole.SUPERADMIN,
            UserRole.PRACTITIONER,
            UserRole.SUBMITTER
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public AttributeDTO findAttributeById(@PathParam("id") Long id) {
        log.debug("rest findAttributeById({})", id);

        AttributeDTO attributeDTO = attributeFacade.findById(id);

        if (attributeDTO == null) {
            throw new ResourceNotFoundException("Attribute for given id does not exist.");
        }

        return attributeDTO;
    }

    private void validateAttribute(AttributeCreateDTO specification) {
        if (specification.getName() == null || specification.getName().isEmpty()) {
            throw new InvalidParameterException("Attribute name needs to be set.");
        }
        if (specification.getPrice() == null) {
            throw new InvalidParameterException("Attribute price can not be null.");
        }
        if (specification.getPrice().compareTo(BigDecimal.ONE) < 0) {
            throw new InvalidParameterException("Attribute price can not be less then 0");
        }
    }
}
