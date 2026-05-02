package com.matheustorres.eadhub.authuser.clients;

import java.util.UUID;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.matheustorres.eadhub.authuser.dtos.ResponsePageDto;
import com.matheustorres.eadhub.authuser.dtos.CourseDTO;

import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "eadhub-course")
public interface CourseClient {

    @CircuitBreaker(name = "circuitBreakerInstance")
    @GetMapping("/courses")
    ResponsePageDto<CourseDTO> getAllCoursesByUser(
            @RequestParam("userId") UUID userId,
            @SpringQueryMap Pageable pageable,
            @RequestHeader("Authorization") String token);
}
