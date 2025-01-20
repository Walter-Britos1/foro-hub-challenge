package com.aluracursos.forohub.domain.response.repository;

import com.aluracursos.forohub.domain.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    Page<Response> findByTopicId(Long topicId, Pageable pageable);

    Page<Response> findAllByUserId(Long userId, Pageable pageable);

    Response getReferenceByTopicId(Long id);

    @SuppressWarnings("null")
    Response getReferenceById(Long id);
}
