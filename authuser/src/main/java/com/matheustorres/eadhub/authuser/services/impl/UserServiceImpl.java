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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.matheustorres.eadhub.authuser.domain.enums.UserStatus;
import com.matheustorres.eadhub.authuser.domain.enums.UserType;
import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.dtos.UserDTO;
import com.matheustorres.eadhub.authuser.mappers.UserMapper;
import com.matheustorres.eadhub.authuser.repositories.UserRepository;
import com.matheustorres.eadhub.authuser.services.UserService;
import com.matheustorres.eadhub.authuser.publishers.UserEventPublisher;
import com.matheustorres.eadhub.authuser.domain.enums.ActionType;
import com.matheustorres.eadhub.authuser.domain.enums.RoleType;
import com.matheustorres.eadhub.authuser.domain.models.Role;
import com.matheustorres.eadhub.authuser.services.RoleService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserEventPublisher userEventPublisher;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    @Override
    public void delete(User user) {
        log.info("UserServiceImpl::delete - Deletando usuário userId {}", user.getUserId());
        userRepository.delete(user);
        userEventPublisher.publishUserEvent(userMapper.toEventDTO(user, ActionType.DELETE));
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

        Role role = roleService.findByRoleName(RoleType.ROLE_STUDENT)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Role is not found."));

        User user = userMapper.toEntityBuilder(userDto)
                .password(passwordEncoder.encode(userDto.password()))
                .userStatus(UserStatus.ACTIVE)
                .userType(UserType.STUDENT)
                .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
                .lastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
        user.getRoles().add(role);

        user = userRepository.save(user);
        userEventPublisher.publishUserEvent(userMapper.toEventDTO(user, ActionType.CREATE));
        return user;
    }

    @Override
    public User updateUser(UUID userId, UserDTO userDto) {
        log.info("UserServiceImpl::updateUser - Atualizando usuário userId {}", userId);
        User user = findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        user.updateInfo(userDto.fullName(), userDto.phoneNumber(), userDto.cpf());
        user = userRepository.save(user);
        userEventPublisher.publishUserEvent(userMapper.toEventDTO(user, ActionType.UPDATE));
        return user;
    }

    @Override
    public void updatePassword(UUID userId, UserDTO userDto) {
        log.info("UserServiceImpl::updatePassword - Atualizando senha userId {}", userId);
        User user = findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!passwordEncoder.matches(userDto.oldPassword(), user.getPassword())) {
            log.warn("Mismatched old password userId {}", userId);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: Mismatched old password!");
        }
        user.updatePassword(passwordEncoder.encode(userDto.password()));
        userRepository.save(user);
    }

    @Override
    public User updateImage(UUID userId, UserDTO userDto) {
        log.info("UserServiceImpl::updateImage - Atualizando imagem userId {}", userId);
        User user = findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        user.updateImage(userDto.imageUrl());
        user = userRepository.save(user);
        userEventPublisher.publishUserEvent(userMapper.toEventDTO(user, ActionType.UPDATE));
        return user;
    }

    @Override
    public User registerInstructor(User user) {
        log.info("UserServiceImpl::registerInstructor - Registrando usuário como instrutor userId {}",
                user.getUserId());
        user.updateInstructor();
        user = userRepository.save(user);
        userEventPublisher.publishUserEvent(userMapper.toEventDTO(user, ActionType.UPDATE));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

    public UserDetails loadUserByUserId(UUID userId) throws UsernameNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with userId: " + userId));
    }
}
