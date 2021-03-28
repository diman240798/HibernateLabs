package ru.sfedu.hiber.lab4.map_component.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "four_map_component_product")
public class Product4MapComponent implements Serializable {
    @Id
    @Column(nullable = false, name = "id")
    protected long id;
    @Column
    protected String name;
    @Column
    protected double weight;
    @Column
    protected double price;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "four_map_component_category",
            joinColumns = @JoinColumn(name = "id")
    )
    @MapKeyColumn(name = "category_name")
    @Column(name = "name")
    protected Map<String, Category4MapComponent> category = new HashMap();

    public Product4MapComponent() {
    }

    public Product4MapComponent(long id, String name, double weight, double price, Map<String, Category4MapComponent> category) {
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

    public Map<String, Category4MapComponent> getCategory() {
        return category;
    }

    public void setCategory(Map<String, Category4MapComponent> category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product4MapComponent)) return false;
        Product4MapComponent product = (Product4MapComponent) o;
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
