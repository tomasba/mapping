package com.learn.mapping.conditional;

import com.learn.mapping.basic.dto.UserPublicDto;
import com.learn.mapping.basic.entity.User;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConditionalMapper {

    // Conditional mapping with @Condition
    @Mapping(target = "fullName", source = "firstName")
    @Mapping(target = "email", conditionExpression = "java(isEmailVerified(user))")
    UserPublicDto toPublicDto(User user);

    // Check if email should be included
    default boolean isEmailVerified(User user) {
        // Only include email if user has verified it
        return user != null && user.getStatus() == User.UserStatus.ACTIVE;
    }

    // Check if phone should be included
    default boolean isPhoneVerified(User user) {
        return user != null && user.getPhoneNumber() != null;
    }

    /**
     * Before mapping any string field, check if the value is not null or empty.
     * If it is null or empty, the mapping will be skipped.
     *
     * Might be defined in separate MappingConditions class and imported in the mapper interface
     * to keep it clean -> @Mapper(componentModel = "spring", uses = MappingConditions.class)
     */
    // Null value check mapping
    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

}
