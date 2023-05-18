package com.metechvn.tenancy;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class DbConnection {

    private String host;
    private int port;
    private String user;
    private String schema;
    private String password;
    private String database;

    public boolean isValid() {
        return port > 0
                && StringUtils.isNotEmpty(host)
                && StringUtils.isNotEmpty(user)
                && StringUtils.isNotEmpty(password)
                && StringUtils.isNotEmpty(database);
    }
}
