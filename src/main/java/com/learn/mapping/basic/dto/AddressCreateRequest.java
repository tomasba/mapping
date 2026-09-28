package com.learn.mapping.basic.dto;

public record AddressCreateRequest(
        String street,
        String city,
        String state,
        String zipCode,
        String country,
        String type
) {
}