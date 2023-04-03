package com.metechvn.validators;


import com.metechvn.validators.dtos.DynamicTypeValidator;
import com.metechvn.validators.dtos.DynamicTypeValidatorDto;
import com.metechvn.validators.dtos.ValidationError;
import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.TesterTypeEnum;

import java.util.ArrayList;

public class DynamicTypeValidatorImpl implements IDynamicTypeValidator {
    @Override
    public boolean test(DynamicTypeValidatorDto dynamicTypeValidatorDto) throws DynamicTypeValidatorException {
        boolean result = true;
        var validationError = new ValidationError(dynamicTypeValidatorDto.getProperty());
        for (DynamicTypeValidator validator : dynamicTypeValidatorDto.getValidators()) {
            var testType = TesterTypeEnum.getTesterByType(validator.getType());
            var tester = testType.getTester();
            try {
                tester.test(validator.getValidatorValue(), dynamicTypeValidatorDto.getTestValue());
            } catch (DynamicTypeValidatorException e) {
                validationError.addError(
                        testType,
                        String.format("%s:%s", validator.getType(), validator.getValidatorValue())
                );

                result = false;
            }
        }

        if (!result) throw new DynamicTypeValidatorException(validationError, null);

        return result;
    }
}
