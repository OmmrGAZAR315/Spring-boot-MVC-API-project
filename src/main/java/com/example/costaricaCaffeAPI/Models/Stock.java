package com.example.costaricaCaffeAPI.Models;

public class Stock {
    private int id;
    private String type;
    private double quantity;
    private String vendorName;
    private double price;

    public Stock(int id, String type, double quantity, String vendorName, double price) {
        setId(id);
        setType(type);
        setQuantity(quantity);
        setVendorName(vendorName);
        setPrice(price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
