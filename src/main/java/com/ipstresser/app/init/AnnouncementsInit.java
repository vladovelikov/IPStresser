package com.ipstresser.app.init;

import com.ipstresser.app.domain.entities.Announcement;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.repositories.AnnouncementRepository;
import com.ipstresser.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class AnnouncementsInit implements CommandLineRunner {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

    @Autowired
    public AnnouncementsInit(AnnouncementRepository announcementRepository, UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = this.userRepository.findUserByUsername("vladimir").orElse(null);
        Announcement announcement = new Announcement("Our services are down", "Our services are down due to maintenance.", user,
                LocalDateTime.now(ZoneId.systemDefault()));

        this.announcementRepository.save(announcement);

    }
}
