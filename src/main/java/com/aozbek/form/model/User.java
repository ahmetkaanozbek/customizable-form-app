package com.aozbek.form.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @NotBlank(message = "Username shouldn't be blank.")
    @Pattern(regexp = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "Not a valid username.")
    private String username;
    private String password;

    /* Adding default role of USER for now. App doesn't need various roles right now.
    *  There could be ADMIN role in the future (add Roles enum). List<Roles> role */
}
