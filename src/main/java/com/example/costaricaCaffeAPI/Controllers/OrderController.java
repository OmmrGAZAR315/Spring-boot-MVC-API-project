package com.example.costaricaCaffeAPI.Controllers;

import com.example.costaricaCaffeAPI.Decorators.*;
import com.example.costaricaCaffeAPI.Models.*;
import com.example.costaricaCaffeAPI.Requests.ObjectRequest;
import com.example.costaricaCaffeAPI.dbConnection;
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
                        resultSet.getString("description"),
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

    @PostMapping("/coffee")
    public Beverage coffee(@RequestBody Coffee coffee) {
        return coffee;
    }

    @PostMapping("/tea")
    public Beverage tea(@RequestBody Tea tea) {
        return tea;
    }

    @PatchMapping("/addMilk")
    public Beverage addMilk(@RequestBody Coffee beverage) {
        return new MilkDecorator(beverage);
    }

    @PatchMapping("/addHoney")
    public Beverage addHoney(@RequestBody Tea beverage) {
        return new HoneyDecorator(beverage);
    }

    @PostMapping
    public Order store(@RequestBody Coffee beverage) {
        Stock stock = StockController.getStockBY(new ObjectRequest(
                "", "", "type", beverage.getType()));

        if (stock != null && stock.getQuantity() > beverage.getGram())
            StockController.update(new ObjectRequest(
                    "quantity", String.valueOf(stock.getQuantity() - beverage.getGram()),
                    "type", beverage.getType()));

        else return new Order(0, "Out of stock", "", "", 0, null);

        String insertSql =
                "INSERT INTO `order` (beverageType, cupOwner, description ,total,created_at ) " +
                        "VALUES (?, ?,?,?,?)";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, beverage.getType());
            preparedStatement.setString(2, beverage.getOwnerName());
            preparedStatement.setString(3, beverage.getDescription());
            preparedStatement.setDouble(4, beverage.getCost());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

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
    public Order destroy(@RequestBody ObjectRequest objectRequest) {
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
                            resultSet.getString("description"),
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

