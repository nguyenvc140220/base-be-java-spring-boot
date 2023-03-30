package com.metechvn.dynamic.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class BatchDynamicEntityDto<T extends Serializable> implements Serializable {

    private String tenant;
    private String entityType;
    private final List<FlattenDynamicEntityDto<T>> batches = new ArrayList<>();

    private BatchDynamicEntityDto() {
    }

    @SafeVarargs
    public BatchDynamicEntityDto(String tenant, String entityType, FlattenDynamicEntityDto<T>... entities) {
        this.tenant = tenant;
        add(entities);
    }

    @SafeVarargs
    public final void add(FlattenDynamicEntityDto<T>... entities) {
        if (entities.length > 0) {
            this.batches.addAll(List.of(entities));
        }
    }

    public int batchSize() {
        return this.batches.size();
    }
}
