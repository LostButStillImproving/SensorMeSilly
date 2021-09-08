package com.example.sensormesilly.network;

import java.io.IOException;
import java.net.Socket;

public class ClientConnector {
    private ClientConnector() {
    }

    public static Socket getConnection(String ip, int port) throws IOException {
        return new Socket(ip, port) ;
    }
}

