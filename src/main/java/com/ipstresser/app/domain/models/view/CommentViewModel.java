package com.ipstresser.app.domain.models.view;

import com.ipstresser.app.domain.models.service.UserServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentViewModel {

    private int rate;
    private String description;
    private UserServiceModel author;
}
