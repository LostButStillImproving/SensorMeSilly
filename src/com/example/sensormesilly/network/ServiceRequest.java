package com.example.sensormesilly.network;


import com.example.sensormesilly.model.Measurement;
import com.example.sensormesilly.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServiceRequest {
    PrintWriter out;
    BufferedReader in;
    private ServiceRequest(Socket connection) throws IOException {
        out = new PrintWriter(connection.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
    }

    public static ServiceRequest getServiceRequest() throws IOException {

        return new ServiceRequest(ClientConnector.getConnection());
    }
    public Measurement getMeasurement() {
        try {
            String data = getData();
            System.out.println(data);
            return Util.jsonToMeasurement(data);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getData() throws IOException {
        out.printf("get%s", "");
        out.flush();
        return in.readLine();
    }

    public void shutdownServer() {
        out.printf("reboot%s", "");
        out.close();
    }

    public void setSensorInterval(int seconds) {
        out.printf("interval%s", seconds);
        out.flush();
        out.close();
    }
}
