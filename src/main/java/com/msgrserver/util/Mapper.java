package com.msgrserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T map(Object fromValue, Class<T> toValueType) {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    public static String mapToJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

}