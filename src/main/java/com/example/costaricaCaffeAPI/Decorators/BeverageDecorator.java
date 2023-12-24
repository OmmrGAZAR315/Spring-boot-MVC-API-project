package com.example.costaricaCaffeAPI.Decorators;

import com.example.costaricaCaffeAPI.Models.Beverage;

public abstract class BeverageDecorator extends Beverage {
    private final Beverage wrapper;

    public BeverageDecorator(Beverage b) {
        wrapper = b;
    }

    @Override
    public int getId() {
        return wrapper.getId();
    }

    @Override
    public String getOwnerName() {
        return wrapper.getOwnerName();
    }

    @Override
    public String getType() {
        return wrapper.getType();
    }

    @Override
    public String getDescription() {
        return wrapper.getDescription();
    }

    @Override
    public String getSize() {
        return wrapper.getSize();
    }

    @Override
    public double getCost() {
        return Math.floor(wrapper.getCost());
    }

    @Override
    public double getGram() {
        return wrapper.getGram();
    }


}
