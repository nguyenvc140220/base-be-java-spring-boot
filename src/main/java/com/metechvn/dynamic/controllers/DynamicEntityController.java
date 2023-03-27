package com.metechvn.dynamic.controllers;

import com.metechvn.common.BaseResponse;
import com.metechvn.dynamic.commands.CreateEntityCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.Bus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/entity")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class DynamicEntityController {

    private final Bus bus;

    @PostMapping({"", "/", "/create"})
    public BaseResponse<Boolean> createEntityType(@Valid @RequestBody CreateEntityCommand cmd) {
        System.out.println(bus.execute(cmd));

        return BaseResponse.onOk(true);
    }

}
