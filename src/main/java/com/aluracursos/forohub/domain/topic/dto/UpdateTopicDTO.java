package com.aluracursos.forohub.domain.topic.dto;

import com.aluracursos.forohub.domain.topic.Status;

public record UpdateTopicDTO(
        String title,
        String message,
        Status status,
        Long courseId
) {
}
