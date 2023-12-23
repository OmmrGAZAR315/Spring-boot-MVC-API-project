package com.example.costaricaCaffeAPI.Models;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private String type;
    private String cupOwner;
    private String description;
    private double total;

    private LocalDateTime localDateTime;

    public Order(int id, String type, String cupOwner, String description, double total, LocalDateTime localDateTime) {
        setId(id);
        setType(type);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
