package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.models.service.AttackServiceModel;

import java.util.List;

public interface AttackService {

    List<AttackServiceModel> getAllAttacksForCurrentUser(String username);

    void clearAttacksByUsername(String username);

    void validateAttack(int time, int servers, String name);

    AttackServiceModel setAttackExpiredOn(int time, AttackServiceModel attackServiceModel);

    AttackServiceModel launchAttack(AttackServiceModel attackServiceModel, String username);
}
