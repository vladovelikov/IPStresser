package com.ipstresser.app.web;

import com.ipstresser.app.web.annotations.PageTitle;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @PageTitle("Oops...")
    public ModelAndView handle(Throwable exception, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", exception.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return modelAndView;
    }
}
