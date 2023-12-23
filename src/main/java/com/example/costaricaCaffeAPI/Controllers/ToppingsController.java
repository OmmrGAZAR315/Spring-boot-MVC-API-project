package com.example.costaricaCaffeAPI.Controllers;

import com.example.costaricaCaffeAPI.Decorators.*;
import com.example.costaricaCaffeAPI.Models.*;
import org.springframework.web.bind.annotation.*;

public class ToppingsController {
    private double beverageCost = 0;
    private String beverageDescription = "";

    @PostMapping("/coffee")
    public Beverage coffee(@RequestBody Coffee coffee) {
        return coffee;
    }

    @PostMapping("/tea")
    public Beverage tea(@RequestBody Tea tea) {
        return tea;
    }

    @PatchMapping("/addMilk")
    public Beverage addMilk(@RequestBody Coffee beverage) {
        if (beverageCost != 0) {
            beverage.setCost(beverageCost);
            beverage.setDescription(beverageDescription);
        }
        Beverage milkDecorator = new MilkDecorator(beverage);
        beverageCost = milkDecorator.getCost();
        beverageDescription = milkDecorator.getDescription();
        return milkDecorator;
    }

    @PatchMapping("/addHoney")
    public Beverage addHoney(@RequestBody Coffee beverage) {
        if (beverageCost != 0) {
            beverage.setCost(beverageCost);
            beverage.setDescription(beverageDescription);

        }
        Beverage milkDecorator = new HoneyDecorator(beverage);
        beverageCost = milkDecorator.getCost();
        beverageDescription = milkDecorator.getDescription();
        return milkDecorator;
    }

    @DeleteMapping("/removeAll")
    public String destroy() {
        beverageCost = 0;
        beverageDescription = "";
        return "All Toppings Removed";
    }
}
