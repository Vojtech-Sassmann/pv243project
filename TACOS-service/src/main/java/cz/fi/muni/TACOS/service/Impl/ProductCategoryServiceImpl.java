package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.ProductCategoryDao;
import cz.fi.muni.TACOS.persistence.entity.Product;
import cz.fi.muni.TACOS.persistence.entity.ProductCategory;
import cz.fi.muni.TACOS.service.AbstractEntityService;
import cz.fi.muni.TACOS.service.ProductCategoryService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@ApplicationScoped
public class ProductCategoryServiceImpl extends AbstractEntityService<ProductCategory> implements ProductCategoryService {

    private final ProductCategoryDao productCategoryDao;

    @Inject
    public ProductCategoryServiceImpl(ProductCategoryDao productCategoryDao) {
        super(productCategoryDao);

        this.productCategoryDao = productCategoryDao;
    }

    @Override
    public void addSubCategory(ProductCategory category, ProductCategory subCategory) {
        category.addSubCategory(subCategory);
    }

    @Override
    public void removeSubCategory(ProductCategory category, ProductCategory subCategory) {
        category.removeSubCategory(subCategory);
    }

    @Override
    public void addProductToCategory(ProductCategory category, Product product) {
        category.addProduct(product);
    }

    @Override
    public void removeProductFromCategory(ProductCategory category, Product product) {
        category.removeProduct(product);
    }

    @Override
    public List<ProductCategory> getLeafCategories() {
        return productCategoryDao.getLeafCategories();
    }
}
