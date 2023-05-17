package com.metechvn.config;

import com.metechvn.tenancy.TenancyHttpService;
import com.metechvn.tenancy.TenantConnectionProvider;
import com.metechvn.tenancy.TenantIdentifierResolver;
import com.metechvn.validators.DynamicTypeValidatorImpl;
import com.metechvn.validators.IDynamicTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableFeignClients(basePackages = "com.metechvn")
public class TenantConfig {

    private final String decryptKey;
    private final String decryptSalt;
    private final String decryptIV;

    public TenantConfig(
            @Value("${services.tenancy.crypto.key:Default#Tenant@123}") String decryptKey,
            @Value("${services.tenancy.crypto.salt:hgt!16kl}") String decryptSalt,
            @Value("${services.tenancy.crypto.iv:jkE49230Tf093b42}") String decryptIV) {
        this.decryptKey = decryptKey;
        this.decryptSalt = decryptSalt;
        this.decryptIV = decryptIV;
    }

    @Bean
    public IDynamicTypeValidator validator() {
        return new DynamicTypeValidatorImpl();
    }

    @Bean
    public TenantConnectionProvider tenantConnectionProvider(
            DataSource dataSource,
            TenancyHttpService tenancyHttpService,
            TenantIdentifierResolver tenantIdentifierResolver) {
        return new TenantConnectionProvider(dataSource, tenancyHttpService, tenantIdentifierResolver, decryptKey, decryptSalt, decryptIV);
    }
}
