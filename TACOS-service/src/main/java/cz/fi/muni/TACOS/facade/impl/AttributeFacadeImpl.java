package cz.fi.muni.TACOS.facade.impl;

import cz.fi.muni.TACOS.dto.AttributeCreateDTO;
import cz.fi.muni.TACOS.dto.AttributeDTO;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;
import cz.fi.muni.TACOS.facade.AttributeFacade;
import cz.fi.muni.TACOS.persistence.entity.Attribute;
import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.service.AttributeCategoryService;
import cz.fi.muni.TACOS.service.AttributeService;
import cz.fi.muni.TACOS.service.BeanMappingService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Transactional(rollbackOn = { Exception.class })
@ApplicationScoped
public class AttributeFacadeImpl implements AttributeFacade {

    private final AttributeService attributeService;

    private final AttributeCategoryService attributeCategoryService;

    private final BeanMappingService beanMappingService;

    @Inject
    public AttributeFacadeImpl(AttributeService attributeService, AttributeCategoryService attributeCategoryService,
                               BeanMappingService beanMappingService) {
        this.attributeService = attributeService;
        this.attributeCategoryService = attributeCategoryService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public Long create(AttributeCreateDTO attributeCreateDTO) throws InvalidRelationEntityIdException {
        Attribute attribute = beanMappingService.mapTo(attributeCreateDTO, Attribute.class);
        attributeService.create(attribute);

        for (Long attributeCategoryId : attributeCreateDTO.getAttributeCategoryIds()) {
            AttributeCategory attributeCategory = attributeCategoryService.findById(attributeCategoryId);
            if (attributeCategory == null) {
                throw new InvalidRelationEntityIdException("Attribute category for given id does not exist. id: " + attributeCategoryId);
            }
            attributeCategoryService.addAttribute(attributeCategory, attribute);
        }

        return attribute.getId();
    }

    @Override
    public void delete(Long id) {
        Attribute attribute = attributeService.findById(id);

        attributeService.delete(attribute);
    }

    @Override
    public AttributeDTO findById(Long id) {
        return beanMappingService.mapTo(attributeService.findById(id), AttributeDTO.class);
    }

    @Override
    public List<AttributeDTO> getAll() {
        return beanMappingService.mapTo(attributeService.getAll(), AttributeDTO.class);
    }
}
