package com.example.sensormesilly.util;

import com.example.sensormesilly.model.Measurement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Util {
    private Util() {
    }
    public static Measurement jsonToMeasurement(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<>() {
        });
        String time = (String) map.get("time");
        String temperature = (String) map.get("temperature");
        String humidity = (String) map.get("humidity");
        return new Measurement(time, temperature, humidity);
    }
}
