package com.matheustorres.eadhub.authuser.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.dtos.UserDTO;

public interface UserService {

    Page<User> findAll(Specification<User> spec, Pageable pageable);

    Optional<User> findById(UUID userId);

    void delete(User user);

    void save(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User registerUser(UserDTO userDto);

    User updateUser(UUID userId, UserDTO userDto);

    void updatePassword(UUID userId, UserDTO userDto);

    User updateImage(UUID userId, UserDTO userDto);

    User registerInstructor(User user);
}
