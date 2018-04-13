package cz.fi.muni.TACOS.service.events;

import java.util.Objects;
import java.util.Set;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributePriceChanged {

    private final Set<Long> affectedCategories;

    public AttributePriceChanged(Set<Long> affectedCategories) {
        this.affectedCategories = affectedCategories;
    }

    public Set<Long> getAffectedCategories() {
        return affectedCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributePriceChanged that = (AttributePriceChanged) o;
        return Objects.equals(affectedCategories, that.affectedCategories);
    }

    @Override
    public int hashCode() {

        return Objects.hash(affectedCategories);
    }

    @Override
    public String toString() {
        return "AttributePriceChanged{" +
                "affectedCategories=" + affectedCategories +
                '}';
    }
}
