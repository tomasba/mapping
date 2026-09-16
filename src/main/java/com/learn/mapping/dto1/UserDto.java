package com.learn.mapping.dto1;

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
}
