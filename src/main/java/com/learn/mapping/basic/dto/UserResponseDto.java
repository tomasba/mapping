package com.learn.mapping.basic.dto;

public record UserResponseDto(
        Long id,
        String fullName,
        String email,
        String phoneNumber,
        String departmentName,
        String departmentCode,
        String displayId,
        String formattedDate,
        String apiVersion,
        String source,
        String status,
        String priority,
        String trackingId
) {
}