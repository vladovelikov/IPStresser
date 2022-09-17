package com.ipstresser.app.unit.services;

import com.ipstresser.app.domain.entities.Plan;
import com.ipstresser.app.domain.models.service.PlanServiceModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.exceptions.PlanNotFoundException;
import com.ipstresser.app.repositories.PlanRepository;
import com.ipstresser.app.services.PlanServiceImpl;
import com.ipstresser.app.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PlanRepository planRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PlanServiceImpl planService;

    private Plan planOne;

    private Plan planTwo;

    private UserServiceModel user;

    private PlanServiceModel expectedOne;

    private PlanServiceModel expectedTwo;

    @BeforeEach
    public void init() {
        this.planOne = new Plan("Starter",
                new BigDecimal("15"), 30, 200, 45, 1, LocalDateTime.now(ZoneId.systemDefault()));
        this.planOne.setId("1");

        this.planTwo = new Plan("Standard",
                new BigDecimal("30"), 60, 400, 90, 1, LocalDateTime.now(ZoneId.systemDefault()));
        this.planTwo.setId("2");

        this.expectedOne = new PlanServiceModel("Starter",
                new BigDecimal("15"), 30, 200, 45, LocalDateTime.now(ZoneId.systemDefault()), 1);
        this.expectedOne.setId("1");

        this.expectedTwo = new PlanServiceModel("Standart", new BigDecimal("30"), 60, 400, 90, LocalDateTime.now(ZoneId.systemDefault()), 1);
        this.expectedTwo.setId("2");

        this.user = new UserServiceModel();
        user.setId("1");
        user.setUsername("vladimir");
    }

    @Test
    public void getPlanByIdShouldReturnCorrectWhenDataIsValid() {
        Mockito.when(this.planRepository.findById("1")).thenReturn(Optional.of(this.planOne));
        Mockito.when(this.modelMapper.map(this.planOne, PlanServiceModel.class)).thenReturn(this.expectedOne);

        PlanServiceModel actual = this.planService.getPlanById("1");

        assertEquals(this.expectedOne, actual);
    }

    @Test
    public void getPlanByIdShouldThrowExceptionWhenDataIsInvalid() {
        assertThrows(PlanNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                planService.getPlanById("3");
            }
        });
    }

    @Test
    public void getAllPlansShouldReturnAllAvailablePlans() {
        Mockito.when(this.planRepository.findAll()).thenReturn(List.of(this.planOne, this.planTwo));
        Mockito.when(this.modelMapper.map(this.planRepository.findAll(), PlanServiceModel[].class))
                .thenReturn(new PlanServiceModel[]{this.expectedOne, this.expectedTwo});

        List<PlanServiceModel> expected = List.of(expectedOne, expectedTwo);
        List<PlanServiceModel> actual = this.planService.getAllPlans();

        assertEquals(actual, expected);
    }

    @Test
    public void registerPlanShouldRegisterNewPlan() {
        PlanServiceModel input = this.expectedOne;

        Mockito.when(this.userService.getUserByUsername("valeri")).thenReturn(this.user);
        Mockito.when(this.modelMapper.map(input, Plan.class)).thenReturn(planOne);

        this.planService.register(input, "valeri");

        Mockito.verify(this.planRepository).save(planOne);
    }

    @Test
    @WithMockUser(roles = {"ADMIN","ROOT"})
    public void deletePlanByIdShouldWorkCorrect() {
        planService.deletePlanById("1");
        Mockito.verify(planRepository).deleteById("1");
    }




}
