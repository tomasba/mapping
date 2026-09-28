package com.learn.mapping.basic.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserCreateRequest(
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        String phoneNumber,
        List<AddressCreateRequest> addressCreateRequest,
        Long departmentId
) {
        public static Builder builder() {
                return new Builder();
        }

        public static final class Builder {
                private String firstName;
                private String lastName;
                private String email;
                private String password;
                private String phoneNumber;
                private List<AddressCreateRequest> addressCreateRequest;
                private Long departmentId;

                private Builder() {}

                public Builder firstName(String firstName) {
                        this.firstName = firstName;
                        return this;
                }

                public Builder lastName(String lastName) {
                        this.lastName = lastName;
                        return this;
                }

                public Builder email(String email) {
                        this.email = email;
                        return this;
                }

                public Builder password(String password) {
                        this.password = password;
                        return this;
                }

                public Builder phoneNumber(String phoneNumber) {
                        this.phoneNumber = phoneNumber;
                        return this;
                }

                public Builder departmentId(Long departmentId) {
                        this.departmentId = departmentId;
                        return this;
                }

                public UserCreateRequest build() {
                        return new UserCreateRequest(firstName, lastName, email, password, phoneNumber, addressCreateRequest, departmentId);
                }
        }
}
