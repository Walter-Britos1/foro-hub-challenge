package com.aluracursos.forohub.domain.topic.repository;

import com.aluracursos.forohub.domain.topic.Status;
import com.aluracursos.forohub.domain.topic.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    @SuppressWarnings("null")
    Page<Topic> findAll(Pageable pageable);

    Page<Topic> findAllByStatusIsNot(Status status, Pageable pageable);

    Boolean existeByTitleAndMessage(String title, String message);

    Topic findByTitle(String title);
}
