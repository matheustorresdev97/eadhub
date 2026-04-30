package com.matheustorres.eadhub.course.services.impl;

import org.springframework.stereotype.Service;
import com.matheustorres.eadhub.course.repositories.CourseRepository;
import com.matheustorres.eadhub.course.services.CourseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
}
