package cz.fi.muni.TACOS.persistence.dao;

import cz.fi.muni.TACOS.persistence.entity.Product;
import cz.fi.muni.TACOS.persistence.entity.Template;

import javax.ejb.Stateless;

@Stateless
public class TemplateDaoImpl extends AbstractDao<Template> implements TemplateDao {

    @Override
    public void delete(Template entity) {
        for (Product product : entity.getProducts()) {
            product.removeTemplate(entity);
        }

        super.delete(entity);
    }
}
