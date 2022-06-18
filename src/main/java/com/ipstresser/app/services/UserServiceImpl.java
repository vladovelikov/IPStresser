package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.repositories.UserRepository;
import com.ipstresser.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserServiceModel getUserByEmail(String email) {
        User user = this.userRepository.findUserByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }

        return this.modelMapper.map(user, UserServiceModel.class);
    }
}
