package com.matheustorres.eadhub.course.services.impl;

import org.springframework.stereotype.Service;
import com.matheustorres.eadhub.course.repositories.LessonRepository;
import com.matheustorres.eadhub.course.services.LessonService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
}
