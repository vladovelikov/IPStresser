package com.ipstresser.app.web.controllers;

import com.ipstresser.app.domain.models.service.CryptocurrencyServiceModel;
import com.ipstresser.app.domain.models.view.PlanViewModel;
import com.ipstresser.app.exceptions.UserPlanActivationException;
import com.ipstresser.app.services.interfaces.CryptocurrencyService;
import com.ipstresser.app.services.interfaces.PlanService;
import com.ipstresser.app.services.interfaces.UserService;
import com.ipstresser.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/plans")
public class PlanController {

    private final PlanService planService;
    private final CryptocurrencyService cryptocurrencyService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlanController(PlanService planService, CryptocurrencyService cryptocurrencyService, UserService userService, ModelMapper modelMapper) {
        this.planService = planService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PageTitle("All plans")
    @GetMapping
    public String allPlans(Model model) {
        List<PlanViewModel> plans = List.of(this.modelMapper.map(this.planService.getAllPlans(), PlanViewModel.class));
        model.addAttribute("plans", plans);

        return "pricing";
    }

    @PageTitle("Confirm Plan")
    @GetMapping("/confirm/{id}")
    @PreAuthorize("isAuthenticated()")
    public String confirmPlan(@PathVariable("id") String id, Model model) {
        model.addAttribute("plan", this.planService.getPlanById(id));
        model.addAttribute("crypto", this.cryptocurrencyService.getAllCryptocurrencies().stream()
                .map(CryptocurrencyServiceModel::getTitle).collect(Collectors.toList()));
        model.addAttribute("id", id);

        return "confirm-order";
    }

    @PostMapping("/confirm/{id}")
    public String postConfirmPlan(@PathVariable("id") String id, @PathParam("cryptocurrency") String cryptocurrency, Principal principal,
                                  RedirectAttributes redirectAttributes) {
        try {
            this.userService.purchasePlan(id, principal.getName(), cryptocurrency);
        } catch (UserPlanActivationException ex) {
            redirectAttributes.addFlashAttribute("activationError", ex.getMessage());
        }

        return "redirect:/home/launch";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePlan(@PathVariable("id") String id) {
        this.planService.deletePlanById(id);
        return "redirect:/plans";
    }
}
