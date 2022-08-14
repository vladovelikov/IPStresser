package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.models.service.UserServiceModel;

public interface UserService {

    UserServiceModel getUserByEmail(String email);

    UserServiceModel register(UserServiceModel userServiceModel);

    void deleteUserById(String id);

    UserServiceModel getUserByUsername(String username);

    void validateUsers(String username, UserServiceModel modified);

    UserServiceModel updateUser(String username, UserServiceModel userServiceModel);

    UserServiceModel purchasePlan(String id, String username, String cryptocurrency);

    void sendConfirmationEmail(String username);

    boolean confirmConfirmationCode(String code, String username);
}
