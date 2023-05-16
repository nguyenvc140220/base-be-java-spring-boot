package com.metechvn.resource.controllers;

import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final DynamicEntityTypeRepository dynamicEntityTypeRepository;

    public TestController(DynamicEntityTypeRepository dynamicEntityTypeRepository) {
        this.dynamicEntityTypeRepository = dynamicEntityTypeRepository;
    }

    @GetMapping({"/", ""})
    public Object test() {
        return dynamicEntityTypeRepository.count();
    }

}
