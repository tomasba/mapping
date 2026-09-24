package com.learn.mapping.service;

import com.learn.mapping.basic.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(
            @NotBlank(message = "Email is required")
            @Email(message = "Invalid email format") String email);
}