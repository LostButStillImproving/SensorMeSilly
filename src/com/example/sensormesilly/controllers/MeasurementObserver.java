package com.example.sensormesilly.controllers;

import com.example.sensormesilly.model.Measurement;

public interface MeasurementObserver {

    void update(Measurement measurement);
}
