package com.example.costaricaCaffeAPI.Models;

public class Hot_Chocolate extends Beverage {
    public Hot_Chocolate(String ownerName, String size) {
        super(ownerName,size);
        setCost(120);
        setType("Hot Chocolate");
        setDescription("Hot Chocolate");
    }
}
