package com.matheustorres.eadhub.authuser.services;

import java.util.UUID;

import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.domain.models.UserCourse;

public interface UserCourseService {
    boolean existsByUserAndCourseId(User user, UUID courseId);

    UserCourse save(UserCourse userCourse);

    boolean existsByCourseId(UUID courseId);

    void deleteUserCourseByCourse(UUID courseId);
}
