package com.learn.mapping.mapper;

import com.learn.mapping.dto1.AddressDto;
import com.learn.mapping.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "fullAddress", expression = "java(buildFullAddress(address))")
    AddressDto toDto(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address toEntity(AddressDto addressDto);

    default String buildFullAddress(Address address) {
        if (address == null) {
            return null;
        }
        StringBuilder fullAddress = new StringBuilder();
        if (address.getStreet() != null) {
            fullAddress.append(address.getStreet());
        }
        if (address.getCity() != null) {
            if (!fullAddress.isEmpty()) fullAddress.append(", ");
            fullAddress.append(address.getCity());
        }
        if (address.getState() != null) {
            if (!fullAddress.isEmpty()) fullAddress.append(", ");
            fullAddress.append(address.getState());
        }
        if (address.getZipCode() != null) {
            if (!fullAddress.isEmpty()) fullAddress.append(" ");
            fullAddress.append(address.getZipCode());
        }
        if (address.getCountry() != null) {
            if (!fullAddress.isEmpty()) fullAddress.append(", ");
            fullAddress.append(address.getCountry());
        }
        return fullAddress.toString();
    }

    // actually MapStruct can handle enum to string mapping automatically, but if you want to customize it, you can define the following methods:
    // Map address type enum to string
    default String mapAddressType(Address.AddressType type) {
        return type != null ? type.name() : null;
    }
    // Map string to address type enum
    default Address.AddressType mapAddressTypeString(String type) {
        return type != null ? Address.AddressType.valueOf(type) : null;
    }
}