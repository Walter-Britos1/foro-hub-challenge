package com.aluracursos.forohub.domain.response.dto;

import com.aluracursos.forohub.domain.response.Response;

import java.time.LocalDateTime;

public record DetailsResponseDTO(
        Long id,
        String message,
        LocalDateTime dateCreation,
        LocalDateTime lastUpdating,
        Boolean solutions,
        Boolean deleted,
        Long userId,
        String username,
        Long topicId,
        String topic
) {

    public DetailsResponseDTO(Response response) {
        this(
                response.getId(),
                response.getMessage(),
                response.getDateCreating(),
                response.getLastUpdating(),
                response.getSolutions(),
                response.getDeleted(),
                response.getUser().getId(),
                response.getUser().getUsername(),
                response.getTopic().getId(),
                response.getTopic().getTitle()
        );

    }
}
