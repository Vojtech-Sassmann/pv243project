package cz.fi.muni.TACOS.facade.impl;

import cz.fi.muni.TACOS.dto.TemplateCreateDTO;
import cz.fi.muni.TACOS.dto.TemplateDTO;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;
import cz.fi.muni.TACOS.facade.TemplateFacade;
import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.persistence.entity.Template;
import cz.fi.muni.TACOS.service.AttributeCategoryService;
import cz.fi.muni.TACOS.service.BeanMappingService;
import cz.fi.muni.TACOS.persistence.entity.Product;
import cz.fi.muni.TACOS.service.ProductService;
import cz.fi.muni.TACOS.service.TemplateService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of TemplateFacade Interface
 *
 * @author Peter Balcirak <peter.balcirak@gmail.com>
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Transactional(rollbackOn = Exception.class)
@ApplicationScoped
public class TemplateFacadeImpl implements TemplateFacade {

    private final TemplateService templateService;

    private final AttributeCategoryService attributeCategoryService;

    private final ProductService productService;

    private final BeanMappingService beanMappingService;

    @Inject
    public TemplateFacadeImpl(TemplateService templateService, AttributeCategoryService attributeCategoryService,
                ProductService productService, BeanMappingService beanMappingService) {
        this.templateService = templateService;
        this.productService = productService;
        this.beanMappingService = beanMappingService;
        this.attributeCategoryService = attributeCategoryService;
    }

    @Override
    public void addAttributeCategory(Long templateId, Long categoryId) {
        templateService.addAttributeCategory(templateService.findById(templateId), attributeCategoryService.findById(categoryId));
    }

    @Override
    public void removeAttributeCategory(Long templateId, Long categoryId) {
        templateService.removeAttributeCategory(templateService.findById(templateId), attributeCategoryService.findById(categoryId));
    }

    @Override
    public Long create(TemplateCreateDTO entity) throws InvalidRelationEntityIdException {
        Template template = beanMappingService.mapTo(entity, Template.class);
        templateService.create(template);

        for (Long id : entity.getProductIds()) {
            Product product = productService.findById(id);
            if (product == null) {
                throw new InvalidRelationEntityIdException("Product for given id does not exist. id: " + id);
            }
            productService.addTemplate(product, template);
        }

        for (Long id : entity.getAttributeCategoryIds()) {
            AttributeCategory attributeCategory = attributeCategoryService.findById(id);
            if (attributeCategory == null) {
                throw new InvalidRelationEntityIdException("Attribute category for given id does not exist. id: " + id);
            }
            templateService.addAttributeCategory(template, attributeCategory);
        }

        return template.getId();
    }

    @Override
    public void delete(Long id) {
        templateService.delete(templateService.findById(id));
    }

    @Override
    public TemplateDTO findById(Long id) {
        return beanMappingService.mapTo(templateService.findById(id), TemplateDTO.class);
    }

    @Override
    public List<TemplateDTO> getAll() {
        return beanMappingService.mapTo(templateService.getAll(), TemplateDTO.class);
    }
}
