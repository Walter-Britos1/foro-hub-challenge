package com.aluracursos.forohub.domain.response.validations.update;

import com.aluracursos.forohub.domain.response.dto.UpdatingResponseDTO;

public interface ValidateResponseUpdated {
    void validate(UpdatingResponseDTO data, Long responseId);
}
