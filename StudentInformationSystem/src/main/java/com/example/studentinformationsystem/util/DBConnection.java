package com.example.studentinformationsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/sis" +
                    "?useSSL=false&serverTimezone=UTC" +
                    "&useUnicode=true&characterEncoding=UTF-8" +
                    "&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found!", e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
