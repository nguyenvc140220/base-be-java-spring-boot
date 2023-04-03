package com.metechvn.common.persistent;

import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public abstract class UUIDFullAuditedEntityImpl extends HaveTenantEntityImpl<UUID, UUID> {

    protected UUIDFullAuditedEntityImpl() {
        setId(UUID.randomUUID());
    }
}
