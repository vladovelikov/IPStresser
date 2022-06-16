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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cryptocurrencies")
public class Cryptocurrency {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "title", unique = true)
    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @NotNull
    private User author;

    @Column(name = "added_on")
    @NotNull
    private LocalDateTime addedOn;

    @Column(name = "image_url", columnDefinition = "TEXT")
    @NotNull
    private String imageUrl;

    public Cryptocurrency(@NotNull String title, @NotNull String description, @NotNull User author, @NotNull LocalDateTime addedOn, @NotNull String imageUrl) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.addedOn = addedOn;
        this.imageUrl = imageUrl;
    }
}
