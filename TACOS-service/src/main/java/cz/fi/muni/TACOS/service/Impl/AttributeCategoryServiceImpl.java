package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.AttributeCategoryDao;
import cz.fi.muni.TACOS.persistence.entity.Attribute;
import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.persistence.entity.Template;
import cz.fi.muni.TACOS.service.AbstractEntityService;
import cz.fi.muni.TACOS.service.AttributeCategoryService;
import cz.fi.muni.TACOS.service.events.AttributeCategoryPriceChanged;
import cz.fi.muni.TACOS.service.events.AttributePriceChanged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@ApplicationScoped
public class AttributeCategoryServiceImpl extends AbstractEntityService<AttributeCategory> implements AttributeCategoryService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AttributeCategoryDao attributeCategoryDao;
    private final Event<AttributeCategoryPriceChanged> attributeCategoryPriceChangedEvent;

    @Inject
    public AttributeCategoryServiceImpl(AttributeCategoryDao attributeCategoryDao,
                                        Event<AttributeCategoryPriceChanged> attributeCategoryPriceChangedEvent) {
        super(attributeCategoryDao);

        this.attributeCategoryDao = attributeCategoryDao;
        this.attributeCategoryPriceChangedEvent = attributeCategoryPriceChangedEvent;
    }

    @Override
    public void create(AttributeCategory entity) {
        super.create(entity);

        attributeCategoryPriceChangedEvent.fire(new AttributeCategoryPriceChanged(entity.getTemplates().stream()
                .map(Template::getId)
                .collect(Collectors.toSet())));
    }

    @Override
    public void delete(AttributeCategory entity) {
        Set<Long> affectedTemplates = entity.getTemplates().stream()
                .map(Template::getId)
                .collect(Collectors.toSet());

        super.delete(entity);

        attributeCategoryPriceChangedEvent.fire(new AttributeCategoryPriceChanged(affectedTemplates));
    }

    @Override
    public void addAttribute(AttributeCategory category, Attribute attribute) {
        category.addAttribute(attribute);
        recalculatePrice(category, true);
    }

    @Override
    public void removeAttribute(AttributeCategory category, Attribute attribute) {
        category.removeAttribute(attribute);
        recalculatePrice(category, true);
    }

    private Set<AttributeCategory> getCategoriesById(Set<Long> ids) {
        Set<AttributeCategory> categories = new HashSet<>();
        for (Long id : ids) {
            categories.add(findById(id));
        }
        return categories;
    }

    private void recalculatePrices(@Observes AttributePriceChanged event) {
        Set<AttributeCategory> categories = getCategoriesById(event.getAffectedCategories());
        Set<Template> affectedTemplates = new HashSet<>();
        log.info("Detected {}", event);
        for (AttributeCategory category : categories) {
            affectedTemplates.addAll(recalculatePrice(category, false));
        }
        log.info("Affected templates: {}" + affectedTemplates);
        attributeCategoryPriceChangedEvent.fire(new AttributeCategoryPriceChanged(affectedTemplates.stream()
                .map(Template::getId)
                .collect(Collectors.toSet())));
    }

    private Set<Template> recalculatePrice(AttributeCategory category, boolean fireEvents) {
        BigDecimal current = category.getMinimalPrice();
        BigDecimal newMinimum = null;

        for(BigDecimal price : category.getAttributes().stream().map(Attribute::getPrice).collect(Collectors.toList())) {
            if (newMinimum == null || (price != null && price.compareTo(newMinimum) < 0)) {
                newMinimum = price;
            }
        }

        if (current == null && newMinimum != null ||
                current != null && newMinimum == null ||
                current != null && current.compareTo(newMinimum) != 0) {

            category.setMinimalPrice(newMinimum);

            if (fireEvents) {
                attributeCategoryPriceChangedEvent.fire(new AttributeCategoryPriceChanged(category.getTemplates().stream()
                        .map(Template::getId)
                        .collect(Collectors.toSet())));
            }
            log.info("Attribute category has a new minimal price: {}", category);
        }
        return category.getTemplates();
    }
}
