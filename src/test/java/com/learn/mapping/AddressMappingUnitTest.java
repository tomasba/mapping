package com.learn.mapping;

import com.learn.mapping.dto1.AddressDto;
import com.learn.mapping.entity.Address;
import com.learn.mapping.mapper.AddressMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("mockdb")
class AddressMappingUnitTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldMapAddressToAddressDto() {
        // Given
        Address address = new Address();
        address.setId(1L);
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        address.setState("IL");
        address.setZipCode("62701");
        address.setCountry("US");
        address.setType(Address.AddressType.HOME);

        // When
        AddressDto addressDTO = addressMapper.toDto(address);

        // Then
        assertEquals(address.getId(), addressDTO.id());
        assertEquals(address.getStreet(), addressDTO.street());
        assertEquals(address.getCity(), addressDTO.city());
        assertEquals(address.getState(), addressDTO.state());
        assertEquals(address.getZipCode(), addressDTO.zipCode());
        assertEquals(address.getCountry(), addressDTO.country());
        assertEquals(address.getType().name(), addressDTO.type());
        assertNotNull(addressDTO.fullAddress());
    }

    void shouldMapAddressDtoToAddress() {
        // Given
        AddressDto addressDTO = new AddressDto(
                123L,
                "123 Main St",
                "Springfield",
                "IL",
                "62701",
                null,
                "WORK",
                "123 Main St, Springfield, IL 62701, US"
        );

        // When
        Address address = addressMapper.toEntity(addressDTO);

        // Then
        assertNull(addressDTO.id());
        assertEquals(addressDTO.street(), address.getStreet());
        assertEquals(addressDTO.city(), address.getCity());
        assertEquals(addressDTO.state(), address.getState());
        assertEquals(addressDTO.zipCode(), address.getZipCode());
        assertEquals(addressDTO.type(), address.getType().name());
    }
}
