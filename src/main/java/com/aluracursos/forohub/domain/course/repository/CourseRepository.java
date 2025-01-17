package com.aluracursos.forohub.domain.course.repository;

import com.aluracursos.forohub.domain.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByActiveTrue(Pageable pageable);
}
