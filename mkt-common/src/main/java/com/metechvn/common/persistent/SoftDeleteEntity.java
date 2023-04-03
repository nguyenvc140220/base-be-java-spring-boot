package com.metechvn.common.persistent;

public interface SoftDeleteEntity {

    void setDeleted(boolean deleted);

    boolean isDeleted();
}
