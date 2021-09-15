package com.example.sensormesilly.controllers;

import com.example.sensormesilly.model.Measurement;
import com.example.sensormesilly.model.ViewModelMeasurement;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller implements MeasurementObserver {

    ViewModelMeasurement measurementViewModel;
    XYChart.Series<String, Number> seriesTemperature;
    XYChart.Series<String, Number> seriesHumidity;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Button shutdownButton;
    @FXML
    Button stopListeningButton;
    @FXML
    Button startListeningButton;
    @FXML
    TextField intervalField;
    @FXML
    Button intervalButton;


    public void initialize() {

        measurementViewModel = ViewModelMeasurement.getInstance();
        measurementViewModel.subscribe(this);
        createChart();
    }

    private void createChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time/s");
        xAxis.setAnimated(false);
        yAxis.setLabel("Temperature");
        yAxis.setAnimated(false);

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        anchorPane.getChildren().add(lineChart);
        lineChart.setTitle("Realtime JavaFX Charts");
        lineChart.setAnimated(false);

        seriesTemperature = new XYChart.Series<>();
        seriesHumidity = new XYChart.Series<>();
        seriesTemperature.setName("Temperature");
        seriesHumidity.setName("Humidity");
        lineChart.getData().add(seriesTemperature);
        lineChart.getData().add(seriesHumidity);
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
            int seconds = Integer.parseInt(intervalField.getText());
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
        Platform.runLater(() -> {
            try {
                updateChart(measurement);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }
    private void updateChart(Measurement measurement) throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date time = simpleDateFormat.parse(measurement.getTime());
        seriesTemperature.getData().add(new XYChart.Data<>(simpleDateFormat.format(time), measurement.getTemperature()));
        seriesHumidity.getData().add(new XYChart.Data<>(simpleDateFormat.format(time), measurement.getHumidity()));
        if (seriesTemperature.getData().size() > 20) {
            seriesTemperature.getData().remove(0);
            seriesHumidity.getData().remove(0);
        }
    }
}