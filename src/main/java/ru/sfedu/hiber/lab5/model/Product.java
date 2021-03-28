package ru.sfedu.hiber.lab5.model;

import ru.sfedu.hiber.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = Constants.PRODUCT_ENTITY)
@Table(name = Constants.PRODUCT)
@Inheritance(strategy = InheritanceType.JOINED)
public class Product implements Serializable {
    @Id
    protected long id;
    @Column
    protected String name;
    @Column
    protected double weight;
    @Column
    protected double price;
    @ManyToOne
    protected Category category;

    public Product() {}

    public Product(long id, String name, double weight, double price, Category category) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
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

