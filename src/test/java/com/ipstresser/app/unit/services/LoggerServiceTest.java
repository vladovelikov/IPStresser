package com.ipstresser.app.unit.services;

import com.ipstresser.app.domain.entities.Action;
import com.ipstresser.app.domain.entities.Logger;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.repositories.LoggerRepository;
import com.ipstresser.app.services.LoggerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class LoggerServiceTest {

    @Mock
    private LoggerRepository loggerRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private LoggerServiceImpl loggerService;
    private User user;
    private UserServiceModel userServiceModel;

    @BeforeEach()
    public void init() {
        this.user = new User();
        this.user.setId("1");
        this.user.setUsername("vladimir");

        this.userServiceModel = new UserServiceModel();
        this.userServiceModel.setUsername("vladimir");
        this.userServiceModel.setId("1");
    }

    @Test
    public void loggerShouldWorkCorrect() {
        Mockito.when(this.modelMapper.map(userServiceModel, User.class)).thenReturn(this.user);
        this.loggerService.log(Action.CREATE, "Test action", this.userServiceModel, LocalDateTime.now());

        Mockito.verify(this.loggerRepository).save(ArgumentMatchers.any());
    }
}
