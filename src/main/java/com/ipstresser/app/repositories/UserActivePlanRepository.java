package com.ipstresser.app.repositories;

import com.ipstresser.app.domain.entities.UserActivePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivePlanRepository extends JpaRepository<UserActivePlan, String> {
}
