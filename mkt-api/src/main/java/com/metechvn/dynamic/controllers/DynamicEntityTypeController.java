package com.metechvn.dynamic.controllers;

import com.metechvn.common.BaseResponse;
import com.metechvn.common.Constants;
import com.metechvn.dynamic.commands.CreateEntityTypeCommand;
import com.metechvn.dynamic.dtos.EntityTypeDto;
import com.metechvn.dynamic.commands.EntityTypeAddPropertyCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.Bus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/entity-type")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class DynamicEntityTypeController {

    private final Bus bus;

    @PostMapping({"", "/", "/create"})
    public BaseResponse<EntityTypeDto> createEntityType(@Valid @RequestBody CreateEntityTypeCommand cmd) {
        var entityType = bus.execute(cmd);

        return BaseResponse.onCreated(
                EntityTypeDto
                        .builder()
                        .id(entityType.getId())
                        .code(entityType.getCode())
                        .displayName(entityType.getDisplayName())
                        .creationTime(entityType.getCreationTime())
                        .build()
        );
    }

    @PostMapping({"/add-property"})
    public BaseResponse<Boolean> addProperty(@Valid @RequestBody EntityTypeAddPropertyCommand cmd) {
        System.out.println(bus.execute(cmd));

        return BaseResponse.onOk(true);
    }

}
