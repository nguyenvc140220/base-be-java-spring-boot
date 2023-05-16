package com.metechvn.tenancy;

import lombok.Data;

@Data
public class DbConnection {

    private String host;
    private int port;
    private String user;
    private String password;
    private String database;

}
