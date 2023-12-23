package com.example.costaricaCaffeAPI.Models;

public class Coffee extends Beverage {
    public Coffee(String ownerName, String size) {
        super(ownerName, size);
        setCost(75);
        setType("Coffee");
        setDescription("Plain Coffee");
    }

}
