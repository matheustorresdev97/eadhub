package com.matheustorres.eadhub.course.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matheustorres.eadhub.course.domain.models.CourseUser;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {
    boolean existsByCourseCourseIdAndUserId(UUID courseId, UUID userId);

    @Query(value = "SELECT * FROM tb_courses_users where course_course_id = :courseId", nativeQuery = true)
    List<CourseUser> findAllCourseUserIntoCourse(@Param("courseId") UUID courseId);

    void deleteAllByUserId(UUID userId);

    boolean existsByUserId(UUID userId);
}
