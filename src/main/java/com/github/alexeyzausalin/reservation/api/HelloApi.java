package com.github.alexeyzausalin.reservation.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApi {

    @GetMapping("/hello")
    public String sayHello() {
        return "{\"message\":\"Hello\"}";
    }
}
