package com.learn.mapping.conditional;

import com.learn.mapping.basic.dto.UserPublicDto;
import com.learn.mapping.basic.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("mockdb")
class ConditionalMapperUnitTest {

    @Autowired
    private ConditionalMapper conditionalMapper;

    @Test
    void shouldIncludeEmailForActiveUser() {
        User user = new User();
        user.setId(5L);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.doe@learn.com");
        user.setStatus(User.UserStatus.ACTIVE);

        UserPublicDto result = conditionalMapper.toPublicDto(user);

        assertNotNull(result);
        assertEquals(5L, result.id());
        assertEquals("jane.doe@learn.com", result.email());
        assertEquals("ACTIVE", result.status());
    }

    @Test
    void shouldOmitEmailForInactiveUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@learn.com");
        user.setStatus(User.UserStatus.INACTIVE);

        UserPublicDto result = conditionalMapper.toPublicDto(user);

        assertNotNull(result);
        assertNull(result.email());
        assertEquals("INACTIVE", result.status());
    }

    @Test
    void shouldSkipBlankStringValuesViaConditionMethod() {
        assertNull(conditionalMapper.toPublicDto(createUser("   ")).fullName());
        assertNull(conditionalMapper.toPublicDto(createUser(null)).fullName());
    }

    @Test
    void shouldKeepNonBlankStringValuesViaConditionMethod() {
        UserPublicDto result = conditionalMapper.toPublicDto(createUser("Visible Name"));

        assertEquals("Visible Name", result.fullName());
    }

    private static User createUser(String firstName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(null);
        user.setStatus(User.UserStatus.ACTIVE);
        return user;
    }
}
