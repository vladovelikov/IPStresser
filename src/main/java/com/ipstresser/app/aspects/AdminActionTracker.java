package com.ipstresser.app.aspects;

import com.ipstresser.app.domain.entities.Action;
import com.ipstresser.app.domain.entities.Logger;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.services.interfaces.LoggerService;
import com.ipstresser.app.services.interfaces.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@Aspect
public class AdminActionTracker {

    private final LoggerService loggerService;
    private final UserService userService;

    @Autowired
    public AdminActionTracker(LoggerService loggerService, UserService userService) {
        this.loggerService = loggerService;
        this.userService = userService;
    }

    @Pointcut("execution(* com.ipstresser.app.web.controllers.AdminPanelController.post*(..))")
    public void trackAdmin() {

    }

    @After("trackAdmin()")
    public void log (JoinPoint joinPoint){
        UserServiceModel user = this.userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        String description = joinPoint.getSignature().getName().substring(4);
        Action action = null;

        if (description.toLowerCase().contains("change")) {
            action = Action.CHANGE;
        } else if (description.toLowerCase().contains("delete")) {
            action = Action.DELETE;
        } else {
            action = Action.CREATE;
        }

        this.loggerService.log(action, description, user, LocalDateTime.now(ZoneId.systemDefault()));
    }
}
