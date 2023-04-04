package com.metechvn.dynamic.queries;

import com.metechvn.common.PageRequest;
import com.metechvn.common.PageResponse;
import com.metechvn.dynamic.entities.DynamicProperty;
import lombok.*;
import luongdev.cqrs.Request;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DynamicPropertyListQuery extends PageRequest implements Request<PageResponse<DynamicProperty>> {

    private String entityTypeCode;
    private String keyword;

    @Builder
    public DynamicPropertyListQuery(int pageNumber, int pageSize, String entityTypeCode, String keyword) {
        super(pageNumber, pageSize);
        this.entityTypeCode = entityTypeCode;
        this.keyword = keyword;
    }
}
