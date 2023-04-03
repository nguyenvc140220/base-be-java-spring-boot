package com.metechvn.common;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponse<T> {
    List<T> content;
    int pageNumber;
    int pageSize;
    int totalPages;
    long totalElements;

    public PageResponse(Page<T> page) {
        this(page.getContent(), page.getNumber(), page.getNumberOfElements(), page.getTotalPages(), page.getTotalElements());
    }

    public PageResponse(List<T> content, int pageNumber, int pageSize, int totalPages, long totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public PageResponse() {
    }

}
