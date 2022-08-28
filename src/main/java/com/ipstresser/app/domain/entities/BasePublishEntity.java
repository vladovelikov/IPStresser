package com.ipstresser.app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasePublishEntity extends BaseEntity{

    @Column(unique = true)
    @NotNull
    private String title;


    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    @NotNull
    private User author;


    @Column
    @NotNull
    private LocalDateTime addedOn;


}
