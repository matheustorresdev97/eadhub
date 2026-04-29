package com.matheustorres.eadhub.authuser.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.dtos.UserDTO;
import com.matheustorres.eadhub.authuser.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserDTO.UserView.RegistrationPost.class) @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO) {
        User user = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
