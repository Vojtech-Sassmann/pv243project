package cz.fi.muni.TACOS.service;

import cz.fi.muni.TACOS.persistence.entity.Product;
import cz.fi.muni.TACOS.persistence.entity.ProductCategory;

import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public interface ProductCategoryService extends EntityService<ProductCategory> {

    /**
     * Adds subcategory to category
     *
     * @param category category
     * @param subCategory sub category
     */
    void addSubCategory(ProductCategory category, ProductCategory subCategory);

    /**
     * Removes subcategory from category
     *
     * @param category category
     * @param subCategory subcategory
     */
    void removeSubCategory(ProductCategory category, ProductCategory subCategory);

    /**
     * Adds product to category
     *
     * @param category category
     * @param product product
     */
    void addProductToCategory(ProductCategory category, Product product);

    /**
     * Removes product from categroy.
     *
     * @param category category
     * @param product product
     */
    void removeProductFromCategory(ProductCategory category, Product product);

    /**
     * Gets all product categories which don't have subCategories
     *
     * @return List of product categories
     */
    List<ProductCategory> getLeafCategories();
}
