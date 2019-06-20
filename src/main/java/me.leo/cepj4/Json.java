package me.leo.cepj4;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Json {
    private static final ObjectMapper MAPPER;

    private static ObjectMapper createMapperWithCustomConfig() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }


    static {
        MAPPER = createMapperWithCustomConfig();
    }

    private Json() {
    }

    public static String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter objeto para json", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter json para objeto", e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter json para objeto", e);
        }
    }

    public static Map<String, Object> toMap(String json) {
        if(json.isEmpty()){
            return new HashMap<>();
        }

        return fromJson(json, new TypeReference<Map<String, Object>>() {
        });
    }

    public static <T> T convert(Object o, Class<T> type) {
        return MAPPER.convertValue(o, type);
    }
}
