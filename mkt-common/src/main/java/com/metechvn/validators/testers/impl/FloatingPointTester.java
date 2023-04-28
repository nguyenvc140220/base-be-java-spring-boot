package com.metechvn.validators.testers.impl;


import com.metechvn.validators.commons.Constants;
import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;
import org.springframework.util.StringUtils;

public class FloatingPointTester implements ITester {
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        if (!StringUtils.hasText(testValue)) return true;

        try {
            int validatorValueInt = Integer.parseInt(validatorValue);
            try {
                Double.parseDouble(testValue);
            } catch (NumberFormatException e) {
                throw new DynamicTypeValidatorException(
                        String.format("FloatingPointTester: testValue is not a number: %s", testValue)
                );

            }
            String[] split = testValue.split(Constants.FLOATING_POINT_LITERAL);
            if (split.length == 1 || (split.length == 2 && split[1].length() <= validatorValueInt)) {
                return true;
            }
        } catch (NumberFormatException e) {
            throw new DynamicTypeValidatorException("FloatingPointTester: validatorValue is not a number");
        }
        return false;
    }
}
