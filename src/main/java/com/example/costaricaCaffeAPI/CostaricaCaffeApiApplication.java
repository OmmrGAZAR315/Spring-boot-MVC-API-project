package com.example.costaricaCaffeAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class CostaricaCaffeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CostaricaCaffeApiApplication.class, args);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (!dbConnection.getConnection().isClosed()) {
                    dbConnection.closeConnection();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Application shut down.");
            }
        }));
    }
}
