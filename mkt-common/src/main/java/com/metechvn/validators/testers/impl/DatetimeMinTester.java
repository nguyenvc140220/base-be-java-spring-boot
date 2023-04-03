package com.metechvn.validators.testers.impl;


import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;

public class DatetimeMinTester implements ITester {
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        try {
            long validatorValueTimestamp = Long.parseLong(validatorValue);
            long testTimeStamp = Long.parseLong(testValue);
            if (testTimeStamp >= validatorValueTimestamp) {
                return true;
            }
        } catch (NumberFormatException e) {
            throw new DynamicTypeValidatorException(
                    "DatetimeMinTester: validatorValue or testValue is not a number"
            );
        }
        return false;
    }
}
