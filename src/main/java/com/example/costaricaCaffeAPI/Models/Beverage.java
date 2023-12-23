package com.example.costaricaCaffeAPI.Models;


public abstract class Beverage {
    private int id;
    private String ownerName;
    private String type;
    private double cost;
    private String description;
    private String size;
    private double gram;

    public Beverage() {
    }

    public Beverage(String ownerName, double cost, String size) {
        setOwnerName(ownerName);
        setCost(cost);
        setSize(size);
    }

    public String getType() {
        return type;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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


    public double getGram() {
        return gram;
    }

    public void setGram(double mg) {
        this.gram = mg;
    }

}
