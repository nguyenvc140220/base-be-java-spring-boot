package com.metechvn.common;

import com.metechvn.common.persistent.FullAuditedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class FullAuditDto<T extends Serializable, UK extends Serializable> {

    private T id;
    private Long creationTime;
    private UK createdBy;
    private Long lastModificationTime;
    private UK lastModificationBy;
    private Long deletedTime;
    private UK deletedBy;

    protected <E extends FullAuditedEntity<T, UK>> void apply(E e) {
        this.id = e.getId();
        this.creationTime = e.getCreationTime();
        this.createdBy = e.getCreationBy();
        this.lastModificationTime = e.getLastModificationTime();
        this.lastModificationBy = e.getLastModificationBy();
        this.deletedBy = e.getDeletedBy();
        this.deletedBy = e.getDeletedBy();
    }
}
