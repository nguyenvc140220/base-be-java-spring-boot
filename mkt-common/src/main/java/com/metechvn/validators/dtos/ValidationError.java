package com.metechvn.validators.dtos;

import com.metechvn.validators.testers.TesterTypeEnum;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationError {

    private String property;
    private final List<Error> errors = new ArrayList<>();

    public ValidationError(String property) {
        this.property = property;
    }

    public void addError(TesterTypeEnum type, String error) {
        if (StringUtils.hasText(error) && type != null) this.errors.add(new Error(type, error));
    }

    public void addError(Error error) {
        if (error != null) this.errors.add(error);
    }

    static class Error {
        private TesterTypeEnum type;
        private String error;

        public Error() {
        }

        public Error(TesterTypeEnum type, String error) {
            this.type = type;
            this.error = error;
        }

        public TesterTypeEnum getType() {
            return type;
        }

        public String getError() {
            return error;
        }
    }
}
