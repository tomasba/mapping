package com.learn.mapping.basic_mapping;

import com.learn.mapping.basic.dto.AddressDto;
import com.learn.mapping.basic.dto.UserCreateRequest;
import com.learn.mapping.basic.dto.UserDto;
import com.learn.mapping.basic.dto.UserUpdateRequest;
import com.learn.mapping.basic.entity.*;
import com.learn.mapping.basic.mapper.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAddressMappingUnitTest {

//    private final UserMapper mapperWithDecorator = Mappers.getMapper(UserMapper.class);
//    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
    private UserMapperDecorator mapperWithDecorator;
    private AddressMapper addressMapper;

//    @InjectMocks
//    private UserMapperDecorator decorator;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // don't like it. but couldn't find an appropriate way to inject the delegate into the decorator for testing purposes
        addressMapper = new AddressMapperImpl();
        UserMapper delegate = new UserMapperImpl_();
        ReflectionTestUtils.setField(delegate, "addressMapper", addressMapper);

        mapperWithDecorator = new UserMapperImpl();
        ReflectionTestUtils.setField(mapperWithDecorator, "delegate", delegate);

        Field field = UserMapperImpl.class.getSuperclass().getDeclaredField("delegate");
        field.setAccessible(true);
        field.set(mapperWithDecorator, delegate);
    }

    @Test
    void shouldMapUserToDto() {
        // Given
        Department department = new DepartmentBuilder()
                .setId(1L)
                .setName("Engineering")
                .setCode("ENG")
                .createDepartment();

        User user = new UserBuilder()
                .setId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("john.doe@example.com")
                .setPhoneNumber("555-123-4567")
                .setStatus(User.UserStatus.ACTIVE)
                .setDepartment(department)
                .setCreatedAt(LocalDateTime.now())
                .createUser();

        // When
        UserDto dto = mapperWithDecorator.toDto(user);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.fullName()).isEqualTo("John Doe");
        assertThat(dto.email()).isEqualTo("john.doe@example.com");
        assertThat(dto.departmentName()).isEqualTo("Engineering");
        assertThat(dto.departmentCode()).isEqualTo("ENG");
        assertThat(dto.status()).isEqualTo("ACTIVE");
        assertThat(dto.phoneNumber()).isEqualTo("***-***-4567");
    }

    @Test
    void shouldMapCreateRequestToEntity() {
        // Given
        UserCreateRequest request = UserCreateRequest.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password("securePassword123")
                .phoneNumber("555-987-6543")
                .build();

        // When
        User entity = mapperWithDecorator.toEntity(request);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull(); // ID should be ignored
        assertThat(entity.getFirstName()).isEqualTo("Jane");
        assertThat(entity.getLastName()).isEqualTo("Smith");
        assertThat(entity.getEmail()).isEqualTo("jane.smith@example.com");
        assertThat(entity.getStatus()).isEqualTo(User.UserStatus.PENDING);
    }

    @Test
    void shouldUpdateEntityFromDto() {
        // Given
        User existingUser = new UserBuilder()
                .setId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("john.doe@example.com")
                .setStatus(User.UserStatus.PENDING)
                .createUser();

        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .firstName("Jonathan")
                .email("jonathan.doe@example.com")
                .build();

        // When
        mapperWithDecorator.updateEntityFromDto(updateRequest, existingUser);

        // Then
        assertThat(existingUser.getId()).isEqualTo(1L); // ID preserved
        assertThat(existingUser.getFirstName()).isEqualTo("Jonathan"); // Updated
        assertThat(existingUser.getLastName()).isEqualTo("Doe"); // Unchanged
        assertThat(existingUser.getEmail()).isEqualTo("jonathan.doe@example.com"); // Updated
    }

    @Test
    void shouldHandleNullSource() {
        // When
        UserDto dto = mapperWithDecorator.toDto(null);

        // Then
        assertThat(dto).isNull();
    }

    @Test
    void shouldMapUserList() {
        // Given
        List<User> users = List.of(
                new UserBuilder().setId(1L).setFirstName("John").setLastName("Doe").createUser(),
                new UserBuilder().setId(2L).setFirstName("Jane").setLastName("Smith").createUser()
        );

        // When
        List<UserDto> dtos = mapperWithDecorator.toDtoList(users);

        // Then
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).fullName()).isEqualTo("John Doe");
        assertThat(dtos.get(1).fullName()).isEqualTo("Jane Smith");
    }

    @Test
    void shouldMapAddressWithFullAddress() {
        // Given
        Address address = new AddressBuilder()
                .setId(1L)
                .setStreet("123 Main St")
                .setCity("Springfield")
                .setState("IL")
                .setZipCode("62701")
                .setCountry("USA")
                .setType(Address.AddressType.HOME)
                .createAddress();

        // When
        AddressDto dto = addressMapper.toDto(address);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.fullAddress()).isEqualTo("123 Main St, Springfield, IL 62701, USA");
        assertThat(dto.type()).isEqualTo("HOME");
    }

}
