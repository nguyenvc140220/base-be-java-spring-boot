package com.metechvn.contacts.controllers;

import com.metechvn.common.BaseResponse;
import com.metechvn.contacts.commands.CreateSegmentationCommand;
import com.metechvn.contacts.dtos.SegmentationListDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.Bus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/segmentation")
public class SegmentationController {

    private final Bus bus;

    @PostMapping("")
    public BaseResponse<Object> createSegmentation(@Valid @RequestBody CreateSegmentationCommand cmd) {
        return BaseResponse.onCreated(SegmentationListDto.of(bus.execute(cmd)));
    }


}
