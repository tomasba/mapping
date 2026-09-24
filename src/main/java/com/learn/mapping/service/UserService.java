package com.learn.mapping.service;

import com.learn.mapping.basic.dto.UserCreateRequest;
import com.learn.mapping.basic.dto.UserDto;
import com.learn.mapping.basic.dto.UserUpdateRequest;
import com.learn.mapping.basic.entity.Department;
import com.learn.mapping.basic.entity.User;
import com.learn.mapping.basic.mapper.UserMapper;
import com.learn.mapping.service.exception.DuplicateResourceException;
import com.learn.mapping.service.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, DepartmentRepository departmentRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.userMapper = userMapper;
    }

    // Get user by ID and map to DTO
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        log.info("Fetching user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        // MapStruct handles the conversion
        return userMapper.toDto(user);
    }

    // Get all users
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    // Create new user from request
    @Transactional
    public UserDto createUser(UserCreateRequest request) {
        log.info("Creating user with email: {}", request.email());

        // Check for existing email
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists: " + request.email());
        }

        // Map request to entity
        User user = userMapper.toEntity(request);

        // Set department if provided
        if (request.departmentId() != null) {
            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found: " + request.departmentId()));
            user.setDepartment(department);
        }

        // Hash password before saving
        user.setPassword(hashPassword(request.password()));

        // Save and return DTO
        User savedUser = userRepository.save(user);
        log.info("User created with id: {}", savedUser.getId());

        return userMapper.toDto(savedUser);
    }

    // Update existing user
    @Transactional
    public UserDto updateUser(Long id, UserUpdateRequest request) {
        log.info("Updating user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        // Update only non-null fields
        userMapper.updateEntityFromDto(request, user);

        // Handle department update separately
        if (request.departmentId() != null) {
            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found: " + request.departmentId()));
            user.setDepartment(department);
        }

        // Handle status update
        if (request.status() != null) {
            user.setStatus(User.UserStatus.valueOf(request.status()));
        }

        User updatedUser = userRepository.save(user);
        log.info("User updated: {}", updatedUser.getId());

        return userMapper.toDto(updatedUser);
    }

    // Delete user
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found: " + id);
        }

        userRepository.deleteById(id);
        log.info("User deleted: {}", id);
    }

    private String hashPassword(String password) {
        // Use BCrypt or similar in production
        return password; // Placeholder
    }
}