package com.aluracursos.forohub.domain.course;

import com.aluracursos.forohub.domain.course.dto.CreateCourseDTO;
import com.aluracursos.forohub.domain.course.dto.UpDateCourseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
@Entity(name = "Course")
@EqualsAndHashCode(of = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Boolean active;

    public Course(CreateCourseDTO createCourseDTO) {
        this.name = createCourseDTO.name();
        this.category = createCourseDTO.category();
        this.active = true;
    }

    public void upDateCourse(UpDateCourseDTO upDateCourseDTO) {
        if (upDateCourseDTO.name() != null) {
            this.name = upDateCourseDTO.name();
        }
        if (upDateCourseDTO.category() != null) {
            this.category = upDateCourseDTO.category();
        }
        if (upDateCourseDTO.active() != null) {
            this.active = upDateCourseDTO.active();
        }
    }

    public void deleteCourse() {
        this.active = false;
    }
}
