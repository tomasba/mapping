package com.learn.mapping.lazy;

import com.learn.mapping.basic.dto.UserSummaryDto;
import com.learn.mapping.basic.entity.User;
import org.hibernate.Hibernate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserSummaryLazyMapper {

    // Safe mapping that checks if lazy collections are initialized
    @Mapping(target = "addressCount", expression = "java(getAddressCount(user))")
    @Mapping(target = "departmentName", expression = "java(getDepartmentName(user))")
    UserSummaryDto toSummary(User user);

    // Safely get count without triggering lazy load
    default int getAddressCount(User user) {
        if (user.getAddresses() == null || !Hibernate.isInitialized(user.getAddresses())) {
            return 0;
        }
        return user.getAddresses().size();
    }

    // Safely get department name
    default String getDepartmentName(User user) {
        if (user.getDepartment() == null || !Hibernate.isInitialized(user.getDepartment())) {
            return null;
        }
        return user.getDepartment().getName();
    }

}
