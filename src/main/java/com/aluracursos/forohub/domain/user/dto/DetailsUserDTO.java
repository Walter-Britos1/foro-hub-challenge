package com.aluracursos.forohub.domain.user.dto;

import com.aluracursos.forohub.domain.user.Role;
import com.aluracursos.forohub.domain.user.User;

public record DetailsUserDTO(
        Long id,
        String username,
        Role role,
        String name,
        String lastname,
        String email,
        Boolean enabled
) {
    public DetailsUserDTO(User user) {
        this(user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getEnabled()
        );
    }
}
