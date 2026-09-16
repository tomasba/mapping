package com.learn.mapping;

import com.learn.mapping.case1.dto.UserDto;
import com.learn.mapping.case1.dto.UserUpdateRequest;
import com.learn.mapping.case1.entity.Department;
import com.learn.mapping.case1.entity.User;
import com.learn.mapping.case1.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("mockdb")
class UserMapperUnitTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void shouldMapUserToDtoWithDepartmentAndFullName() {
        User user = new User();
        user.setId(11L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@learn.com");
        user.setPhoneNumber("+123456789");
        user.setStatus(User.UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.of(2026, 1, 1, 10, 0));

        Department department = new Department();
        department.setName("Engineering");
        department.setCode("ENG");
        user.setDepartment(department);

        UserDto dto = userMapper.toDto(user);

        assertEquals(user.getId(), dto.id());
        assertEquals("John Doe", dto.fullName());
        assertEquals(user.getEmail(), dto.email());
        assertEquals(user.getPhoneNumber(), dto.phoneNumber());
        assertEquals(User.UserStatus.ACTIVE.name(), dto.status());
        assertEquals("Engineering", dto.departmentName());
        assertEquals("ENG", dto.departmentCode());
        assertEquals(user.getCreatedAt(), dto.createdAt());
    }

    @Test
    void shouldMapUserToDtoWhenDepartmentIsNull() {
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Roe");
        user.setEmail("jane.roe@learn.com");
        user.setStatus(User.UserStatus.INACTIVE);

        UserDto dto = userMapper.toDto(user);

        assertEquals("Jane Roe", dto.fullName());
        assertEquals(User.UserStatus.INACTIVE.name(), dto.status());
        assertNull(dto.departmentName());
        assertNull(dto.departmentCode());
    }

    @Test
    void shouldMapUserToDtoWhenNamePartsContainNullValues() {
        User user = new User();
        user.setFirstName(null);
        user.setLastName("Doe");

        UserDto dto = userMapper.toDto(user);

        assertEquals("null Doe", dto.fullName());
    }

    @Test
    void shouldMapUserListToDtoList() {
        User user1 = new User();
        user1.setFirstName("A");
        user1.setLastName("One");

        User user2 = new User();
        user2.setFirstName("B");
        user2.setLastName("Two");

        List<UserDto> dtos = userMapper.toDtoList(List.of(user1, user2));

        assertEquals(2, dtos.size());
        assertEquals("A One", dtos.get(0).fullName());
        assertEquals("B Two", dtos.get(1).fullName());
    }

    @Test
    void shouldMapUserDtoToEntityWithPendingStatusAndIgnoredFields() {
        UserDto dto = new UserDto(
                77L,
                "John Doe",
                "john.doe@learn.com",
                "+123456789",
                User.UserStatus.SUSPENDED.name(),
                "Engineering",
                "ENG",
                List.of(),
                LocalDateTime.of(2026, 2, 2, 8, 30)
        );

        User entity = userMapper.toEntity(dto);

        assertNull(entity.getId());
        assertEquals(dto.email(), entity.getEmail());
        assertEquals(dto.phoneNumber(), entity.getPhoneNumber());
        assertEquals(User.UserStatus.PENDING, entity.getStatus());
        assertNull(entity.getDepartment());
        assertNull(entity.getAddresses());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
    }

    @Test
    void shouldUpdateEntityFromDtoIgnoringNullValuesAndRestrictedFields() {
        User user = new User();
        user.setId(100L);
        user.setFirstName("OldFirst");
        user.setLastName("OldLast");
        user.setEmail("old@learn.com");
        user.setPhoneNumber("111");
        user.setPassword("secret");
        user.setStatus(User.UserStatus.INACTIVE);
        user.setCreatedAt(LocalDateTime.of(2025, 1, 1, 0, 0));
        user.setUpdatedAt(LocalDateTime.of(2025, 1, 2, 0, 0));
        user.setAddresses(Set.of());

        Department existingDepartment = new Department();
        existingDepartment.setName("OldDept");
        user.setDepartment(existingDepartment);

        UserUpdateRequest request = new UserUpdateRequest(
                "NewFirst",
                null,
                "new@learn.com",
                null,
                User.UserStatus.ACTIVE.name(),
                999L
        );

        userMapper.updateEntityFromDto(request, user);

        assertEquals(100L, user.getId());
        assertEquals("NewFirst", user.getFirstName());
        assertEquals("OldLast", user.getLastName());
        assertEquals("new@learn.com", user.getEmail());
        assertEquals("111", user.getPhoneNumber());
        assertEquals("secret", user.getPassword());
        assertEquals(User.UserStatus.ACTIVE, user.getStatus());
        assertSame(existingDepartment, user.getDepartment());
        assertNotNull(user.getAddresses());
        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0), user.getCreatedAt());
        assertEquals(LocalDateTime.of(2025, 1, 2, 0, 0), user.getUpdatedAt());
    }
}
