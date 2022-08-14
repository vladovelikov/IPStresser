package com.ipstresser.app.services;

import com.ipstresser.app.domain.models.service.PlanServiceModel;
import com.ipstresser.app.exceptions.PlanNotFoundException;
import com.ipstresser.app.repositories.PlanRepository;
import com.ipstresser.app.services.interfaces.PlanService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final ModelMapper modelMapper;

    public PlanServiceImpl(PlanRepository planRepository, ModelMapper modelMapper) {
        this.planRepository = planRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PlanServiceModel> getAllPlans() {
        return List.of(this.modelMapper.map(this.planRepository.findAll(), PlanServiceModel.class));
    }

    @Override
    public PlanServiceModel getPlanById(String id) {
        return this.modelMapper.map(this.planRepository.findById(id)
                .orElseThrow(()->new PlanNotFoundException("The plan is not available")), PlanServiceModel.class);
    }

    @Override
    public void deletePlanById(String id) {
        this.planRepository.deleteById(id);
    }


}
