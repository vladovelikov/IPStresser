package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.exceptions.DuplicatedEmailException;
import com.ipstresser.app.exceptions.DuplicatedUsernameException;
import com.ipstresser.app.repositories.UserRepository;
import com.ipstresser.app.services.interfaces.ConfirmationService;
import com.ipstresser.app.services.interfaces.RoleService;
import com.ipstresser.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationService confirmationService;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, ConfirmationService confirmationService, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.confirmationService = confirmationService;
        this.roleService = roleService;
    }

    @Override
    public UserServiceModel getUserByEmail(String email) {
        User user = this.userRepository.findUserByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(this.roleService.getRoleByName("UNCONFIRMED")));
        user.setRegisteredOn(LocalDateTime.now(ZoneId.systemDefault()));

        this.userRepository.save(user);

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public void deleteUserById(String id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public UserServiceModel getUserByUsername(String username) {
        User user = this.userRepository.findUserByUsername(username).orElse(null);

        if (user == null) {
            return null;
        }

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public void validateUsers(String oldUsername, UserServiceModel modified) {
        User main = this.userRepository.findUserByUsername(oldUsername).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        User userWithUsername = this.userRepository.findUserByUsername(modified.getUsername()).orElse(null);
        User userWithEmail = this.userRepository.findUserByEmail(modified.getEmail()).orElse(null);

        if (userWithUsername != null && userWithUsername.getId().equals(main.getId())) {
            throw new DuplicatedUsernameException("User with this name already exists!");
        }

        if (userWithEmail != null && userWithEmail.getId().equals(main.getId())) {
            throw new DuplicatedEmailException("User with this email already exists!");
        }

    }

    @Override
    public UserServiceModel updateUser(String username, UserServiceModel userServiceModel) {
        User user = this.userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        modifyUser(userServiceModel, user);

        this.userRepository.save(user);
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public void sendConfirmationEmail(String username) {
        String email = this.userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!")).getEmail();

        this.confirmationService.sendConfirmationEmail(email);
    }

    @Override
    public boolean confirmConfirmationCode(String code, String username) {
        boolean isConfirmed = this.confirmationService.confirmConfirmationCode(code);

        if (isConfirmed) {
            this.userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"))
                    .setRoles(Set.of(roleService.getRoleByName("USER")));
        }

        return isConfirmed;
    }

    private void modifyUser(UserServiceModel modified, User main) {
        main.setUsername(modified.getUsername());
        main.setEmail(modified.getEmail());
        main.setPassword(this.passwordEncoder.encode(modified.getPassword()));
        main.setImageUrl(modified.getImageUrl());
    }
}
