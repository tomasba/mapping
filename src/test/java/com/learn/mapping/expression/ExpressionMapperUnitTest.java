package com.learn.mapping.expression;

import com.learn.mapping.basic.dto.UserResponseDto;
import com.learn.mapping.basic.entity.User;
import com.learn.mapping.basic.entity.UserBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("mockdb")
class ExpressionMapperUnitTest {

    @Autowired
    private ExpressionMapper expressionMapper;

    @Test
    void shouldMapUserToResponseWithExpressionFields() {
        User user = new UserBuilder().createUser();
        user.setId(42L);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.doe@learn.com");
        user.setPhoneNumber("+123456789");
        user.setStatus(User.UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.of(2026, 3, 15, 9, 30, 45));

        UserResponseDto result = expressionMapper.toResponse(user);

        assertEquals(42L, result.id());
        assertEquals("Jane Doe", result.fullName());
        assertEquals("jane.doe@learn.com", result.email());
        assertEquals("+123456789", result.phoneNumber());
        assertNull(result.departmentName());
        assertNull(result.departmentCode());
        assertEquals("USR-42", result.displayId());
        assertEquals("2026-03-15 09:30:45", result.formattedDate());
        assertEquals("v2", result.apiVersion());
        assertEquals("INTERNAL", result.source());
        assertEquals("ACTIVE", result.status());
        assertEquals("NORMAL", result.priority());
        assertNotNull(result.trackingId());
        assertTrue(result.trackingId().matches("[0-9a-fA-F\\-]{36}"));
    }

    @Test
    void shouldReturnNullForFormattedDateHelperWhenDateIsNull() {
        assertNull(expressionMapper.formatDate(null));
    }

    @Test
    void shouldFormatDateWithExpectedPattern() {
        String result = expressionMapper.formatDate(LocalDateTime.of(2026, 12, 1, 14, 5, 9));

        assertEquals("2026-12-01 14:05:09", result);
    }

    @Test
    void shouldGenerateTrackingDataOnEachMapping() {
        User user = new UserBuilder().createUser();
        user.setId(7L);
        user.setFirstName("A");
        user.setLastName("User");
        user.setEmail("a.user@learn.com");

        UserResponseDto first = expressionMapper.toResponse(user);
        UserResponseDto second = expressionMapper.toResponse(user);

        assertNotNull(first);
        assertNotNull(second);
        assertEquals(7L, first.id());
        assertEquals("A User", first.fullName());
        assertEquals("A User", second.fullName());
        assertEquals("USR-7", first.displayId());
        assertEquals("USR-7", second.displayId());
        assertEquals("v2", first.apiVersion());
        assertEquals("v2", second.apiVersion());
        assertEquals("INTERNAL", first.source());
        assertEquals("INTERNAL", second.source());
        assertEquals("UNKNOWN", first.status());
        assertEquals("UNKNOWN", second.status());
        assertEquals("NORMAL", first.priority());
        assertEquals("NORMAL", second.priority());
        assertNotNull(first.trackingId());
        assertNotNull(second.trackingId());
        assertNotEquals(first.trackingId(), second.trackingId());
    }
}
