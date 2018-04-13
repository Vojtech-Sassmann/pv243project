package cz.fi.muni.TACOS.persistence.dao;

import cz.fi.muni.TACOS.persistence.entity.CreatedProduct;
import cz.fi.muni.TACOS.persistence.entity.Product;
import cz.fi.muni.TACOS.persistence.entity.ProductCategory;

import javax.ejb.Stateless;

/**
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@Stateless
public class ProductDaoImpl extends AbstractDao<Product> implements ProductDao {
    @Override
    public void delete(Product entity) {
        for (CreatedProduct createdProduct : entity.getCreatedProducts()) {
            createdProduct.setProductFromOneSide(null);
        }

        for (ProductCategory category : entity.getProductCategories()) {
            category.removeProduct(entity);
        }

        super.delete(entity);
    }
}
