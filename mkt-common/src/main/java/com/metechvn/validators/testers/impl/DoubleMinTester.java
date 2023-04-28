package com.metechvn.validators.testers.impl;

import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;
import org.springframework.util.StringUtils;

public class DoubleMinTester implements ITester {
    /**
     * Check testValue is greater than validatorValue or not
     *
     * @param validatorValue
     * @param testValue
     * @return true if testValue is match with validatorValue. otherwise return false
     * @throws DynamicTypeValidatorException if validatorValue or testValue is not a number
     */
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        if (!StringUtils.hasText(testValue)) return true;

        try {
            double validatorDouble = Double.parseDouble(validatorValue);
            double testDouble = Double.parseDouble(testValue);
            return testDouble >= validatorDouble;
        } catch (NumberFormatException e) {
            throw new DynamicTypeValidatorException("validatorValue or testValue is not a number");
        }
    }
}
