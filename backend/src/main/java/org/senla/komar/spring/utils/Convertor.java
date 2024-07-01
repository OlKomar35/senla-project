package org.senla.komar.spring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Convertor {
    private final ObjectMapper objectMapper;

    public Convertor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public  <T> T getEntityDtoFromJson(String json, Class<T> dtoClass) {
        try {
            return objectMapper.readValue(json, dtoClass);
        } catch (JsonProcessingException e) {
            System.out.println("Что-то пошло не так с парсингом json: " + e.getMessage());
            return null;
        }
    }
    public String convertDtoToJson(Object dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            System.out.println("Что-то пошло не так с преобразованием объекта в JSON: " + e.getMessage());
            return null;
        }
    }
}
