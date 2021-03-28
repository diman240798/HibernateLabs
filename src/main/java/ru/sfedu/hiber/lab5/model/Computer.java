package ru.sfedu.hiber.lab5.model;

import ru.sfedu.hiber.Constants;
import ru.sfedu.hiber.lab5.api.helper.InitializerData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = Constants.COMPUTER_ENTITY)
@Table(name = Constants.COMPUTER)
public class Computer extends Product {


    @Column
    private String processorName;
    @Column
    private int processorPower;
    @Column
    private String graphicsName;
    @Column
    private int graphicsVolume;
    @Column
    private boolean integratedWifi;
    @Column
    private boolean integratedBluetooth;


    public Computer() {
    }

    public Computer(long id, String name, double weight, double price, String processorName, int processorPower, String graphicsName, int graphicsVolume) {
        super(id, name, weight, price, InitializerData.CATEGORY_COMPUTER);
        this.processorName = processorName;
        this.processorPower = processorPower;
        this.graphicsName = graphicsName;
        this.graphicsVolume = graphicsVolume;
    }

    public Computer(long id, String name, double weight, double price, String processorName, int processorPower, String graphicsName, int graphicsVolume, boolean integratedWifi, boolean integratedBluetooth) {
        this(id, name, weight, price, processorName, processorPower, graphicsName, graphicsVolume);
        this.integratedWifi = integratedWifi;
        this.integratedBluetooth = integratedBluetooth;
    }

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public int getProcessorPower() {
        return processorPower;
    }

    public void setProcessorPower(int processorPower) {
        this.processorPower = processorPower;
    }

    public String getGraphicsName() {
        return graphicsName;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public int getGraphicsVolume() {
        return graphicsVolume;
    }

    public void setGraphicsVolume(int graphicsVolume) {
        this.graphicsVolume = graphicsVolume;
    }

    public boolean isIntegratedWifi() {
        return integratedWifi;
    }

    public void setIntegratedWifi(boolean integratedWifi) {
        this.integratedWifi = integratedWifi;
    }

    public boolean isIntegratedBluetooth() {
        return integratedBluetooth;
    }

    public void setIntegratedBluetooth(boolean integratedBluetooth) {
        this.integratedBluetooth = integratedBluetooth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Computer)) return false;
        Computer computer = (Computer) o;
        return getProcessorPower() == computer.getProcessorPower() &&
                getGraphicsVolume() == computer.getGraphicsVolume() &&
                isIntegratedWifi() == computer.isIntegratedWifi() &&
                isIntegratedBluetooth() == computer.isIntegratedBluetooth() &&
                Objects.equals(getProcessorName(), computer.getProcessorName()) &&
                Objects.equals(getGraphicsName(), computer.getGraphicsName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProcessorName(), getProcessorPower(), getGraphicsName(), getGraphicsVolume(), isIntegratedWifi(), isIntegratedBluetooth());
    }

    @Override
    public String toString() {
        return "Computer{" +
                "processorName='" + processorName + '\'' +
                ", processorPower=" + processorPower +
                ", graphicsName='" + graphicsName + '\'' +
                ", graphicsVolume=" + graphicsVolume +
                ", integratedWifi=" + integratedWifi +
                ", integratedBluetooth=" + integratedBluetooth +
                '}';
    }
}
