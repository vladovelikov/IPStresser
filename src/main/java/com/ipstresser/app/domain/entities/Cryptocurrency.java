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
@AllArgsConstructor
@Table(name = "cryptocurrencies")
public class Cryptocurrency extends BasePublishEntity {

    public Cryptocurrency(@NotNull String title, @NotNull String description, @NotNull User author, @NotNull LocalDateTime addedOn, @NotNull String imageUrl) {
        super(title, description, author, addedOn);
        this.imageUrl = imageUrl;
    }

    @Column(name = "image_url", columnDefinition = "TEXT")
    @NotNull
    private String imageUrl;

}
