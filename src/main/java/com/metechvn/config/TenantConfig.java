package com.metechvn.config;

import com.metechvn.common.CurrentTenantProvider;
import com.metechvn.validators.DynamicTypeValidatorImpl;
import com.metechvn.validators.IDynamicTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TenantConfig {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CurrentTenantProvider currentTenantProvider() {
        return new CurrentTenantProvider();
    }

    @Bean
    public IDynamicTypeValidator validator() {
        return new DynamicTypeValidatorImpl();
    }

}
