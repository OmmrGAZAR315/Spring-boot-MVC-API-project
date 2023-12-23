package com.example.costaricaCaffeAPI.Models;

public class Tea extends Beverage {
    public Tea(String ownerName, String size) {
        super(ownerName, size);
        setCost(30);
        setType("Tea");
        setDescription("Plain tea");
    }
}
