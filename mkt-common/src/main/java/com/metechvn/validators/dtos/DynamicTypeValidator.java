package com.metechvn.validators.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DynamicTypeValidator {
    private String type;
    private String validatorValue;

    public static List<DynamicTypeValidator> fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, new TypeReference<List<DynamicTypeValidator>>() {
            });
        } catch (JsonProcessingException ignored) {
            return Collections.emptyList();
        }
    }
}
