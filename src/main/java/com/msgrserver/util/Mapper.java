package com.msgrserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class Mapper {

    private final static Gson gson = new Gson();
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T map(Object fromValue, Class<T> toValueType) {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    public static String mapToJson(Object value) throws JsonProcessingException {
//        return objectMapper.writeValueAsString(value);
        return gson.toJson(value);
    }

    public static <T> T mapToObject(String content, Class<T> valueType) throws JsonProcessingException {
//        return objectMapper.readValue(content, valueType);
        return gson.fromJson(content, valueType);
    }
}