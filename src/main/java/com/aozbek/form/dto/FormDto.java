package com.aozbek.form.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormDto {
    private String id;
    private String formName;
    private String description;
    private Instant createdDate;
    private String userId;
}
