package com.ipstresser.app.unit.services;

import com.ipstresser.app.domain.entities.*;
import com.ipstresser.app.domain.models.service.CryptocurrencyServiceModel;
import com.ipstresser.app.domain.models.service.PlanServiceModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.exceptions.ChangeRoleException;
import com.ipstresser.app.exceptions.CommentNotFoundException;
import com.ipstresser.app.exceptions.UserDeletionException;
import com.ipstresser.app.exceptions.UserPlanActivationException;
import com.ipstresser.app.repositories.UserRepository;
import com.ipstresser.app.services.UserServiceImpl;
import com.ipstresser.app.services.interfaces.*;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
    private RoleService roleService;

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
        Mockito.when(userRepository.findUserByEmail("vladimir")).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(user, UserServiceModel.class)).thenReturn(userServiceModel);
        UserServiceModel actual = userService.getUserByEmail("vladimir");
        assertEquals(user.getId(), actual.getId());
        assertEquals(user.getUsername(), actual.getUsername());
    }

    @Test
    public void getUserByEmailShouldReturnNull() {
        Mockito.when(userRepository.findUserByEmail("dsb")).thenReturn(null);
        assertNull(userRepository.findUserByEmail("dsb"));
    }

    @Test
    public void hasUserActivePlanShouldWork() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        user.setUserActivePlan(new UserActivePlan(null, 15, 1, null));
        assertTrue(userService.hasUserActivePlan("vladimir"));
        user.setUserActivePlan(null);
        assertFalse(userService.hasUserActivePlan("vladimir"));
    }

    @Test
    public void purchasePlanShouldThrowExceptionIfUserAlreadyHasPlan() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        Mockito.when(planService.getPlanById("1")).thenReturn(new PlanServiceModel());
        Mockito.when(cryptocurrencyService.getCryptocurrencyByName("Bitcoin")).thenReturn(new CryptocurrencyServiceModel());

        Mockito.when(modelMapper.map(planService.getPlanById("1"), Plan.class)).thenReturn(new Plan());
        Mockito.when(modelMapper.map(cryptocurrencyService.getCryptocurrencyByName("Bitcoin"), Cryptocurrency.class)).thenReturn(new Cryptocurrency());

        this.user.setUserActivePlan(new UserActivePlan(null, 15, 1, null));

        assertThrows(UserPlanActivationException.class, () -> {
            userService.purchasePlan("1", "vladimir", "Bitcoin");
        });
    }

    @Test
    public void purchasePlanShouldWork() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        Mockito.when(planService.getPlanById("1")).thenReturn(new PlanServiceModel());
        Mockito.when(cryptocurrencyService.getCryptocurrencyByName("bitcoin")).thenReturn(new CryptocurrencyServiceModel());

        Mockito.when(modelMapper.map(planService.getPlanById("1"), Plan.class)).thenReturn(new Plan());
        Mockito.when(modelMapper.map(cryptocurrencyService.getCryptocurrencyByName("bitcoin"), Cryptocurrency.class)).thenReturn(new Cryptocurrency());

        userService.purchasePlan("1", "vladimir", "bitcoin");

        Mockito.verify(userActivePlanService).saveActivatedPlan(ArgumentMatchers.any());
        Mockito.verify(transactionService).saveTransaction(ArgumentMatchers.any());
    }

    @Test
    public void getUsersAvailableAttacksShouldWork() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        user.setUserActivePlan(new UserActivePlan(null, 15, 1, null));

        assertEquals(1, userService.getUserAvailableAttacks("vladimir"));
    }

    @Test
    public void getAllUsersShouldReturnAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        Mockito.when(modelMapper.map(userRepository.findAll(), UserServiceModel[].class))
                .thenReturn(new UserServiceModel[]{userServiceModel});

        List<UserServiceModel> actual = userService.getAllUsers();

        assertEquals(1, actual.size());
    }

    @Test
    public void deleteUserByUsernameShouldWork() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        userService.deleteUserByUsername("vladimir", "ivan");
        Mockito.verify(userRepository).deleteById("1");
    }

    @Test
    public void deleteUserByUsernameShouldThrowException() {
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
        Mockito.verify(userRepository).deleteById("1");
    }

    @Test
    public void getUserByUsernameShouldReturnUserServiceModel() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(user, UserServiceModel.class)).thenReturn(userServiceModel);
        UserServiceModel actual = userService.getUserByUsername("vladimir");
        assertEquals(actual, userServiceModel);
    }

    @Test
    public void getUserByUsernameShouldReturnNullIfUserIsInvalid() {
        Mockito.when(userRepository.findUserByUsername("vlado")).thenReturn(Optional.empty());
        assertNull(userService.getUserByUsername("vlado"));
    }

    @Test
    public void loadUserByUsernameShouldThrowUserNotFoundException() {
        Mockito.when(userRepository.findUserByUsername("test")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.loadUserByUsername("test");
            }
        });
    }



    @Test
    public void changeUserRoleShouldRemoveRoleIfEverythingIsOK() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        Mockito.when(roleService.getRoleByName("ADMIN")).thenReturn(adminRole);

        userService.changeUserRole("vladimir", "ADMIN", "Remove", "gosho");
        assertFalse(user.getRoles().contains(adminRole));
        assertEquals(1, user.getRoles().size());
    }

    @Test
    public void changeUserRoleShouldAddRoleIfEverythingIsOk() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        Mockito.when(roleService.getRoleByName("USER")).thenReturn(userRole);

        userService.changeUserRole("vladimir", "USER", "Add", "gosho");
        assertTrue(user.getRoles().contains(rootRole));
        assertEquals(3, user.getRoles().size());
    }

    @Test
    public void changeUserRoleShouldThrowChangeRoleException_UserDoesntHaveThisRole() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        Mockito.when(roleService.getRoleByName("ROOT")).thenReturn(userRole);

        assertThrows(ChangeRoleException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.changeUserRole("vladimir", "ROOT", "Remove", "vladimir");
            }
        });
    }

    @Test
    public void changeUserRoleShouldThrowChangeRoleException_SameUser() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        Mockito.when(roleService.getRoleByName("USER")).thenReturn(userRole);

        assertThrows(ChangeRoleException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.changeUserRole("vladimir", "USER", "Add", "vladimir");
            }
        });
    }

    @Test
    public void changeUserRoleShouldThrowChangeRoleException_UserAlreadyHasThatRole() {
        Mockito.when(userRepository.findUserByUsername("vladimir")).thenReturn(Optional.of(user));
        Mockito.when(roleService.getRoleByName("ADMIN")).thenReturn(adminRole);

        assertThrows(ChangeRoleException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.changeUserRole("vladimir", "ADMIN", "Add", "gosho");
            }
        });
    }














}
