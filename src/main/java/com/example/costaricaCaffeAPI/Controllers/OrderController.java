package com.example.costaricaCaffeAPI.Controllers;

import com.example.costaricaCaffeAPI.Decorators.*;
import com.example.costaricaCaffeAPI.Models.*;
import com.example.costaricaCaffeAPI.Requests.MakeBeverageRequest;
import com.example.costaricaCaffeAPI.Requests.WhereObject;
import com.example.costaricaCaffeAPI.dbConnection;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private Beverage beverage;

    @GetMapping
    public List<Order> index() {
        List<Order> orderList = new ArrayList<>();
        String selectSql = "SELECT * FROM `order`";
        try (Statement statement = dbConnection.getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(selectSql)) {
            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt("id"), resultSet.getString("type"), resultSet.getString("cupOwner"), resultSet.getString("description"), resultSet.getDouble("total"), resultSet.getTimestamp("created_at").toLocalDateTime());
                orderList.add(order);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return orderList;

    }

    @PostMapping("/{id}")
    public Order store(@PathVariable int id) {
        if (beverage == null) throw new RuntimeException("No Beverage Selected");
        if (id != beverage.getId()) throw new RuntimeException("Invalid Beverage Id");
        Stock stock = StockController.getStockBY(new WhereObject("", "", "type", beverage.getType()));

        if (stock != null && stock.getQuantity() > beverage.getGram())
            StockController.update(new WhereObject("quantity", String.valueOf(stock.getQuantity() - beverage.getGram()), "type", beverage.getType()));

        else return new Order(0, "Out of stock", "", "", 0, null);
//storedprocedure
        String insertSql = "INSERT INTO `order` (type, cupOwner, description ,total,created_at ) " + "VALUES (?, ?,?,?,?)";
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
                    WhereObject whereObject = new WhereObject("", "", "id", String.valueOf(generatedKeys.getInt(1)));
                    return getOrderBY(whereObject);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public Order destroy(@PathVariable int id) {

        Order order = getOrderBY(new WhereObject("", "", "id", String.valueOf(id)));
        String deleteSql = "DELETE FROM `order` WHERE id = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0)
                throw new SQLException("id not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }


    @PatchMapping
    public Order update(@RequestBody WhereObject whereObject) {
        String updateSql = "UPDATE `order` SET " + whereObject.getUpdateColumn() + " = ? WHERE  " + whereObject.getWhere() + "  = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, whereObject.getUpdateValue());
            preparedStatement.setString(2, whereObject.getWhereValue());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating order failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getOrderBY(whereObject);
    }

    Order getOrderBY(WhereObject whereObject) {
        String selectSql = "SELECT * FROM `order` WHERE " + whereObject.getWhere() + " = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(selectSql)) {
            preparedStatement.setString(1, whereObject.getWhereValue());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Order(resultSet.getInt("id"), resultSet.getString("type"), resultSet.getString("cupOwner"), resultSet.getString("description"), resultSet.getDouble("total"), resultSet.getTimestamp("created_at").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @PostMapping("/makeBeverage/{type}")
    public Beverage makeBeverage(@RequestBody MakeBeverageRequest beverageRequest, @PathVariable String type) {
        switch (type.toLowerCase()) {
            case "coffee":
                beverage = new Coffee(beverageRequest.getOwnerName(), beverageRequest.getSize());
                break;
            case "tea":
                beverage = new Tea(beverageRequest.getOwnerName(), beverageRequest.getSize());
                break;
            case "hot chocolate":
                beverage = new Hot_Chocolate(beverageRequest.getOwnerName(), beverageRequest.getSize());
                break;
            default:
                throw new RuntimeException("Invalid Beverage Type");
        }
        return beverage;
    }

    @PatchMapping("/addTopping/{id}/{toppingType}")
    public Beverage addTopping(@PathVariable int id, @PathVariable String toppingType) {
        if (id != beverage.getId()) throw new RuntimeException("Invalid Beverage Id");
        switch (toppingType.toLowerCase()) {
            case "milk":
                beverage = new MilkDecorator(beverage);
                break;
            case "mint":
                beverage = new MintDecorator(beverage);
                break;

            case "honey":
                beverage = new HoneyDecorator(beverage);
                break;

            default:
                throw new RuntimeException("Invalid Topping Type");
        }
        return beverage;
    }

    @DeleteMapping("/removeAll/{id}")
    public String removeAll(@PathVariable int id) {
        if (beverage != null && id == beverage.getId()) {
            beverage = null;
            return "All Toppings Removed";
        }
        throw new RuntimeException("Invalid Beverage Id");
    }
}

