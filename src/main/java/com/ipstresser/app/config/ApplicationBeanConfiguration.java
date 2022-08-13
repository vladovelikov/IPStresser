package com.ipstresser.app.config;

import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.domain.models.view.ProfileEditViewModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

        modelMapper.addMappings(editMap);

        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
