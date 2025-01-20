package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.domain.response.Response;
import com.aluracursos.forohub.domain.response.dto.CreateResponseDTO;
import com.aluracursos.forohub.domain.response.dto.DetailsResponseDTO;
import com.aluracursos.forohub.domain.response.dto.UpdatingResponseDTO;
import com.aluracursos.forohub.domain.response.repository.ResponseRepository;
import com.aluracursos.forohub.domain.response.validations.create.ValidateResponseCreated;
import com.aluracursos.forohub.domain.response.validations.update.ValidateResponseUpdated;
import com.aluracursos.forohub.domain.topic.Status;
import com.aluracursos.forohub.domain.topic.Topic;
import com.aluracursos.forohub.domain.topic.repository.TopicRepository;
import com.aluracursos.forohub.domain.user.User;
import com.aluracursos.forohub.domain.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respuestas", description = "Solo puede ser una la solucion al tema")
public class ResponseController {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    List<ValidateResponseCreated> createdValidate;

    @Autowired
    List<ValidateResponseUpdated> updatingsValidate;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra una nueva actividad a la base de datos vinculada a un usuario y tema existente")
    public ResponseEntity<DetailsResponseDTO> createResponse(@RequestBody @Valid CreateResponseDTO createResponseDTO,
                                                             UriComponentsBuilder componentsBuilder) {
        createdValidate.forEach(v -> v.validate(createResponseDTO));

        User user = userRepository.getReferenceById(createResponseDTO.userId());
        Topic topic = topicRepository.findById(createResponseDTO.topicId()).get();

        var response = new Response(createResponseDTO, user, topic);

        var uri = componentsBuilder.path("/respuestas/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsResponseDTO(response));
    }

    @GetMapping("/topico/{topicId}")
    @Operation(summary = "Lee todas las respuestas de un tema dado")
    public ResponseEntity<Page<DetailsResponseDTO>> responseOfTopic(@PageableDefault(size = 5, sort = {"lastUpdating"},
            direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long topicId) {

        var page = responseRepository.findByTopicId(topicId, pageable).map(DetailsResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/usuario/{nameUser}")
    @Operation(summary = "Lee todas las respuesta del usuario que se le proporcione")
    public ResponseEntity<Page<DetailsResponseDTO>> responseOfUsers(@PageableDefault(size = 5, sort = {"lastUpdating"},
           direction = Sort.Direction.ASC)Pageable pageable, @PathVariable Long userId) {

        var page = responseRepository.findAllByUserId(userId, pageable).map(DetailsResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lee una respuesta por su id")
    public ResponseEntity<DetailsResponseDTO> responseById(@PathVariable Long id) {
        Response response = responseRepository.getReferenceByTopicId(id);

        var dataResponse = new DetailsResponseDTO(
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

        return ResponseEntity.ok(dataResponse);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza el mensaje, el estado o la solucion de la respuesta")
    public ResponseEntity<DetailsResponseDTO> updatedResponse(@RequestBody @Valid UpdatingResponseDTO updateResponseDTO,
                                                              @PathVariable Long id) {
        updatingsValidate.forEach(v -> v.validate(updateResponseDTO, id));
        Response response = responseRepository.getReferenceById(id);
        response.UpdatingResponse(updateResponseDTO);

        if (updateResponseDTO.solutions()) {
            var temeResolved = topicRepository.getReferenceById(response.getTopic().getId());
            temeResolved.setStatus(Status.CLOSED);
        }

        var dataResponse = new DetailsResponseDTO(
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

        return ResponseEntity.ok(dataResponse);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina respuestas por su id")
    public ResponseEntity<?> deletedResponse(@PathVariable Long id) {
        Response response = responseRepository.getReferenceById(id);
        response.deleteResponse();

        return ResponseEntity.noContent().build();
    }
}
