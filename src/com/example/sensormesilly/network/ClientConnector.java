package com.example.sensormesilly.network;

import java.io.IOException;
import java.net.Socket;

public class ClientConnector {

    private static final String IP = "127.0.0.1";
    private static final String RASBERRY_PI_IP = "192.168.1.118";
    private static final int PORT = 1234;
    private static Socket socket;

    private ClientConnector() {
    }

    public static Socket getConnection() throws IOException {

        return new Socket(IP, PORT);
    }
}

