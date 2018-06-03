package cz.fi.muni.TACOS.persistence.dao;

import cz.fi.muni.TACOS.persistence.entity.ProductCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Set<ProductCategory> subCategoriesCopy = new HashSet<>(entity.getSubCategories());

        for (ProductCategory category : subCategoriesCopy) {
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

    @Override
    public List<ProductCategory> getLeafCategories() {
        try {
            return em.createQuery("select c from ProductCategory c where c.subCategories IS EMPTY ",
                    ProductCategory.class)
                    .getResultList();
        } catch (NoResultException nrf) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        try {
            return em.createQuery("select c from ProductCategory c where parentCategory IS NULL",
                    ProductCategory.class)
                    .getResultList();
        } catch (NoResultException nrf) {
            return new ArrayList<>();
        }
    }
}
