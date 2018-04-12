package cz.fi.muni.TACOS.service.integration;

import cz.fi.muni.TACOS.persistence.entity.Attribute;
import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.persistence.entity.Product;
import cz.fi.muni.TACOS.persistence.entity.Template;
import cz.fi.muni.TACOS.persistence.enums.ProductAttributeStatus;
import cz.fi.muni.TACOS.service.AttributeCategoryService;
import cz.fi.muni.TACOS.service.AttributeService;
import cz.fi.muni.TACOS.service.ProductCategoryService;
import cz.fi.muni.TACOS.service.ProductService;
import cz.fi.muni.TACOS.service.TemplateService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Transactional(TransactionMode.ROLLBACK)
@RunWith(Arquillian.class)
public class AttributeServiceIntegrationTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addPackages(true, "cz.fi.muni.TACOS", "org.assertj.core",
                        "java.lang", "org.dozer")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private AttributeService attributeService;

    @Inject
    private AttributeCategoryService attributeCategoryService;

    @Inject
    private ProductCategoryService productCategoryService;

    @Inject
    private TemplateService templateService;

    @Inject
    private ProductService productService;

    @Test
    public void testMinimalPriceChange() {
        //Create product
        Product product = new Product();
        product.setName("T-shirt");
        product.setDescription("Simple T-shirt");
        productService.create(product);

        //Create template
        Template template = new Template();
        template.setName("Men's T-shirt");
        product.addTemplate(template);
        templateService.create(template);

        //Create attribute category
        AttributeCategory attributeCategory = new AttributeCategory();
        attributeCategory.setName("T-shirt size");
        template.addAttributeCategory(attributeCategory);
        attributeCategoryService.create(attributeCategory);

        //Create attribute
        Attribute attribute = new Attribute();
        attribute.setPrice(BigDecimal.TEN);
        attribute.setName("L");
        attribute.setStatus(ProductAttributeStatus.IN_STOCK);
        attributeCategory.addAttribute(attribute);
        attributeService.create(attribute);

        //Update attribute with new minimal price
        Attribute newVersion = new Attribute();
        newVersion.setPrice(BigDecimal.ONE);
        attributeService.updateAttribute(attribute.getId(), newVersion);

        assertThat(product.getMinimalPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(template.getMinimalPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(attributeCategory.getMinimalPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(attribute.getPrice()).isEqualTo(BigDecimal.ONE);
    }
}
