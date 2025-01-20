package com.aluracursos.forohub.domain.topic.validations.create;

import com.aluracursos.forohub.domain.topic.dto.CreateTopicDTO;
import com.aluracursos.forohub.domain.user.repository.UserRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateTopicUser implements ValidateCreatedTopic {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(CreateTopicDTO data) {

        var existUser = userRepository.existsById(data.userId());
        if (!existUser) {
            try {
                throw new ValidationException("El usuario no existe");
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }

        var userAvilited = userRepository.findById(data.userId()).get().getEnabled();
        if (!userAvilited) {
            try {
                throw new ValidationException("El usuario ha sido inhabilitado");
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
