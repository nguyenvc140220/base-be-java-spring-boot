package com.metechvn.validators.testers.impl;


import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;
import org.springframework.util.StringUtils;

public class LongMinTester implements ITester {
    /**
     * Check testValue is greater than validatorValue or not
     * @param validatorValue
     * @param testValue
     * @return
     * @throws DynamicTypeValidatorException if validatorValue or testValue is not a number
     */
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        if (!StringUtils.hasText(testValue)) return true;

        try {
            long validatorValueLong = Long.parseLong(validatorValue);
            long testValueLong = Long.parseLong(testValue);
            if (testValueLong >= validatorValueLong) {
                return true;
            }
        } catch (NumberFormatException e) {
            throw new DynamicTypeValidatorException("LongMinTester: validatorValue or testValue is not a "
                    + "number");
        }
        return false;
    }
}
