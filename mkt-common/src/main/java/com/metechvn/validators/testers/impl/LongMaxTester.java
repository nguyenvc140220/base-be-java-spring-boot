package com.metechvn.validators.testers.impl;


import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;
import org.springframework.util.StringUtils;

public class LongMaxTester implements ITester {
    /**
     * Check testValue is less than validatorValue or not
     *
     * @param validatorValue
     * @param testValue
     * @return true if testValue is less than validatorValue. otherwise return false
     * @throws DynamicTypeValidatorException if validatorValue is not a number, or testValue is not a number
     */
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        if (!StringUtils.hasText(testValue)) return true;

        try {
            long validatorLong = Long.parseLong(validatorValue);
            long testLong = Long.parseLong(testValue);
            if (testLong <= validatorLong) {
                return true;
            }
        } catch (NumberFormatException e) {
            throw new DynamicTypeValidatorException(
                    "LongMaxTester: validatorValue or testValue is not a number");
        }
        return false;
    }
}
