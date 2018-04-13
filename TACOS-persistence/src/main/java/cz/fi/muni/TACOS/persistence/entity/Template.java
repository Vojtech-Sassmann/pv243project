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
public class Template implements Serializable {

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
    private Set<AttributeCategory> attributeCategories = new HashSet<>();

    @ManyToMany(mappedBy = "templates")
    private Set<Product> products = new HashSet<>();

    public Template() {

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

    public Set<AttributeCategory> getAttributeCategories() {
        return Collections.unmodifiableSet(attributeCategories);
    }

    public void addAttributeCategory(AttributeCategory category) {
        this.attributeCategories.add(category);
        category.addProductTemplateFromOneSide(this);
    }

    public void removeAttributeCategory(AttributeCategory category) {
        this.attributeCategories.remove(category);
        category.removeProductTemplateFromOneSide(this);
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }

    public void addProductFromOneSide(Product product) {
        this.products.add(product);
    }

    public void removeProductFromOneSide(Product product) {
        this.products.remove(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Template)) return false;
        Template template = (Template) o;
        return Objects.equals(getName(), template.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Template{" +
                "name='" + name + '\'' +
                ", minimalPrice=" + minimalPrice +
                ", attributeCategories=" + attributeCategories +
                '}';
    }
}
