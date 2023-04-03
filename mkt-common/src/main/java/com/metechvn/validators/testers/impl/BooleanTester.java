package com.metechvn.validators.testers.impl;


import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;

public class BooleanTester implements ITester {
    /**
     * if validatorValue is '1' or 'true' then return true if testValue is '1' or '0' or equals without case-sensitive 'true' or 'false'
     *
     * @param validatorValue
     * @param testValue
     * @return
     * @throws DynamicTypeValidatorException if validatorValue is not '1' or 'true'
     */
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        if (validatorValue.equals("1") || validatorValue.equalsIgnoreCase("true")) {
            return testValue.equals("1") ||
                    testValue.equals("0") ||
                    testValue.equalsIgnoreCase("true") ||
                    testValue.equalsIgnoreCase("false");
        } else {
            throw new DynamicTypeValidatorException("validatorValue must be '1' or 'true'");
        }
    }
}
