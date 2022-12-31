package com.msgrserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Mapper {

    <T> T map(Object fromValue, Class<T> toValueType);

    String toJson(Object value) throws JsonProcessingException;

    <T> T fromJson(String json, Class<T> valueType) throws JsonProcessingException;
}
