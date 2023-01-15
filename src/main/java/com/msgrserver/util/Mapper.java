package com.msgrserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.msgrserver.model.dto.ActionDto;
import org.modelmapper.ModelMapper;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Set;

public class Mapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();
//    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Reflections reflections = new Reflections("com.msgrserver.model.dto");
        Set<Class<? extends ActionDto>> subTypes = reflections.getSubTypesOf(ActionDto.class);
        objectMapper.registerSubtypes(new ArrayList<>(subTypes));
    }

    public static <T> T map(Object fromValue, Class<T> toValueType) {
//        return modelMapper.map(fromValue, toValueType);
        return objectMapper.convertValue(fromValue, toValueType);
    }

    public static String toJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    public static <T> T fromJson(String json, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(json, valueType);
    }
}