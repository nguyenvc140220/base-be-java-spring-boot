package com.metechvn.common.persistent;


import jakarta.persistence.*;

import java.io.Serializable;

@MappedSuperclass
public abstract class FullAuditedEntityImpl<K extends Serializable, U extends Serializable>
        extends SoftDeleteEntityImpl implements FullAuditedEntity<K, U> {

    @Id
    private K id;

    @Column(name = "creation_time")
    private Long creationTime = 0L;

    @Column(name = "creation_by")
    private U creationBy;

    @Column(name = "last_modification_time")
    private Long lastModificationTime = 0L;

    @Column(name = "last_modification_by")
    private U lastModificationBy;

    @Column(name = "deleted_time")
    private Long deletedTime = 0L;

    @Column(name = "deleted_by")
    private U deletedBy;

    @PrePersist
    public void prePersist() {
        this.creationTime = System.currentTimeMillis();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModificationTime = System.currentTimeMillis();
    }

    @Override
    public K getId() {
        return id;
    }

    @Override
    public void setId(K id) {
        this.id = id;
    }

    @Override
    public Long getCreationTime() {
        return creationTime;
    }

    @Override
    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public U getCreationBy() {
        return creationBy;
    }

    @Override
    public void setCreationBy(U creationBy) {
        this.creationBy = creationBy;
    }

    @Override
    public Long getLastModificationTime() {
        return lastModificationTime;
    }

    @Override
    public void setLastModificationTime(Long lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    @Override
    public U getLastModificationBy() {
        return lastModificationBy;
    }

    @Override
    public void setLastModificationBy(U lastModificationBy) {
        this.lastModificationBy = lastModificationBy;
    }

    public Long getDeletedTime() {
        return deletedTime;
    }

    @Override
    public void setDeletedTime(Long deletedTime) {
        this.deletedTime = deletedTime;
    }

    @Override
    public U getDeletedBy() {
        return deletedBy;
    }

    @Override
    public void setDeletedBy(U deletedBy) {
        this.deletedBy = deletedBy;
    }
}
