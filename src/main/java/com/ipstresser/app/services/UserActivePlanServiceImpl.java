package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.UserActivePlan;
import com.ipstresser.app.repositories.UserActivePlanRepository;
import com.ipstresser.app.services.interfaces.UserActivePlanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActivePlanServiceImpl implements UserActivePlanService {

    private final UserActivePlanRepository userActivePlanRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserActivePlanServiceImpl(UserActivePlanRepository userActivePlanRepository, ModelMapper modelMapper) {
        this.userActivePlanRepository = userActivePlanRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveActivatedPlan(UserActivePlan userActivePlan) {
        this.userActivePlanRepository.save(userActivePlan);
    }

    @Override
    public void decreaseLeftAttacksForTheDay(UserActivePlan userActivePlan) {
        int leftAttacksForTheDay = userActivePlan.getLeftAttacksForTheDay();

        if (leftAttacksForTheDay <= 0) {
            userActivePlan.setLeftAttacksForTheDay(0);
        } else {
            userActivePlan.setLeftAttacksForTheDay(leftAttacksForTheDay - 1);
        }

        this.userActivePlanRepository.save(userActivePlan);
    }
}
