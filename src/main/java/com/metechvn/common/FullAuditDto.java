package com.metechvn.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class FullAuditDto<T, UK> {

    private T id;
    private Long creationTime;
    private UK createdBy;
    private Long lastModificationTime;
    private UK lastModificationBy;
    private Long deletedTime;
    private UK deletedBy;

}
