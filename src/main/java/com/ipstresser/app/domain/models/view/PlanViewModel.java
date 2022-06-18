package com.ipstresser.app.domain.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PlanViewModel {

    private String id;

    private String type;

    private BigDecimal price;

    private int durationInDays;

    private double maxBootTimeInSeconds;

    private int maxBootsPerDay;

    private int servers;

}
