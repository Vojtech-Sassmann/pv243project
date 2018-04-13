package cz.fi.muni.TACOS.persistence.dao;

import cz.fi.muni.TACOS.persistence.entity.ProductCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Implementation of ProductCategoryDao
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
@Stateless
public class ProductCategoryDaoImpl extends AbstractDao<ProductCategory> implements ProductCategoryDao {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    public void delete(ProductCategory entity) {
        ProductCategory parentCategory = entity.getParentCategory();

        for (ProductCategory category : entity.getSubCategories()) {
            delete(category);
        }

        if (parentCategory != null) {
            parentCategory.removeSubCategory(entity);
        }

        super.delete(entity);
    }

    @Override
    public ProductCategory findByName(String name) {
        if(name == null){
            throw new IllegalArgumentException("Parameter name cannot be null!");
        }
        if(name.isEmpty()){
            throw new IllegalArgumentException("Parameter name cannot be empty!");
        }
        try {
            return em
                    .createQuery("select c from ProductCategory c where name = :name",
                            ProductCategory.class).setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}
