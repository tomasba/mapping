package com.learn.mapping.basic.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UserDto(
        Long id,
        // Combined name for display
        String fullName,
        String email,
        String phoneNumber,
        String status,
        // Flattened department info
        String departmentName,
        String departmentCode,
        // Nested addresses
        List<AddressDto> addresses,
        LocalDateTime createdAt
) {

    public UserDto withPhoneNumber(String newPhoneNumber) {
        return new UserDto(id, fullName, email, newPhoneNumber, status, departmentName, departmentCode, addresses, createdAt);
    }

}