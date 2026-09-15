package com.learn.mapping.dto;

import jakarta.validation.constraints.Email;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        @Email(message = "Invalid email format")
        String email,
        String phoneNumber,
        String status,
        Long departmentId
) {
}
