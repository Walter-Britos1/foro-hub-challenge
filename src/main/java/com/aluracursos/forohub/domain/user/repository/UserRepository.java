package com.aluracursos.forohub.domain.user.repository;

import com.aluracursos.forohub.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);

    UserDetails findByEmail(String email);

    Page<User> findByEnabledTrue(Pageable pageable);

    @SuppressWarnings("null")
    User getReferenceById(Long id);

    @SuppressWarnings("null")
    Page<User> findAll(Pageable pageable);

    User getReferenceByUsername(String username);

    Boolean existsByUsername(String username);
}
