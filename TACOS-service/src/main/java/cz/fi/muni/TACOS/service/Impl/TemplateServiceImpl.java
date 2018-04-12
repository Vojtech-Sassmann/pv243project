package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.TemplateDao;
import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.persistence.entity.Template;
import cz.fi.muni.TACOS.service.AbstractEntityService;
import cz.fi.muni.TACOS.service.TemplateService;
import cz.fi.muni.TACOS.service.events.AttributeCategoryPriceChanged;
import cz.fi.muni.TACOS.service.events.TemplatePriceChanged;
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
public class TemplateServiceImpl extends AbstractEntityService<Template> implements TemplateService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final TemplateDao templateDao;
    private final Event<TemplatePriceChanged> templatePriceChangedEvent;

    @Inject
    public TemplateServiceImpl(TemplateDao templateDao, Event<TemplatePriceChanged> templatePriceChangedEvent) {
        super(templateDao);

        this.templateDao = templateDao;
        this.templatePriceChangedEvent = templatePriceChangedEvent;
    }

    @Override
    public void addAttributeCategory(Template template, AttributeCategory category) {
        template.addAttributeCategory(category);
    }

    @Override
    public void removeAttributeCategory(Template template, AttributeCategory category) {
        template.removeAttributeCategory(category);
    }

    private void recalculatePrices(@Observes AttributeCategoryPriceChanged event) {
        final AttributeCategory changedAttributeCategory = event.getAttributeCategory();
        List<Template> templates = getAll();

        templates = templates.stream()
                .filter(t -> t.getAttributeCategories()
                        .stream()
                        .map(AttributeCategory::getId)
                        .collect(Collectors.toList())
                        .contains(changedAttributeCategory.getId()))
                .collect(Collectors.toList());

        templates.forEach(t -> recalculatePrice(t, changedAttributeCategory.getMinimalPrice()));
    }

    private void recalculatePrice(Template template, BigDecimal potentialMinimum) {
        BigDecimal current = template.getMinimalPrice();
        if (current == null || current.compareTo(potentialMinimum) > 0) {
            template.setMinimalPrice(potentialMinimum);

            templatePriceChangedEvent.fire(new TemplatePriceChanged(template));
            log.info("Template has a new minimal price: {}", template);
        }
    }
}
