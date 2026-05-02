package com.matheustorres.eadhub.authuser.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.dtos.InstructorDTO;
import com.matheustorres.eadhub.authuser.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDTO instructorDTO) {
        log.debug("POST saveSubscriptionInstructor instructorDTO received {}", instructorDTO.toString());
        Optional<User> userOptional = userService.findById(instructorDTO.userId());
        if (!userOptional.isPresent()) {
            log.warn("User not found for subscription userId {}", instructorDTO.userId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        User user = userService.registerInstructor(userOptional.get());

        log.debug("POST saveSubscriptionInstructor user saved {}", user.toString());
        log.info("User successfully subscribed to instructor userId {}", user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
