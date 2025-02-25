package com.example.oauth2example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/secure")
    public String getSecureMessage(){
        return "Hi! This is secure message!";
    }
}
