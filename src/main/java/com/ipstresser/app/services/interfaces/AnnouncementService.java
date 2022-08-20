package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.models.service.AnnouncementServiceModel;

import java.util.List;

public interface AnnouncementService {

    void registerAnnouncement(AnnouncementServiceModel announcementServiceModel, String username);
    List<AnnouncementServiceModel> getAllAnnouncements();

    void deleteById(String id);

}
