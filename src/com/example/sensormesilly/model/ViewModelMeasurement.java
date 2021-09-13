package com.example.sensormesilly.model;


import com.example.sensormesilly.controllers.MeasurementObserver;
import com.example.sensormesilly.network.ClientConnector;
import com.example.sensormesilly.network.ServiceRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


interface ObservableMeasurement {
    void subscribe(MeasurementObserver observerOfMeasurement);
}
public class ViewModelMeasurement implements  ObservableMeasurement {
    private static ViewModelMeasurement instance = null;
    private final ArrayList<MeasurementObserver> listOfObservers = new ArrayList<>();
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private ViewModelMeasurement() {
        startListening();
    }

    public static ViewModelMeasurement getInstance()
    {
        if (instance == null)
            instance = new ViewModelMeasurement();

        return instance;
    }

    public void startListening() {
        if (executorService.isShutdown()) {
            executorService = Executors.newSingleThreadScheduledExecutor();
        }
        executorService.scheduleAtFixedRate(() -> {
                    try {
                        Measurement measurement = getMeasurement();
                        listOfObservers.forEach(
                                observer -> observer.update(measurement));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, 0, 1,
                        TimeUnit.SECONDS);
    }
    public void stopListening() {
        executorService.shutdown();
    }
    public void updateInterval(int seconds) throws IOException {
        ServiceRequest.getServiceRequest(ClientConnector.getConnection())
                .setSensorInterval(seconds);
    }

    private Measurement getMeasurement() throws IOException {

        return ServiceRequest.getServiceRequest(ClientConnector.getConnection())
                .getMeasurement();
    }
    public void shutdownServer() throws IOException {
        ServiceRequest.getServiceRequest(ClientConnector.getConnection())
                .shutdownServer();
    }
    @Override
    public void subscribe(MeasurementObserver observerOfMeasurement) {

        listOfObservers.add(observerOfMeasurement);
    }
}
