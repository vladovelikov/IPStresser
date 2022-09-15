package com.ipstresser.app.unit.services;

import com.ipstresser.app.domain.entities.Announcement;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.AnnouncementServiceModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.repositories.AnnouncementRepository;
import com.ipstresser.app.services.AnnouncementServiceImpl;
import com.ipstresser.app.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AnnouncementServiceTest {

    @Mock
    private AnnouncementRepository announcementRepository;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AnnouncementServiceImpl announcementService;

    private User user;
    private UserServiceModel userServiceModel;
    private Announcement announcement;
    private AnnouncementServiceModel announcementServiceModel;

    @BeforeEach
    public void init() {
        this.user = new User();
        user.setId("1");
        user.setUsername("vladimir");

        this.announcement = new Announcement();
        this.announcement.setId("1");
        this.announcement.setTitle("Test");

        this.announcementServiceModel = new AnnouncementServiceModel();
        this.announcementServiceModel.setId("1");
        this.announcementServiceModel.setTitle("Test");

        this.userServiceModel = new UserServiceModel();
    }

    @Test
    public void registerAnnouncementShouldWorkCorrect() {
        Mockito.when(modelMapper.map(userServiceModel,User.class)).thenReturn(user);
        Mockito.when(userService.getUserByUsername("vladimir")).thenReturn(userServiceModel);
        Mockito.when(modelMapper.map(announcementServiceModel,Announcement.class)).thenReturn(announcement);

        announcementService.registerAnnouncement(announcementServiceModel, "vladimir");
        Mockito.verify(announcementRepository).save(announcement);
    }

    @Test
    public void getAllAnnouncementsShouldReturnCorrect() {
        Mockito.when(modelMapper.map(
                announcementRepository.findAllByOrderByAddedOnDesc(), AnnouncementServiceModel[].class))
                .thenReturn(new AnnouncementServiceModel[]{announcementServiceModel});

        List<AnnouncementServiceModel> actual = announcementService.getAllAnnouncements();

        assertEquals(1, actual.size());
        assertEquals(announcementServiceModel, actual.get(0));
    }

    @Test
    public void deleteByIdShouldWorkCorrect() {
        this.announcementRepository.deleteById("1");
        Mockito.verify(this.announcementRepository).deleteById("1");
    }




}
