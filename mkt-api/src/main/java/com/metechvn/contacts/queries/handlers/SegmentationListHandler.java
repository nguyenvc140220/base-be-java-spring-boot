package com.metechvn.contacts.queries.handlers;

import com.metechvn.common.PageResponse;
import com.metechvn.contacts.entities.Segmentation;
import com.metechvn.contacts.queries.SegmentationListQuery;
import com.metechvn.contacts.repositories.SegmentationRepository;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SegmentationListHandler implements RequestHandler<PageResponse<Segmentation>, SegmentationListQuery> {

    private final SegmentationRepository segmentationRepository;

    @Override
    public PageResponse<Segmentation> handle(SegmentationListQuery query) {
        var filterKeyword = StringUtils.isEmpty(query.getKeyword())
                ? null
                : ("%" + query.getKeyword() + "%").toLowerCase();

        var pagedResult = segmentationRepository.getSegmentations(
                filterKeyword,
                PageRequest.of(
                        query.getPageNumber() - 1,
                        query.getPageSize(),
                        Sort.by("lastModificationTime").descending()
                                .and(Sort.by("creationTime").descending())
                )
        );

        return new PageResponse<>(
                pagedResult.toList(),
                query.getPageNumber(),
                query.getPageSize(),
                pagedResult.getTotalPages(),
                pagedResult.getTotalElements()
        );
    }
}
