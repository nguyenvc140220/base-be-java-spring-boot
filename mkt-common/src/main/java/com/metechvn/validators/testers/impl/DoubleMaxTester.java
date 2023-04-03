package com.metechvn.validators.testers.impl;

import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;

public class DoubleMaxTester implements ITester {
    /**
     * Check testValue is less than validatorValue or not
     * @param validatorValue
     * @param testValue
     * @return
     * @throws DynamicTypeValidatorException if validatorValue is not a number, or testValue is not a number
     */
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        try {
            double validatorDouble = Double.parseDouble(validatorValue);
            double testDouble = Double.parseDouble(testValue);
            return testDouble <= validatorDouble;
        } catch (NumberFormatException e) {
            throw new DynamicTypeValidatorException(
                    "DoubleMaxTester: validatorValue or testValue is not a number");
        }
    }
}
