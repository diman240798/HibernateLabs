package ru.sfedu.hiber.lab5.model;


import ru.sfedu.hiber.Constants;
import ru.sfedu.hiber.lab5.api.helper.InitializerData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = Constants.SODA)
public class Soda extends Product implements Serializable {
    @Column
    private String flavour;
    @Column
    private boolean sparkled = false;

    public Soda() {
    }

    public Soda(long id, String name, double weight, double price, String flavour, boolean sparkled) {
        this(id, name, weight, price, flavour);
        this.sparkled = sparkled;
    }

    public Soda(long id, String name, double weight, double price, String flavour) {
        super(id, name, weight, price, InitializerData.CATEGORY_SODA);
        this.flavour = flavour;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public boolean isSparkled() {
        return sparkled;
    }

    public void setSparkled(boolean sparkled) {
        this.sparkled = sparkled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Soda)) return false;
        Soda soda = (Soda) o;
        return isSparkled() == soda.isSparkled() &&
                Objects.equals(getFlavour(), soda.getFlavour());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFlavour(), isSparkled());
    }

    @Override
    public String toString() {
        return "Soda{" +
                "flavour='" + flavour + '\'' +
                ", sparkled=" + sparkled +
                '}';
    }
}
