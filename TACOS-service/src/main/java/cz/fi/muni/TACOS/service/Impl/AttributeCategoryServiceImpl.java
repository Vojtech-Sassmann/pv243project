package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.AttributeCategoryDao;
import cz.fi.muni.TACOS.persistence.entity.Attribute;
import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
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
import java.util.List;
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
    public void addAttribute(AttributeCategory category, Attribute attribute) {
        category.addAttribute(attribute);
    }

    @Override
    public void removeAttribute(AttributeCategory category, Attribute attribute) {
        category.removeAttribute(attribute);
    }

    private void recalculatePrices(@Observes AttributePriceChanged event) {
        final Attribute changedAttribute = event.getAttribute();
        List<AttributeCategory> categories = getAll();
        log.info("Categories: {}", categories);
        categories = categories.stream()
                .filter(c -> c.getAttributes()
                        .stream()
                        .map(Attribute::getId)
                        .collect(Collectors.toList())
                        .contains(changedAttribute.getId()))
                .collect(Collectors.toList());
        log.info("Suitable: {}", categories);
        categories.forEach(c -> recalculatePrice(c, changedAttribute.getPrice()));
    }

    private void recalculatePrice(AttributeCategory category, BigDecimal potentialMinimum) {
        BigDecimal current = category.getMinimalPrice();
        if (current == null || current.compareTo(potentialMinimum) > 0) {
            category.setMinimalPrice(potentialMinimum);

            attributeCategoryPriceChangedEvent.fire(new AttributeCategoryPriceChanged(category));
            log.info("Attribute category has a new minimal price: {}", category);
        }
    }
}
