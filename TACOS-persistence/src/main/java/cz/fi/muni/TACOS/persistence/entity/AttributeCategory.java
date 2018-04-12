package cz.fi.muni.TACOS.persistence.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class AttributeCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @DecimalMin("0.00")
    private BigDecimal minimalPrice;

    @ManyToMany
    private Set<Attribute> attributes = new HashSet<>();

    @ManyToMany(mappedBy = "attributeCategories")
    private Set<Template> templates = new HashSet<>();

    public AttributeCategory() {

    }

    public BigDecimal getMinimalPrice() {
        return minimalPrice;
    }

    public void setMinimalPrice(BigDecimal minimalPrice) {
        this.minimalPrice = minimalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Attribute> getAttributes() {
        return Collections.unmodifiableSet(attributes);
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
        attribute.addProductAttributeCategoryFromOneSide(this);
    }

    public void removeAttribute(Attribute attribute) {
        this.attributes.remove(attribute);
        attribute.removeProductAttributeCategoryFromOneSide(this);
    }

    public Set<Template> getTemplates() {
        return Collections.unmodifiableSet(templates);
    }

    public void addProductTemplateFromOneSide(Template template) {
        this.templates.add(template);
    }

    public void removeProductTemplateFromOneSide(Template template) {
        this.templates.remove(template);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeCategory)) return false;
        AttributeCategory that = (AttributeCategory) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getAttributes(), that.getAttributes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAttributes());
    }

    @Override
    public String toString() {
        return "AttributeCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", minimalPrice=" + minimalPrice +
                '}';
    }
}
