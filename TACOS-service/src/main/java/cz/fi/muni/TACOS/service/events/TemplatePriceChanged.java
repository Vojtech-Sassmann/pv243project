package cz.fi.muni.TACOS.service.events;

import cz.fi.muni.TACOS.persistence.entity.Template;

import java.util.Objects;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class TemplatePriceChanged {

    private final Template template;

    public TemplatePriceChanged(Template template) {
        this.template = template;
    }

    public Template getTemplate() {
        return template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplatePriceChanged that = (TemplatePriceChanged) o;
        return Objects.equals(template, that.template);
    }

    @Override
    public int hashCode() {

        return Objects.hash(template);
    }

    @Override
    public String toString() {
        return "TemplatePriceChanged{" +
                "template=" + template +
                '}';
    }
}
