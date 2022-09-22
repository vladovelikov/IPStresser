package com.ipstresser.app.web.controllers;

import com.ipstresser.app.domain.models.binding.CommentBindingModel;
import com.ipstresser.app.domain.models.service.CommentServiceModel;
import com.ipstresser.app.domain.models.view.CommentViewModel;
import com.ipstresser.app.services.interfaces.CommentService;
import com.ipstresser.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/about")
public class AboutController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public AboutController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @PageTitle("About us")
    @GetMapping
    public String about(Model model) {
        model.addAttribute("comments", List.of(this.modelMapper.map(
                this.commentService.getAllCommentsSortedByRatingDesc(), CommentViewModel[].class)));

        return "about-us";
    }

    @PageTitle("Create comment")
    @PreAuthorize("not (@commentServiceImpl.hasUserAlreadyCommented(authentication.name))" +
    "and (@transactionServiceImpl.hasUserTransactions(authentication.name))")
    @GetMapping("/comments/create")
    public String createComment(Model model) {
        if (!model.containsAttribute("comment")) {
            model.addAttribute("comment", new CommentBindingModel());
        }

        return "comment-add";
    }

    @PostMapping("/comments/create")
    public String createComment(@Valid CommentBindingModel commentBindingModel, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("comment", commentBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.comment", bindingResult);
            return "redirect:/about/comments/create";
        }

        this.commentService.registerComment(this.modelMapper.map(commentBindingModel, CommentServiceModel.class), principal.getName());
        return "redirect:/about#comments";
    }

    @DeleteMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable String id) {
        this.commentService.deleteCommentById(id);
        return "redirect:/about#comments";
    }
}
