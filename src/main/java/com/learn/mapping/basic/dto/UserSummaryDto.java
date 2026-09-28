package com.learn.mapping.basic.dto;

public record UserSummaryDto(
        String fullName,
        String email,
        String phoneNumber,
        String status,
        String departmentName,
        int addressCount
) {
}
