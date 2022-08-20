package com.ipstresser.app.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CommentBindingModel {

    @Min(value = 1, message = "The rate must be between 1 and 5.")
    @Max(value = 5, message = "The rate must be between 1 and 5.")
    private int rate;

    @Size(min = 10, max = 100, message = "The size must be between 10 and 100 characters.")
    private int description;
}
