package cz.fi.muni.TACOS.persistence.dao.utils;

import cz.fi.muni.TACOS.persistence.dao.CreatedProductDao;
import cz.fi.muni.TACOS.persistence.dao.OrderDao;
import cz.fi.muni.TACOS.persistence.dao.AttributeCategoryDao;
import cz.fi.muni.TACOS.persistence.dao.AttributeDao;
import cz.fi.muni.TACOS.persistence.dao.ProductCategoryDao;
import cz.fi.muni.TACOS.persistence.dao.ProductDao;
import cz.fi.muni.TACOS.persistence.dao.TemplateDao;
import cz.fi.muni.TACOS.persistence.dao.UserDao;
import cz.fi.muni.TACOS.persistence.entity.Attribute;
import cz.fi.muni.TACOS.persistence.entity.CreatedProduct;
import cz.fi.muni.TACOS.persistence.entity.Order;
import cz.fi.muni.TACOS.persistence.entity.Product;
import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.persistence.entity.ProductCategory;
import cz.fi.muni.TACOS.persistence.entity.Template;
import cz.fi.muni.TACOS.persistence.entity.User;
import cz.fi.muni.TACOS.persistence.enums.OrderState;
import cz.fi.muni.TACOS.persistence.enums.ProductAttributeStatus;
import cz.fi.muni.TACOS.persistence.enums.UserRole;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public final class EntityCreator {

    public static User createTestUser(UserDao userDao, String randomPrefix) {
        return createTestUser(userDao, randomPrefix, UserRole.SUBMITTER);
    }

    public static User createTestUser(UserDao userDao, String randomPrefix, UserRole userRole) {
        User user = new User();
        user.setName(randomPrefix + "Regular");
        user.setSurname(randomPrefix + "User");
        user.setEmail(randomPrefix + "somerandomregularuser@worldofjava.com");
        user.setRole(userRole);
        userDao.create(user);
        return user;
    }

    public static Attribute createTestProductAttribute(AttributeDao attributeDao, AttributeCategoryDao attributeCategoryDao) {
        Attribute attribute = new Attribute();
        attribute.setName("M");
        attribute.setDescription("Random description(to be honest, not so random)");
        attribute.setPrice(BigDecimal.TEN);
        attribute.setStatus(ProductAttributeStatus.IN_STOCK);

        createProductAttributeCategory(attributeCategoryDao).addAttribute(attribute);
        attributeDao.create(attribute);
        return attribute;
    }

    public static Order createTestOrder(OrderDao orderDao, UserDao userDao) {
        User user = createTestUser(userDao, "TestOrder");

        Order order = new Order();
        order.setState(OrderState.NEW);
        order.setSubmitted(LocalDate.now());
        order.setFinished(LocalDate.now());
        order.setPrice(BigDecimal.ONE);
        user.addSubmittedOrder(order);
        orderDao.create(order);
        return order;
    }

    public static Product createTestProduct(ProductDao productDao) {
        Product product = new Product();
        product.setName("productName");
        product.setDescription("Description");
        product.setMinimalPrice(BigDecimal.valueOf(10.0));
        productDao.create(product);
        return product;
    }

    public static Product createTestSecondProduct(ProductDao productDao) {
        Product product = new Product();
        product.setName("name");
        product.setDescription("Description");
        product.setMinimalPrice(BigDecimal.valueOf(10.0));
        productDao.create(product);
        return product;
    }

    public static CreatedProduct createCreatedProductWithOrder(ProductDao productDao, OrderDao orderDao,
                                                               CreatedProductDao createdProductDao, UserDao userDao) {
        CreatedProduct createdProduct = new CreatedProduct();
        createdProduct.setProductFromOneSide(createTestProduct(productDao));
        createdProduct.setPrice(BigDecimal.valueOf(10));
        createdProduct.setDescription("Description");
        createdProduct.setCount(10L);

        Order order = createTestOrder(orderDao, userDao);
        order.addProduct(createdProduct);

        createdProductDao.create(createdProduct);
        return createdProduct;
    }

    public static Template createTestTemplate(TemplateDao templateDao) {
        Template template = new Template();
        template.setName("template");
        templateDao.create(template);
        return template;
    }


    public static ProductCategory createProductCategory(ProductCategoryDao productCategoryDao) {
        ProductCategory category = new ProductCategory();
        category.setName("categoryOne");
        productCategoryDao.create(category);
        return category;
    }

    public static ProductCategory createSecondProductCategory(ProductCategoryDao productCategoryDao) {
        ProductCategory category = new ProductCategory();
        category.setName("categoryTwo");
        productCategoryDao.create(category);
        return category;
    }

    public static Product createProduct(ProductDao productDao) {
        Product product = new Product();
        product.setDescription("desc");
        product.setName("productOne");
        product.setMinimalPrice(BigDecimal.valueOf(10));
        productDao.create(product);
        return product;
    }

    public static CreatedProduct createCreatedProduct(CreatedProductDao createdProductDao, OrderDao orderDao,
                                                      ProductDao productDao, UserDao userDao) {
        CreatedProduct createdProduct = new CreatedProduct();
        createdProduct.setCount(1L);
        createTestOrder(orderDao, userDao).addProduct(createdProduct);
        createProduct(productDao).addCreatedProduct(createdProduct);
        createdProduct.setDescription("desc");
        createdProduct.setPrice(BigDecimal.valueOf(20));
        createdProductDao.create(createdProduct);
        return createdProduct;
    }

    public static CreatedProduct createSecondCreatedProduct(CreatedProductDao createdProductDao, OrderDao orderDao,
                                                            ProductDao productDao, UserDao userDao) {
        CreatedProduct createdProduct = new CreatedProduct();
        createdProduct.setCount(2L);
        createdProduct.setOrderFromOneSide(createSecondOrder(orderDao, userDao));
        createdProduct.setProductFromOneSide(createProduct(productDao));
        createdProduct.setDescription("desc");
        createdProduct.setPrice(BigDecimal.valueOf(10));
        createdProductDao.create(createdProduct);
        return createdProduct;
    }

    public static Order createSecondOrder(OrderDao orderDao, UserDao userDao) {
        User user = createTestUser(userDao, "SecondOrder");
        Order order = new Order();
        order.setState(OrderState.PROCESSED);
        order.setSubmitted(LocalDate.now());
        order.setFinished(LocalDate.now());
        order.setPrice(BigDecimal.valueOf(10));
        user.addSubmittedOrder(order);
        orderDao.create(order);
        return order;
    }

    public static Order createFinishedOrder(OrderDao orderDao, UserDao userDao) {
        User user = createTestUser(userDao, "FinishedOrder");
        Order order = new Order();
        order.setState(OrderState.FINISHED);
        order.setSubmitted(LocalDate.now());
        order.setFinished(LocalDate.now());
        order.setPrice(BigDecimal.ONE);
        user.addSubmittedOrder(order);
        orderDao.create(order);
        return order;
    }

    public static AttributeCategory createProductAttributeCategory(AttributeCategoryDao attributeCategoryDao) {
        AttributeCategory category = new AttributeCategory();
        category.setName("Tshirt size");
        attributeCategoryDao.create(category);
        return category;
    }
}
