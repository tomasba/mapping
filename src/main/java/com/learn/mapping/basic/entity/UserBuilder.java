package com.learn.mapping.basic.entity;

import java.time.LocalDateTime;
import java.util.Set;

public class UserBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private User.UserStatus status;
    private Department department;
    private Set<Address> addresses;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder setStatus(User.UserStatus status) {
        this.status = status;
        return this;
    }

    public UserBuilder setDepartment(Department department) {
        this.department = department;
        return this;
    }

    public UserBuilder setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public UserBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserBuilder setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public User createUser() {
        return new User(id, firstName, lastName, email, password, phoneNumber, status, department, addresses, createdAt, updatedAt);
    }
}