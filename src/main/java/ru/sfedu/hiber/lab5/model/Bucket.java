package ru.sfedu.hiber.lab5.model;


import ru.sfedu.hiber.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = Constants.BUCKET)
public class Bucket implements Serializable {
    @Id
    private String id;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> products;

    public Bucket() {}

    public Bucket(String id, List<Product> products) {
        this.id = id;
        this.products = products;
    }

    public Bucket(String id) {
        this.id = id;
        this.products = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bucket)) return false;
        Bucket bucket = (Bucket) o;
        return Objects.equals(getId(), bucket.getId()) &&
                new ArrayList<>(getProducts()).equals(new ArrayList<>(bucket.getProducts()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProducts());
    }

    @Override
    public String toString() {
        return "Bucket{" +
                "id='" + id + '\'' +
                ", products=" + products +
                '}';
    }
}