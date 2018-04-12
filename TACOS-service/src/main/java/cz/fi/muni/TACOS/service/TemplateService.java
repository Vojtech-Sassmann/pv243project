package cz.fi.muni.TACOS.service;

import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;
import cz.fi.muni.TACOS.persistence.entity.Template;


/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public interface TemplateService extends EntityService<Template> {

    /**
     * Adds attribute category to template
     *
     * @param template template
     * @param category category
     */
    void addAttributeCategory(Template template, AttributeCategory category);

    /**
     * Removes attribute category from template
     *
     * @param template template
     * @param category category
     */
    void removeAttributeCategory(Template template, AttributeCategory category);
}
