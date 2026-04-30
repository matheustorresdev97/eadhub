package com.matheustorres.eadhub.course.services;

import java.util.UUID;

import com.matheustorres.eadhub.course.domain.models.CourseUser;

public interface CourseUserService {
    boolean existsByCourseAndUserId(UUID courseId, UUID userId);

    CourseUser save(CourseUser courseUserModel);

    CourseUser saveAndSendSubscriptionUserInCourse(CourseUser courseUserModel);

    boolean existsByUserId(UUID userId);

    void deleteCourseUserByUser(UUID userId);
}
