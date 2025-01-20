package com.aluracursos.forohub.domain.response.validations.create;

import com.aluracursos.forohub.domain.response.dto.CreateResponseDTO;
import com.aluracursos.forohub.domain.topic.Status;
import com.aluracursos.forohub.domain.topic.repository.TopicRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

public class ResponseTopicValidate implements ValidateResponseCreated {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validate(CreateResponseDTO data) {
        var topicExisted = topicRepository.existsById(data.topicId());

        if (!topicExisted) {
            try {
                throw new ValidationException("Este topico no existe.");
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }

        var topicOpen = topicRepository.findById(data.topicId()).get().getStatus();

        if (topicOpen != Status.OPEN) {
            try {
                throw new ValidationException("Este topico ya se encuentra cerrado.");
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
