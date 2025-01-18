package com.aluracursos.forohub.domain.topic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTopicDTO(
        @NotBlank String title,
        @NotBlank String message,
        @NotNull Long userId,
        @NotNull Long courseId
) {
}
