package com.ipstresser.app.domain.models.service;

import com.ipstresser.app.domain.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AnnouncementServiceModel extends BaseServiceModel {

    private String title;

    private String description;

    private User author;

    private LocalDateTime addedOn;
}
