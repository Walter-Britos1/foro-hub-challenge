package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.domain.course.Course;
import com.aluracursos.forohub.domain.course.dto.CreateCourseDTO;
import com.aluracursos.forohub.domain.course.dto.DetailsCourseDTO;
import com.aluracursos.forohub.domain.course.dto.UpDateCourseDTO;
import com.aluracursos.forohub.domain.course.repository.CourseRepository;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Cursos", description = "Puede formar parte de diversas categorías designadas")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
    @PostMapping
    @Transactional
    @Operation(summary = "Registra nuevos cursos en la base de datos.")
    public ResponseEntity<DetailsCourseDTO> createTopic(@RequestBody @Valid CreateCourseDTO createCourseDTO,
                                                        UriComponentsBuilder uriComponentsBuilder){

        Course course = new Course(createCourseDTO);
        courseRepository.save(course);

        var uri = uriComponentsBuilder.path("/cursos/{i}").buildAndExpand(course.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailsCourseDTO(course));
    }

    @GetMapping("/all")
    @Operation(summary = "Obtiene todos los cursos independientemente en que estado se encuentren")
    public ResponseEntity<Page<DetailsCourseDTO>> CourseList(@PageableDefault(size = 5, sort = {"id"})Pageable pageable) {

        var page = courseRepository.findAll(pageable).map(DetailsCourseDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping
    @Operation(summary = "Obtiene la lista de cursos que esten activos unicamente")
    public ResponseEntity<Page<DetailsCourseDTO>> courseListByActive(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {

        var page = courseRepository.findAllByActiveTrue(pageable).map(DetailsCourseDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un curso en especifico determinado por su id")
    public ResponseEntity<DetailsCourseDTO> GetCurseById(@PathVariable Long id) {

        Course course = courseRepository.getReferenceById(id);

        var dataCourse = new DetailsCourseDTO(
                course.getId(),
                course.getName(),
                course.getCategory(),
                course.getActive()
        );

        return ResponseEntity.ok(dataCourse);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza nombre, categoria y/o el estado de un curso en especifico")
    public ResponseEntity<DetailsCourseDTO> UpdateCourseById(@RequestBody @Valid UpDateCourseDTO upDateCourseDTO,
                                                             @PathVariable Long id) {
        Course course = courseRepository.getReferenceById(id);

        course.upDateCourse(upDateCourseDTO);

        var dataCourse = new DetailsCourseDTO(
                course.getId(),
                course.getName(),
                course.getCategory(),
                course.getActive()
        );

        return ResponseEntity.ok(dataCourse);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un curso determinado por su id")
    public ResponseEntity<?> DeleteCourse(@PathVariable Long id) {

        Course course = courseRepository.getReferenceById(id);

        course.deleteCourse();

        return ResponseEntity.noContent().build();
    }
}
