package com.metechvn.validators;

import com.metechvn.validators.dtos.DynamicTypeValidator;
import com.metechvn.validators.dtos.DynamicTypeValidatorDto;
import com.metechvn.validators.exceptions.DynamicTypeValidatorException;

import java.util.Arrays;

public class ValidatorExampleUsageMain {

    public static void main(String[] args) {
        try {
            IDynamicTypeValidator dynamicTypeValidator = new DynamicTypeValidatorImpl();
            DynamicTypeValidatorDto validatorDto = new DynamicTypeValidatorDto();
            String shouldFailValue = "";
            validatorDto.setTestValue(shouldFailValue);
            validatorDto.setValidators(Arrays.asList(
                    DynamicTypeValidator.builder().type("required").validatorValue("1").build(),
                    DynamicTypeValidator.builder().type("string_length_min").validatorValue("2").build()
            ));
            System.out.println(dynamicTypeValidator.test(validatorDto)); // should print false

            String shouldPassValue = "abc";
            validatorDto.setTestValue(shouldPassValue);
            System.out.println(dynamicTypeValidator.test(validatorDto)); // should print true
        } catch (DynamicTypeValidatorException e) {
            e.printStackTrace();
        }


    }
}
