package cz.fi.muni.TACOS.persistence.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Vyskocil Pavel <vyskocilpavel@muni.cz>
 */
@Entity
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String description;

    @DecimalMin("0.00")
    private BigDecimal minimalPrice;

    @NotNull
    @Column(nullable = false)
    @ManyToMany
    private Set<Template> templates = new HashSet<>();

    @NotNull
    @Column(nullable = false)
    @ManyToMany(mappedBy = "products")
    private Set<ProductCategory> productCategories = new HashSet<>();

    @NotNull
    @Column(nullable = false)
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<CreatedProduct> createdProducts = new HashSet<>();

    public Product() {
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
        checkName(name);
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        checkDescription(description);
        this.description = description;
    }

    public BigDecimal getMinimalPrice() {
        return minimalPrice;
    }

    public void setMinimalPrice(BigDecimal minimalPrice) {
        this.minimalPrice = minimalPrice;
    }

    public Set<Template> getTemplates() {
        return Collections.unmodifiableSet(templates);
    }

    public void addTemplate(Template template) {
        this.templates.add(template);
        template.addProductFromOneSide(this);
    }

    public void removeTemplate(Template template) {
        this.templates.remove(template);
        template.removeProductFromOneSide(this);
    }

    public Set<ProductCategory> getProductCategories() {
        return Collections.unmodifiableSet(productCategories);
    }

    public void addProductCategoryFromOneSide(ProductCategory category) {
        this.productCategories.add(category);
    }

    public void removeProductCategoryFromOneSide(ProductCategory category) {
        this.productCategories.remove(category);
    }

    public Set<CreatedProduct> getCreatedProducts() {
        return Collections.unmodifiableSet(createdProducts);
    }

    public void addCreatedProduct(CreatedProduct product) {
        this.createdProducts.add(product);
        product.setProductFromOneSide(this);
    }

    public void removeCreatedProduct(CreatedProduct product) {
        this.createdProducts.remove(product);
        product.setProductFromOneSide(null);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;

        return Objects.equals(getName(), product.getName()) &&
                Objects.equals(getDescription(), product.getDescription()) &&
                Objects.equals(getMinimalPrice(), product.getMinimalPrice());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getDescription(), getMinimalPrice());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", minimalPrice=" + minimalPrice +
                '}';
    }

    private void checkName(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
    }
    private void checkDescription(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Description cannot be null.");
        }
    }
}
