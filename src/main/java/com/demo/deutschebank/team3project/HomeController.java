package com.demo.deutschebank.team3project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {




    @GetMapping("/hello")
    public String hello(){
        return "hello from team 3";
    }
}
