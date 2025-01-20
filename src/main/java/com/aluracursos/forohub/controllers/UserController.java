package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.domain.user.User;
import com.aluracursos.forohub.domain.user.dto.CreateUserDTO;
import com.aluracursos.forohub.domain.user.dto.DetailsUserDTO;
import com.aluracursos.forohub.domain.user.dto.UpdateUserDTO;
import com.aluracursos.forohub.domain.user.repository.UserRepository;
import com.aluracursos.forohub.domain.user.validations.create.UserCreationValidation;
import com.aluracursos.forohub.domain.user.validations.update.ValidateUserUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuario", description = "Crear, publica topicos y sus respuestas")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    List<UserCreationValidation> validationsUser;

    @Autowired
    List<ValidateUserUpdate> validateUserUpdates;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra los nuevos usuarios en la Base de Datos")
    public ResponseEntity<DetailsUserDTO> CreateUser(@RequestBody @Valid CreateUserDTO createUserDTO,
                                                           UriComponentsBuilder uriBuilder){
        validationsUser.forEach(v -> v.validate(createUserDTO));

        String hashedPassword = passwordEncoder.encode(createUserDTO.password());
        User user = new User(createUserDTO, hashedPassword);

        userRepository.save(user);
        var uri = uriBuilder.path("/usuarios/{username}").buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(uri).body(new DetailsUserDTO(user));
    }

    @GetMapping("/all")
    @Operation(summary = "Lista todos los usuarios independientemente de sus estados")
    public ResponseEntity<Page<DetailsUserDTO>> GetAllUsers(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var page = userRepository.findAll(pageable).map(DetailsUserDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    @Operation(summary = "Lista unicamente los usuarios habilitados")
    public ResponseEntity<Page<DetailsUserDTO>> leerUsuariosActivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var page = userRepository.findByEnabledTrue(pageable).map(DetailsUserDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Obtenemos un usuario por un nombre en especifico")
    public ResponseEntity<DetailsUserDTO> GetUser(@PathVariable String username){
        User user = (User) userRepository.findByUsername(username);
        var dataUser = new DetailsUserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getEnabled()
        );

        return ResponseEntity.ok(dataUser);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Obtenemos un usuario por su ID")
    public ResponseEntity<DetailsUserDTO> GetUserById(@PathVariable Long id){
        User user = userRepository.getReferenceById(id);
        var dataUser = new DetailsUserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getEnabled()
        );
        return ResponseEntity.ok(dataUser);
    }

    @PutMapping("/{username}")
    @Transactional
    @Operation(summary = "Actualiza un usuario incluyendo su nombre, apellido, rol, contraseña, email y estado")
    public ResponseEntity<DetailsUserDTO> UpdateUser(@RequestBody @Valid UpdateUserDTO updateUserDTO, @PathVariable String username){
        validateUserUpdates.forEach(v -> v.validate(updateUserDTO));

        User user = (User) userRepository.findByUsername(username);

        if (updateUserDTO.password() != null){
            String hashedPassword = passwordEncoder.encode(updateUserDTO.password());
            user.UpdateUserWhitPassword(updateUserDTO, hashedPassword);

        }else {
            user.UpdateUser(updateUserDTO);
        }

        var dataUser = new DetailsUserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getEnabled()
        );
        return ResponseEntity.ok(dataUser);
    }

    @DeleteMapping("/{username}")
    @Transactional
    @Operation(summary = "Deshabilitamos usuarios")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        User user = (User) userRepository.findByUsername(username);
        user.deleteUser();
        return ResponseEntity.noContent().build();
    }
}
