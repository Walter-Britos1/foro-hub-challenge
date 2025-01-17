package com.aluracursos.forohub.domain.course.dto;

import com.aluracursos.forohub.domain.course.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCourseDTO(
        @NotBlank String name,
        @NotNull Category category
        ) {
}
