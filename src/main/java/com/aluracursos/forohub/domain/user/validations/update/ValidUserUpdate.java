package com.aluracursos.forohub.domain.user.validations.update;

import com.aluracursos.forohub.domain.user.dto.UpdateUserDTO;
import com.aluracursos.forohub.domain.user.repository.UserRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidUserUpdate {
    @Autowired
    private UserRepository userRepository;


    public void validate(UpdateUserDTO data) {
        if (data.email() != null) {
            var emailDuplicated = userRepository.findByEmail(data.email());
            if (emailDuplicated != null) {
                try {
                    throw new ValidationException("El email: " + data.email() + " ya se encuentra registrado");
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
