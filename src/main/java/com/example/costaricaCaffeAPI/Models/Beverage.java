package com.example.costaricaCaffeAPI.Models;

public abstract class Beverage{
    private int id;
    private String ownerName;
    private double cost;
    private String description;
    private String size;
    private String imageUrl;

    public Beverage(int id, String ownerName, double cost, String description, String size) {
        setId(id);
        setOwnerName(ownerName);
        setCost(cost);
        setDescription(description);
        setSize(size);
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
