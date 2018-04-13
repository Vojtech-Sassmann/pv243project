package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.AttributeDao;
import cz.fi.muni.TACOS.persistence.entity.Attribute;
import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.service.AbstractEntityService;
import cz.fi.muni.TACOS.service.AttributeService;
import cz.fi.muni.TACOS.service.events.AttributePriceChanged;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@ApplicationScoped
public class AttributeServiceImpl extends AbstractEntityService<Attribute> implements AttributeService {

    private final AttributeDao attributeDao;
    private final Event<AttributePriceChanged> attributePriceChangedEvent;

    @Inject
    public AttributeServiceImpl(AttributeDao attributeDao, Event<AttributePriceChanged> attributePriceChangedEvent) {
        super(attributeDao);

        this.attributeDao = attributeDao;
        this.attributePriceChangedEvent = attributePriceChangedEvent;
    }

    @Override
    public void create(Attribute entity) {
        super.create(entity);

        attributePriceChangedEvent.fire(new AttributePriceChanged(entity.getAttributeCategories().stream()
                .map(AttributeCategory::getId)
                .collect(Collectors.toSet())));
    }

    @Override
    public void delete(Attribute entity) {
        Set<Long> affectedCategories = entity.getAttributeCategories().stream()
                .map(AttributeCategory::getId)
                .collect(Collectors.toSet());
        super.delete(entity);
        attributePriceChangedEvent.fire(new AttributePriceChanged(affectedCategories));
    }

    @Override
    public void updateAttribute(Long id, Attribute newVersion) {
        Attribute attribute = attributeDao.findById(id);
        if (attribute == null) {
            throw new IllegalArgumentException("Attribute for given id does not exist.");
        }
        attribute.setDescription(newVersion.getDescription());
        if (newVersion.getName() != null) {
            attribute.setName(newVersion.getName());
        }
        if (newVersion.getPrice() != null && !newVersion.getPrice().equals(attribute.getPrice())) {
            attribute.setPrice(newVersion.getPrice());
            attributePriceChangedEvent.fire(new AttributePriceChanged(attribute.getAttributeCategories().stream()
                    .map(AttributeCategory::getId)
                    .collect(Collectors.toSet())));
        }
    }
}
