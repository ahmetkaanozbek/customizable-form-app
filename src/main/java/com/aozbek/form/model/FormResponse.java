package com.aozbek.form.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormResponse {
    @Id
    private String id;
    private Object responseValue;
    private String formFieldId;
}
