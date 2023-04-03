package com.metechvn.validators.testers.impl;


import com.metechvn.validators.commons.Constants;
import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;

public class MaxChoiceTester implements ITester {
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        try {
            int validatorValueInt = Integer.parseInt(validatorValue);
            if (testValue != null && testValue.split(Constants.LIST_SEP).length <= validatorValueInt) {
                return true;
            }
        } catch (NumberFormatException e) {
            throw new DynamicTypeValidatorException("MaxChoiceTester: validatorValue is not a number");
        }
        return false;
    }
}
