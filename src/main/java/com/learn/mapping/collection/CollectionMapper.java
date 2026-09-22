package com.learn.mapping.collection;

import com.learn.mapping.basic.dto.AddressDto;
import com.learn.mapping.basic.dto.UserDto;
import com.learn.mapping.basic.entity.Address;
import com.learn.mapping.basic.entity.User;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CollectionMapper {

    // Set to List conversion
    List<AddressDto> setToList(Set<Address> addresses);

    // List to Set conversion
    Set<Address> listToSet(List<AddressDto> dtos);

    // Map keys and values
    @MapMapping(keyTargetType = String.class, valueTargetType = UserDto.class)
    Map<String, UserDto> mapUsers(Map<Long, User> users);

    // Convert Long key to String
    default String longToString(Long value) {
        return value != null ? value.toString() : null;
    }

    // Stream to List
    default List<UserDto> streamToList(java.util.stream.Stream<User> users) {
        return users.map(this::toDto).toList();
    }

    UserDto toDto(User user);

}
