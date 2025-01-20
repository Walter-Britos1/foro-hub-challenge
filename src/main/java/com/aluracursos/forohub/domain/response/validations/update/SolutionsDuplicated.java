package com.aluracursos.forohub.domain.response.validations.update;

import com.aluracursos.forohub.domain.response.Response;
import com.aluracursos.forohub.domain.response.dto.UpdatingResponseDTO;
import com.aluracursos.forohub.domain.response.repository.ResponseRepository;
import com.aluracursos.forohub.domain.topic.Status;
import com.aluracursos.forohub.domain.topic.repository.TopicRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolutionsDuplicated implements ValidateResponseUpdated {
    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validate(UpdatingResponseDTO data, Long responseId) {
        if (data.solutions()) {
            Response response = responseRepository.getReferenceById(responseId);
            var topicResolved = topicRepository.getReferenceById(response.getTopic().getId());

            if (topicResolved.getStatus() == Status.CLOSED) {
                try {
                    throw new ValidationException("Ya se soluciono este topico.");
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
