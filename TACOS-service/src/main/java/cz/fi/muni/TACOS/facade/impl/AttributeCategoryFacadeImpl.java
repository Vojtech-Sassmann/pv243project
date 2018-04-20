package cz.fi.muni.TACOS.facade.impl;

import cz.fi.muni.TACOS.dto.AttributeCategoryCreateDTO;
import cz.fi.muni.TACOS.dto.AttributeCategoryDTO;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;
import cz.fi.muni.TACOS.facade.AttributeCategoryFacade;
import cz.fi.muni.TACOS.persistence.entity.Attribute;
import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.persistence.entity.Template;
import cz.fi.muni.TACOS.service.AttributeCategoryService;
import cz.fi.muni.TACOS.service.AttributeService;
import cz.fi.muni.TACOS.service.BeanMappingService;
import cz.fi.muni.TACOS.service.TemplateService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of AttributeCategoryFacade Interface
 *
 * @author Peter Balcirak <peter.balcirak@gmail.com>
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Transactional(rollbackOn = { Exception.class })
@ApplicationScoped
public class AttributeCategoryFacadeImpl implements AttributeCategoryFacade {

    private final AttributeCategoryService attributeCategoryService;

    private final AttributeService attributeService;

    private final BeanMappingService beanMappingService;

    private final TemplateService templateService;

    @Inject
    public AttributeCategoryFacadeImpl(AttributeCategoryService attributeCategoryService, AttributeService attributeService,
                                       BeanMappingService beanMappingService, TemplateService templateService) {
        this.attributeCategoryService = attributeCategoryService;
        this.beanMappingService = beanMappingService;
        this.templateService = templateService;
        this.attributeService = attributeService;
    }

    @Override
    public void addAttribute(Long categoryId, Long attributeId) {
        attributeCategoryService.addAttribute(attributeCategoryService.findById(categoryId), attributeService.findById(attributeId));
    }

    @Override
    public void removeAttribute(Long categoryId, Long attributeId) {
        attributeCategoryService.removeAttribute(attributeCategoryService.findById(categoryId), attributeService.findById(attributeId));
    }

    @Override
    public Long create(AttributeCategoryCreateDTO entity) throws InvalidRelationEntityIdException {
        AttributeCategory attributeCategory = beanMappingService.mapTo(entity, AttributeCategory.class);
        attributeCategoryService.create(attributeCategory);

        for (Long templateId : entity.getTemplateIds()) {
            Template template = templateService.findById(templateId);
            if (template == null) {
                throw new InvalidRelationEntityIdException("Template for given id does not exist. id: " + templateId);
            }
            templateService.addAttributeCategory(template, attributeCategory);
        }

        for (Long attributeId : entity.getAttributeIds()) {
            Attribute attribute = attributeService.findById(attributeId);
            if (attribute == null) {
                throw new InvalidRelationEntityIdException("Attribute for given id does not exist. id: " + attributeId);
            }
            attributeCategoryService.addAttribute(attributeCategory, attribute);
        }

        return attributeCategory.getId();
    }

    @Override
    public void delete(Long id) {
        attributeCategoryService.delete(attributeCategoryService.findById(id));
    }

    @Override
    public AttributeCategoryDTO findById(Long id) {
        return beanMappingService.mapTo(attributeCategoryService.findById(id), AttributeCategoryDTO.class);
    }

    @Override
    public List<AttributeCategoryDTO> getAll() {
        return beanMappingService.mapTo(attributeCategoryService.getAll(), AttributeCategoryDTO.class);
    }
}
