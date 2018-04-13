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
import java.util.HashSet;
import java.util.Set;
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
        recalculatePrice(product);
    }

    @Override
    public void removeTemplate(Product product, Template template) {
        product.removeTemplate(template);
        recalculatePrice(product);
    }

    private Set<Product> getProductsById(Set<Long> ids) {
        Set<Product> products = new HashSet<>();
        for (Long id : ids) {
            products.add(findById(id));
        }
        return products;
    }

    private void recalculatePrices(@Observes TemplatePriceChanged event) {
        Set<Product> templates = getProductsById(event.getAffectedProducts());
        templates.forEach(this::recalculatePrice);
    }

    private void recalculatePrice(Product product) {
        BigDecimal current = product.getMinimalPrice();
        BigDecimal newMinimum = null;

        for(BigDecimal price : product.getTemplates().stream()
                .map(Template::getMinimalPrice)
                .collect(Collectors.toList())) {
            if (newMinimum == null || (price != null && price.compareTo(newMinimum) < 0)) {
                newMinimum = price;
            }
        }

        if (current == null && newMinimum != null ||
                current != null && newMinimum == null ||
                current != null && current.compareTo(newMinimum) != 0) {

            product.setMinimalPrice(newMinimum);

            log.info("Product has a new minimal price: {}", product);
        }
    }
}
