package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.Plan;
import com.ipstresser.app.domain.models.service.PlanServiceModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.exceptions.PlanNotFoundException;
import com.ipstresser.app.repositories.PlanRepository;
import com.ipstresser.app.services.interfaces.PlanService;
import com.ipstresser.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlanServiceImpl(PlanRepository planRepository, UserService userService, ModelMapper modelMapper) {
        this.planRepository = planRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PlanServiceModel> getAllPlans() {
        return List.of(this.modelMapper.map(this.planRepository.findAll(), PlanServiceModel.class));
    }

    @Override
    public PlanServiceModel getPlanById(String id) {
        return this.modelMapper.map(this.planRepository.findById(id)
                .orElseThrow(() -> new PlanNotFoundException("The plan is not available")), PlanServiceModel.class);
    }

    @Override
    public void deletePlanById(String id) {
        this.planRepository.deleteById(id);
    }

    @Override
    public PlanServiceModel register(PlanServiceModel planServiceModel, String username) {
        UserServiceModel author = this.userService.getUserByUsername(username);

        planServiceModel.setAuthor(author);
        Plan plan = this.modelMapper.map(planServiceModel, Plan.class);

        plan.setCreatedOn(LocalDateTime.now(ZoneId.systemDefault()));
        planRepository.save(plan);

        return this.modelMapper.map(plan, PlanServiceModel.class);
    }


}
