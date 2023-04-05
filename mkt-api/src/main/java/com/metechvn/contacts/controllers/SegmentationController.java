package com.metechvn.contacts.controllers;

import com.metechvn.common.BaseResponse;
import com.metechvn.common.PageResponse;
import com.metechvn.contacts.commands.CreateSegmentationCommand;
import com.metechvn.contacts.commands.DeleteSegmentationCommand;
import com.metechvn.contacts.dtos.SegmentationListDto;
import com.metechvn.contacts.queries.SegmentationListQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.Bus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/segmentation")
public class SegmentationController {

    private final Bus bus;

    @PostMapping("")
    public BaseResponse<SegmentationListDto> createSegmentation(@Valid @RequestBody CreateSegmentationCommand cmd) {
        return BaseResponse.onCreated(SegmentationListDto.of(bus.execute(cmd)));
    }

    @GetMapping("")
    public BaseResponse<PageResponse<SegmentationListDto>> getSegmentations(
            @RequestParam("page") int pageNumber,
            @RequestParam("size") int pageSize,
            @RequestParam(name = "keyword", required = false) String keyword) {

        var pagedResult = bus.execute(
                SegmentationListQuery.builder()
                        .pageNumber(pageNumber)
                        .pageSize(pageSize)
                        .keyword(keyword)
                        .build()
        );

        var response = new PageResponse<>(
                pagedResult.getContent().stream().map(SegmentationListDto::of).toList(),
                pageNumber,
                pageSize,
                pagedResult.getTotalPages(),
                pagedResult.getTotalElements()
        );

        return BaseResponse.onOk(response);
    }

    @DeleteMapping("")
    public BaseResponse<Boolean> deleteSegmentation(@RequestParam("id") UUID id) {
        return BaseResponse.onOk(bus.execute(new DeleteSegmentationCommand(id)));
    }

}
