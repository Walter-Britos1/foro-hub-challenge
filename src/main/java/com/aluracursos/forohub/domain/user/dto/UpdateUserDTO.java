package com.aluracursos.forohub.domain.user.dto;

import com.aluracursos.forohub.domain.user.Role;

public record UpdateUserDTO(
        String password,
        Role role,
        String name,
        String lastname,
        String email,
        Boolean enabled
) {
}
