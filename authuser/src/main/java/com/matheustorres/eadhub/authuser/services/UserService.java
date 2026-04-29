package com.matheustorres.eadhub.authuser.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.dtos.UserDTO;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(UUID userId);

    void delete(User user);

    void save(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User registerUser(UserDTO userDto);

    User updateUser(UUID userId, UserDTO userDto);

    void updatePassword(UUID userId, UserDTO userDto);

    User updateImage(UUID userId, UserDTO userDto);
}
