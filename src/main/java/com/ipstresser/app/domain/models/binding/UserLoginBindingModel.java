package com.ipstresser.app.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginBindingModel {

    private String username;
    private String password;
}
