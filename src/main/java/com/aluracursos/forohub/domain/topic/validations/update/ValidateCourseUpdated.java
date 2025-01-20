package com.aluracursos.forohub.domain.topic.validations.update;

import com.aluracursos.forohub.domain.course.repository.CourseRepository;
import com.aluracursos.forohub.domain.topic.dto.UpdateTopicDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateCourseUpdated implements ValidateUpdatedTopic {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void validate(UpdateTopicDTO data) {
        if (data.courseId() != null) {
            var exitsCourse = courseRepository.existsById(data.courseId());
            if (!exitsCourse) {
                try {
                    throw new ValidationException("Este curso es inexistente");
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
            }

            var courseEnable = courseRepository.findById(data.courseId()).get().getActive();
            if (!courseEnable) {
                try {
                    throw new ValidationException("El curso se encuentra inhabilitado en este momento, intente con otro");
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
