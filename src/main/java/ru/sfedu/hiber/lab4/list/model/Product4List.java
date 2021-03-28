package ru.sfedu.hiber.lab4.list.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "four_list_product")
public class Product4List implements Serializable {
    @Id
    @Column(nullable = false, name = "id")
    protected Long id;
    @Column
    protected String name;
    @Column
    protected double weight;
    @Column
    protected double price;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "four_list_category",
            joinColumns = @JoinColumn(name = "id")
    )
    @OrderColumn
    @Column(name = "name")
    protected List<String> category = new ArrayList<>();

    public Product4List() { }

    public Product4List(long id, String name, double weight, double price, List<String> category) {
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

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product4List)) return false;
        Product4List product = (Product4List) o;
        return getId() == product.getId() &&
                Double.compare(product.getWeight(), getWeight()) == 0 &&
                Double.compare(product.getPrice(), getPrice()) == 0 &&
                Objects.equals(getName(), product.getName()) &&
                Objects.deepEquals(new ArrayList<>(getCategory()), new ArrayList<>(product.getCategory()));
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
