package com.aozbek.form.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormField {
    @Id
    private String id;
    private String fieldLabel;
    private String fieldType;
    // Reference to Form
    private String formId;
}
