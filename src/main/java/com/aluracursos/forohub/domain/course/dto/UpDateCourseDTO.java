package com.aluracursos.forohub.domain.course.dto;

import com.aluracursos.forohub.domain.course.Category;

public record UpDateCourseDTO(
        String name,
        Category category,
        Boolean active
) {
}
