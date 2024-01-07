package com.aozbek.form.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
    private String username;
    @NotBlank(message = "Password shouldn't be blank.")
    private String password;
}
