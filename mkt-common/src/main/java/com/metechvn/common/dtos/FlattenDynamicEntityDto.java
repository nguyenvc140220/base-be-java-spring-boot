package com.metechvn.common.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FlattenDynamicEntityDto<T extends Serializable> implements Map<String, Object> {

    private T id;

    @JsonIgnore
    private final Map<String, Object> properties = new HashMap<>();

    private FlattenDynamicEntityDto() {
    }

    public FlattenDynamicEntityDto(T id) {
        this.id = id;
        this.put("id", id);
    }

    @Override
    public int size() {
        return this.properties.size();
    }

    @Override
    public boolean isEmpty() {
        return this.properties.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.properties.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.properties.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.properties.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return this.properties.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.properties.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        this.properties.putAll(m);
    }

    @Override
    public void clear() {
        this.properties.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.properties.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.properties.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.properties.entrySet();
    }

    public T getId() {
        return id;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }
}