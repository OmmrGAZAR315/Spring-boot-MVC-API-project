package com.example.costaricaCaffeAPI.Models;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private String beverageType;
    private String cupOwner;
    private String description;
    private double total;

    private LocalDateTime localDateTime;

    public Order(int id, String beverageType, String cupOwner, String description, double total, LocalDateTime localDateTime) {
        setId(id);
        setBeverageType(beverageType);
        setCupOwner(cupOwner);
        setDescription(description);
        setTotal(total);
        setLocalDateTime(localDateTime);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    } public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeverageType() {
        return beverageType;
    }

    public void setBeverageType(String beverageType) {
        this.beverageType = beverageType;
    }

    public String getCupOwner() {
        return cupOwner;
    }

    public void setCupOwner(String cupOwner) {
        this.cupOwner = cupOwner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
