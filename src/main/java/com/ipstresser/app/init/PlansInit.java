package com.ipstresser.app.init;

import com.ipstresser.app.domain.entities.Plan;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.repositories.PlanRepository;
import com.ipstresser.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@Order(value = 3)
public class PlansInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    @Autowired
    public PlansInit(UserRepository userRepository, PlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Plan starterPlan = new Plan("Starter", new BigDecimal(25), 30, 200, 45, 1, LocalDateTime.now(ZoneId.systemDefault()));
        Plan standardPlan = new Plan("Standard", new BigDecimal(50), 60, 400, 100, 1, LocalDateTime.now(ZoneId.systemDefault()));
        User user = this.userRepository.findUserByUsername("vladimir").orElse(null);

        starterPlan.setAuthor(user);
        standardPlan.setAuthor(user);

        this.planRepository.save(starterPlan);
        this.planRepository.save(standardPlan);
    }
}
