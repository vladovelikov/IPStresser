package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.models.service.PlanServiceModel;

import java.util.List;

public interface PlanService {

    List<PlanServiceModel> getAllPlans();

    PlanServiceModel getPlanById(String id);

    void deletePlanById(String id);
}
