package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.TemplateDao;
import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.persistence.entity.Product;
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
import java.util.HashSet;
import java.util.Set;
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
    public void create(Template entity) {
        super.create(entity);

        templatePriceChangedEvent.fire(new TemplatePriceChanged(entity.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toSet())));
    }

    @Override
    public void delete(Template entity) {
        Set<Long> affectedProducts = entity.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        super.delete(entity);

        templatePriceChangedEvent.fire(new TemplatePriceChanged(affectedProducts));
    }

    @Override
    public void addAttributeCategory(Template template, AttributeCategory category) {
        template.addAttributeCategory(category);
        recalculatePrice(template, true);
    }

    @Override
    public void removeAttributeCategory(Template template, AttributeCategory category) {
        template.removeAttributeCategory(category);
        recalculatePrice(template, true);
    }

    private Set<Template> getTemplatesById(Set<Long> ids) {
        Set<Template> templates = new HashSet<>();
        for (Long id : ids) {
            templates.add(findById(id));
        }
        return templates;
    }

    private void recalculatePrices(@Observes AttributeCategoryPriceChanged event) {
        Set<Template> templates = getTemplatesById(event.getAffectedTemplates());
        Set<Product> affectedProducts = new HashSet<>();
        log.info("Detected attribute category change: {}", event);
        for (Template template : templates) {
            affectedProducts.addAll(recalculatePrice(template, false));
        }
        log.info("Detected affected products: {}", affectedProducts);
        templatePriceChangedEvent.fire(new TemplatePriceChanged(affectedProducts.stream()
                .map(Product::getId)
                .collect(Collectors.toSet())));
    }

    private Set<Product> recalculatePrice(Template template, boolean fireEvents) {
        BigDecimal current = template.getMinimalPrice();
        BigDecimal newMinimum = null;

        template = templateDao.findById(template.getId());

        log.debug("Recalculating template: {}", template);

        for(BigDecimal price : template.getAttributeCategories().stream()
                .map(AttributeCategory::getMinimalPrice)
                .collect(Collectors.toList())) {
            if (newMinimum == null && price != null) {
                newMinimum = price;
            } else if (newMinimum != null && price != null) {
                newMinimum = newMinimum.add(price);
            }
        }

        log.debug("Previous price: {}, newMinimum {}", current, newMinimum);

        if (current == null && newMinimum != null ||
                current != null && newMinimum == null ||
                current != null && current.compareTo(newMinimum) != 0) {

            template.setMinimalPrice(newMinimum);

            if (fireEvents) {
                templatePriceChangedEvent.fire(new TemplatePriceChanged(template.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toSet())));
            }
            log.info("Template has a new minimal price: {}", template);
        }
        return template.getProducts();
    }
}
