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

import com.matheustorres.eadhub.course.domain.enums.UserStatus;
import com.matheustorres.eadhub.course.domain.models.User;
import com.matheustorres.eadhub.course.domain.models.Course;
import com.matheustorres.eadhub.course.dtos.SubscriptionDTO;
import com.matheustorres.eadhub.course.services.CourseService;
import com.matheustorres.eadhub.course.services.UserService;
import com.matheustorres.eadhub.course.specifications.UserSpec;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import com.matheustorres.eadhub.course.configs.security.AuthenticationCurrentUserService;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseUserController {

    private final CourseService courseService;
    private final UserService userService;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @GetMapping("/{courseId}/users")
    public ResponseEntity<Object> getAllUsersByCourse(
            @PathVariable(value = "courseId") UUID courseId,
            UserSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("GET request GET /courses/{}/users", courseId);
        Optional<Course> courseOptional = courseService.findById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
        }
        validateInstructorAccess(courseOptional.get().getUserInstructor());
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAll(UserSpec.userCourseId(courseId).and(spec), pageable));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
            @RequestBody @Valid SubscriptionDTO subscriptionDTO) {
        validateStudentAccess(subscriptionDTO.userId());
        Optional<Course> courseOptional = courseService.findById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
        }
        
        if (courseService.existsByCourseAndUser(courseId, subscriptionDTO.userId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
        }

        Optional<User> userOptional = userService.findById(subscriptionDTO.userId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        if (userOptional.get().getUserStatus().equals(UserStatus.BLOCKED.name())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked.");
        }

        courseService.saveAndSubscriptionUserInCourseAndSendNotification(courseOptional.get(), userOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.");
    }

    private void validateInstructorAccess(UUID instructorId) {
        UUID currentUserId = authenticationCurrentUserService.getCurrentUser().getUserId();
        if (!currentUserId.equals(instructorId) && !authenticationCurrentUserService.getAuthentication().getAuthorities().toString().contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Forbidden");
        }
    }

    private void validateStudentAccess(UUID userId) {
        UUID currentUserId = authenticationCurrentUserService.getCurrentUser().getUserId();
        if (!currentUserId.equals(userId) && !authenticationCurrentUserService.getAuthentication().getAuthorities().toString().contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Forbidden");
        }
    }
}
