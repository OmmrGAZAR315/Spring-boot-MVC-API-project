package com.example.costaricaCaffeAPI.Controllers;

import com.example.costaricaCaffeAPI.Models.Coffee;
import com.example.costaricaCaffeAPI.Models.Tea;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
//@RequestMapping("/api/tea")
public class TeaController {

    @GetMapping("/api/tea")
    public List<Tea> getAllTea() {
        return Arrays.asList(
                new Tea(1, "John", 10.0, "Tea", "Large"),
                new Tea(2, "Jane", 10.0, "Tea", "Large"),
                new Tea(3, "Max", 10.0, "Tea", "Large")
        );
    }
}
