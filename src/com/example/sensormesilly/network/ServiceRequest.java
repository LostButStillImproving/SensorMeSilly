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
    private final Socket clientSocket;
    PrintWriter out;
    BufferedReader in;
    private ServiceRequest(Socket connection) throws IOException {
        this.clientSocket= connection;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
    }

    public static ServiceRequest getServiceRequest(Socket socket) throws IOException {

        return new ServiceRequest(socket);
    }
    public Measurement getMeasurement() {
        try {
            String data = getData();
            return Util.jsonToMeasurement(data);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getData() throws IOException {
        out.print("get");
        String msgRecv = in.readLine();
        clientSocket.close();
        System.out.println(msgRecv);
        return msgRecv;
    }

    public void shutdownServer() {
        out.print("shutdown%s");
        out.close();
    }

    public void setSensorInterval(int seconds) {
        out.printf("interval%s", seconds);
        out.close();
    }
}
