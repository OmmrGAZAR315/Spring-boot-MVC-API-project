package com.example.costaricaCaffeAPI.Controllers;

import com.example.costaricaCaffeAPI.Models.Order;
import com.example.costaricaCaffeAPI.Requests.ObjectRequest;
import com.example.costaricaCaffeAPI.dbConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @GetMapping
    public List<Order> index() {
        List<Order> orderList = new ArrayList<>();
        String selectSql = "SELECT * FROM `order`";
        try (Statement statement = dbConnection.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(selectSql)) {
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getString("beverageType"),
                        resultSet.getString("cupOwner"),
                        resultSet.getString("receipt"),
                        resultSet.getDouble("total"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );
                orderList.add(order);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return orderList;

    }

    @PostMapping
    public Order store(@RequestBody Order order) {
        LocalDateTime dateTime = LocalDateTime.now();
        String insertSql =
                "INSERT INTO `order` (beverageType, cupOwner, receipt, total,created_at ) VALUES (?, ?,?,?,?)";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, order.getBeverageType());
            preparedStatement.setString(2, order.getCupOwner());
            preparedStatement.setString(3, order.getReceipt());
            preparedStatement.setDouble(4, order.getTotal());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(dateTime));

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ObjectRequest objectRequest = new ObjectRequest("", "", "id", String.valueOf(generatedKeys.getInt(1)));
                    return getOrderBY(objectRequest);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public Order destroy(@RequestBody ObjectRequest objectRequest)  {
        String deleteSql = "DELETE FROM `order` WHERE " + objectRequest.getWhere() + " = ?";
        Order order = getOrderBY(objectRequest);
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(deleteSql)) {
            preparedStatement.setString(1, objectRequest.getWhereValue());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Deleting order failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @PatchMapping
    public Order update(@RequestBody ObjectRequest objectRequest) {
        String updateSql = "UPDATE `order` SET " + objectRequest.getUpdateColumn()
                + " = ? WHERE  " + objectRequest.getWhere() + "  = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, objectRequest.getUpdateValue());
            preparedStatement.setString(2, objectRequest.getWhereValue());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating order failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getOrderBY(objectRequest);
    }

    Order getOrderBY(ObjectRequest objectRequest) {
        String selectSql = "SELECT * FROM `order` WHERE " + objectRequest.getWhere() + " = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(selectSql)) {
            preparedStatement.setString(1, objectRequest.getWhereValue());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Order(
                            resultSet.getInt("id"),
                            resultSet.getString("beverageType"),
                            resultSet.getString("cupOwner"),
                            resultSet.getString("receipt"),
                            resultSet.getDouble("total"),
                            resultSet.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}

