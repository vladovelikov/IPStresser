package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService {

    List<UserServiceModel> getAllUsers();

    UserServiceModel getUserByEmail(String email);

    void deleteUserByUsername(String username, String currentName);

    UserServiceModel register(UserServiceModel userServiceModel);

    void deleteUserById(String id);

    UserServiceModel getUserByUsername(String username);

    void validateUsers(String username, UserServiceModel modified);

    int getUserAvailableAttacks(String username);

    UserServiceModel updateUser(String username, UserServiceModel userServiceModel);

    UserServiceModel purchasePlan(String id, String username, String cryptocurrency);

    boolean hasUserActivePlan(String username);

    void sendConfirmationEmail(String username);

    boolean confirmConfirmationCode(String code, String username);

    void changeUserRole(String username, String role, String type, String currentUsername);
}
