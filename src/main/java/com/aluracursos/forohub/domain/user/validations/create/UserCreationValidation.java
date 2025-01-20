package com.aluracursos.forohub.domain.user.validations.create;

import com.aluracursos.forohub.domain.user.dto.CreateUserDTO;

public interface UserCreationValidation {
    void validate(CreateUserDTO data);
}
