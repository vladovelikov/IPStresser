package com.ipstresser.app.unit.services;

import com.ipstresser.app.domain.entities.*;
import com.ipstresser.app.domain.models.service.CryptocurrencyServiceModel;
import com.ipstresser.app.domain.models.service.PlanServiceModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.exceptions.CommentNotFoundException;
import com.ipstresser.app.exceptions.UserDeletionException;
import com.ipstresser.app.exceptions.UserPlanActivationException;
import com.ipstresser.app.repositories.UserRepository;
import com.ipstresser.app.services.UserServiceImpl;
import com.ipstresser.app.services.interfaces.CryptocurrencyService;
import com.ipstresser.app.services.interfaces.PlanService;
import com.ipstresser.app.services.interfaces.TransactionService;
import com.ipstresser.app.services.interfaces.UserActivePlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CryptocurrencyService cryptocurrencyService;

    @Mock
    private PlanService planService;

    @Mock
    private UserActivePlanService userActivePlanService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserServiceModel userServiceModel;

    private Plan plan;

    private Role adminRole;

    private Role rootRole;

    private Role userRole;



    @BeforeEach
    public void init() {
        this.adminRole = new Role("ADMIN");
        this.rootRole = new Role("ROOT");
        this.userRole = new Role("USER");

        this.user = new User();
        this.user.setId("1");
        this.user.setUsername("vladimir");
        this.user.setRoles(new HashSet<>(List.of(this.adminRole, this.rootRole)));

        this.userServiceModel = new UserServiceModel();
        this.userServiceModel.setId("1");
        this.userServiceModel.setUsername("vladimir");
        this.userServiceModel.setUserActivePlan(this.user.getUserActivePlan());

        plan = new Plan("Starter",
                new BigDecimal("15"), 30, 200, 45, 1,
                LocalDateTime.now(ZoneId.systemDefault()));

    }

    @Test
    public void getUserByEmailShouldReturnUser() {
        Mockito.when(this.userRepository.findUserByEmail("vladimir")).thenReturn(Optional.of(this.user));
        Mockito.when(this.modelMapper.map(this.user, UserServiceModel.class)).thenReturn(this.userServiceModel);
        UserServiceModel actual = userService.getUserByEmail("vladimir");
        assertEquals(user.getId(), actual.getId());
        assertEquals(user.getUsername(), actual.getUsername());
    }

    @Test
    public void getUserByEmailShouldReturnNull() {
        Mockito.when(this.userRepository.findUserByEmail("dsb")).thenReturn(Optional.empty());
        assertNull(this.userRepository.findUserByEmail("dsb"));
    }

    @Test
    public void hasUserActivePlanShouldWork() {
        Mockito.when(this.userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(this.user));
        this.user.setUserActivePlan(new UserActivePlan(null, 15, 1, null));
        assertTrue(this.userService.hasUserActivePlan("vladimir"));
        this.user.setUserActivePlan(null);
        assertFalse(this.userService.hasUserActivePlan("vladimir"));
    }

    @Test
    public void purchasePlanShouldThrowExceptionIfUserAlreadyHasPlan() {
        Mockito.when(this.userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(this.user));
        Mockito.when(this.planService.getPlanById("1")).thenReturn(new PlanServiceModel());
        Mockito.when(this.cryptocurrencyService.getCryptocurrencyByName("Bitcoin")).thenReturn(new CryptocurrencyServiceModel());

        Mockito.when(this.modelMapper.map(this.planService.getPlanById("1"), Plan.class)).thenReturn(new Plan());
        Mockito.when(this.modelMapper.map(this.cryptocurrencyService.getCryptocurrencyByName("bitcoin"), Cryptocurrency.class)).thenReturn(new Cryptocurrency());

        this.user.setUserActivePlan(new UserActivePlan(null, 15, 1, null));

        assertThrows(UserPlanActivationException.class, () -> {
            this.userService.purchasePlan("1", "vladimir", "bitcoin");
        });
    }

    @Test
    public void purchasePlanShouldWork() {
        Mockito.when(this.userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(this.user));
        Mockito.when(this.planService.getPlanById("1")).thenReturn(new PlanServiceModel());
        Mockito.when(this.cryptocurrencyService.getCryptocurrencyByName("bitcoin")).thenReturn(new CryptocurrencyServiceModel());

        Mockito.when(this.modelMapper.map(this.planService.getPlanById("1"), Plan.class)).thenReturn(new Plan());
        Mockito.when(this.modelMapper.map(this.cryptocurrencyService.getCryptocurrencyByName("bitcoin"), Cryptocurrency.class)).thenReturn(new Cryptocurrency());

        this.userService.purchasePlan("1", "vladimir", "bitcoin");

        Mockito.verify(this.userActivePlanService).saveActivatedPlan(ArgumentMatchers.any());
        Mockito.verify(this.transactionService).saveTransaction(ArgumentMatchers.any());
    }

    @Test
    public void getUsersAvailableAttacksShouldWork() {
        Mockito.when(this.userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(this.user));
        this.user.setUserActivePlan(new UserActivePlan(null, 15, 1, null));

        assertEquals(1, this.userService.getUserAvailableAttacks("vladimir"));
    }

    @Test
    public void getAllUsersShouldReturnAllUsers() {
        Mockito.when(this.userRepository.findAll()).thenReturn(List.of(this.user));
        Mockito.when(this.modelMapper.map(this.userRepository.findAll(), UserServiceModel[].class))
                .thenReturn(new UserServiceModel[]{userServiceModel});

        List<UserServiceModel> actual = this.userService.getAllUsers();

        assertEquals(1, actual.size());
    }

    @Test
    public void deleteUserByUsernameShouldWork() {
        Mockito.when(this.userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(this.user));
        this.userService.deleteUserByUsername("vladimir", "ivan");
        Mockito.verify(this.userRepository).deleteById("1");
    }

    @Test
    public void deleteUserByUsernameShouldThrowException() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        assertThrows(UserDeletionException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.deleteUserByUsername("vladimir", "vladimir");
            }
        });
    }

    @Test
    public void deleteByIdShouldDeleteUser() {
        this.userService.deleteUserById("1");
        Mockito.verify(this.userService).deleteUserById("1");
    }










}
