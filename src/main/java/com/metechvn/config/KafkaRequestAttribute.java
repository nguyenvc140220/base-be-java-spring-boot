package com.metechvn.config;

import org.springframework.web.context.request.RequestAttributes;

import java.util.HashMap;
import java.util.Map;

public class KafkaRequestAttribute implements RequestAttributes {

    private final Map<String, Object> requestAttributeMap = new HashMap();

    public KafkaRequestAttribute() {
    }

    public Object getAttribute(String name, int scope) {
        return scope == 0 ? this.requestAttributeMap.get(name) : null;
    }

    public void setAttribute(String name, Object value, int scope) {
        if (scope == 0) {
            this.requestAttributeMap.put(name, value);
        } else {
            throw new RuntimeException(String.format("Scope of external request should be : Request."));
        }
    }

    public void removeAttribute(String name, int scope) {
        if (scope == 0) {
            this.requestAttributeMap.remove(name);
        } else {
            throw new RuntimeException(String.format("Scope of external request should be : Request."));
        }
    }

    public String[] getAttributeNames(int scope) {
        return scope == 0 ? (String[]) this.requestAttributeMap.keySet().toArray(new String[0]) : new String[0];
    }

    public void registerDestructionCallback(String name, Runnable callback, int scope) {
        throw new RuntimeException(String.format("Operation not supported."));
    }

    public Object resolveReference(String key) {
        throw new RuntimeException(String.format("Operation not supported."));
    }

    public String getSessionId() {
        return null;
    }

    public Object getSessionMutex() {
        return null;
    }
}