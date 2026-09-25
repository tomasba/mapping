package com.learn.mapping.enummap;

import com.learn.mapping.basic.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EnumMapper {

    // Map enum by name (default behavior)
//    String userStatusToString(User.UserStatus status);
    User.UserStatus stringToUserStatus(String status);

    // Custom enum mapping with @ValueMapping
    @ValueMappings({
            @ValueMapping(source = "ACTIVE", target = "ENABLED"),
            @ValueMapping(source = "INACTIVE", target = "DISABLED"),
            @ValueMapping(source = "PENDING", target = "PENDING_REVIEW"),
            @ValueMapping(source = "SUSPENDED", target = "BLOCKED")
// "<ANY_REMAINING>" can only be used on targets of type enum and not for java.lang.String
//            @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "UNKNOWN")
    })
    String mapStatusToExternal(User.UserStatus status);

    // Reverse mapping
    @InheritInverseConfiguration(name = "mapStatusToExternal")
    User.UserStatus mapExternalToStatus(String external);

}