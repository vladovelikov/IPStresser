package com.ipstresser.app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "articles")
public class Article extends BasePublishEntity {

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    public Article(@NotNull String title, @NotNull String description, @NotNull User author, @NotNull LocalDateTime addedOn, String imageUrl) {
        super(title, description, author, addedOn);
        this.imageUrl = imageUrl;
    }

}
