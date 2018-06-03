package cz.fi.muni.TACOS.persistence.dao;


import cz.fi.muni.TACOS.persistence.entity.ProductCategory;

import java.util.List;

/**
 * Interface of ProductCategoryDao
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
public interface ProductCategoryDao extends Dao<ProductCategory> {

    /**
     * Finds product category in database by name and returns it as object ProductCategory
     * @param name by which will be category find
     * @return ProductCategory or null if there is not category whit that name
     *
     * @throws IllegalArgumentException when given parameter is null or empty
     */
    ProductCategory findByName(String name);

    /**
     * Gets all product categories which don't have subCategories
     *
     * @return List of product categories
     */
    List<ProductCategory> getLeafCategories();
}
