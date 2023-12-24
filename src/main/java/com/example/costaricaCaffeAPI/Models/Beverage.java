package com.example.costaricaCaffeAPI.Models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.lang.Nullable;

public abstract class Beverage {
    //    @JsonIgnore
    private int id;
    private String ownerName;
    @JsonIgnore
    private String type;
    private String description;
    private String size;
    @JsonIgnore
    private double cost;
    @JsonIgnore
    private double gram;

    public Beverage() {
    }

    public Beverage(String ownerName, String size) {
        setId((int) Math.floor(Math.random() * 1000000));
        setOwnerName(ownerName);
        setSize(size);
    }

    @JsonGetter("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter("ownerName")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @JsonGetter("type")
    public String getType() {
        return type;
    }

    @JsonIgnore
    public void setType(String type) {
        this.type = type;
    }


    @JsonGetter("description")
    public String getDescription() {
        if (description != null)
            return description;

        return null;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @JsonGetter("size")
    public String getSize() {
        return size;
    }

    @JsonIgnore
    public void setCost(double cost) {
        this.cost = cost;
    }


    @JsonGetter("cost")
    public double getCost() {
        return Math.floor(cost);
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
