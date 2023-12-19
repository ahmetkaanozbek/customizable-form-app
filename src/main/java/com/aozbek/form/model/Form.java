package com.aozbek.form.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Form")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Form {
    @Id
    private String id;
    private String formName;
    private String description;
    /* Obtain User by getByUsername method */
    @DBRef
    private User user;
}
