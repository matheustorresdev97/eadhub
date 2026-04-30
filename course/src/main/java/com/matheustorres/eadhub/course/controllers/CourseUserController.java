package com.matheustorres.eadhub.course.controllers;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.eadhub.course.clients.AuthUserClient;
import com.matheustorres.eadhub.course.dtos.ResponsePageDto;
import com.matheustorres.eadhub.course.dtos.UserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseUserController {

    private final AuthUserClient authUserClient;

    @GetMapping("/{courseId}/users")
    public ResponseEntity<ResponsePageDto<UserDTO>> getAllUsersByCourse(
            @PathVariable(value = "courseId") UUID courseId,
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("GET request GET /courses/{}/users", courseId);
        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(courseId, pageable));
    }
}
