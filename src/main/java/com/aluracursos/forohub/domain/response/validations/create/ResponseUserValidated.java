package com.aluracursos.forohub.domain.response.validations.create;

import com.aluracursos.forohub.domain.response.dto.CreateResponseDTO;
import com.aluracursos.forohub.domain.user.repository.UserRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

public class ResponseUserValidated implements ValidateResponseCreated {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(CreateResponseDTO data) {
        var userExited = userRepository.existsById(data.userId());

        if (!userExited) {
            try {
                throw new ValidationException("Este usuario no existe");
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }

        var userHabilitated = userRepository.findById(data.userId()).get().isEnabled();

        if (!userHabilitated) {
            try {
                throw new ValidationException("Este usuario se encuentra inhabilitado");
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
