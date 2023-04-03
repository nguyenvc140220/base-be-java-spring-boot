package com.metechvn.common.persistent;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class HaveTenantEntityImpl<K extends Serializable, U extends Serializable>
        extends FullAuditedEntityImpl<K, U> implements HaveTenantEntity {

    @Column(name = "tenant", length = 50)
    private String tenant;

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
