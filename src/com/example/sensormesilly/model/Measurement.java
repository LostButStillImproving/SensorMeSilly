package com.example.sensormesilly.model;

public class Measurement {

    final String time;
    final double temperature;
    final double humidity;

    public Measurement(String time, String temperature, String humidity) {
        this.time = time;
        this.temperature = Double.parseDouble(temperature);
        this.humidity = Double.parseDouble(humidity);
    }
}
