package com.ipstresser.app.config;

import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.domain.models.view.ProfileEditViewModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<UserServiceModel, ProfileEditViewModel> editMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().getPasswordWrapper().setPassword(source.getPassword());
                map().getPasswordWrapper().setConfirmPassword(source.getPassword());
            }
        };

        return modelMapper;
    }
}
