package com.msgrserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.aspectj.lang.annotation.SuppressAjWarnings;

import java.io.IOException;

public class Mapper {

    private final static Gson gson = new Gson();
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T map(Object fromValue, Class<T> toValueType) {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    public static String toJson(Object value) throws JsonProcessingException {
        return gson.toJson(value);
    }

    public static <T> T fromJson(String json, Class<T> valueType) throws JsonProcessingException {
        return gson.fromJson(json, valueType);
    }

    public static <T> T fromJson(String json, String className) throws ClassNotFoundException, JsonProcessingException {
        Class<?> clz = Class.forName(className);
        return (T) fromJson(json, clz);
    }
}