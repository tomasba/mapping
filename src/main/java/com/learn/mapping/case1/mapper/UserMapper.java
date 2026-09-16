package com.learn.mapping.case1.mapper;

import com.learn.mapping.case1.dto.UserDto;
import com.learn.mapping.case1.dto.UserUpdateRequest;
import com.learn.mapping.case1.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {AddressMapper.class},
        // Ignore unmapped properties by default (can override per method)
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        // How to handle null values
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "departmentCode", source = "department.code")
    @Mapping(target = "status", source = "status")
    UserDto toDto(User user);

    List<UserDto> toDtoList(List<User> users);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserDto userDto);


    // Update existing entity from update request
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserUpdateRequest request, @MappingTarget User user);

}
