package cz.fi.muni.TACOS.service;

import cz.fi.muni.TACOS.persistence.entity.Attribute;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public interface AttributeService extends EntityService<Attribute> {

    /**
     * Updates attribute with given id by values from newVersion
     *
     * @param id id of attribute
     * @param newVersion newVersion
     */
    void updateAttribute(Long id, Attribute newVersion);
}
