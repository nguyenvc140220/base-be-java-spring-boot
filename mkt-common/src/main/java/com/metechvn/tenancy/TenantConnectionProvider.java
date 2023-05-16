package com.metechvn.tenancy;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

@Component
public class TenantConnectionProvider
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl implements HibernatePropertiesCustomizer {

    private final Map<String, DataSource> dataSources = new TreeMap<>();
    private final DataSource dataSource;
    private final TenancyHttpService tenancyHttpService;
    private final TenantIdentifierResolver tenantIdentifierResolver;

    private final String decryptKey;
    private final String decryptSalt;
    private final String decryptIV;

    public TenantConnectionProvider(
            DataSource dataSource,
            TenancyHttpService tenancyHttpService,
            TenantIdentifierResolver tenantIdentifierResolver,
            @Value("${services.tenancy.crypto.key:Default#Tenant@123}") String decryptKey,
            @Value("${services.tenancy.crypto.salt:hgt!16kl}") String decryptSalt,
            @Value("${services.tenancy.crypto.iv:jkE49230Tf093b42}") String decryptIV) {
        this.dataSource = dataSource;
        this.tenancyHttpService = tenancyHttpService;
        this.tenantIdentifierResolver = tenantIdentifierResolver;

        this.decryptKey = decryptKey;
        this.decryptSalt = decryptSalt;
        this.decryptIV = decryptIV;
    }


    @Override
    protected DataSource selectAnyDataSource() {
        if (dataSources.isEmpty()) {return dataSource;}

        return this.dataSources.values().iterator().next();
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        var tenantName = tenantIdentifierResolver.resolveCurrentTenantIdentifier();
        if (!this.dataSources.containsKey(tenantName)) {
            var tenant = tenancyHttpService.find(tenantName);
            if (tenant == null) throw new TenantNotFoundException(tenantName);

            var ds = tenant.getDataSource(decryptKey, decryptSalt, decryptIV);
            if (dataSource instanceof HikariDataSource hds) ds.setSchema(hds.getSchema());

            this.dataSources.put(tenantName, ds);
        }

        return this.dataSources.get(tenantName);
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return selectAnyDataSource().getConnection();
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}