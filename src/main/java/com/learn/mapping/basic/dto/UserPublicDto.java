package com.learn.mapping.basic.dto;

public record UserPublicDto(
        Long id,
        String fullName,
        String email,
        String status
) {
}
