package com.ipstresser.app.unit.services;

import com.ipstresser.app.domain.entities.UserActivePlan;
import com.ipstresser.app.repositories.UserActivePlanRepository;
import com.ipstresser.app.repositories.UserRepository;
import com.ipstresser.app.services.UserActivePlanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class UserActivePlanServiceTest {


    @Mock
    private UserActivePlanRepository userActivePlanRepository;

    @InjectMocks
    private UserActivePlanServiceImpl userActivePlanService;

    private UserActivePlan userActivePlan;

    @BeforeEach
    public void init() {
        this.userActivePlan = new UserActivePlan(null, 15, 1, null);
    }

    @Test
    public void decreaseLeftAttacksForTheDayShouldWorkCorrect() {
        userActivePlanService.decreaseLeftAttacksForTheDay(userActivePlan);
        assertEquals(0, userActivePlan.getLeftAttacksForTheDay());
    }

    @Test
    public void decreaseLeftDaysShouldWorkCorrect() {
        userActivePlanService.decreaseLeftDays();
        Mockito.verify(userActivePlanService).decreaseLeftDays();
    }

    @Test
    public void clearExpiredPlansShouldWorkCorrect() {
        userActivePlanService.clearExpiredPlans();
        Mockito.verify(userActivePlanRepository).clearExpiredPlans();
    }

    @Test
    public void refreshLeftAttacksShouldWorkCorrect() {
        userActivePlanService.refreshLeftAttacks();
        Mockito.verify(userActivePlanService).refreshLeftAttacks();
    }

    @Test
    public void saveActivatedPlanShouldWorkCorrect() {
        userActivePlanRepository.save(userActivePlan);
        Mockito.verify(userActivePlanService).saveActivatedPlan(userActivePlan);
    }



}
