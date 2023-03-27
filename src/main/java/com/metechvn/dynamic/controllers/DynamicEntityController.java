package com.metechvn.dynamic.controllers;

import com.metechvn.common.BaseResponse;
import com.metechvn.dynamic.commands.CreateEntityCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.Bus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/entity")
public class DynamicEntityController {

    private final Bus bus;

    @PostMapping({"", "/", "/create"})
    public BaseResponse<Boolean> createEntityType(@Valid @RequestBody CreateEntityCommand cmd) {
        System.out.println(bus.execute(cmd));

        return BaseResponse.onOk(true);
    }

}
