package com.metechvn.validators;


import com.metechvn.validators.dtos.DynamicTypeValidatorDto;
import com.metechvn.validators.exceptions.DynamicTypeValidatorException;

public interface IDynamicTypeValidator {
    boolean test(DynamicTypeValidatorDto dynamicTypeValidatorDto) throws DynamicTypeValidatorException;
}
