package com.example.sensormesilly.controllers;

import com.example.sensormesilly.model.Measurement;
import com.example.sensormesilly.model.ViewModelMeasurement;
import javafx.application.Platform;
import javafx.fxml.FXML;

import java.io.IOException;

public class Controller implements MeasurementObserver {
    ViewModelMeasurement measurementViewModel;

    @FXML
    public void initialize() {
        measurementViewModel = ViewModelMeasurement.getInstance();
        measurementViewModel.subscribe(this);
    }

    @FXML
    protected void onShutdownClick() {
        try {
            measurementViewModel.shutdownServer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onUpdateIntervalClick() {
        try {
            int seconds = 5;
            measurementViewModel.updateInterval(seconds);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onStartListeningClick() {
        measurementViewModel.startListening();
    }
    @FXML
    protected void onStopListeningClick() {
        measurementViewModel.stopListening();
    }

    @Override
    public void update(Measurement measurement) {
        Platform.runLater(() -> updateChart(measurement));
    }

    private void updateChart(Measurement measurement) {
        System.out.println(measurement.toString());
    }
}