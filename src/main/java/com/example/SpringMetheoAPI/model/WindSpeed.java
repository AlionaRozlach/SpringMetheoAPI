package com.example.SpringMetheoAPI.model;

public class WindSpeed {
    private double value;

    public WindSpeed(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
