package com.mfk.hogwarts_artifacts_online.hogwartsuser.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HogwartsUserRequest(
        @NotBlank(message = "username is required")
        @Size(min = 1, max = 30)
        @Pattern(
                regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$",
                message = "invalid username format"
        )
        String username,

        @NotBlank(message = "password is required")
        @Size(min = 8, max = 30)
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "password must contain at least one letter and one number"
        )
        String password,
        @NotNull(message = "enabled is required")
        Boolean enabled,

        @NotBlank(message = "roles are required")
        String roles
) {}

