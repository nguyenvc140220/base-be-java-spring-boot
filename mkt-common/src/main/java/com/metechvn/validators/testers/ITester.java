package com.metechvn.validators.testers;


import com.metechvn.validators.exceptions.DynamicTypeValidatorException;

public interface ITester {
    boolean test(String validatorValue, String testValue) throws DynamicTypeValidatorException;
}
