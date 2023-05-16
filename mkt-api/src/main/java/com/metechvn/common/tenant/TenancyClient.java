package com.metechvn.common.tenant;

import com.metechvn.tenancy.Tenant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tenancy", url = "${services.tenancy.url}", path = "${services.tenancy.path:}")
public interface TenancyClient {
    @GetMapping("/tenant/connection")
    Tenant find(@RequestParam("name") String tenant);
}
