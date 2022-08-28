package com.ipstresser.app.domain.models.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentServiceModel extends BaseServiceModel {

    private int rate;

    private String description;

    private UserServiceModel author;
}
