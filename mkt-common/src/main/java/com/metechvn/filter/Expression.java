package com.metechvn.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class Expression {

    private String type;
    private Object payload;

    protected Expression() {
    }

    public Expression(String type, Object payload) {
        this();

        if (StringUtils.hasText(type)) this.type = type;

        this.payload = payload;
    }

    public static Expression and(Object payload) {
        return new Expression("AND", payload);
    }

    public static Expression or(Object payload) {
        return new Expression("OR", payload);
    }

    public static Expression not(Object payload) {
        return new Expression("NOT", payload);
    }
}
