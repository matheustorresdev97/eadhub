package com.matheustorres.eadhub.course.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.matheustorres.eadhub.course.domain.models.CourseUser;
import com.matheustorres.eadhub.course.repositories.CourseUserRepository;
import com.matheustorres.eadhub.course.services.CourseUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseUserRepository courseUserRepository;

    @Override
    public boolean existsByCourseAndUserId(UUID courseId, UUID userId) {
                return courseUserRepository.existsByCourseAndUserId(courseId, userId);
    }

    @Override
    public CourseUser save(CourseUser courseUser) {
        return courseUserRepository.save(courseUser);        
    }



}
