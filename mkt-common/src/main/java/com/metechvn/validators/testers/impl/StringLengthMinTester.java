package com.metechvn.validators.testers.impl;


import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;

public class StringLengthMinTester implements ITester {
    /**
     * Check testValue length is greater than validatorValue or not
     *
     * @param validatorValue
     * @param testValue
     * @return true if testValue length is greater than validatorValue. otherwise return false
     * @throws DynamicTypeValidatorException if validatorValue is not a number
     */
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        if (testValue == null) return true;

        try {
            if (testValue.length() >= Integer.parseInt(validatorValue)) {
                return true;
            }

            throw new DynamicTypeValidatorException(String.format("maxLength:%s", validatorValue));
        } catch (NumberFormatException e) {
            throw new DynamicTypeValidatorException("StringLengthMinTester: validatorValue is not a number");
        }
    }
}
