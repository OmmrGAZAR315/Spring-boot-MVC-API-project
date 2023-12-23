package com.example.costaricaCaffeAPI;

import jakarta.annotation.PreDestroy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    private static Connection connection = null;
    private static final String JDBC_URL =
            "jdbc:mysql://127.0.0.1:8080/costarica_cafee";
    private static final String USER = "root";
    private static final String PASSWORD = "90950";

    private dbConnection() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
