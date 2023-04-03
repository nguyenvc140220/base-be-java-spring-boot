package com.metechvn.common.persistent;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SoftDeleteEntityImpl implements SoftDeleteEntity {

    @Column(name = "is_deleted")
    private Boolean deleted;

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean isDeleted() {
        return deleted != null && deleted;
    }
}
