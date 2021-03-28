package ru.sfedu.hiber.lab4.component.model;


import org.hibernate.annotations.Parent;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Table(name = "four_component_category")
public class Category4Component implements Serializable {

    protected long number;

    @Column(nullable = false)
    private String name;

    @Parent
    protected Product4Component parent;

    public Category4Component() {}

    public Category4Component(long number, String name) {
        this.number = number;
        this.name = name;
    }

    public Category4Component(String name) {
        this.name = name;
    }

    public Category4Component(String name, Product4Component parent) {
        this.name = name;
        this.parent = parent;
    }

    public Category4Component(Long number, String name, Product4Component parent) {
        this.number = number;
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product4Component getParent() {
        return parent;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setParent(Product4Component parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category4Component)) return false;
        Category4Component category = (Category4Component) o;
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
}
