package com.example.costaricaCaffeAPI.Decorators;

import com.example.costaricaCaffeAPI.Models.Beverage;

public class MilkDecorator extends BeverageDecorator {
    public MilkDecorator(Beverage b) {
        super(b);
    }

    public double getCost() {
        return super.getCost() + 5.7;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Milk";
    }
}
