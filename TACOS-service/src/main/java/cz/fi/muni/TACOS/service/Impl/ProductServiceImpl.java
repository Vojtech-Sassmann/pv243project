package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.ProductDao;
import cz.fi.muni.TACOS.persistence.entity.Product;
import cz.fi.muni.TACOS.persistence.entity.Template;
import cz.fi.muni.TACOS.service.AbstractEntityService;
import cz.fi.muni.TACOS.service.ProductService;
import cz.fi.muni.TACOS.service.events.TemplatePriceChanged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@ApplicationScoped
public class ProductServiceImpl extends AbstractEntityService<Product> implements ProductService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ProductDao productDao;

    @Inject
    public ProductServiceImpl(ProductDao productDao) {
        super(productDao);

        this.productDao = productDao;
    }

    @Override
    public void addTemplate(Product product, Template template) {
        product.addTemplate(template);
    }

    @Override
    public void removeTemplate(Product product, Template template) {
        product.removeTemplate(template);
    }


    private void recalculatePrices(@Observes TemplatePriceChanged event) {
        final Template template = event.getTemplate();
        List<Product> products = getAll();

        products = products.stream()
                .filter(p -> p.getTemplates()
                        .stream()
                        .map(Template::getId)
                        .collect(Collectors.toList())
                        .contains(template.getId()))
                .collect(Collectors.toList());

        products.forEach(p -> recalculatePrice(p, template.getMinimalPrice()));
    }

    private void recalculatePrice(Product product, BigDecimal potentialMinimum) {
        BigDecimal current = product.getMinimalPrice();
        if (current == null || current.compareTo(potentialMinimum) > 0) {
            product.setMinimalPrice(potentialMinimum);

            log.info("Product has a new minimal price: {}", product);
        }
    }
}
