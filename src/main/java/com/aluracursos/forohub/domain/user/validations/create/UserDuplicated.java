package com.aluracursos.forohub.domain.user.validations.create;

import com.aluracursos.forohub.domain.user.dto.CreateUserDTO;
import com.aluracursos.forohub.domain.user.repository.UserRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDuplicated implements UserCreationValidation {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(CreateUserDTO data) {
        var userDuplicated = userRepository.findByUsername(data.username());

        if (userDuplicated != null) {
            try {
                throw new ValidationException("El usuario: " + data.username() + " ya se encuentra registrado.");
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }

        var emailDuplicated = userRepository.findByEmail(data.email());
        if(emailDuplicated!= null){
            try {
                throw new ValidationException("El email: " + data.email() + " ya se encuentra registrado.");
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
