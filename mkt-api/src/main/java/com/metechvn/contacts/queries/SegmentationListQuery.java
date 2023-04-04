package com.metechvn.contacts.queries;

import com.metechvn.common.PageRequest;
import com.metechvn.common.PageResponse;
import com.metechvn.contacts.entities.Segmentation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import luongdev.cqrs.Request;

@Getter
@Setter
public class SegmentationListQuery extends PageRequest implements Request<PageResponse<Segmentation>> {

    private String keyword;

    @Builder
    public SegmentationListQuery(int pageNumber, int pageSize, String keyword) {
        super(pageNumber, pageSize);

        this.keyword = keyword;
    }
}
