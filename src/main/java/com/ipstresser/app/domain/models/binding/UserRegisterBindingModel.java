package com.ipstresser.app.domain.models.binding;

import com.ipstresser.app.validation.UniqueUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterBindingModel {

    @NotNull
    @Size(min = 2, max = 15, message = "Username length must be between 2 and 15 characters!")
    @Pattern(regexp = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "The username can contain:alphanumeric characters, underscore and dot,Underscore and dot can't be at the end or start of a username,underscore and dot can't be next to each other ,underscore or dot can't be used multiple times in a row ")
    @UniqueUser(fieldType = "username", message = "User with that username already exists!")
    private String username;

    @Valid
    private PasswordWrapper passwordWrapper;

    @Email(message = "The email is not valid!")
    @UniqueUser(fieldType = "email", message = "User with that email already exists.")
    @NotBlank(message = "Cannot be empty!")
    private String email;

    @Pattern(regexp = "(^$)|(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)",flags ={Pattern.Flag.CASE_INSENSITIVE} , message = "The image url is not valid!Should start with https:// and ends with jpg|gif|png.")
    private String imageUrl;


}
