package cz.fi.muni.TACOS.service.events;

import java.util.Objects;
import java.util.Set;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributeCategoryPriceChanged {

    private final Set<Long> affectedTemplates;

    public AttributeCategoryPriceChanged(Set<Long> affectedTemplates) {
        this.affectedTemplates = affectedTemplates;
    }

    public Set<Long> getAffectedTemplates() {
        return affectedTemplates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeCategoryPriceChanged that = (AttributeCategoryPriceChanged) o;
        return Objects.equals(affectedTemplates, that.affectedTemplates);
    }

    @Override
    public int hashCode() {

        return Objects.hash(affectedTemplates);
    }

    @Override
    public String toString() {
        return "AttributeCategoryPriceChanged{" +
                "affectedTemplates=" + affectedTemplates +
                '}';
    }
}
