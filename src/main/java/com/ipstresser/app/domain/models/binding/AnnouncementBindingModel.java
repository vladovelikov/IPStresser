package com.ipstresser.app.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AnnouncementBindingModel {

    @Size(min = 8, max = 80, message = "The title must be between 8 and 80 characters!")
    private String title;

    @Size(min = 10, max = 200, message = "The description must be between 10 and 200 characters!")
    private String description;



}
