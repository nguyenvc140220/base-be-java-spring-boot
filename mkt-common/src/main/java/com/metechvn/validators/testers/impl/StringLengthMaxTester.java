package com.metechvn.validators.testers.impl;


import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;

public class StringLengthMaxTester implements ITester {
    /**
     * Cast validatorValue to int and compare testValue length with validatorValue
     *
     * @param validatorValue
     * @param testValue
     * @return true if testValue length is less than or equal to validatorValue. otherwise return false
     * @throws DynamicTypeValidatorException if validatorValue is not a number
     */
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        if (testValue == null) return true;

        try {
            if (testValue.length() <= Integer.parseInt(validatorValue)) {
                return true;
            }

            throw new DynamicTypeValidatorException(String.format("minLength:%s", validatorValue));
        } catch (NumberFormatException e) {
            throw new DynamicTypeValidatorException("StringLengthMaxTester: validatorValue is not a number");
        }
    }
}
