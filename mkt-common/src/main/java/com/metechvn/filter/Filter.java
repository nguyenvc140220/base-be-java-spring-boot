package com.metechvn.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Filter extends Expression {

    private String field;
    private String operator;
    private Object value;

    public Filter() {
    }

    @Builder
    public Filter(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }
}
