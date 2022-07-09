package com.ipstresser.app.init;

import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.repositories.UserRepository;
import com.ipstresser.app.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsersInit implements CommandLineRunner {

    private final RoleService roleService;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersInit(RoleService roleService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (this.userRepository.count() == 0) {
            User admin = new User("vladimir", passwordEncoder.encode("12345678"), "vladimir.velikov1995@gmail.com",
                    "https://i.ytimg.com/vi/WhIrvsbEJ6Q/maxresdefault.jpg", LocalDateTime.now(ZoneId.systemDefault()), null,
                    new HashSet<>(this.roleService.getAllRoles().stream().filter(e->!e.getName().equals("UNCONFIRMED")).collect(Collectors.toSet())), null,
                    null, null, null, null, null);

            User user = new User("test",
                    passwordEncoder.encode("test123"),
                    "test@mail.bg", "", LocalDateTime.now(ZoneId.systemDefault()), null, Set.of(this.roleService.getRoleByName("USER")), null, null
                    ,null,null,null,null);

            User unconfirmed=new User("unconfirmed_user",passwordEncoder.encode("12345678"),"vladimir.velikov123456@gmail.com","",LocalDateTime.now(ZoneId.systemDefault()),null,Set.of(roleService.getRoleByName("UNCONFIRMED")),
                    null,null,null,null,null,null);


            this.userRepository.save(admin);
            this.userRepository.save(user);
            this.userRepository.save(unconfirmed);
        }
    }
}
