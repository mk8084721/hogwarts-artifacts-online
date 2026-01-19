package com.mfk.hogwarts_artifacts_online.hogwartsuser.dto;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

public record HogwartsUserDto(
        Integer id,
        @NotBlank(message = "Username is required")
        @Size(min = 1, max = 30, message = "Username must be between 1 and 30 characters")
        @Pattern(
                regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$",
                message = "Invalid username format"
        )
        String username,
        boolean enabled,
        @NotEmpty
        String roles
) {
}
