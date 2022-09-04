package com.ipstresser.app.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CryptocurrencyBindingModel {

    @Size(min = 1,max=20,message = "The length must be between 1 and 20 characters")
    private String title;

    @Size(min = 10,max=30,message = "The length must be between 1 and 30 characters")
    private String description;

    //@Pattern(regexp = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)",flags ={Pattern.Flag.CASE_INSENSITIVE} , message = "The image url is not valid!Should start with https:// and ends with jpg|gif|png.")
    private String imageUrl;
}
