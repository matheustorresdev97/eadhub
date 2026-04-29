package com.matheustorres.eadhub.authuser.controllers;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.dtos.UserDTO;
import com.matheustorres.eadhub.authuser.services.UserService;
import com.matheustorres.eadhub.authuser.specifications.SpecificationTemplate;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                  @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> userModelPage = userService.findAll(spec, pageable);
        if (!userModelPage.isEmpty()) {
            for (User user : userModelPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "userId") UUID userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        userService.delete(userOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted success.");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated(UserDTO.UserView.UserPut.class) @JsonView(UserDTO.UserView.UserPut.class) UserDTO userDTO) {
        User user = userService.updateUser(userId, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated(UserDTO.UserView.PasswordPut.class) @JsonView(UserDTO.UserView.PasswordPut.class) UserDTO userDTO) {
        userService.updatePassword(userId, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated(UserDTO.UserView.ImagePut.class) @JsonView(UserDTO.UserView.ImagePut.class) UserDTO userDTO) {
        User user = userService.updateImage(userId, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
