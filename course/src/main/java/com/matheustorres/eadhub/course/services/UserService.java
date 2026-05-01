package com.matheustorres.eadhub.course.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

import com.matheustorres.eadhub.course.domain.models.User;

public interface UserService {

    Page<User> findAll(Specification<User> spec, Pageable pageable);

    User save(User user);

    void delete(UUID userId);

    Optional<User> findById(UUID userId);
}
