package com.metechvn.validators.testers.impl;


import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;
import org.springframework.util.StringUtils;

public class DatetimeMaxTester implements ITester {
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        if (!StringUtils.hasText(testValue)) return true;

        try {
            long validatorValueTimestamp = Long.parseLong(validatorValue);
            long testTimeStamp = Long.parseLong(testValue);
            if (testTimeStamp <= validatorValueTimestamp) {
                return true;
            }
        } catch (NumberFormatException e) {
            throw new DynamicTypeValidatorException(
                    "DatetimeMaxTester: validatorValue or testValue is not a number"
            );
        }
        return false;
    }
}
