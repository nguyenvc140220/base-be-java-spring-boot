package com.metechvn.validators.testers.impl;


import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import com.metechvn.validators.testers.ITester;
import org.springframework.util.StringUtils;

public class LongNegativeTester implements ITester {
    /**
     * if validatorValue is "1" and testValue is negative number, return true. if validatorValue is "0" and testValue is not negative number, return true. otherwise return false
     * @param validatorValue
     * @param testValue
     * @return
     * @throws DynamicTypeValidatorException if testValue is not a number, or validatorValue is not "0" or "1"
     */
    @Override
    public boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException {
        if (!StringUtils.hasText(testValue)) return true;

        if ("1".equals(validatorValue)) {
            try {
                long testValueLong = Long.parseLong(testValue);
                if (testValueLong < 0) {
                    return true;
                }
            } catch (NumberFormatException e) {
                throw new DynamicTypeValidatorException("testValue is not a number");
            }
        } else if ("0".equals(validatorValue)) {
            try {
                long testValueLong = Long.parseLong(testValue);
                if (testValueLong >= 0) {
                    return true;
                }
            } catch (NumberFormatException e) {
                throw new DynamicTypeValidatorException("testValue is not a number");
            }
        } else {
            throw new DynamicTypeValidatorException("validatorValue is not \"0\" or \"1\"");
        }
        return false;
    }
}
