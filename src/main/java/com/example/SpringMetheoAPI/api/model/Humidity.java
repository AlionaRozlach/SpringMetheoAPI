package com.example.SpringMetheoAPI.api.model;

public class Humidity {
    private double value;

    public Humidity(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
