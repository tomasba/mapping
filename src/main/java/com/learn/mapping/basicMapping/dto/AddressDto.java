package com.learn.mapping.basicMapping.dto;

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