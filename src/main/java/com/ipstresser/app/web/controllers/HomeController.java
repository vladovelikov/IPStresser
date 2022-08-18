package com.ipstresser.app.web.controllers;

import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.binding.AttackBindingModel;
import com.ipstresser.app.domain.models.service.AttackServiceModel;
import com.ipstresser.app.domain.models.view.AnnouncementViewModel;
import com.ipstresser.app.domain.models.view.AttackViewModel;
import com.ipstresser.app.services.interfaces.*;
import com.ipstresser.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final AnnouncementService announcementService;
    private final AttackService attackService;
    private final CommentService commentService;
    private final UserService userService;
    private final TransactionService transactionService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(AnnouncementService announcementService, AttackService attackService, CommentService commentService, UserService userService, TransactionService transactionService, ModelMapper modelMapper) {
        this.announcementService = announcementService;
        this.attackService = attackService;
        this.commentService = commentService;
        this.userService = userService;
        this.transactionService = transactionService;
        this.modelMapper = modelMapper;
    }

    @ResponseBody
    @GetMapping("/launch/refresh")
    public ResponseEntity<List<AttackViewModel>> getAllAttacksForCurrentUser(Principal principal) {
        return ResponseEntity.ok(List.of(this.modelMapper.map(
                this.attackService.getAllAttacksForCurrentUser(principal.getName()), AttackViewModel.class)));
    }

    @ResponseBody
    @GetMapping("/launch/refresh")
    public ResponseEntity<String> clearAllAttacksForCurrentUser(Principal principal) {
        this.attackService.clearAttacksByUsername(principal.getName());

        return ResponseEntity.ok().build();
    }

    @PageTitle("Launch attack")
    @GetMapping("/launch")
    public String launch(Model model, Authentication authentication) {
        String userId = ((User)authentication.getPrincipal()).getId();
        String username = ((User) authentication.getPrincipal()).getUsername();

        if (username != null) {
            model.addAttribute("availableAttacks", this.userService.getUserAvailableAttacks(username));
            model.addAttribute("userId", userId);
            model.addAttribute("attacksHistory", List.of(this.modelMapper.map(
                    this.attackService.getAllAttacksForCurrentUser(username), AttackViewModel.class)));
            model.addAttribute("hasRated", this.commentService.hasUserAlreadyCommented(username));
            model.addAttribute("hasUserActivePlan", this.userService.hasUserActivePlan(username));
            model.addAttribute("hasTransaction", this.transactionService.hasUserTransactions(username));
        }

        if (!model.containsAttribute("attack")) {
            model.addAttribute("attack", new AttackBindingModel());
        }

        return "home-launch-attack";
    }

    @PostMapping("/launch")
    public String postLaunch(@Valid @ModelAttribute AttackBindingModel attackBindingModel,
                             BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {

        if (!this.userService.hasUserActivePlan(principal.getName())) {
            bindingResult.reject("errorCode1", "You do not have an active plan!");
        } else {
            try {
                this.attackService.validateAttack(attackBindingModel.getTime(), attackBindingModel.getServers(), principal.getName());
            } catch (IllegalArgumentException ex) {
                bindingResult.reject("global", ex.getMessage());
            }
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.attack", bindingResult);
            redirectAttributes.addFlashAttribute("attack", attackBindingModel);
            return "redirect:/home/launch";
        }

        AttackServiceModel attackServiceModel = this.modelMapper.map(attackBindingModel, AttackServiceModel.class);
        attackServiceModel = this.attackService.setAttackExpiredOn(attackBindingModel.getTime(), attackServiceModel);

        this.attackService.launchAttack(attackServiceModel, principal.getName());

        return "redirect:/home/launch";
    }

    @GetMapping("/accouncements")
    public String announcements(Model model) {
        model.addAttribute("announcements", this.modelMapper.map(this.announcementService.getAllAnnouncements(), AnnouncementViewModel.class));
        return "home-announcements";
    }

    @DeleteMapping("/announcements/delete/{id}")
    public String deleteAnnouncement(@PathVariable String id) {
        this.announcementService.deleteById(id);

        return "redirect:/home/announcements";
    }
}
