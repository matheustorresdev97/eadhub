package com.matheustorres.eadhub.authuser.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.domain.models.UserCourse;

import feign.Param;

public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {

    boolean existsByUserAndCourseId(User user, UUID courseId);

    @Query(value = "SELECT * FROM tb_users_courses WHERE user_user_id = :userId", nativeQuery = true)
    List<UserCourse> findAllUserCourseIntoUser(@Param("userId") UUID userId);

    boolean existsByCourseId(UUID courseId);

    void deleteAllByCourseId(UUID courseId);

}
