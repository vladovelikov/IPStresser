package com.ipstresser.app.domain.models.view;

import com.ipstresser.app.domain.entities.Role;
import com.ipstresser.app.domain.models.binding.PasswordWrapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProfileEditViewModel {

    private String id;

    @NotNull
    @Size(min = 2, max = 15, message = "Username length must be minimum 2 characters and maximum 15 characters!")
    @Pattern(regexp = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "The username can contain alphanumeric characters, underscore and dot. Underscore and dot can't be at the end or start of a username, underscore and dot can't be next to each other, underscore or dot can't be used multiple times in a row.")
    private String username;

    @Email(message = "The email is not valid!")
    @NotBlank(message = "This field cannot be empty!")
    private String email;

    @Pattern(regexp = "(^$)|(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)", flags = {Pattern.Flag.CASE_INSENSITIVE}, message = "The image url is not valid! It should start with https:// and ends with jpg|gif|png.")
    private String imageUrl;

    @Valid
    private PasswordWrapper passwordWrapper;

    private Set<Role> roles;

    private PlanViewModel plan;


}
