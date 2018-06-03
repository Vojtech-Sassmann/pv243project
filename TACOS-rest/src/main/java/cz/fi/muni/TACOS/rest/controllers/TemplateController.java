package cz.fi.muni.TACOS.rest.controllers;

import cz.fi.muni.TACOS.dto.TemplateCreateDTO;
import cz.fi.muni.TACOS.dto.TemplateDTO;
import cz.fi.muni.TACOS.enums.UserRole;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;
import cz.fi.muni.TACOS.facade.TemplateFacade;
import cz.fi.muni.TACOS.rest.ApiUris;
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
@Path(ApiUris.URI_TEMPLATES)
@Stateless
public class TemplateController {

    private static final Logger log = LoggerFactory.getLogger(TemplateController.class);

    @Inject
    private TemplateFacade templateFacade;

    @POST
    @Secured(roles = {
            UserRole.SUPERADMIN,
            UserRole.PRACTITIONER
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Long createTemplate(TemplateCreateDTO specification) {
        log.debug("rest createTemplate({})", specification);

        try {
            return templateFacade.create(specification);
        } catch (InvalidRelationEntityIdException e) {
            throw new ResourceNotFoundException("Invalid relation ids given.", e);
        }
    }

    @DELETE
    @Secured(roles = {
            UserRole.SUPERADMIN,
            UserRole.PRACTITIONER,
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void deleteTemplate(@PathParam("id") Long id) {
        log.debug("rest deleteTemplate({})", id);

        TemplateDTO templateDTO = templateFacade.findById(id);

        if (templateDTO == null) {
            throw new ResourceNotFoundException("For given id does not exist any template. id: " + id);
        }

        templateFacade.delete(id);
    }

    @GET
    @Secured(roles = {
            UserRole.SUPERADMIN,
            UserRole.PRACTITIONER,
            UserRole.SUBMITTER
    })
    @Produces(MediaType.APPLICATION_JSON)
    public List<TemplateDTO> getAllTemplates() {
        log.debug("rest getAllTemplates()");

        return templateFacade.getAll();
    }

    @GET
    @Secured(roles = {
            UserRole.SUPERADMIN,
            UserRole.PRACTITIONER,
            UserRole.SUBMITTER
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public TemplateDTO getTemplateById(@PathParam("id") Long id) {
        log.debug("rest getTemplateById({})", id);

        TemplateDTO templateDTO = templateFacade.findById(id);

        if (templateDTO == null) {
            throw new ResourceNotFoundException("For given id does not exist any template. id: " + id);
        }

        return templateDTO;
    }
}
