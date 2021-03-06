package ru.sfedu.hiber.lab5.model;

import ru.sfedu.hiber.Constants;
import ru.sfedu.hiber.lab5.api.helper.InitializerData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = Constants.FRIDGE)
public class Fridge extends Product implements Serializable {
    @Column
    private int volume;
    @Column
    private String color;
    @Column
    private int power;
    @Column
    private boolean noFrost;

    public Fridge() {}

    public Fridge(long id, String name, double weight, double price, int volume, String color, int power, boolean noFrost) {
        this(id, name, weight, price, volume, color, power);
        this.noFrost = noFrost;
    }

    public Fridge(long id, String name, double weight, double price, int volume, String color, int power) {
        super(id, name, weight, price, InitializerData.CATEGORY_FRIDGE);
        this.volume = volume;
        this.color = color;
        this.power = power;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isNoFrost() {
        return noFrost;
    }

    public void setNoFrost(boolean noFrost) {
        this.noFrost = noFrost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fridge)) return false;
        if (!super.equals(o)) return false;
        Fridge fridge = (Fridge) o;
        return getVolume() == fridge.getVolume() &&
                getPower() == fridge.getPower() &&
                isNoFrost() == fridge.isNoFrost() &&
                Objects.equals(getColor(), fridge.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getVolume(), getColor(), getPower(), isNoFrost());
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "volume=" + volume +
                ", color='" + color + '\'' +
                ", power=" + power +
                ", noFrost=" + noFrost +
                '}';
    }
}
