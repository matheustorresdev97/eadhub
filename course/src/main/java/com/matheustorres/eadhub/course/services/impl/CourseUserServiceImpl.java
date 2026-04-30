package com.matheustorres.eadhub.course.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matheustorres.eadhub.course.clients.AuthUserClient;
import com.matheustorres.eadhub.course.domain.models.CourseUser;
import com.matheustorres.eadhub.course.dtos.CourseUserDTO;
import com.matheustorres.eadhub.course.repositories.CourseUserRepository;
import com.matheustorres.eadhub.course.services.CourseUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseUserRepository courseUserRepository;

    private final AuthUserClient authUserClient;

    @Override
    public boolean existsByCourseAndUserId(UUID courseId, UUID userId) {
        return courseUserRepository.existsByCourseCourseIdAndUserId(courseId, userId);
    }

    @Override
    public CourseUser save(CourseUser courseUser) {
        return courseUserRepository.save(courseUser);
    }

    @Transactional
    @Override
    public CourseUser saveAndSendSubscriptionUserInCourse(CourseUser courseUser) {
        courseUser = save(courseUser);

        var courseUserDTO = new CourseUserDTO(courseUser.getCourse().getCourseId(), courseUser.getUserId());

        authUserClient.saveSubscriptionUserInCourse(courseUser.getUserId(), courseUserDTO);
        return courseUser;
    }

}
