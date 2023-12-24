package com.example.costaricaCaffeAPI.Requests;


public class AddConcreteDecorator {
    private String ownerName;
    private String size;

    public AddConcreteDecorator(String ownerName, String size) {
        setOwnerName(ownerName);
        setSize(size);
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        if (ownerName != null && !ownerName.isEmpty())
            this.ownerName = ownerName;
        else this.ownerName = "Unknown";
    }

}
