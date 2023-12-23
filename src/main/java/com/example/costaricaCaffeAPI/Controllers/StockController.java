package com.example.costaricaCaffeAPI.Controllers;

import com.example.costaricaCaffeAPI.Models.Stock;
import com.example.costaricaCaffeAPI.Requests.ObjectRequest;
import com.example.costaricaCaffeAPI.dbConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    @GetMapping
    public List<Stock> index() {
        List<Stock> stockList = new ArrayList<>();
        String selectSql = "SELECT * FROM stock";
        try (Statement statement = dbConnection.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(selectSql)) {
            while (resultSet.next()) {
                Stock stock = new Stock(
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getDouble("quantity"),
                        resultSet.getString("vendorName"),
                        resultSet.getDouble("price")
                );
                stockList.add(stock);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return stockList;

    }

    @PatchMapping("refill/{type}/{quantity}")
    public Stock reFillStock(@PathVariable String type, @PathVariable double quantity) {
        String selectSql = "UPDATE stock SET quantity = ?+quantity WHERE type = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(selectSql)) {
            preparedStatement.setDouble(1, quantity);
            preparedStatement.setString(2, type);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating stock failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getStockBY(new ObjectRequest(null, null, "type", type));
    }

    @PostMapping
    public Stock store(@RequestBody Stock stock) {
        checkUnique(stock.getType());

        String insertSql =
                "INSERT INTO stock (type, quantity,vendorName,price) " +
                        "VALUES (?, ?,?, ? )";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, stock.getType());
            preparedStatement.setDouble(2, stock.getQuantity());
            preparedStatement.setString(3, stock.getVendorName());
            preparedStatement.setDouble(4, stock.getPrice());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Stock(
                            generatedKeys.getInt(1),
                            stock.getType(),
                            stock.getQuantity(),
                            stock.getVendorName(),
                            stock.getPrice()
                    );
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public Stock destroy(@RequestBody ObjectRequest objectRequest) throws JsonProcessingException {
        String deleteSql = "DELETE FROM stock WHERE " + objectRequest.getWhere() + " = ?";
        Stock stock = getStockBY(objectRequest);
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(deleteSql)) {
            preparedStatement.setString(1, objectRequest.getWhereValue());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Deleting stock failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stock;
    }

    @PatchMapping
    public static Stock update(@RequestBody ObjectRequest objectRequest) {
        String updateSql = "UPDATE stock SET " + objectRequest.getUpdateColumn()
                + " = ? WHERE  " + objectRequest.getWhere() + "  = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, objectRequest.getUpdateValue());
            preparedStatement.setString(2, objectRequest.getWhereValue());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating stock failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getStockBY(objectRequest);
    }

    static Stock getStockBY(ObjectRequest objectRequest) {
        String selectSql = "SELECT * FROM stock WHERE " + objectRequest.getWhere() + " = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(selectSql)) {
            preparedStatement.setString(1, objectRequest.getWhereValue());


            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Stock(
                            resultSet.getInt("id"),
                            resultSet.getString("type"),
                            resultSet.getDouble("quantity"),
                            resultSet.getString("vendorName"),
                            resultSet.getDouble("price")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    void checkUnique(String columnValue) {
        String checkUniquenessSql = "SELECT COUNT(*) FROM stock WHERE type = ?";
        try (PreparedStatement checkStatement = dbConnection.getConnection().prepareStatement(checkUniquenessSql)) {
            checkStatement.setString(1, columnValue);

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0)
                    throw new RuntimeException("Duplicate value");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

