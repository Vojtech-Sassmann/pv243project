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
public class PriceCalculationIntegrationTest {

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
    public void testMinimalPriceChangeOnUpdate() {
        Product product = new Product();
        Template template = new Template();
        AttributeCategory attributeCategory = new AttributeCategory();
        Attribute attribute = new Attribute();

        setUpEntities(product, template, attributeCategory, attribute);

        //Update attribute with new minimal price
        Attribute newVersion = new Attribute();
        newVersion.setPrice(BigDecimal.ONE);
        attributeService.updateAttribute(attribute.getId(), newVersion);

        assertThat(product.getMinimalPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(template.getMinimalPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(attributeCategory.getMinimalPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(attribute.getPrice()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void testMinimalPriceChangeOnDeleteAttribute() {
        Product product = new Product();
        Template template = new Template();
        AttributeCategory attributeCategory = new AttributeCategory();
        Attribute attribute = new Attribute();

        setUpEntities(product, template, attributeCategory, attribute);

        //Remove attribute
        attributeService.delete(attribute);

        assertThat(attributeCategory.getMinimalPrice()).isNull();
        assertThat(template.getMinimalPrice()).isNull();
        assertThat(product.getMinimalPrice()).isNull();
    }

    @Test
    public void testMinimalPriceChangeOnDeleteAttributeCategory() {
        Product product = new Product();
        Template template = new Template();
        AttributeCategory attributeCategory = new AttributeCategory();
        Attribute attribute = new Attribute();

        setUpEntities(product, template, attributeCategory, attribute);

        //add another attribute category with greater minimal price
        AttributeCategory moreExpensiveCategory = new AttributeCategory();
        moreExpensiveCategory.setName("More expensive category");

        BigDecimal biggerPrice = BigDecimal.valueOf(90L);
        Attribute moreExpensiveAttribute = new Attribute();
        moreExpensiveAttribute.setStatus(ProductAttributeStatus.IN_STOCK);
        moreExpensiveAttribute.setName("More expensive attr");
        moreExpensiveAttribute.setPrice(biggerPrice);

        attributeCategoryService.create(moreExpensiveCategory);
        templateService.addAttributeCategory(template, moreExpensiveCategory);
        moreExpensiveCategory.addAttribute(moreExpensiveAttribute);
        attributeService.create(moreExpensiveAttribute);

        attributeCategoryService.delete(attributeCategory);

        assertThat(template.getMinimalPrice()).isEqualTo(biggerPrice);
        assertThat(product.getMinimalPrice()).isEqualTo(biggerPrice);
    }

    @Test
    public void testMinimalPriceChangeOnDeleteTemplate() {
        Product product = new Product();
        Template template = new Template();
        AttributeCategory attributeCategory = new AttributeCategory();
        Attribute attribute = new Attribute();

        setUpEntities(product, template, attributeCategory, attribute);

        AttributeCategory moreExpensiveCategory = new AttributeCategory();
        moreExpensiveCategory.setName("More expensive category");

        BigDecimal biggerPrice = BigDecimal.valueOf(90L);
        Attribute moreExpensiveAttribute = new Attribute();
        moreExpensiveAttribute.setStatus(ProductAttributeStatus.IN_STOCK);
        moreExpensiveAttribute.setName("More expensive attr");
        moreExpensiveAttribute.setPrice(biggerPrice);

        attributeCategoryService.create(moreExpensiveCategory);
        templateService.addAttributeCategory(template, moreExpensiveCategory);
        moreExpensiveCategory.addAttribute(moreExpensiveAttribute);
        attributeService.create(moreExpensiveAttribute);

        Template moreExpensiveTemplate = new Template();
        moreExpensiveTemplate.setName("More expensive template");
        product.addTemplate(moreExpensiveTemplate);
        templateService.create(moreExpensiveTemplate);
        templateService.addAttributeCategory(moreExpensiveTemplate, moreExpensiveCategory);


        templateService.delete(template);

        assertThat(product.getMinimalPrice()).isEqualTo(biggerPrice);
    }

    @Test
    public void testMinimalPriceChangeRemoveAttribute() {
        Product product = new Product();
        Template template = new Template();
        AttributeCategory attributeCategory = new AttributeCategory();
        Attribute attribute = new Attribute();

        setUpEntities(product, template, attributeCategory, attribute);

        attributeCategoryService.removeAttribute(attributeCategory, attribute);

        assertThat(product.getMinimalPrice()).isNull();
        assertThat(template.getMinimalPrice()).isNull();
        assertThat(attributeCategory.getMinimalPrice()).isNull();
        assertThat(attribute.getPrice()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void testMinimalPriceTemplateWithMultipleAttributeCategories() {
        Product product = new Product();
        Template template = new Template();
        AttributeCategory attributeCategory = new AttributeCategory();
        Attribute attribute = new Attribute();

        setUpEntities(product, template, attributeCategory, attribute);

        AttributeCategory newCategory = new AttributeCategory();
        newCategory.setName("Material");
        attributeCategoryService.create(newCategory);

        templateService.addAttributeCategory(template, newCategory);

        Attribute newAttribute = new Attribute();
        newAttribute.setPrice(BigDecimal.valueOf(23L));
        newAttribute.setName("Cotton");
        newAttribute.setStatus(ProductAttributeStatus.IN_STOCK);
        newAttribute.setDescription("Cotton");
        attributeService.create(newAttribute);

        attributeCategoryService.addAttribute(newCategory, newAttribute);

        assertThat(product.getMinimalPrice()).isEqualTo(BigDecimal.valueOf(33L));
        assertThat(template.getMinimalPrice()).isEqualTo(BigDecimal.valueOf(33L));
        assertThat(attributeCategory.getMinimalPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(attribute.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(newCategory.getMinimalPrice()).isEqualTo(BigDecimal.valueOf(23L));
    }

    private void setUpEntities(Product product, Template template, AttributeCategory attributeCategory, Attribute attribute) {
        //Create product
        product.setName("T-shirt");
        product.setDescription("Simple T-shirt");
        productService.create(product);

        //Create template
        template.setName("Men's T-shirt");
        product.addTemplate(template);
        templateService.create(template);

        //Create attribute category
        attributeCategory.setName("T-shirt size");
        attributeCategoryService.create(attributeCategory);
        templateService.addAttributeCategory(template, attributeCategory);

        //Create attribute
        attribute.setPrice(BigDecimal.TEN);
        attribute.setName("L");
        attribute.setStatus(ProductAttributeStatus.IN_STOCK);
        attributeService.create(attribute);
        attributeCategoryService.addAttribute(attributeCategory, attribute);
    }
}
