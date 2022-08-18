package com.ipstresser.app.domain.models.service;

import com.ipstresser.app.domain.entities.Cryptocurrency;
import com.ipstresser.app.domain.entities.Plan;
import com.ipstresser.app.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionServiceModel extends BaseServiceModel {

    private User user;
    private Plan plan;
    private Cryptocurrency paymentMethod;
    private LocalDateTime createdOn;

}
