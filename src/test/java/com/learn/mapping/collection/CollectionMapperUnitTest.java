package com.learn.mapping.collection;

import com.learn.mapping.basic.dto.AddressDto;
import com.learn.mapping.basic.dto.UserDto;
import com.learn.mapping.basic.entity.Address;
import com.learn.mapping.basic.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("mockdb")
class CollectionMapperUnitTest {

    @Autowired
    private CollectionMapper collectionMapper;

    @Test
    void shouldMapAddressSetToAddressDtoList() {
        Address home = new Address();
        home.setId(1L);
        home.setStreet("123 Main St");
        home.setCity("Springfield");
        home.setState("IL");
        home.setZipCode("62701");
        home.setCountry("US");
        home.setType(Address.AddressType.HOME);

        Address work = new Address();
        work.setId(2L);
        work.setStreet("500 Market St");
        work.setCity("Chicago");
        work.setState("IL");
        work.setZipCode("60601");
        work.setCountry("US");
        work.setType(Address.AddressType.WORK);

        List<AddressDto> result = collectionMapper.setToList(new LinkedHashSet<>(List.of(home, work)));

        assertEquals(2, result.size());
        assertEquals("123 Main St", result.get(0).street());
        assertEquals("HOME", result.get(0).type());
        assertEquals("500 Market St", result.get(1).street());
        assertEquals("WORK", result.get(1).type());
    }

    @Test
    void shouldMapAddressDtoListToAddressSet() {
        AddressDto home = new AddressDto(10L, "123 Main St", "Springfield", "IL", "62701", "US", "HOME", "ignored");
        AddressDto billing = new AddressDto(11L, "42 Wallaby Way", "Sydney", "NSW", "2000", "AU", "BILLING", "ignored");

        Set<Address> result = collectionMapper.listToSet(List.of(home, billing));

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(address ->
                "123 Main St".equals(address.getStreet()) && address.getType() == Address.AddressType.HOME));
        assertTrue(result.stream().anyMatch(address ->
                "42 Wallaby Way".equals(address.getStreet()) && address.getType() == Address.AddressType.BILLING));
        assertTrue(result.stream().allMatch(address -> address.getUser() == null));
    }

    @Test
    void shouldMapUserMapWithLongKeysToStringKeys() {
        User user = new User();
        user.setId(7L);
        user.setEmail("jane.doe@learn.com");
        user.setPhoneNumber("+123456789");
        user.setStatus(User.UserStatus.ACTIVE);

        Map<Long, User> users = new LinkedHashMap<>();
        users.put(99L, user);

        Map<String, UserDto> result = collectionMapper.mapUsers(users);

        assertEquals(1, result.size());
        assertTrue(result.containsKey("99"));
        UserDto dto = result.get("99");
        assertNotNull(dto);
        assertEquals(user.getId(), dto.id());
        assertEquals("jane.doe@learn.com", dto.email());
        assertEquals("ACTIVE", dto.status());
    }

    @Test
    void shouldConvertNullLongKeyToNullString() {
        assertNull(collectionMapper.longToString(null));
        assertEquals("15", collectionMapper.longToString(15L));
    }

    @Test
    void shouldMapUserStreamToUserDtoList() {
        User first = new User();
        first.setEmail("a.one@learn.com");
        first.setStatus(User.UserStatus.PENDING);

        User second = new User();
        second.setEmail("b.two@learn.com");
        second.setStatus(User.UserStatus.SUSPENDED);

        List<UserDto> result = collectionMapper.streamToList(Stream.of(first, second));

        assertEquals(2, result.size());
        assertEquals("a.one@learn.com", result.get(0).email());
        assertEquals("PENDING", result.get(0).status());
        assertEquals("b.two@learn.com", result.get(1).email());
        assertEquals("SUSPENDED", result.get(1).status());
        assertInstanceOf(List.class, result);
    }
}
