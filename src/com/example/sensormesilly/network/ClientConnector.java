package com.example.sensormesilly.network;

import com.example.sensormesilly.model.Measurement;
import com.example.sensormesilly.util.AESDecryptor;
import com.example.sensormesilly.util.Json;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ClientConnector {

    private static final String IP = "127.0.0.1";
    private static final String HOME_IP = "192.168.1.118";
    private static final String SCHOOL_IP = "10.200.130.32";
    private static final int PORT = 1233;
    private static final int ANDERS_PORT = 30001;
    private static Socket socket;

    private ClientConnector() throws IOException {
    }

    public static Socket getConnection() throws IOException {

        return new Socket(IP, ANDERS_PORT);

    }

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(IP, ANDERS_PORT)) {
            var out = new PrintWriter(socket.getOutputStream(), true);
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out.printf("get%s", "");
            out.flush();
            String line = in.readLine();
            AESDecryptor.Message message = Json.jsonToMessage(line);
            String decryptedText = AESDecryptor.decrypt(message.getCt(), message.getIv());
            Measurement measurement = Json.jsonToMeasurement(decryptedText);
            System.out.println(measurement.getHumidity());

        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | NoSuchAlgorithmException
                | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}

