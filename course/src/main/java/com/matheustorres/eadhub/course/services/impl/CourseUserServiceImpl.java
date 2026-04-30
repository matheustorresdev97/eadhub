package com.matheustorres.eadhub.course.services.impl;

import org.springframework.stereotype.Service;

import com.matheustorres.eadhub.course.repositories.CourseUserRepository;
import com.matheustorres.eadhub.course.services.CourseUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseUserRepository courseUserRepository;



}
