package com.dmtlabs.aidocentserver.global.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class JsonColumnListConverter<T> implements AttributeConverter<List<T>, String> {
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<T> objectList) {
        if (objectList != null && !objectList.isEmpty()) {
            try {
                return objectMapper.writeValueAsString(objectList);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return "[]";
        }
    }

    @Override
    public List<T> convertToEntityAttribute(String jsonColumnStr) {
        try {
            return objectMapper.readValue(jsonColumnStr, new TypeReference<>() {});
        } catch (Exception e) {
            return null;
        }
    }
}
