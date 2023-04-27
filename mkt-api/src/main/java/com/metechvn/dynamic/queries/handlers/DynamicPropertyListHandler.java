package com.metechvn.dynamic.queries.handlers;

import com.metechvn.common.PageResponse;
import com.metechvn.dynamic.entities.DynamicEntityTypeProperty;
import com.metechvn.dynamic.entities.DynamicProperty;
import com.metechvn.dynamic.queries.DynamicPropertyListQuery;
import com.metechvn.dynamic.repositories.DynamicEntityTypePropertyRepository;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.dynamic.repositories.DynamicPropertyRepository;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@Transactional
@RequiredArgsConstructor
public class DynamicPropertyListHandler
        implements RequestHandler<PageResponse<DynamicProperty>, DynamicPropertyListQuery> {

    private final DynamicPropertyRepository dynamicPropertyRepository;
    private final DynamicEntityTypeRepository dynamicEntityTypeRepository;
    private final DynamicEntityTypePropertyRepository dynamicEntityTypePropertyRepository;

    private final Sort DEFAULT_SORT = Sort.by("creationTime");

    @Override
    public PageResponse<DynamicProperty> handle(DynamicPropertyListQuery query) {
        if (StringUtils.isEmpty(query.getEntityTypeCode())) return filterWithoutEntityType(query);

        return filterWithEntityType(query);
    }

    private PageResponse<DynamicProperty> filterWithEntityType(DynamicPropertyListQuery query) {
        var filterKeyword = StringUtils.isEmpty(query.getKeyword())
                ? null
                : query.getKeyword().trim().toLowerCase();

        var type = dynamicEntityTypeRepository.findByCode(query.getEntityTypeCode());
        if (type == null) {
            return new PageResponse<>(Collections.emptyList(), query.getPageNumber(), query.getPageNumber(), 0, 0);
        }

        var pagedResult = dynamicEntityTypePropertyRepository.findBy(
                type.getId(),
                filterKeyword,
                PageRequest.of(query.getPageNumber() - 1, query.getPageSize(), DEFAULT_SORT)
        );

        return new PageResponse<>(
                pagedResult.map(DynamicEntityTypeProperty::getProperty).toList(),
                query.getPageNumber(),
                query.getPageSize(),
                pagedResult.getTotalPages(),
                pagedResult.getTotalElements()
        );
    }

    private PageResponse<DynamicProperty> filterWithoutEntityType(DynamicPropertyListQuery query) {
        var filterKeyword = StringUtils.isEmpty(query.getKeyword())
                ? null
                : query.getKeyword().trim().toLowerCase();

        var pagedResult = dynamicPropertyRepository.findByKeyword(
                filterKeyword,
                PageRequest.of(query.getPageNumber() - 1, query.getPageSize(), DEFAULT_SORT)
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
