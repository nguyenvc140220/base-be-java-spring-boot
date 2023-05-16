package com.metechvn.tenancy;

public class TenantNotFoundException extends RuntimeException {

    public TenantNotFoundException(String tenant) {
        super(String.format("Cannot found any tenant named %s", tenant));
    }
}
