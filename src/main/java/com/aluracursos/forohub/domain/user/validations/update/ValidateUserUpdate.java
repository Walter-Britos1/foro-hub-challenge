package com.aluracursos.forohub.domain.user.validations.update;

import com.aluracursos.forohub.domain.user.dto.UpdateUserDTO;

public interface ValidateUserUpdate {
    void validate(UpdateUserDTO data);
}
