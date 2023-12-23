package com.example.costaricaCaffeAPI.Models;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private String beverageType;
    private String cupOwner;
    private String receipt;
    private double total;



    private LocalDateTime localDateTime;

    public Order(int id, String beverageType, String cupOwner, String receipt, double total, LocalDateTime localDateTime) {
        setId(id);
        setBeverageType(beverageType);
        setCupOwner(cupOwner);
        setReceipt(receipt);
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

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
