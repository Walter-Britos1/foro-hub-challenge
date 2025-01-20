package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.domain.course.Course;
import com.aluracursos.forohub.domain.course.repository.CourseRepository;
import com.aluracursos.forohub.domain.response.Response;
import com.aluracursos.forohub.domain.response.dto.DetailsResponseDTO;
import com.aluracursos.forohub.domain.response.repository.ResponseRepository;
import com.aluracursos.forohub.domain.topic.Status;
import com.aluracursos.forohub.domain.topic.Topic;
import com.aluracursos.forohub.domain.topic.dto.CreateTopicDTO;
import com.aluracursos.forohub.domain.topic.dto.DetailsTipocDTO;
import com.aluracursos.forohub.domain.topic.dto.UpdateTopicDTO;
import com.aluracursos.forohub.domain.topic.repository.TopicRepository;
import com.aluracursos.forohub.domain.topic.validations.create.ValidateCreatedTopic;
import com.aluracursos.forohub.domain.topic.validations.update.ValidateUpdatedTopic;
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
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Topic", description = "Se encuentra vinculado a un curso y a un usuario en especifico")
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    List<ValidateCreatedTopic> createdTopicsValidators;

    @Autowired
    List<ValidateUpdatedTopic> validateUpdatedTopics;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo topic en la Base de Datos")
    public ResponseEntity<DetailsTipocDTO> creteTopic(@RequestBody @Valid CreateTopicDTO createTopicDTO,
                                                      UriComponentsBuilder uriBuilder) {
        createdTopicsValidators.forEach(v -> v.validate(createTopicDTO));

        User user = userRepository.findById(createTopicDTO.userId()).get();
        Course course = courseRepository.findById(createTopicDTO.courseId()).get();
        Topic topic = new Topic(createTopicDTO, user, course);

        topicRepository.save(topic);

        var uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsTipocDTO(topic));
    }

    @GetMapping("/all")
    @Operation(summary = "Obtenemos la lista de topics")
    public ResponseEntity<Page<DetailsTipocDTO>> allTopics(@PageableDefault(size = 5, sort = "{lastUpdating}",
            direction = Sort.Direction.DESC)Pageable pageable) {
        var page = topicRepository.findAll(pageable).map(DetailsTipocDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping
    @Operation(summary = "Lista de temas abiertos y cerrados")
    public ResponseEntity<Page<DetailsTipocDTO>> TopicsNotDeleted(@PageableDefault(size = 5, sort = {"lastUpdating"},
            direction = Sort.Direction.DESC) Pageable pageable){
        var page = topicRepository.findAllByStatusIsNot(Status.DELETED, pageable).map(DetailsTipocDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lee un topic por su ID")
    public ResponseEntity<DetailsTipocDTO> UneTopic(@PathVariable Long id){
        Topic topic = topicRepository.getReferenceById(id);
        var dataTopic = new DetailsTipocDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getDateCreation(),
                topic.getLastUpdating(),
                topic.getStatus(),
                topic.getUser().getUsername(),
                topic.getCourse().getName(),
                topic.getCourse().getCategory()
        );

        return ResponseEntity.ok(dataTopic);
    }

    @GetMapping("/{id}/solucion")
    @Operation(summary = "Muestra las responses de los topics con su solucion")
    public ResponseEntity<DetailsResponseDTO> SolutionTopic(@PathVariable Long id){
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
    @Operation(summary = "Actualiza el título, el mensaje, el estado o el ID del curso de un tema")
    public ResponseEntity<DetailsTipocDTO> UpdatedTopico(@RequestBody @Valid UpdateTopicDTO updateTopicDTO,
                                                         @PathVariable Long id){
        validateUpdatedTopics.forEach(v -> v.validate(updateTopicDTO));

        Topic topic = topicRepository.getReferenceById(id);

        if(updateTopicDTO.courseId() != null){
            Course course = courseRepository.getReferenceById(updateTopicDTO.courseId());
            topic.UpdateTopicWhitCourse(updateTopicDTO, course);
        }else{
            topic.UpdateTopic(updateTopicDTO);
        }

        var dataTopic = new DetailsTipocDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getDateCreation(),
                topic.getLastUpdating(),
                topic.getStatus(),
                topic.getUser().getUsername(),
                topic.getCourse().getName(),
                topic.getCourse().getCategory()
        );
        return ResponseEntity.ok(dataTopic);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un topic")
    public ResponseEntity<?> DeleteTopic(@PathVariable Long id){
        Topic topic = topicRepository.getReferenceById(id);
        topic.deleteTopic();

        return ResponseEntity.noContent().build();
    }
}
