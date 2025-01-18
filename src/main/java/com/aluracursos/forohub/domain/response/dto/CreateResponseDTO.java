package com.aluracursos.forohub.domain.response.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateResponseDTO(
        @NotBlank String message,
        @NotNull Long userId,
        @NotNull Long topicId
) {
}
