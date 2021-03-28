package ru.sfedu.hiber.lab5.model;

import ru.sfedu.hiber.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = Constants.RECEIPT)
public class Receipt implements Serializable {
    @Id
    private long id;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> products;
    @Column
    private double totalPrice;


    public Receipt() {
    }

    public Receipt(long id, List<Product> products, double totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receipt)) return false;
        Receipt receipt = (Receipt) o;
        return getId() == receipt.getId() &&
                Double.compare(receipt.getTotalPrice(), getTotalPrice()) == 0 &&
                new ArrayList<>(getProducts()).equals(new ArrayList<>(receipt.getProducts()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProducts(), getTotalPrice());
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", products=" + products +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
