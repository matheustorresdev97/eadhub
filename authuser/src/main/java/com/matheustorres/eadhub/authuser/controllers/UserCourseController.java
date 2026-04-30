package com.matheustorres.eadhub.authuser.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

import com.matheustorres.eadhub.authuser.clients.CourseClient;
import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.domain.models.UserCourse;
import com.matheustorres.eadhub.authuser.dtos.CourseDTO;
import com.matheustorres.eadhub.authuser.dtos.UserCourseDTO;
import com.matheustorres.eadhub.authuser.services.UserCourseService;
import com.matheustorres.eadhub.authuser.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserCourseController {

    private final CourseClient courseClient;

    private final UserService userService;

    private final UserCourseService userCourseService;

    @GetMapping("/{userId}/courses")
    public ResponseEntity<Page<CourseDTO>> getAllCoursesByUser(
            @PathVariable(value = "userId") UUID userId,
            @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("GET request GET /users/{}/courses", userId);
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));
    }

       @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                               @RequestBody @Valid UserCourseDTO userCourseDto){
        Optional<User> userOptional = userService.findById(userId);
        if(!userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if(userCourseService.existsByUserAndCourseId(userOptional.get(), userCourseDto.courseId())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
        }
        UserCourse userCourse = userCourseService.save(userOptional.get().convertToUserCourse(userCourseDto.courseId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userCourse);
    }
}
