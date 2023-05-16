package com.metechvn.config;

import com.metechvn.validators.DynamicTypeValidatorImpl;
import com.metechvn.validators.IDynamicTypeValidator;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.metechvn")
public class TenantConfig {

    @Bean
    public IDynamicTypeValidator validator() {
        return new DynamicTypeValidatorImpl();
    }

}
