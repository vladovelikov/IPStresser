package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.entities.Action;
import com.ipstresser.app.domain.models.service.UserServiceModel;

import java.time.LocalDateTime;

public interface LoggerService {

    void log(Action action, String description, UserServiceModel userServiceModel, LocalDateTime dateTime);
}
