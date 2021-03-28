package ru.sfedu.hiber.lab4.map_component.model;


import org.hibernate.annotations.Parent;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Table(name = "four_map_component_category")
public class Category4MapComponent implements Serializable {

    @Column
    private long number;
    @Column
    private long emptyfield;
    @Column
    private String name;

    @Parent
    protected Product4MapComponent parent;

    public Category4MapComponent() {}

    public Category4MapComponent(String name) {
        this.name = name;
    }

    public Category4MapComponent(long number, long emptyfield, String name) {
        this.number = number;
        this.emptyfield = emptyfield;
        this.name = name;
    }

    public Category4MapComponent(String name, Product4MapComponent parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product4MapComponent getParent() {
        return parent;
    }

    public void setParent(Product4MapComponent parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category4MapComponent)) return false;
        Category4MapComponent category = (Category4MapComponent) o;
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

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getEmptyfield() {
        return emptyfield;
    }

    public void setEmptyfield(long emptyfield) {
        this.emptyfield = emptyfield;
    }
}
