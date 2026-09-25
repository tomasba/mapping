package com.learn.mapping.enummap;

import com.learn.mapping.basic.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("mockdb")
class EnumMapperUnitTest {

    @Autowired
    private EnumMapper enumMapper;

    @Test
    void shouldMapStatusByNameInBothDirections() {
//        assertEquals("ACTIVE", enumMapper.userStatusToString(User.UserStatus.ACTIVE));
        assertEquals("ENABLED", enumMapper.mapStatusToExternal(User.UserStatus.ACTIVE));
        assertEquals(User.UserStatus.SUSPENDED, enumMapper.stringToUserStatus("SUSPENDED"));
    }

    @Test
    void shouldMapStatusToExternalValues() {
        assertEquals("ENABLED", enumMapper.mapStatusToExternal(User.UserStatus.ACTIVE));
        assertEquals("DISABLED", enumMapper.mapStatusToExternal(User.UserStatus.INACTIVE));
        assertEquals("PENDING_REVIEW", enumMapper.mapStatusToExternal(User.UserStatus.PENDING));
        assertEquals("BLOCKED", enumMapper.mapStatusToExternal(User.UserStatus.SUSPENDED));
//        assertEquals("UNKNOWN", enumMapper.mapStatusToExternal(User.UserStatus.VOIDED));
        assertEquals("VOIDED", enumMapper.mapStatusToExternal(User.UserStatus.VOIDED));
    }

    @Test
    void shouldMapExternalValuesBackToStatus() {
        assertEquals(User.UserStatus.ACTIVE, enumMapper.mapExternalToStatus("ENABLED"));
        assertEquals(User.UserStatus.INACTIVE, enumMapper.mapExternalToStatus("DISABLED"));
        assertEquals(User.UserStatus.PENDING, enumMapper.mapExternalToStatus("PENDING_REVIEW"));
        assertEquals(User.UserStatus.SUSPENDED, enumMapper.mapExternalToStatus("BLOCKED"));
    }
}
