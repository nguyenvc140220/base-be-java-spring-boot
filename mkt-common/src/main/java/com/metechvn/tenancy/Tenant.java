package com.metechvn.tenancy;

import com.metechvn.util.CryptoUtils;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.UUID;

@Data
public class Tenant {

    private UUID id;
    private String name;
    private DbConnection dbConnection;

    public String getJdbcUrl(String dbName) {
        var url = String.format("jdbc:postgresql://%s:%s", dbConnection.getHost(), dbConnection.getPort());
        if (StringUtils.isEmpty(dbName)) return url;

        return String.format("%s/%s", url, dbName);
    }

    public DataSource getDataSource(String key, String salt, String iv) {
        var ds = new HikariDataSource();
        ds.setJdbcUrl(getJdbcUrl(dbConnection.getDatabase()));
        ds.setUsername(dbConnection.getUser());
        ds.setConnectionTestQuery("select 1");
        ds.setPoolName(String.format("%s-DSPOOL", dbConnection.getDatabase()).toUpperCase());
        ds.setDriverClassName("org.postgresql.Driver");

        try {
            ds.setPassword(CryptoUtils.decrypt(dbConnection.getPassword(), key, salt, iv));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ds;
    }
}
