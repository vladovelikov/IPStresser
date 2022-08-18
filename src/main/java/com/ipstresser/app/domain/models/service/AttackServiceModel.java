package com.ipstresser.app.domain.models.service;

import com.ipstresser.app.domain.entities.MethodType;
import com.ipstresser.app.domain.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AttackServiceModel extends BaseServiceModel {

    private String host;

    private String port;

    private MethodType method;

    private int servers;

    private LocalDateTime expiresOn;

    //TODO: UserServiceModel here
    private User attacker;
}
