package com.metechvn.validators.testers.impl;


import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;

public class RequiredTester implements ITester {
    /**
     * Check testValue is required or not
     * @param validatorValue
     * @param testValue
     * @return true if validatorValue is "1" and testValue is not null or not empty. otherwise return false
     * @throws DynamicTypeValidatorException
     */
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        return "1".equals(validatorValue) && testValue != null && !testValue.isEmpty();
    }
}
