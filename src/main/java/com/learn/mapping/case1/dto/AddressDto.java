package com.learn.mapping.case1.dto;

public record AddressDto(
        Long id,
        String street,
        String city,
        String state,
        String zipCode,
        String country,
        String type,
        // Computed full address for display
        String fullAddress
) {
}