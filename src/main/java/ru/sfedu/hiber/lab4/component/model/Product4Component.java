package ru.sfedu.hiber.lab4.component.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "four_component_product")
public class Product4Component implements Serializable {

    @Id
    protected long id;
    @Column(nullable = false)
    protected String name;
    @Column
    protected double weight;
    @Column
    protected double price;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "four_component_category")
    @AttributeOverride(
            name = "name",
            column = @Column(name = "category_name", nullable = false)
    )
    @Column
    protected Set<Category4Component> category = new HashSet<>();

    public Product4Component() {
    }

    public Product4Component(long id, String name, double weight, double price, Set<Category4Component> category) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Category4Component> getCategory() {
        return category;
    }

    public void setCategory(Set<Category4Component> category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product4Component)) return false;
        Product4Component product = (Product4Component) o;
        return getId() == product.getId() &&
                Double.compare(product.getWeight(), getWeight()) == 0 &&
                Double.compare(product.getPrice(), getPrice()) == 0 &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getCategory(), product.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getWeight(), getPrice(), getCategory());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
