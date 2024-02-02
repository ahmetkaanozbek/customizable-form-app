package com.aozbek.form.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

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
    /* reference to form */
    private String formId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        FormField that = (FormField) obj;
        return (Objects.equals(fieldLabel, that.fieldLabel)) && (Objects.equals(fieldType, that.fieldType))
                && (Objects.equals(formId, that.formId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldLabel, fieldType, formId);
    }
}
