package com.ipstresser.app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "rate")
    @NotNull
    @Min(value = 1,message = "The rating must be >= 1")
    @Max(value = 5,message = "The rating must be <=5")
    private int rate;

    @Column(name = "description")
    @NotNull
    @Size(min = 10,max = 100,message = "The size must be between 10 and 100 characters")
    private String description;

    @OneToOne
    @NotNull
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User author;

    public Comment(int rate, String description, User author) {
        this.rate = rate;
        this.description = description;
        this.author = author;
    }
}
