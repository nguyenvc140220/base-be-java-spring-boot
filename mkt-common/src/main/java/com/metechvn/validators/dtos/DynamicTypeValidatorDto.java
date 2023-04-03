package com.metechvn.validators.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DynamicTypeValidatorDto {
    private String property;
    private String testValue;
    private List<DynamicTypeValidator> validators = new ArrayList<>();

    public DynamicTypeValidatorDto(String property, Object testValue, List<DynamicTypeValidator> validators) {
        this.property = property;
        this.testValue = testValue == null ? null : String.valueOf(testValue);
        if (validators != null && !validators.isEmpty()) this.validators.addAll(validators);
    }

    /**
     * Convert json to DynamicTypeValidatorDto using ObjectMapper
     *
     * @param json
     * @return DynamicTypeValidatorDto
     */
    public static DynamicTypeValidatorDto fromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, DynamicTypeValidatorDto.class);
    }
}
