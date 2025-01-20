package com.aluracursos.forohub.domain.topic.validations.create;

import com.aluracursos.forohub.domain.course.repository.CourseRepository;
import com.aluracursos.forohub.domain.topic.dto.CreateTopicDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateCreatedCourse implements ValidateCreatedTopic {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void validate(CreateTopicDTO data) {
        var exitedCourse = courseRepository.existsById(data.courseId());
        if (!exitedCourse) {
            try {
                throw new ValidationException("El curso es inexistente");
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }

        var CourseEnabled = courseRepository.findById(data.courseId()).get().getActive();
        if (!CourseEnabled) {
            try {
                throw new ValidationException("El curso no se encuentra actualmente habilitado");
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
