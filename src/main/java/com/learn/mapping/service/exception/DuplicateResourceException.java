package com.learn.mapping.service.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String s) {
        super(s);
    }
}
