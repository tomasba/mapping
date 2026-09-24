package com.learn.mapping.bidirectional;

import com.learn.mapping.basic.dto.AddressDto;
import com.learn.mapping.basic.dto.UserDto;
import com.learn.mapping.basic.entity.Address;
import com.learn.mapping.basic.entity.User;
import com.learn.mapping.basic.mapper.AddressMapper;
import com.learn.mapping.basic.mapper.UserMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",
        uses =  {AddressMapper.class})
public interface BidirectionalMapper {

    // Avoid infinite loops in bidirectional relationships
//    @Mapping(target = "user", ignore = true) // Break the cycle
    @Mapping(target = "fullAddress", expression = "java(addressMapper.buildFullAddress(address))")
    AddressDto addressToDto(Address address);

    /**
     * AddressMapper is used to map Address to AddressDto, but we ignore the user field to avoid infinite recursion.
     * @Mapping(target = "user", ignore = true)
     * And UserMapper uses = {AddressMapper.class}
     * So we do not have cyclic dependency.
     *
     * Here is another way to avoid infinite loops in bidirectional relationships using @Context and a custom mapping context.
     */
    // Or use @Context for more complex scenarios
    @Mapping(target = "addresses", qualifiedByName = "mapAddressesWithoutUser")
    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    UserDto userToDto(User user, @Context CycleAvoidingMappingContext context);

    @Named("mapAddressesWithoutUser")
    default java.util.List<AddressDto> mapAddressesWithoutUser(
            java.util.Set<Address> addresses,
            @Context CycleAvoidingMappingContext context) {
        if (addresses == null) return null;
        return addresses.stream()
                .map(this::addressToDto)
                .toList();
    }

}