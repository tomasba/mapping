package com.learn.mapping.basic.dto;

import jakarta.validation.constraints.Email;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        @Email(message = "Invalid email format")
        String email,
        String phoneNumber,
        String status,
        Long departmentId
) {
        public static UserUpdateRequest.Builder builder() {
                return new UserUpdateRequest.Builder();
        }

        public static final class Builder {
                private String firstName;
                private String lastName;
                private String email;
                private String phoneNumber;
                private String status;
                private Long departmentId;

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

                public Builder phoneNumber(String phoneNumber) {
                        this.phoneNumber = phoneNumber;
                        return this;
                }

                public Builder status(String status) {
                        this.status = status;
                        return this;
                }

                public Builder departmentId(Long departmentId) {
                        this.departmentId = departmentId;
                        return this;
                }

                public UserUpdateRequest build() {
                        return new UserUpdateRequest(firstName, lastName, email, phoneNumber, status, departmentId);
                }
        }
}
