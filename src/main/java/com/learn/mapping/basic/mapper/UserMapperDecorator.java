package com.learn.mapping.basic.mapper;

import com.learn.mapping.basic.dto.UserDto;
import com.learn.mapping.basic.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class UserMapperDecorator implements UserMapper {

    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    // You can override methods here to add custom behavior if needed
    @Override
    public UserDto toDto(User user) {
        // Call the generated mapper
        UserDto dto = delegate.toDto(user);

        // Add custom post-processing logic
        if (dto != null) {
            if (dto.phoneNumber() != null) {
                return dto.withPhoneNumber(maskPhoneNumber(dto.phoneNumber()));
            }
        }

        return dto;
    }

    private String maskPhoneNumber(String phone) {
        if (phone.length() <= 4) return phone;
        return "***-***-" + phone.substring(phone.length() - 4);
    }


}
