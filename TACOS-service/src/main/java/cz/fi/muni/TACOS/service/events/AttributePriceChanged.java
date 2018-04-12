package cz.fi.muni.TACOS.service.events;

import cz.fi.muni.TACOS.persistence.entity.Attribute;

import java.util.Objects;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributePriceChanged {

    private final Attribute attribute;

    public AttributePriceChanged(Attribute attribute) {
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributePriceChanged that = (AttributePriceChanged) o;
        return Objects.equals(attribute, that.attribute);
    }

    @Override
    public int hashCode() {

        return Objects.hash(attribute);
    }

    @Override
    public String toString() {
        return "AttributePriceChanged{" +
                "attribute=" + attribute +
                '}';
    }
}
