package com.apex.eqp.inventory.controllers;
// import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HomeController {

    @GetMapping
    public String home(){
        return "This is root page of Spring Web APP";
    }
    
}
