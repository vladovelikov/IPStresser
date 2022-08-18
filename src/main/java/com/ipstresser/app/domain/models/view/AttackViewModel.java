package com.ipstresser.app.domain.models.view;

import com.ipstresser.app.domain.entities.MethodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AttackViewModel {

    private String host;

    private String port;

    private MethodType method;

    private int servers;

    private LocalDateTime expiresOn;

}
