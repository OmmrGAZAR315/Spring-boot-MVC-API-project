package com.example.costaricaCaffeAPI.Models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.lang.Nullable;

public abstract class Beverage {
    private String ownerName;
    @JsonIgnore
    private String type;
    @JsonIgnore
    private double cost;
    private String description;
    private String size;
    @JsonIgnore
    private double gram;

    public Beverage() {
    }

    public Beverage(String ownerName, String size) {
        setOwnerName(ownerName);
        setSize(size);
    }

    @JsonIgnore
    public void setCost(double cost) {
        this.cost = cost;
    }

    @JsonGetter("type")
    public String getType() {
        return type;
    }

    @JsonIgnore
    public void setType(String type) {
        this.type = type;
    }

    public void setSize(String size) {
        switch (size) {
            case "Large":
                setGram(15);
                break;
            case "Medium":
                setGram(10);
                break;
            case "Small":
                setGram(5);
                break;
        }
        this.size = size;
    }


    public String getOwnerName() {
        return ownerName;
    }

    @JsonGetter("cost")
    public double getCost() {
        return Math.floor(cost);
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public String getDescription() {
        if (description != null)
            return description;

        return null;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

   @JsonGetter("gram")
    public double getGram() {
        return gram;
    }

    @JsonIgnore
    public void setGram(double mg) {
        this.gram = mg;
    }

}
