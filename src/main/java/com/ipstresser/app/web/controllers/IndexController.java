package com.ipstresser.app.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    @PageTitle("Welcome!")
    public String index() {
        return "index";
    }
}
