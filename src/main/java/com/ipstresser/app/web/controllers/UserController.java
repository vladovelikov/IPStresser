package com.ipstresser.app.web.controllers;

import com.ipstresser.app.domain.models.binding.UserLoginBindingModel;
import com.ipstresser.app.domain.models.binding.UserRegisterBindingModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.services.interfaces.UserService;
import com.ipstresser.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

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

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute UserRegisterBindingModel user, BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            return "redirect:/users/register";
        }

        this.userService.register(this.modelMapper.map(user, UserServiceModel.class));

        return "redirect:/users/login";

    }

    @PostMapping("/profile/delete/{id}")
    public String deleteProfile(@PathVariable String id, HttpSession session) {
        this.userService.deleteUserById(id);
        session.invalidate();

        return "redirect:/index";
    }



}
