package com.example.costaricaCaffeAPI.Models;

public class Coffee extends Beverage {
    public Coffee(String ownerName, double cost, String size) {
        super(ownerName, cost, size);
        setType("Coffee");
        setDescription("Plain Coffee");
    }

}
