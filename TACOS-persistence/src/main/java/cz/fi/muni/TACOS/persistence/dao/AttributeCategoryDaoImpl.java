package cz.fi.muni.TACOS.persistence.dao;

import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.persistence.entity.Template;

import javax.ejb.Stateless;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Stateless
public class AttributeCategoryDaoImpl extends AbstractDao<AttributeCategory> implements AttributeCategoryDao {
    @Override
    public void delete(AttributeCategory entity) {
        for (Template template : entity.getTemplates()) {
            template.removeAttributeCategory(entity);
        }

        super.delete(entity);
    }
}
