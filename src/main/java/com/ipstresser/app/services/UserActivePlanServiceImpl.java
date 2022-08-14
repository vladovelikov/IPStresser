package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.UserActivePlan;
import com.ipstresser.app.repositories.UserActivePlanRepository;
import com.ipstresser.app.services.interfaces.UserActivePlanService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserActivePlanServiceImpl implements UserActivePlanService {

    private final UserActivePlanRepository userActivePlanRepository;

    public UserActivePlanServiceImpl(UserActivePlanRepository userActivePlanRepository) {
        this.userActivePlanRepository = userActivePlanRepository;
    }

    @Override
    public void saveActivatedPlan(UserActivePlan userActivePlan) {
        this.userActivePlanRepository.save(userActivePlan);
    }
}
