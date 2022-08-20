package com.ipstresser.app.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ArticleBindingModel {

    @Size(min = 8, max = 80, message = "The title must be between 8 and 40 characters!")
    private String title;

    @Size(min = 50, max = 2000, message = "The description must be between 50 and 2000 characters!")
    private String description;

    @Pattern(regexp = "(http(s?):)([/|.|\\\\w|\\\\s|-])*\\\\.(?:jpg|gif|png)", flags ={Pattern.Flag.CASE_INSENSITIVE} , message = "The image url is not valid!Should start with https:// and ends with jpg|gif|png.")
    private String imageUrl;
}
