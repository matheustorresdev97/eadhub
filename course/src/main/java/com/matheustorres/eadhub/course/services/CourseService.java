package com.matheustorres.eadhub.course.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.matheustorres.eadhub.course.domain.models.Course;
import com.matheustorres.eadhub.course.dtos.CourseDTO;

public interface CourseService {
    boolean existsByCourseId(UUID courseId);

    void delete(Course course);

    Page<Course> findAll(Specification<Course> spec, Pageable pageable);

    Optional<Course> findById(UUID courseId);

    Course save(CourseDTO courseDto);

    Course update(UUID courseId, CourseDTO courseDto);
}
