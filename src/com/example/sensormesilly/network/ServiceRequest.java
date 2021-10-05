package com.example.sensormesilly.network;


import com.example.sensormesilly.model.Measurement;
import com.example.sensormesilly.util.AESDecryptor;
import com.example.sensormesilly.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.crypto.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
            String decryptedMessage = decrypt(data);
            return Json.jsonToMeasurement(decryptedMessage);
        } catch (IOException | InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException
                | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String decrypt(String data) throws JsonProcessingException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        AESDecryptor.Message message = Json.jsonToMessage(data);
        return AESDecryptor.decrypt(message.getCt(), message.getIv());
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
