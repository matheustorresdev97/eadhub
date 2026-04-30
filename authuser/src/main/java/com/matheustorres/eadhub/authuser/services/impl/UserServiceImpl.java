package com.matheustorres.eadhub.authuser.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.matheustorres.eadhub.authuser.domain.enums.UserStatus;
import com.matheustorres.eadhub.authuser.domain.enums.UserType;
import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.dtos.UserDTO;
import com.matheustorres.eadhub.authuser.mappers.UserMapper;
import com.matheustorres.eadhub.authuser.repositories.UserRepository;
import com.matheustorres.eadhub.authuser.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void delete(User user) {
        log.info("UserServiceImpl::delete - Deletando usuário userId {}", user.getUserId());
        userRepository.delete(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User registerUser(UserDTO userDto) {
        log.info("UserServiceImpl::registerUser - Registrando usuário {}", userDto);
        if (existsByUsername(userDto.username())) {
            log.warn("Username {} is Already Taken", userDto.username());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: Username is already taken!");
        }
        if (existsByEmail(userDto.email())) {
            log.warn("Email {} is Already Taken", userDto.email());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: Email is already taken!");
        }

        User user = userMapper.toEntityBuilder(userDto)
            .userStatus(UserStatus.ACTIVE)
            .userType(UserType.STUDENT)
            .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
            .lastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")))
            .build();
        
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UUID userId, UserDTO userDto) {
        log.info("UserServiceImpl::updateUser - Atualizando usuário userId {}", userId);
        User user = findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        user.updateInfo(userDto.fullName(), userDto.phoneNumber(), userDto.cpf());
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(UUID userId, UserDTO userDto) {
        log.info("UserServiceImpl::updatePassword - Atualizando senha userId {}", userId);
        User user = findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.getPassword().equals(userDto.oldPassword())) {
            log.warn("Mismatched old password userId {}", userId);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: Mismatched old password!");
        }
        user.updatePassword(userDto.password());
        userRepository.save(user);
    }

    @Override
    public User updateImage(UUID userId, UserDTO userDto) {
        log.info("UserServiceImpl::updateImage - Atualizando imagem userId {}", userId);
        User user = findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        user.updateImage(userDto.imageUrl());
        return userRepository.save(user);
    }

    @Override
    public User registerInstructor(User user) {
        log.info("UserServiceImpl::registerInstructor - Registrando usuário como instrutor userId {}", user.getUserId());
        user.updateInstructor();
        return userRepository.save(user);
    }
}
