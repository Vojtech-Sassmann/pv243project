package cz.fi.muni.TACOS.facade;

import cz.fi.muni.TACOS.dto.ProductCategoryCreateDTO;
import cz.fi.muni.TACOS.dto.ProductCategoryDTO;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;

import java.util.List;

/**
 * Facade Interface for ProductCategory entity.
 *
 * @author Peter Balcirak <peter.balcirak@gmail.com>
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public interface ProductCategoryFacade extends Facade<ProductCategoryDTO> {

    Long create(ProductCategoryCreateDTO entity) throws InvalidRelationEntityIdException;

    /**
     * Adds subcategory to category
     *
     * @param categoryId
     * @param subCategoryId
     */
    void addSubCategory(Long categoryId, Long subCategoryId);

    /**
     * Removes subcategory from category
     *
     * @param categoryId
     * @param subCategoryId
     */
    void removeSubCategory(Long categoryId, Long subCategoryId);
    /**
     * Creates subcategory for given category
     *
     * @param entity specification
     * @param parentCategoryId parent category id
     * @return id of newly created entity
     */
    Long createSubCategory(ProductCategoryCreateDTO entity, Long parentCategoryId);

    /**
     * Adds product to category
     *
     * @param categoryId
     * @param productId
     */
    void addProductToCategory(Long categoryId, Long productId);

    /**
     * Removes product from categroy.
     *
     * @param categoryId
     * @param productId
     */
    void removeProductFromCategory(Long categoryId, Long productId);

    /**
     * Gets all product categories which don't have subCategories
     *
     * @return List of product categories DTO
     */
    List<ProductCategoryDTO> getLeafCategories();
}
