package com.ipstresser.app.web.controllers;

import com.ipstresser.app.domain.models.binding.UserLoginBindingModel;
import com.ipstresser.app.domain.models.binding.UserRegisterBindingModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.domain.models.view.ProfileEditViewModel;
import com.ipstresser.app.exceptions.DuplicatedEmailException;
import com.ipstresser.app.exceptions.DuplicatedUsernameException;
import com.ipstresser.app.services.interfaces.UserService;
import com.ipstresser.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

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

    @GetMapping("/profile/verification")
    @ResponseBody
    public ResponseEntity<Void> sendConfirmationCode(Principal principal) {
        this.userService.sendConfirmationEmail(principal.getName());

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/profile/verification", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> confirmConfirmationCode(@RequestBody String code, Principal principal) {

        if (this.userService.confirmConfirmationCode(code, principal.getName())) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
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

    @PageTitle("Profile")
    @PreAuthorize("@userSecurityAccessChecker.canAccess(authentication,#username)")
    @GetMapping("/profile/{username}")
    public String profileEdit(@PathVariable String username, Model model) {
        ProfileEditViewModel profile = this.modelMapper.map(this.userService.getUserByUsername(username), ProfileEditViewModel.class);

        if (!model.containsAttribute("userEdit")) {
            model.addAttribute("userEdit", profile);
        }

        return "profile-edit";
    }

    @PostMapping("/profile/{username}")
    public String postProfileEdit(@PathVariable String username, @Valid @ModelAttribute ProfileEditViewModel profile, BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        try {
            this.userService.validateUsers(username, this.modelMapper.map(profile, UserServiceModel.class));
        } catch (DuplicatedUsernameException exception) {
            result.rejectValue("username", "error", exception.getMessage());
        } catch (DuplicatedEmailException exception) {
            result.rejectValue("email", "error1", exception.getMessage());
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEdit", result);
            redirectAttributes.addFlashAttribute("userEdit", profile);
            return String.format("redirect:/users/profile/%s", username);
        }

        this.userService.updateUser(username, this.modelMapper.map(profile, UserServiceModel.class));
        SecurityContextHolder.clearContext();

        return "redirect:/users/login";

    }

    @PostMapping("/profile/delete/{id}")
    public String deleteProfile(@PathVariable String id, HttpSession session) {
        this.userService.deleteUserById(id);
        session.invalidate();

        return "redirect:/index";
    }



}
