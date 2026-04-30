package com.matheustorres.eadhub.authuser.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.domain.models.UserCourse;
import com.matheustorres.eadhub.authuser.repositories.UserCourseRepository;
import com.matheustorres.eadhub.authuser.services.UserCourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserCourseServiceImpl implements UserCourseService {

    private final UserCourseRepository userCourseRepository;

    @Override
    public boolean existsByUserAndCourseId(User user, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(user, courseId);
    }

    @Override
    public UserCourse save(UserCourse userCourse) {
        return userCourseRepository.save(userCourse);
    }
}
