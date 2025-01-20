package com.aluracursos.forohub.domain.topic.validations.create;

import com.aluracursos.forohub.domain.topic.dto.CreateTopicDTO;
import com.aluracursos.forohub.domain.topic.repository.TopicRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicDuplicated implements ValidateCreatedTopic {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validate(CreateTopicDTO data) {
        var topicDuplicated = topicRepository.existeByTitleAndMessage(data.title(), data.message());
        if(topicDuplicated){
            try {
                throw new ValidationException("Este topico ya existe. Revisa /topicos/" + topicRepository.findByTitle(data.title()).getId());
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
