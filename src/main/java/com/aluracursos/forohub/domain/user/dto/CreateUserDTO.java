package com.aluracursos.forohub.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String name,
        @NotBlank String lastname,
        @NotBlank @Email String email
) {
}
