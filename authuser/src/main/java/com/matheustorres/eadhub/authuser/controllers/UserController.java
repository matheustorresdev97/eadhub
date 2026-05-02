package com.matheustorres.eadhub.authuser.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonView;
import com.matheustorres.eadhub.authuser.configs.security.AuthenticationCurrentUserService;
import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.dtos.UserDTO;
import com.matheustorres.eadhub.authuser.services.UserService;
import com.matheustorres.eadhub.authuser.specifications.UserSpec;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(UserSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> userModelPage = userService.findAll(spec, pageable);
        if (!userModelPage.isEmpty()) {
            for (User user : userModelPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "userId") UUID userId) {
        validateUserAccess(userId);
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        log.debug("DELETE deleteUser userId received {}", userId);
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        userService.delete(userOptional.get());
        log.debug("DELETE deleteUser userModel saved {}", userId);
        log.info("User deleted successfully userId {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted success.");
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated(UserDTO.UserView.UserPut.class) @JsonView(UserDTO.UserView.UserPut.class) UserDTO userDTO) {
        validateUserAccess(userId);
        log.debug("PUT updateUser userDto received {}", userDTO.toString());
        User user = userService.updateUser(userId, userDTO);
        log.debug("PUT updateUser userModel saved {}", user.toString());
        log.info("User updated successfully userId {}", user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated(UserDTO.UserView.PasswordPut.class) @JsonView(UserDTO.UserView.PasswordPut.class) UserDTO userDTO) {
        validateUserAccess(userId);
        log.debug("PUT updatePassword userDto received {}", userDTO.toString());
        userService.updatePassword(userId, userDTO);
        log.info("Password updated successfully userId {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.");
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated(UserDTO.UserView.ImagePut.class) @JsonView(UserDTO.UserView.ImagePut.class) UserDTO userDTO) {
        validateUserAccess(userId);
        log.debug("PUT updateImage userDto received {}", userDTO.toString());
        User user = userService.updateImage(userId, userDTO);
        log.debug("PUT updateImage userModel saved {}", user.toString());
        log.info("User image updated successfully userId {}", user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    private void validateUserAccess(UUID userId) {
        User currentUser = authenticationCurrentUserService.getCurrentUser();
        UUID currentUserId = currentUser.getUserId();
        if (!currentUserId.equals(userId) && !currentUser.getAuthorities().toString().contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Forbidden");
        }
    }
}
