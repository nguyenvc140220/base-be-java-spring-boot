package com.metechvn.emloyee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class DBService {
    private final DataSource ds;

    public int createDB(String dbname) throws SQLException {
        Connection connection = ds.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE DATABASE " + dbname)) {
            System.out.println("statement " + preparedStatement);
            int i = preparedStatement.executeUpdate();
            return i;
        }
    }
}
