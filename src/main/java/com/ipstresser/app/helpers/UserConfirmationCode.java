package com.ipstresser.app.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
public class UserConfirmationCode implements Serializable {

    private String code;

    public UserConfirmationCode(String code) {
        this.code = code;
    }

    public UserConfirmationCode() {
    }

    public String getCode() {
        return code;
    }

    public UserConfirmationCode setCode(String code) {
        this.code = code;
        return this;
    }
}
