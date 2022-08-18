package com.ipstresser.app.services;

import com.ipstresser.app.domain.models.service.AnnouncementServiceModel;
import com.ipstresser.app.repositories.AnnouncementRepository;
import com.ipstresser.app.services.interfaces.AnnouncementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository, ModelMapper modelMapper) {
        this.announcementRepository = announcementRepository;
        this.modelMapper = modelMapper;
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
