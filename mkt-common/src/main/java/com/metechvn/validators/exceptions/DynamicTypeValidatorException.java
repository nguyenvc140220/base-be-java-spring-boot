package com.metechvn.validators.exceptions;

import com.metechvn.validators.dtos.ValidationError;

public class DynamicTypeValidatorException extends RuntimeException {
    private String message;
    private ValidationError error;

    public DynamicTypeValidatorException(String message) {
        this.message = message;
    }

    public DynamicTypeValidatorException(ValidationError error, String message) {
        this(message);
        this.error = error;
    }

    public DynamicTypeValidatorException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    public DynamicTypeValidatorException(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }

    public DynamicTypeValidatorException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    public DynamicTypeValidatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
    }

    public String getMessage() {
        return message;
    }

    public void setError(ValidationError error) {
        this.error = error;
    }

    public ValidationError getError() {
        return error;
    }
}
