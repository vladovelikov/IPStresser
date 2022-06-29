package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.models.service.UserServiceModel;

public interface UserService {

    UserServiceModel getUserByEmail(String email);

    UserServiceModel register(UserServiceModel userServiceModel);

    void deleteUserById(String id);
}
