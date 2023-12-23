package com.example.costaricaCaffeAPI.Decorators;

import com.example.costaricaCaffeAPI.Models.Beverage;

public class HoneyDecorator extends BeverageDecorator{
    public HoneyDecorator(Beverage b) {
        super(b);
    }

    @Override
    public double getCost() {
        return super.getCost()+7;
    }
    @Override
    public String getDescription() {
        return super.getDescription()+", Honey";
    }
}
