package com.ipstresser.app.repositories;

import com.ipstresser.app.domain.entities.UserActivePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserActivePlanRepository extends JpaRepository<UserActivePlan, String> {

    @Modifying
    @Transactional
    @Query("UPDATE UserActivePlan uap SET uap.leftDays=uap.leftDays-1")
    void decreaseLeftDays();

    @Modifying
    @Transactional
    @Query("DELETE FROM UserActivePlan uap WHERE uap.leftDays=0")
    void clearExpiredPlans();

    @Modifying
    @Transactional
    @Query("UPDATE UserActivePlan uap SET uap.leftAttacksForTheDay=(SELECT p.maxBootsPerDay FROM Plan p WHERE uap.plan.id=p.id)")
    void refreshLeftAttacksForTheDay();
}
