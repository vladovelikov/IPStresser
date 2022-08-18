package com.ipstresser.app.domain.models.service;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class PlanServiceModel extends BaseServiceModel {

    @NonNull
    private String type;

    @NonNull
    private BigDecimal price;
    @NonNull
    private int durationInDays;

    @NonNull
    private double maxBootTimeInSeconds;

    @NonNull
    private int maxBootsPerDay;

    @NonNull
    private LocalDateTime createdOn;

    @NonNull
    private int servers;

    @NotNull
    private UserServiceModel author;
}
