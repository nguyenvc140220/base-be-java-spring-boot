package com.metechvn.resource.controllers;

import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.tenancy.TenantIdentifierResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    private final DynamicEntityTypeRepository dynamicEntityTypeRepository;
    private final TenantIdentifierResolver tenantIdentifierResolver;

    public TestController(DynamicEntityTypeRepository dynamicEntityTypeRepository, TenantIdentifierResolver tenantIdentifierResolver) {
        this.dynamicEntityTypeRepository = dynamicEntityTypeRepository;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
    }

    @GetMapping({"/", ""})
    public Boolean test() {
        tenantIdentifierResolver.setCurrentTenant("tenant2");
        System.out.println(tenantIdentifierResolver.resolveCurrentTenantIdentifier());

        var all = dynamicEntityTypeRepository.findAll();

        return true;
    }

}
