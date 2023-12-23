package com.example.costaricaCaffeAPI.Decorators;

import com.example.costaricaCaffeAPI.Models.Beverage;

public class MintDecorator extends BeverageDecorator {
    public MintDecorator(Beverage b) {
        super(b);
    }

    public double getCost() {
        return super.getCost() + 3;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Mint";
    }
}
