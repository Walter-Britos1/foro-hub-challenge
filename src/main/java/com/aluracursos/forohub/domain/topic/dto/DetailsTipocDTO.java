package com.aluracursos.forohub.domain.topic.dto;

import com.aluracursos.forohub.domain.course.Category;
import com.aluracursos.forohub.domain.topic.Status;
import com.aluracursos.forohub.domain.topic.Topic;

import java.time.LocalDateTime;

public record DetailsTipocDTO(
        Long id,
        String title,
        String message,
        LocalDateTime dateCreation,
        LocalDateTime lastUpdating,
        Status status,
        String user,
        String course,
        Category categryCourse
) {

    public DetailsTipocDTO(Topic topic) {
        this(topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getDateCreation(),
                topic.getLastUpdating(),
                topic.getStatus(),
                topic.getUser().getUsername(),
                topic.getCourse().getName(),
                topic.getCourse().getCategory());
    }
}
