package com.metechvn.dynamic.controllers;

import com.metechvn.common.BaseResponse;
import com.metechvn.common.PageResponse;
import com.metechvn.dynamic.commands.CreatePropertyCommand;
import com.metechvn.dynamic.commands.UpdatePropertyCommand;
import com.metechvn.dynamic.dtos.PropertyListDto;
import com.metechvn.dynamic.queries.DynamicPropertyFilterQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.Bus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/property")
public class DynamicPropertyController {

    private final Bus bus;

    @PostMapping({""})
    public BaseResponse<Boolean> createProperty(@Valid @RequestBody CreatePropertyCommand cmd) {
        System.out.println(bus.execute(cmd));
        return BaseResponse.onOk(true);
    }

    @PutMapping({""})
    public BaseResponse<Boolean> updateProperty(@Valid @RequestBody UpdatePropertyCommand cmd) {
        System.out.println(bus.execute(cmd));
        return BaseResponse.onOk(true);
    }

    @GetMapping("")
    public BaseResponse<PageResponse<PropertyListDto>> filter(
            @RequestParam("page") int pageNumber,
            @RequestParam("size") int pageSize,
            @RequestParam(name = "type", required = false) String entityTypeCode,
            @RequestParam(name = "keyword", required = false) String keyword) {

        var query = DynamicPropertyFilterQuery.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .entityTypeCode(entityTypeCode)
                .keyword(keyword)
                .build();

        var pagedResult = bus.execute(query);
        var response = new PageResponse<>(
                pagedResult.getContent().stream().map(PropertyListDto::of).toList(),
                pageNumber,
                pageSize,
                pagedResult.getTotalPages(),
                pagedResult.getTotalElements()
        );

        return BaseResponse.onOk(response);
    }
}