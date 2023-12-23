package com.example.costaricaCaffeAPI.Models;

public class Tea extends Beverage {
    public Tea(String ownerName, double cost, String size) {
        super(ownerName, cost, size);
        setType("Tea");
        setDescription("Plain tea");
    }
}
