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

    @InjectMocks
    private AnnouncementServiceImpl announcementService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;
    private User user;
    private UserServiceModel userServiceModel;
    private Announcement announcement;
    private AnnouncementServiceModel announcementServiceModel;

    @BeforeEach
    public void init() {
        this.user = new User();
        this.user.setId("1");
        this.user.setUsername("vladimir");

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
        Mockito.when(this.userService.getUserByUsername("vladimir")).thenReturn(this.userServiceModel);
        Mockito.when(this.modelMapper.map(this.announcementServiceModel, Announcement.class)).thenReturn(this.announcement);

        this.announcementService.registerAnnouncement(this.announcementServiceModel, "vladimir");
        Mockito.verify(this.announcementRepository).save(this.announcement);
    }

    @Test
    public void getAllAnnouncementsShouldReturnCorrect() {
        Mockito.when(this.announcementService.getAllAnnouncements()).thenReturn(List.of(announcementServiceModel));
        Mockito.when(this.modelMapper.map(
                this.announcementRepository.findAllByOrderByAddedOnDesc(), AnnouncementServiceModel[].class))
                .thenReturn(new AnnouncementServiceModel[]{announcementServiceModel});

        List<AnnouncementServiceModel> actual = this.announcementService.getAllAnnouncements();

        assertEquals(1, actual.size());
        assertEquals(this.announcementServiceModel, actual.get(0));
    }

    @Test
    public void deleteByIdShouldWorkCorrect() {
        this.announcementRepository.deleteById("1");
        Mockito.verify(this.announcementRepository).deleteById("1");
    }




}
