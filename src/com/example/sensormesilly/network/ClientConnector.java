package com.example.sensormesilly.network;

import java.io.IOException;
import java.net.Socket;

public class ClientConnector {

    private static final String IP = "127.0.0.1";
    private static final String HOME_IP = "192.168.1.118";
    private static final String SCHOOL_IP = "10.200.130.32";

    private static final int PORT = 1233;
    private static Socket socket;

    private ClientConnector() {
    }

    public static Socket getConnection() throws IOException {

        if (socket == null) {

            return new Socket(SCHOOL_IP, PORT);
        }
        return socket;
    }
}

