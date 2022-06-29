package com.ipstresser.app.init;

import com.ipstresser.app.domain.entities.Role;
import com.ipstresser.app.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolesInit implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public RolesInit(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.roleRepository.count() == 0) {
            Role admin = new Role("ADMIN");
            Role root = new Role("ROOT");
            Role user = new Role("USER");
            Role unconfirmed = new Role("UNCONFIRMED");

            this.roleRepository.saveAll(List.of(admin, root, user, unconfirmed));
        }
    }

}
