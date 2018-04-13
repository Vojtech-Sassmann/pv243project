package cz.fi.muni.TACOS.service.events;

import java.util.Objects;
import java.util.Set;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class TemplatePriceChanged {

    private final Set<Long> affectedProducts;

    public TemplatePriceChanged(Set<Long> affectedProducts) {
        this.affectedProducts = affectedProducts;
    }

    public Set<Long> getAffectedProducts() {
        return affectedProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplatePriceChanged that = (TemplatePriceChanged) o;
        return Objects.equals(affectedProducts, that.affectedProducts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(affectedProducts);
    }

    @Override
    public String toString() {
        return "TemplatePriceChanged{" +
                "affectedProducts=" + affectedProducts +
                '}';
    }
}
