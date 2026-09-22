package com.learn.mapping.expression;

import com.learn.mapping.basic.dto.UserResponseDto;
import com.learn.mapping.basic.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * imports = {LocalDateTime.class, DateTimeFormatter.class, UUID.class}
 * MapStruct will add those types as imports in the generated mapper implementation.
 * Then it will allow defining:
 * @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
 * instead of
 * @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
 */
@Mapper(
        componentModel = "spring",
        imports = {LocalDateTime.class, DateTimeFormatter.class, UUID.class}
)
public interface ExpressionMapper {

    // Use Java expression for computed fields
    @Mapping(target = "fullName", expression = "java(entity.getFirstName() + \" \" + entity.getLastName())")
    @Mapping(target = "displayId", expression = "java(\"USR-\" + entity.getId())")
    @Mapping(target = "formattedDate", expression = "java(formatDate(entity.getCreatedAt()))")

    // Constant values
    @Mapping(target = "apiVersion", constant = "v2")
    @Mapping(target = "source", constant = "INTERNAL")
    @Mapping(target = "priority", constant = "NORMAL")

    // Default values when source property exists but is null
    @Mapping(target = "status", defaultValue = "UNKNOWN")

    // Generate UUID
    @Mapping(target = "trackingId", expression = "java(UUID.randomUUID().toString())")
    UserResponseDto toResponse(User entity);

    // Helper method for date formatting
    default String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
