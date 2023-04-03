package com.metechvn.common.persistent;

import java.io.Serializable;

public interface FullAuditedEntity<K extends Serializable, UK extends Serializable> {

    void setId(K id);

    K getId();

    void setCreationTime(Long creationTime);

    Long getCreationTime();

    void setCreationBy(UK creationBy);

    UK getCreationBy();

    void setLastModificationTime(Long lastModificationTime);

    Long getLastModificationTime();

    void setLastModificationBy(UK lastModificationBy);

    UK getLastModificationBy();

    void setDeletedTime(Long deletedTime);

    Long getDeletedTime();

    void setDeletedBy(UK deletedBy);

    UK getDeletedBy();
}
