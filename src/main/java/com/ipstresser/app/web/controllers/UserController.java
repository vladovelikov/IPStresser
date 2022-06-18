package com.ipstresser.app.web.controllers;

import com.ipstresser.app.domain.models.binding.UserLoginBindingModel;
import com.ipstresser.app.domain.models.binding.UserRegisterBindingModel;
import com.ipstresser.app.web.annotations.PageTitle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @PageTitle("Login")
    @GetMapping("/login")
    public String login(Model model) {
        if(!model.containsAttribute("user")) {
            model.addAttribute("user", new UserLoginBindingModel());
        }
        return "login";
    }

    @PageTitle("Register")
    @GetMapping("/register")
    public String register(Model model) {
        if(!model.containsAttribute("user")) {
            model.addAttribute("user", new UserRegisterBindingModel());
        }
        return "register";
    }


}
