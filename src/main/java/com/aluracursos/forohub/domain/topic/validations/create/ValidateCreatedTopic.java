package com.aluracursos.forohub.domain.topic.validations.create;

import com.aluracursos.forohub.domain.topic.dto.CreateTopicDTO;

public interface ValidateCreatedTopic {
    void validate(CreateTopicDTO data);
}
