package ru.sfedu.hiber.lab5.model;

import org.hibernate.annotations.Cascade;
import ru.sfedu.hiber.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = Constants.CATEGORY)
public class Category implements Serializable {
    @Id
    private String name;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(getName(), category.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productList;

    public List getProductList() {
        return productList;
    }

    public  void setProductList(List productList) {
        this.productList = productList;
    }
}
