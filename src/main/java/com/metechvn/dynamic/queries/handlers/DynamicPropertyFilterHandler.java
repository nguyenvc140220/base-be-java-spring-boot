package com.metechvn.dynamic.queries.handlers;

import com.metechvn.common.PageResponse;
import com.metechvn.dynamic.entities.DynamicEntityTypeProperty;
import com.metechvn.dynamic.entities.DynamicProperty;
import com.metechvn.dynamic.queries.DynamicPropertyFilterQuery;
import com.metechvn.dynamic.repositories.DynamicEntityTypePropertyRepository;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.dynamic.repositories.DynamicPropertyRepository;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DynamicPropertyFilterHandler
        implements RequestHandler<PageResponse<DynamicProperty>, DynamicPropertyFilterQuery> {

    private final DynamicPropertyRepository dynamicPropertyRepository;
    private final DynamicEntityTypeRepository dynamicEntityTypeRepository;
    private final DynamicEntityTypePropertyRepository dynamicEntityTypePropertyRepository;

    @Override
    public PageResponse<DynamicProperty> handle(DynamicPropertyFilterQuery query) {
        if (StringUtils.isEmpty(query.getEntityTypeCode())) return filterWithoutEntityType(query);

        return filterWithEntityType(query);
    }

    private PageResponse<DynamicProperty> filterWithEntityType(DynamicPropertyFilterQuery query) {
        var filterKeyword = StringUtils.isEmpty(query.getKeyword())
                ? null
                : "%" + query.getKeyword() + "%s";

        var type = dynamicEntityTypeRepository.findByCode(query.getEntityTypeCode());
        if (type == null) {
            return new PageResponse<>(Collections.emptyList(), query.getPageNumber(), query.getPageNumber(), 0, 0);
        }

        var pagedResult = dynamicEntityTypePropertyRepository.findBy(
                type,
                filterKeyword,
                PageRequest.of(query.getPageNumber() - 1, query.getPageSize())
        );

        return new PageResponse<>(
                pagedResult.map(DynamicEntityTypeProperty::getProperty).toList(),
                query.getPageNumber(),
                query.getPageSize(),
                pagedResult.getTotalPages(),
                pagedResult.getTotalElements()
        );
    }

    private PageResponse<DynamicProperty> filterWithoutEntityType(DynamicPropertyFilterQuery query) {
        var filterKeyword = StringUtils.isEmpty(query.getKeyword())
                ? null
                : "%" + query.getKeyword() + "%s";

        var pagedResult = dynamicPropertyRepository
                .findByKeyword(filterKeyword, PageRequest.of(query.getPageNumber() - 1, query.getPageSize()));

        return new PageResponse<>(
                pagedResult.toList(),
                query.getPageNumber(),
                query.getPageSize(),
                pagedResult.getTotalPages(),
                pagedResult.getTotalElements()
        );
    }
}
