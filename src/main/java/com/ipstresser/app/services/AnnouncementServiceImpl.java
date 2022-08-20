package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.Announcement;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.AnnouncementServiceModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.repositories.AnnouncementRepository;
import com.ipstresser.app.services.interfaces.AnnouncementService;
import com.ipstresser.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository, UserService userService, ModelMapper modelMapper) {
        this.announcementRepository = announcementRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerAnnouncement(AnnouncementServiceModel announcementServiceModel, String username) {
        User user = this.modelMapper.map(this.userService.getUserByUsername(username), User.class);
        Announcement announcement = this.modelMapper.map(announcementServiceModel, Announcement.class);
        announcement.setAuthor(user);
        announcement.setAddedOn(LocalDateTime.now(ZoneId.systemDefault()));

        this.announcementRepository.save(announcement);
    }

    @Override
    public List<AnnouncementServiceModel> getAllAnnouncements() {
        return List.of(this.modelMapper.map(this.announcementRepository.findAllByOrderByAddedOnDesc(), AnnouncementServiceModel.class));
    }

    @Override
    public void deleteById(String id) {
        this.announcementRepository.deleteById(id);
    }
}
