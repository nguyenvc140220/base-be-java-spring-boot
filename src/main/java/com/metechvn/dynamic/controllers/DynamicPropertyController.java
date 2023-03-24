package com.metechvn.dynamic.controllers;

import com.metechvn.common.BaseResponse;
import com.metechvn.dynamic.commands.CreatePropertyCommand;
import com.metechvn.dynamic.commands.UpdatePropertyCommand;
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
}
