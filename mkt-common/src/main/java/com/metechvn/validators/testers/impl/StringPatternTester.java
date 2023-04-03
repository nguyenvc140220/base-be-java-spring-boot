package com.metechvn.validators.testers.impl;


import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;

public class StringPatternTester implements ITester {
    /**
     * Check testValue is match with validatorValue or not
     *
     * @param validatorValue
     * @param testValue
     * @return true if testValue is match with validatorValue. otherwise return false
     * @throws DynamicTypeValidatorException if validatorValue is not a regex
     */
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        if (testValue != null && testValue.matches(validatorValue)) {
            return true;
        }
        return false;
    }
}
