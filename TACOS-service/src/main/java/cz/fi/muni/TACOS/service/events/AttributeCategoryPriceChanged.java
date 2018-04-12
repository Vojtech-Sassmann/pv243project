package cz.fi.muni.TACOS.service.events;

import cz.fi.muni.TACOS.persistence.entity.AttributeCategory;

import java.util.Objects;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributeCategoryPriceChanged {

    private final AttributeCategory attributeCategory;

    public AttributeCategoryPriceChanged(AttributeCategory attributeCategory) {
        this.attributeCategory = attributeCategory;
    }

    public AttributeCategory getAttributeCategory() {
        return attributeCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeCategoryPriceChanged that = (AttributeCategoryPriceChanged) o;
        return Objects.equals(attributeCategory, that.attributeCategory);
    }

    @Override
    public int hashCode() {

        return Objects.hash(attributeCategory);
    }

    @Override
    public String toString() {
        return "AttributeCategoryPriceChanged{" +
                "attributeCategory=" + attributeCategory +
                '}';
    }
}
