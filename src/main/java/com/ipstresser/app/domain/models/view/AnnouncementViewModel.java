package com.ipstresser.app.domain.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AnnouncementViewModel {

    private String id;

    private String title;

    private String description;

    private LocalDateTime addedOn;
}
