package com.matheustorres.eadhub.course.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.cloud.openfeign.SpringQueryMap;

import com.matheustorres.eadhub.course.dtos.CourseUserDTO;
import com.matheustorres.eadhub.course.dtos.ResponsePageDto;
import com.matheustorres.eadhub.course.dtos.UserDTO;

@FeignClient(name = "eadhub-authuser")
public interface AuthUserClient {

    @GetMapping("/users")
    ResponsePageDto<UserDTO> getAllUsersByCourse(
            @RequestParam("courseId") UUID courseId,
            @SpringQueryMap Pageable pageable);

    @GetMapping("/users/{userId}")
    UserDTO getUserById(@PathVariable("userId") UUID userId);

    @PostMapping("/users/{userId}/courses/subscription")
    void saveSubscriptionUserInCourse(@PathVariable("userId") UUID userId, @RequestBody CourseUserDTO courseUserDTO);
}
