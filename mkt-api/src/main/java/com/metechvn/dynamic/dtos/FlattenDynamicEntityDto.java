package com.metechvn.dynamic.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.metechvn.common.FullAuditDto;
import com.metechvn.common.persistent.FullAuditedEntity;
import com.metechvn.dynamic.entities.DynamicEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

public class FlattenDynamicEntityDto<T extends Serializable>
        extends FullAuditDto<UUID, UUID> implements Map<String, Object> {

    @JsonIgnore
    private final Map<String, Object> properties = new HashMap<>();

    private FlattenDynamicEntityDto() {
    }

    public FlattenDynamicEntityDto(DynamicEntity entity) {
        this.put("id", entity.getId());
    }

    @Override
    protected <E extends FullAuditedEntity<UUID, UUID>> void apply(E e) {
        super.apply(e);

        var fields = this.getClass().getSuperclass().getDeclaredFields();
        for (var field : fields) {
            try {
                field.setAccessible(true);
                this.properties.put(field.getName(), field.get(this));
            } catch (Exception ignored) {
            }
        }

    }

    public static FlattenDynamicEntityDto<UUID> of(DynamicEntity entity) {
        var dto = new FlattenDynamicEntityDto<UUID>();
        dto.apply(entity);

        return dto;
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

    @Nullable
    @Override
    public Object put(String key, Object value) {
        return this.properties.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.properties.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ?> m) {
        this.properties.putAll(m);
    }

    @Override
    public void clear() {
        this.properties.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return this.properties.keySet();
    }

    @NotNull
    @Override
    public Collection<Object> values() {
        return this.properties.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.properties.entrySet();
    }
}
