package com.matheustorres.eadhub.authuser.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.domain.models.UserCourse;

public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {

    boolean existsByUserAndCourseId(User user, UUID courseId);

}
