package com.example.sensormesilly.network;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class ClientConnector {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 1234;
    private ClientConnector() {
    }

    public static Socket getConnection() throws IOException {
        try {
            return new Socket(IP, PORT);
        } catch (ConnectException e) {
            throw new ConnectException(e.getMessage());
        }
    }
}

