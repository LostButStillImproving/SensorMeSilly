package com.example.sensormesilly.util;

import com.example.sensormesilly.model.Measurement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Json {
    private Json() {
    }
    public static AESDecryptor.Message jsonToMessage(String json) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<>() {
        });
        String iv = (String) map.get("iv");
        String cipherText =(String) map.get("ct");
        return new AESDecryptor.Message(iv,cipherText);
    }
    public static Measurement jsonToMeasurement(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<>() {
        });
        String time = (String) map.get("time");
        double temperature = Double.parseDouble(String.valueOf(map.get("temp")));
        double humidity = Double.parseDouble(String.valueOf(map.get("humi")));
        return new Measurement(time, temperature, humidity);
    }
}
