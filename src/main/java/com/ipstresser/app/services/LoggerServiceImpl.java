package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.Action;
import com.ipstresser.app.domain.entities.Logger;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.repositories.LoggerRepository;
import com.ipstresser.app.services.interfaces.LoggerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoggerServiceImpl implements LoggerService {

    private final LoggerRepository loggerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LoggerServiceImpl(LoggerRepository loggerRepository, ModelMapper modelMapper) {
        this.loggerRepository = loggerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void log(Action action, String description, UserServiceModel userServiceModel, LocalDateTime time) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        Logger logger = new Logger(action, description, user, time);
        this.loggerRepository.save(logger);
    }
}
