package com.ipstresser.app.domain.models.binding;

import com.ipstresser.app.domain.entities.MethodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class AttackBindingModel {

    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "The host is not valid!")
    private String host;

    @Min(value = 0, message = "The minimum value is 0")
    @Max(value = 65535, message = "The maximum value is 65535")
    private int port;

    private MethodType method;

    @Positive(message = "The time must be greater than 0")
    private int time;

    @Min(value = 1, message = "The minimum value is 1")
    int servers;

}
