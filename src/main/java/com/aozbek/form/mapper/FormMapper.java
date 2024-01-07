package com.aozbek.form.mapper;

import com.aozbek.form.dto.FormDto;
import com.aozbek.form.model.Form;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FormMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "formName", source = "formDto.formName")
    @Mapping(target = "description", source = "formDto.description")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "userId", source = "userId")
    Form map(FormDto formDto, String userId);
}
