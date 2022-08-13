package com.ipstresser.app.domain.models.view;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ArticleViewModel {

    private String id;
    private String title;
    private String imageUrl;
    private String description;
    private LocalDateTime addedOn;

}
