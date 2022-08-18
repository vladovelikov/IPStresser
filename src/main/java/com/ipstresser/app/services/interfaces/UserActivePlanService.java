package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.entities.UserActivePlan;

public interface UserActivePlanService {

    void saveActivatedPlan(UserActivePlan userActivePlan);

    void decreaseLeftAttacksForTheDay(UserActivePlan userActivePlan);
}
