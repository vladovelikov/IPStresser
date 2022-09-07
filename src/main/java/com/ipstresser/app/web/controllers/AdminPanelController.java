package com.ipstresser.app.web.controllers;

import com.ipstresser.app.domain.entities.Role;
import com.ipstresser.app.domain.models.binding.AnnouncementBindingModel;
import com.ipstresser.app.domain.models.binding.ArticleBindingModel;
import com.ipstresser.app.domain.models.binding.CryptocurrencyBindingModel;
import com.ipstresser.app.domain.models.service.AnnouncementServiceModel;
import com.ipstresser.app.domain.models.service.ArticleServiceModel;
import com.ipstresser.app.domain.models.service.CryptocurrencyServiceModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.exceptions.ChangeRoleException;
import com.ipstresser.app.exceptions.UserDeletionException;
import com.ipstresser.app.services.interfaces.*;
import com.ipstresser.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
@RequestMapping("/admin")
public class AdminPanelController {

    private final UserService userService;
    private final RoleService roleService;
    private final ArticleService articleService;
    private final CryptocurrencyService cryptocurrencyService;
    private final AnnouncementService announcementService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminPanelController(UserService userService, RoleService roleService, ArticleService articleService, CryptocurrencyService cryptocurrencyService, AnnouncementService announcementService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.articleService = articleService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.announcementService = announcementService;
        this.modelMapper = modelMapper;
    }

    @PageTitle("Change roles")
    @GetMapping("/user-roles")
    public String changeRole(Model model) {
        model.addAttribute("users", this.userService.getAllUsers().stream().map(UserServiceModel::getUsername).collect(Collectors.toList()));
        model.addAttribute("roles", this.roleService.getAllRoles().stream().map(Role::getName).filter(r -> !r.equals("USER")).collect(Collectors.toList()));

        return "admin-panel-user-roles";
    }

    @PostMapping("/user-roles")
    public String postChangeRole(@RequestParam String username, @RequestParam String role, @RequestParam String type,
                             Principal principal, RedirectAttributes redirectAttributes) {
        try {
            this.userService.changeUserRole(username, role, type, principal.getName());
        } catch (ChangeRoleException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/user-roles";
    }

    @PageTitle("Add cryptocurrency")
    @GetMapping("/add-cryptocurrency")
    public String addCryptocurrency(Model model) {
        if (!model.containsAttribute("cryptocurrency")) {
            model.addAttribute("cryptocurrency", new CryptocurrencyBindingModel());
        }

        return "admin-panel-add-cryptocurrency";
    }

    @PostMapping("/add-cryptocurrency")
    public String postAddCryptocurrency(@Valid @ModelAttribute CryptocurrencyBindingModel cryptocurrencyBindingModel, BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes, Principal principal) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("cryptocurrency",cryptocurrencyBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cryptocurrency",bindingResult);
        }else{
            this.cryptocurrencyService.registerCryptocurrency(this.modelMapper.map(cryptocurrencyBindingModel, CryptocurrencyServiceModel.class)
                    ,principal.getName());
        }

        return "redirect:/admin/add-cryptocurrency";
    }

    @PageTitle("Add article")
    @GetMapping("/add-article")
    public String addArticle(Model model) {
        if (!model.containsAttribute("article")) {
            model.addAttribute("article", new ArticleBindingModel());
        }

        return "admin-panel-article-add";
    }

    @PostMapping("/add-article")
    public String postAddArticle(@Valid @ModelAttribute("article") ArticleBindingModel articleBindingModel, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("article", articleBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.article", bindingResult);
        } else {
            this.articleService.registerArticle(this.modelMapper.map(
                    articleBindingModel, ArticleServiceModel.class), principal.getName());
        }

        return "redirect:/admin/add-article";
    }

    @PageTitle("Add announcement")
    @GetMapping("/add-announcement")
    public String addAnnouncement(Model model) {
        if (!model.containsAttribute("announcement")) {
            model.addAttribute("announcement", new AnnouncementBindingModel());
        }

        return "admin-panel-announcement-add";
    }

    @PostMapping("/add-announcement")
    public String postAddAnnouncement(@Valid @ModelAttribute("announcement") AnnouncementBindingModel announcementBindingModel, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("announcement", announcementBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.announcement", bindingResult);
        } else {
            this.announcementService.registerAnnouncement(
                    this.modelMapper.map(announcementBindingModel, AnnouncementServiceModel.class), principal.getName());
        }

        return "redirect:/add-announcement";
    }

    @PageTitle("Delete user")
    @GetMapping("/delete-user")
    public String deleteUser(Model model) {
        model.addAttribute("users", this.userService.getAllUsers().stream().map(UserServiceModel::getUsername).collect(Collectors.toList()));
        model.addAttribute("roles", this.roleService.getAllRoles().stream().map(Role::getName).filter(r -> !r.equals("USER")).collect(Collectors.toList()));

        return "admin-panel-delete-user";
    }

    @PostMapping("/delete-user")
    public String postDeleteUser(@RequestParam String username, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            this.userService.deleteUserByUsername(username, principal.getName());
        } catch (UserDeletionException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/delete-user";
    }


}
