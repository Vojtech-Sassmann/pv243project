package cz.fi.muni.TACOS.persistence.dao;

import cz.fi.muni.TACOS.persistence.entity.Attribute;
import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.persistence.entity.CreatedProduct;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Stateless
public class AttributeDaoImpl extends AbstractDao<Attribute> implements AttributeDao {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    public void delete(Attribute entity) {
        for (AttributeCategory category : entity.getAttributeCategories()) {
            category.removeAttribute(entity);
        }
        for (CreatedProduct createdProduct : entity.getCreatedProducts()) {
            createdProduct.removeAttribute(entity);
        }
        super.delete(entity);
    }
}
