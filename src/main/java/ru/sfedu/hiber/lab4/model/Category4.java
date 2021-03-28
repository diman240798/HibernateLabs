package ru.sfedu.hiber.lab4.model;


import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class Category4  implements Serializable {

    @Id
    private String name;

    public Category4() {}

    public Category4(String name) {
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
        if (!(o instanceof Category4)) return false;
        Category4 category = (Category4) o;
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
