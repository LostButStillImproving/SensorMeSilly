package com.example.sensormesilly.network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

public class ServiceRequest implements Callable<String> {
    private final Socket clientSocket;
    private final String msg;
    PrintWriter out;
    BufferedReader in;
    public ServiceRequest(Socket connection, String msg) throws IOException {
        this.clientSocket= connection;
        this.msg = msg;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
    }

    private String getData() throws IOException {
        out.printf("get");
        String msgRecv = in.readLine();
        clientSocket.close();
        System.out.println(msgRecv);
        return msgRecv;
    }

    private void shutdownServer() {
        out.printf("shutdown");
        out.close();
    }

    private void setSensorInterval(int seconds) {
        out.printf("interval%s", seconds);
        out.close();
    }

    @Override
    public String call() {
        try {
            if (msg.equals("get")) {

                return getData();
            }

            if (msg.equals("shutdown")) {

                shutdownServer();
            }

            if (msg.contains("interval")) {
                int intervalInSeconds = Integer.parseInt(msg.replace("interval", ""));
                setSensorInterval(intervalInSeconds);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return null;
    }
}
