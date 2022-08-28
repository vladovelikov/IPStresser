package com.ipstresser.app.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "announcements")
public class Announcement extends BasePublishEntity {

    public Announcement(@NotNull String title, @NotNull String description, @NotNull User author, @NotNull LocalDateTime addedOn) {
        super(title, description, author, addedOn);
    }

}
