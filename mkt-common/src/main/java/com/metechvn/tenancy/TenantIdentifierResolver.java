package com.metechvn.tenancy;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {

    private String currentTenant = "default";

    public void setTenant(String domain) {
        currentTenant = domain;
    }

    public void defaultTenant() {
        this.setTenant("default");
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return StringUtils.isEmpty(currentTenant) ? "default" : currentTenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }

}