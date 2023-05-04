package com.metechvn.dynamic.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@NoArgsConstructor
public class BatchProcessResult {
    private String tenant;
    private String entityType;
    private final List<Map<String, Object>> successRows = new ArrayList<>();
    private final List<Map<String, Object>> errorRows = new ArrayList<>();

    public BatchProcessResult(String tenant, String entityType) {
        this();
        this.tenant = tenant;
        this.entityType = entityType;
    }

    public static BatchProcessResult empty() {
        return new BatchProcessResult();
    }

    public BatchProcessResult success(Map<String, Object> row) {
        return success(Collections.singletonList(row));
    }

    public BatchProcessResult success(Collection<Map<String, Object>> rows) {
        this.successRows.addAll(rows);

        return this;
    }

    public BatchProcessResult error(Map<String, Object> row) {
        return error(Collections.singletonList(row));
    }

    public BatchProcessResult error(Collection<Map<String, Object>> rows) {
        this.errorRows.addAll(rows);

        return this;
    }
}
