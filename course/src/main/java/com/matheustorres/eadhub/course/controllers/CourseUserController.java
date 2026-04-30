package com.matheustorres.eadhub.course.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import feign.FeignException;

import com.matheustorres.eadhub.course.clients.AuthUserClient;
import com.matheustorres.eadhub.course.domain.enums.UserStatus;
import com.matheustorres.eadhub.course.domain.models.Course;
import com.matheustorres.eadhub.course.domain.models.CourseUser;
import com.matheustorres.eadhub.course.dtos.ResponsePageDto;
import com.matheustorres.eadhub.course.dtos.SubscriptionDTO;
import com.matheustorres.eadhub.course.dtos.UserDTO;
import com.matheustorres.eadhub.course.services.CourseService;
import com.matheustorres.eadhub.course.services.CourseUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseUserController {

    private final AuthUserClient authUserClient;
    private final CourseService courseService;
    private final CourseUserService courseUserService;

    @GetMapping("/{courseId}/users")
    public ResponseEntity<ResponsePageDto<UserDTO>> getAllUsersByCourse(
            @PathVariable(value = "courseId") UUID courseId,
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("GET request GET /courses/{}/users", courseId);
        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(courseId, pageable));
    }
    @PostMapping("/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
            @RequestBody @Valid SubscriptionDTO subscriptionDTO) {
        Optional<Course> courseOptional = courseService.findById(courseId);
        if (!courseOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
        }

        if (courseUserService.existsByCourseAndUserId(courseOptional.get().getCourseId(), subscriptionDTO.userId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
        }
        UserDTO responseUser;
        try {
            responseUser = authUserClient.getUserById(subscriptionDTO.userId());
            if (responseUser.userStatus().equals(UserStatus.BLOCKED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked");
            }
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        CourseUser courseUser = courseUserService
                .saveAndSendSubscriptionUserInCourse(
                        courseOptional.get().convertToCourseUserModel(subscriptionDTO.userId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(courseUser);
    }
}
