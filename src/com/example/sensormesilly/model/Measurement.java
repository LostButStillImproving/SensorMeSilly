package com.example.sensormesilly.model;

public record Measurement(String time, double temperature, double humidity) {

    public String getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

}
