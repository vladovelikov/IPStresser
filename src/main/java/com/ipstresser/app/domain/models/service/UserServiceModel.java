package com.ipstresser.app.domain.models.service;

import com.ipstresser.app.domain.entities.Attack;
import com.ipstresser.app.domain.entities.Role;
import com.ipstresser.app.domain.entities.UserActivePlan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel extends BaseServiceModel {

    private String username;

    private String password;

    private String email;

    private String imageUrl;

    private LocalDateTime registeredOn;

    private UserActivePlan userActivePlan;

    private Set<Role> roles;

    private List<Attack> attacks;

    private CommentServiceModel comment;
}
