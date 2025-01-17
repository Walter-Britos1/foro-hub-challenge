package com.aluracursos.forohub.domain.course.dto;

import com.aluracursos.forohub.domain.course.Category;
import com.aluracursos.forohub.domain.course.Course;

public record DetailsCourseDTO(
        Long id,
        String name,
        Category category,
        Boolean active
) {
    public DetailsCourseDTO(Course course) {
        this(
                course.getId(),
                course.getName(),
                course.getCategory(),
                course.getActive()
        );
    }
}
