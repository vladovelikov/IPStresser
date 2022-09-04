package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.Attack;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.entities.UserActivePlan;
import com.ipstresser.app.domain.models.service.AttackServiceModel;
import com.ipstresser.app.repositories.AttackRepository;
import com.ipstresser.app.repositories.UserRepository;
import com.ipstresser.app.services.interfaces.AttackService;
import com.ipstresser.app.services.interfaces.UserActivePlanService;
import com.ipstresser.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

@Service
public class AttackServiceImpl implements AttackService {

    private final AttackRepository attackRepository;
    private final UserActivePlanService userActivePlanService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AttackServiceImpl(AttackRepository attackRepository, UserRepository userRepository, UserActivePlanService userActivePlanService, UserService userService, ModelMapper modelMapper) {
        this.attackRepository = attackRepository;
        this.userActivePlanService = userActivePlanService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AttackServiceModel> getAllAttacksForCurrentUser(String username) {
        List<Attack> attacks = this.attackRepository.findAllByAttacker_Username(username);

        return Arrays.asList(this.modelMapper.map(attacks, AttackServiceModel[].class));
    }

    @Override
    public void clearAttacksByUsername(String username) {
        String userId = this.userService.getUserByUsername(username).getId();
        this.attackRepository.deleteAllAttacksForUser(userId);
    }

    @Override
    public void validateAttack(int time, int servers, String username) {
        StringBuilder errorMessages = new StringBuilder();
        UserActivePlan userActivePlan = this.userService.getUserByUsername(username).getUserActivePlan();

        double includedMaxBootTimeInPlan = userActivePlan.getPlan().getMaxBootTimeInSeconds();
        int includedMaxServersInPlan = userActivePlan.getPlan().getServers();

        if (time > includedMaxBootTimeInPlan) {
            errorMessages.append(String.format("The maximum time included in your plan is: %.0f seconds\n", includedMaxBootTimeInPlan));
        }

        if (servers > includedMaxServersInPlan) {
            errorMessages.append(String.format("The maximum included servers in your plan are: %d\n", includedMaxServersInPlan));
        }

        if (userActivePlan.getLeftAttacksForTheDay() <= 0) {
            errorMessages.append("You do not have left attacks for today.\n");
        }

        if (errorMessages.length() != 0) {
            throw new IllegalArgumentException(errorMessages.toString());
        }

    }

    @Override
    public AttackServiceModel setAttackExpiredOn(int seconds, AttackServiceModel attackServiceModel) {
        attackServiceModel.setExpiresOn(LocalDateTime.now(ZoneId.systemDefault()).plusSeconds(seconds));
        return attackServiceModel;
    }

    @Override
    public AttackServiceModel launchAttack(AttackServiceModel attackServiceModel, String username) {
//        try {
//            serverConnection.sendRequest(attackServiceModel.getHost(),attackServiceModel.getPort(),String.valueOf(15),String.valueOf(2),2);
//        } catch (URISyntaxException e) {
//            System.out.println("ERR");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Attack attack = this.modelMapper.map(attackServiceModel, Attack.class);
        User user = this.modelMapper.map(this.userService.getUserByUsername(username), User.class);

        attack.setAttacker(user);

        this.userActivePlanService.decreaseLeftAttacksForTheDay(user.getUserActivePlan());

        return this.modelMapper.map(this.attackRepository.save(attack), AttackServiceModel.class);
    }


}
