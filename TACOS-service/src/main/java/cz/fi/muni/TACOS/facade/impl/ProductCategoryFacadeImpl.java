package cz.fi.muni.TACOS.facade.impl;

import cz.fi.muni.TACOS.dto.ProductCategoryCreateDTO;
import cz.fi.muni.TACOS.dto.ProductCategoryDTO;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;
import cz.fi.muni.TACOS.facade.ProductCategoryFacade;
import cz.fi.muni.TACOS.persistence.entity.ProductCategory;
import cz.fi.muni.TACOS.service.BeanMappingService;
import cz.fi.muni.TACOS.service.ProductCategoryService;
import cz.fi.muni.TACOS.service.ProductService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of ProductCategoryFacade Interface
 *
 * @author Peter Balcirak <peter.balcirak@gmail.com>
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Transactional(rollbackOn = { Exception.class })
@ApplicationScoped
public class ProductCategoryFacadeImpl implements ProductCategoryFacade {

    private final ProductCategoryService productCategoryService;

	private final ProductService productService;

	private final BeanMappingService beanMappingService;

    @Inject
    public ProductCategoryFacadeImpl(ProductCategoryService productCategoryService, ProductService productService,
                                     BeanMappingService beanMappingService) {
        this.productCategoryService = productCategoryService;
        this.productService = productService;
        this.beanMappingService = beanMappingService;
    }

    public void addSubCategory(Long categoryId, Long subCategoryId) {
        productCategoryService.addSubCategory(productCategoryService.findById(categoryId), productCategoryService.findById(subCategoryId));
    }

    @Override
    public void removeSubCategory(Long categoryId, Long subCategoryId) {
        productCategoryService.removeSubCategory(productCategoryService.findById(categoryId), productCategoryService.findById(subCategoryId));
    }

    @Override
    public void addProductToCategory(Long categoryId, Long productId) {
        productCategoryService.addProductToCategory(productCategoryService.findById(categoryId), productService.findById(productId));
    }

    @Override
    public void removeProductFromCategory(Long categoryId, Long productId) {
        productCategoryService.removeProductFromCategory(productCategoryService.findById(categoryId), productService.findById(productId));
    }

    @Override
    public List<ProductCategoryDTO> getLeafCategories() {
        return beanMappingService.mapTo(productCategoryService.getLeafCategories(), ProductCategoryDTO.class);
    }

    @Override
    public Long createSubCategory(ProductCategoryCreateDTO entity, Long parentCategoryId) {
        ProductCategory productCategory = beanMappingService.mapTo(entity, ProductCategory.class);
        ProductCategory parentCategory = productCategoryService.findById(parentCategoryId);

        productCategoryService.create(productCategory);
        productCategoryService.addSubCategory(parentCategory, productCategory);

        return productCategory.getId();
    }

    @Override
    public Long create(ProductCategoryCreateDTO entity) throws InvalidRelationEntityIdException {
        ProductCategory productCategory = beanMappingService.mapTo(entity, ProductCategory.class);
        productCategoryService.create(productCategory);

        if (entity.getParentCategoryId() != null) {
            ProductCategory parentCategory = productCategoryService.findById(entity.getParentCategoryId());
            if (parentCategory == null) {
                throw new InvalidRelationEntityIdException("Parent category for given id does not exist. id: " + entity.getParentCategoryId());
            }
            productCategoryService.addSubCategory(parentCategory, productCategory);
        }

        return productCategory.getId();
    }

    @Override
    public void delete(Long id) {
        productCategoryService.delete(productCategoryService.findById(id));
    }

    @Override
    public ProductCategoryDTO findById(Long id) {
        return beanMappingService.mapTo(productCategoryService.findById(id), ProductCategoryDTO.class);
    }

    @Override
    public List<ProductCategoryDTO> getAll() {
        return beanMappingService.mapTo(productCategoryService.getAll(), ProductCategoryDTO.class);
    }
}
